package shared.model;

import java.util.List;

/**
 * Created by jeffreybacon on 9/25/14.
 */
public class Game implements IGame {
    private List<IPlayer> players;
    private IPlayer currentPlayer;
    private GameState state;
    private IPlayer longestRoad;
    private IPlayer largestArmy;

    @Override
    public GameState getGameState() {
        return state;
    }

    @Override
    public IPlayer getCurrentPlayer() {
        return currentPlayer;
    }

    @Override
    public IPlayer getLocalPlayer() {
        return null;
    }

    @Override
    public List<IPlayer> getPlayers() {
        return players;
    }

    @Override
    public IResourceBundle getResourceBank() {
        return null;
    }

    @Override
    public IMap getMap() {
        return null;
    }

    @Override
    public List<ILogMessage> getGameplayLog() {
        return null;
    }

    @Override
    public List<ILogMessage> getChatHistory() {
        return null;
    }
}
