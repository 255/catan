package client.network;

import com.google.gson.JsonObject;
import client.network.NetworkException;
import shared.definitions.CatanColor;

import java.util.List;

/**
 * @author StevenBarnett
 */
public class GameAdministrator implements IGameAdministrator {

    private IGameAdminServerProxy m_gameAdminServerProxy;

    public GameAdministrator(IGameAdminServerProxy gameAdminServerProxy) {
        m_gameAdminServerProxy = gameAdminServerProxy;
    }

    @Override
    public boolean login(String username, String password) throws NetworkException {
        return m_gameAdminServerProxy.login(username, password);
    }

    @Override
    public boolean register(String username, String password) throws NetworkException {
        return m_gameAdminServerProxy.register(username, password);
    }

    @Override
    public List<IGameInfo> listGames() throws NetworkException {
        return null;//m_gameAdminServerProxy.listGames();
    }

    @Override
    public boolean joinGame(int gameIndex, CatanColor playerColor) throws NetworkException {
        return m_gameAdminServerProxy.joinGame(gameIndex, playerColor);
    }

    @Override
    public String createGame(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String gameName) throws NetworkException {

        return m_gameAdminServerProxy.createGame(randomTiles, randomNumbers, randomPorts, gameName);
    }
}
