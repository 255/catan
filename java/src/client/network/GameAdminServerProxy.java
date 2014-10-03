package client.network;

import com.google.gson.JsonObject;

import java.util.List;

/**
 * @author StevenBarnett
 */
public class GameAdminServerProxy implements IGameAdminServerProxy {

    private IHttpCommunicator m_httpCommunicator;

    public GameAdminServerProxy(IHttpCommunicator httpCommunicator) {
        m_httpCommunicator = httpCommunicator;
    }

    @Override
    public boolean login(String username, String password) throws NetworkException {
        JsonObject json = new JsonObject();
        json.addProperty("username", username);
        json.addProperty("password", password);
        String response = m_httpCommunicator.post("/user/login", json.toString());

        return response != null;
    }

    @Override
    public boolean register(String username, String password) throws NetworkException {
        String request =
                "{" +
                        "\"username\":" + username + "," +
                        "\"password\":" + password + "," +
                "}";

        String response = m_httpCommunicator.post("/user/register", "");

        return (response != null ? true : false);
    }

    @Override
    public String listGames() throws NetworkException {
        String response = m_httpCommunicator.get("/games/list");
        return response;
    }

    @Override
    public String createGame(boolean randTiles, boolean randNum, boolean randPorts, String name) throws NetworkException {
        String request =
                "{" +
                        "\"randomTiles\":" + randTiles + "," +
                        "\"randomNumbers\":" + randNum + "," +
                        "\"randomPorts\":" + randPorts + "," +
                        "\"name\":" + name +
                "}";

        String response = m_httpCommunicator.post("/games/create", request);
        return response;
    }

    @Override
    public String joinGame(int gameId, String playerColor) throws NetworkException {
        JsonObject json = new JsonObject();
        json.addProperty("id", gameId);
        json.addProperty("color", "orange");
        String response = m_httpCommunicator.post("/games/join", json.toString());

        return response;
    }

    @Override
    public String listAI() throws NetworkException {
        String response = m_httpCommunicator.post("/games/listAI", "");
        return response;
    }

    @Override
    public String addAI(String name) throws NetworkException {
        String request =
                "{" +
                        "\"AIType\":" + name +
                "}";

        String response = m_httpCommunicator.post("/games/addAI", "");

        return response;
    }
}
