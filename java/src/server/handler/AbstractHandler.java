package server.handler;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.MalformedJsonException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import server.command.IllegalCommandException;
import shared.model.ModelException;
import shared.model.serialization.Serializer;

/**
 * A superclass for all request handlers.
 * A concrete handler simply needs to specify the types and the facade method to call.
 * Many aspects of how to respond to the request can be customized by inheriting classes.
 *
 * @param <ReqType> the type of data being sent in the client's HTTP request
 * @param <RespType> the type of data sent back to the client in response
 * @param <FacadeType> the type of the facade that the handler will call with the request data
 */
public abstract class AbstractHandler<ReqType, RespType, FacadeType> implements HttpHandler {
    private static Logger logger = Logger.getLogger("catanserver");

    private Class<ReqType> m_reqClass;
    private FacadeType m_facade; // the class where the request data is forwarded

    /**
     * Get the facade that this handler should call methods on.
     *
     * @return the appropriate facade
     */
    protected final FacadeType getFacade() {
        return m_facade;
    }

    /**
     * Setup a new Request Handler.
     */
    public AbstractHandler(Class<ReqType> reqClass, FacadeType facade) {
        m_reqClass = reqClass;
        m_facade = facade;
    }

    /**
     * Extract the data from the request and return the result.
     * This is the only method most concrete classes will need to define. Basically, the need to define
     * which facade method to call.
     *
     * @param requestData the data from the HTTP request
     * @return the data to return to the requester
     * @throws MissingCookieException if there was an error
     *                                in which case handle() sends back an error (500) response
     */
    protected abstract RespType exchangeData(ReqType requestData) throws MissingCookieException, IllegalCommandException, ModelException;

    /**
     * Examine the cookies sent by the client and extract information as needed.
     * By default, this method does nothing.
     *
     * @param cookies     the list of cookies the client has set
     * @param requestData the request sent by the client (in case cookie info should be attached to it)
     * @throws IOException
     */
    protected void processRequestCookies(CookieJar cookies, ReqType requestData) throws IOException, MissingCookieException {
        // cookies are not processed by default
    }

    /**
     * Extract the parameters of the request from the HttpExchange object.
     * This is typically extracted from the body of the request using Gson, but this can be overridden
     * (i.e. for getting the game model where the model number is passed in the URL).
     *
     * @param exch the http exchange
     * @return the data that was extracted
     * @throws IOException
     */
    protected ReqType getRequestParameters(HttpExchange exch) throws IOException {
        try (InputStreamReader requestBody = new InputStreamReader(exch.getRequestBody())) {
            return Serializer.instance().fromJson(requestBody, m_reqClass);
        }
    }

    /**
     * Generate a response given the responseData that was generated.
     * Some handlers set cookies but send no response body; some don't set cookies but send data.
     * The default handler uses Gson to deserialize the body.
     *
     * @param exch         the ongoing http exchange
     * @param responseData the response that will be sent to the client, as generated by exchangeData
     * @throws IOException
     * @see #exchangeData
     */
    protected void generateResponse(HttpExchange exch, RespType responseData) throws IOException {
        try (OutputStreamWriter responseBody = new OutputStreamWriter(exch.getResponseBody())) {
            // if a valid response was generated, send it back
            if (responseData != null) {
                exch.getResponseHeaders().add("Content-Type", "application/json");
                Serializer.instance().toJson(responseData, responseBody);
                exch.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            } else {
                exch.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, -1);
            }
        }
    }

    /**
     * Handle an http request.
     *
     * @throws IOException
     */
    @Override
    public final void handle(HttpExchange exch) throws IOException {
        logger.entering("server.RequestHandler", "handle");

        logger.finer("Received HTTP request for " + this.getFacade().getClass().getSimpleName()
                + " on " + this.getClass().getName() + " from " + exch.getRemoteAddress() + '.');

        try {
            ReqType reqData = getRequestParameters(exch);

            if (exch.getRequestHeaders().containsKey("Cookie")) {
                CookieJar cookies = new CookieJar(exch.getRequestHeaders().get("Cookie"));
                processRequestCookies(cookies, reqData);
            }

            RespType respData = exchangeData(reqData);

            generateResponse(exch, respData);

            logger.finer("Responding to request with: " + respData);
        } catch (JsonSyntaxException | MalformedJsonException e) {
            logger.log(Level.INFO, "Received an improperly formatted request.", e);
            sendErrorResponse(exch, HttpURLConnection.HTTP_BAD_REQUEST, e);
        } catch (MissingCookieException e) {
            logger.log(Level.INFO, "Client does not have the correct cookies set to perform the desired action.", e);
            sendErrorResponse(exch, HttpURLConnection.HTTP_UNAUTHORIZED, e);
        } catch (IllegalCommandException | ModelException e) {
            logger.log(Level.WARNING, "Client attempted an illegal command.", e);
            sendErrorResponse(exch, HttpURLConnection.HTTP_BAD_METHOD, e);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "An HTTP error occurred.", e);
            exch.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            logger.exiting("server.RequestHandler", "handle");
        }
    }

    /**
     * write a response body message
     */
    private void sendErrorResponse(HttpExchange exch, int httpResponse, Throwable e) throws IOException {
        try (OutputStreamWriter responseBody = new OutputStreamWriter(exch.getResponseBody())) {
            exch.getResponseHeaders().add("Content-type", "text/plain");
            responseBody.write(e.getMessage());
            exch.sendResponseHeaders(httpResponse, 0);
        }
    }

    /**
     * Parse a userID cookie. Multiple child classes use this.
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

            throw new IOException("Player ID not found in cookie.");
        }
    }
}
