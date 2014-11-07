package server.handler;

import java.io.*;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import server.ServerException;
import shared.model.Game;
import shared.model.GameSerializer;

/**
 * A superclass for all request handlers. All each handler does is make the
 * calls to the model and specify the data types.
 *
 * @param <ReqType> the type of data being sent in the client's HTTP request
 * @param <RespType> the type of data sent back to the client in response
 */
abstract class AbstractRequestHandler<ReqType, RespType> implements HttpHandler {
	private static Logger logger = Logger.getLogger("catanserver");

    private static Gson gson = new GsonBuilder().registerTypeAdapter(Game.class, new GameSerializer()).create();

    private Class<ReqType> m_reqClass;

    /**
     * Setup a new RequestHandler.
     */
    public AbstractRequestHandler(Class<ReqType> reqClass) {
        m_reqClass = reqClass;
    }

	/**
	 * Extract the data from the request and return the result.
	 * @param requestData the data from the HTTP request
	 * @return the data to return to the requester
	 * @throws server.ServerException if there was an error
	 * in which case handle() sends back an empty error (500) response
	 */
	public abstract RespType exchangeData(ReqType requestData) throws ServerException;

    /**
     * Examine the cookies sent by the client and extract information as needed.
     * @param cookies the list of cookies the client has set
     * @param requestData the request sent by the client (in case cookie info should be attached to it)
     * @throws IOException
     */
    protected void processRequestCookies(List<HttpCookie> cookies, ReqType requestData) throws IOException {}

    /**
     * Generate a response given the responseData that was generated.
     * Some handlers set cookies but send no response body; some don't set cookies but send data.
     * @param exch
     * @param responseData
     * @throws IOException
     */
    protected void generateResponse(HttpExchange exch, RespType responseData) throws IOException {
        try (OutputStreamWriter responseBody = new OutputStreamWriter(exch.getResponseBody())) {
            // if a valid response was generated, send it back
            if (responseData != null) {
                gson.toJson(responseData, responseBody);
                exch.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            } else {
                exch.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            }
        }
    }

	/**
	 * Handle an http request
	 * @throws IOException 
	 */
	public void handle(HttpExchange exch) throws IOException {
		logger.entering("server.RequestHandler", "handle");
		
		logger.info("Received HTTP request for " + this.getClass().getSimpleName()
			+ " from " + exch.getRemoteAddress() + '.'); 

		try (InputStreamReader requestBody = new InputStreamReader(exch.getRequestBody())) {
            ReqType reqData = gson.fromJson(requestBody, m_reqClass);

            processRequestCookies(HttpCookie.parse(exch.getRequestHeaders().get("Cookie").toString()), reqData);

			RespType respData = exchangeData(reqData);

            generateResponse(exch, respData);

			logger.fine("Responding to request with: " + respData);
		}
        catch (ServerException e) {
			logger.log(Level.WARNING, "Failed to generate a response to the HTTP request.", e);
			try {
				exch.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			} catch (IOException e2) {
				logger.log(Level.WARNING, "An HTTP error occurred.", e2);
				throw e2;
			}
		}
        catch (IOException e) {
			logger.log(Level.WARNING, "An HTTP error occurred.", e);
			throw e;
		}
        finally {
            logger.exiting("server.RequestHandler", "handle");
        }
	}

    /**
     * Parse a userID cookie.
     * @param cookie the cookie's body
     * @return the user ID
     * @throws IOException
     */
    protected static int readUserID(String cookie) throws IOException {
        try (JsonReader reader = new JsonReader(new StringReader(cookie))) {
            reader.beginObject();

            while (reader.hasNext()) {
                String name = reader.nextName();

                if (name.equalsIgnoreCase("playerID")) {
                    return reader.nextInt();
                }
                else {
                    reader.skipValue();
                }
            }

            reader.endObject();

            throw new IOException("Player ID or name not found in cookie.");
        }
    }
}
