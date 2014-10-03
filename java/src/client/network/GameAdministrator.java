package client.network;

import com.google.gson.JsonObject;
import client.network.NetworkException;

import java.util.List;

/**
 * @author StevenBarnett
 */
public class GameAdministrator implements IGameAdministrator {

    private IHttpCommunicator m_httpCommunicator;

    public GameAdministrator(IHttpCommunicator httpCommunicator) {
        m_httpCommunicator = httpCommunicator;
    }

    @Override
    public boolean login(String username, String password) {
        return false;
    }

    @Override
    public boolean register(String username, String password) {
        return false;
    }

    @Override
    public List<IGameInfo> listGames() {
        return null;
    }

    @Override
    public String joinGame() {
        return null;
    }

    @Override
    public String createGame() {
        return null;
    }
}
