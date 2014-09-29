package client.network;

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
        String request =
                "{" +
                        "\"username\":" + username + "," +
                        "\"password\":" + password + "," +
                "}";

        String response = m_httpCommunicator.post("/user/login", request);

        return (response != null ? true : false);
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
    public String joinGame(int playerId, String playerColor) throws NetworkException {
        String request =
                "{" +
                        "\"id\":" + playerId + "," +
                        "\"color\":" + playerColor + "," +
                "}";
        String response = m_httpCommunicator.post("/games/join", request);

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
