package client.network;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author StevenBarnett
 */
public class HttpCommunicator implements IHttpCommunicator {

    private static String SERVER_HOST = "localhost";
    private static int SERVER_PORT = 8081;
    private static String URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;
    private static String HTTP_POST = "POST";
    private static String HTTP_GET = "GET";

    @Override
    public String get(String commandName) throws NetworkException {
        assert (commandName != null & commandName.length() > 0);

        String response = null;
        HttpURLConnection connection = null;

        try {
            URL url = new URL (URL_PREFIX + "/" + commandName);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod((HTTP_GET));
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                if (connection.getContentLength() == 0) {
                    InputStream inputStream = connection.getInputStream();
                    byte[] buffer = new byte[inputStream.available()];
                    inputStream.read(buffer);
                    connection.getInputStream().close();
                    response = new String(buffer);
                }
            } else {
                throw new NetworkException();
            }

        } catch (MalformedURLException ex1) {

        } catch (IOException ex2) {

        } finally {
            connection.disconnect();
        }

        return response;
    }

    @Override
    public String post(String commandName, String postData) throws NetworkException {
        assert (commandName != null && commandName.length() > 0 && postData != null);

        String response = null;
        HttpURLConnection connection = null;

        try {
            URL url = new URL(URL_PREFIX + "/" + commandName);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod((HTTP_POST));
            connection.setDoOutput(true);
            connection.connect();
            connection.getOutputStream().write(postData.getBytes());

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                byte[] buffer = new byte[inputStream.available()];
                inputStream.read(buffer);
                connection.getInputStream().close();
                response = new String(buffer);
            } else {
                throw new NetworkException();
            }

        } catch (MalformedURLException ex1) {

        } catch (IOException ex2) {

        } finally {
            connection.disconnect();
        }
        return response;
    }
}