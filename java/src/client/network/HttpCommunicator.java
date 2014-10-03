package client.network;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author StevenBarnett
 */
public class HttpCommunicator implements IHttpCommunicator {

    private static String SERVER_HOST = "localhost";
    private static int SERVER_PORT = 8081;
    private static String URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;
    private static String HTTP_POST = "POST";
    private static String HTTP_GET = "GET";

    private String m_userCookie = null;
    private String m_gameIdCookie = null;
    private int m_playerId;

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
            URL url = new URL(URL_PREFIX + commandName);
            connection = (HttpURLConnection)url.openConnection();

            if (commandName.equals("/games/join")) {
                connection.setRequestProperty("Cookie", "catan.user=" + m_userCookie);
            }
            if (m_userCookie != null && m_gameIdCookie != null) {
                connection.setRequestProperty("Cookie", "catan.user=" + m_userCookie + "; " + "catan.game=" + m_gameIdCookie);
            }
            connection.setRequestMethod((HTTP_POST));
            connection.setDoOutput(true);
            connection.connect();
            connection.getOutputStream().write(postData.getBytes());
            connection.getOutputStream().close();
            System.out.println(connection.getResponseCode());

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                byte[] buffer = new byte[inputStream.available()];
                inputStream.read(buffer);
                if (commandName.equals("/user/login")) {
                    String cookie = connection.getHeaderFields().get("Set-cookie").get(0);
                    cookie = cookie.substring(11, cookie.length() - 8);
                    m_userCookie = cookie;
                    String jsonCookie = URLDecoder.decode(cookie, "UTF-8");
                    JsonReader reader = new JsonReader(new StringReader(jsonCookie));
                    m_playerId = readPlayerId(reader);
                }
                if (commandName.equals("/games/join")) {
                    String cookie = connection.getHeaderFields().get("Set-cookie").get(0);
                    cookie = cookie.substring(11, cookie.length() - 8);
                    m_gameIdCookie = cookie;
                }
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

    @Override
    public int getPlayerId() {
        return m_playerId;
    }

    @Override
    public int getGameIdCookie() {
        return Integer.parseInt(m_gameIdCookie);
    }

    private int readPlayerId(JsonReader reader) throws IOException {
        reader.beginObject();

        while (reader.hasNext()) {
            String name = reader.nextName();

            if (name.equals("playerID")) {
                return reader.nextInt();
            } else {
                reader.skipValue();
            }
        }

        return -1;
    }
}