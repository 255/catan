package server.debug;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Created by Wyatt on 11/12/2014.
 */
public class SwaggerHandler {
    public abstract static class BaseFile implements HttpHandler {
        private static Logger LOGGER = Logger.getLogger("catanserver");

        public BaseFile(String rootPath){ this.rootPath = rootPath; }
        protected String rootPath;
        protected String getRequestPath(HttpExchange exchange){
            return exchange.getRequestURI().getPath().substring(1);
        }
        protected void sendFile(HttpExchange exchange, String filepath) throws IOException{
            try {
                LOGGER.log(Level.FINE, "Requesting " + filepath);
                byte[] response = FileUtils.readFile(filepath);
                ArrayList<String> mimetypes = new ArrayList<String>();
                mimetypes.add(FileUtils.getMimeType(filepath));
                exchange.getResponseHeaders().put("Content­type", mimetypes);

                exchange.sendResponseHeaders(200,response.length);
                OutputStream os = exchange.getResponseBody();
                os.write(response);
                os.close();
            } catch (IOException ioe){
                LOGGER.log(Level.SEVERE, "Failed to retrieve " + filepath);
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, -1);
                OutputStream os = exchange.getResponseBody();
                os.close();
                System.out.println("Couldn't find the file " + new
                        File(filepath).getAbsolutePath());
            }
        }
    }
    // get the file from the system
    public static class BasicFile extends BaseFile {
        public BasicFile(String rootPath) { super(rootPath); }
        public void handle(HttpExchange exchange) throws IOException {
            String filepath = this.rootPath + this.getRequestPath(exchange);
            this.sendFile(exchange, filepath);
        }
    }
    // appends ".json" to the request before getting the proper file from the file system
    public static class JSONAppender extends BaseFile{
        public JSONAppender(String rootPath){ super(rootPath); }
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            System.out.println( this.rootPath + " ___ " + this.getRequestPath(exchange));
            this.sendFile(exchange, this.rootPath + this.getRequestPath(exchange) + ".json");
        }
    }
}
