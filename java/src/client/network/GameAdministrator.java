package client.network;

import java.util.List;

/**
 * @author StevenBarnett
 */
public class GameAdministrator implements IGameAdministrator {
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
