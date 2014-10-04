package client.network;

import java.util.List;

/**
 * @author StevenBarnett
 */
public class GameInfo implements IGameInfo {

    private int m_gameIndex;
    private String m_gameName;
    private List<String> m_listOfPlayers;
    private boolean m_isFull;

    public GameInfo(int gameIndex, String gameName, List<String> listOfPlayers) {
        m_gameIndex = gameIndex;
        m_gameName = gameName;
        m_listOfPlayers = listOfPlayers;
        m_isFull = m_listOfPlayers.size() >= 4;
    }

    @Override
    public int gameIndex() {
        return m_gameIndex;
    }

    @Override
    public String gameName() {
        return m_gameName;
    }

    @Override
    public List<String> listOfPlayers() {
        return m_listOfPlayers;
    }

    @Override
    public boolean isFull() {
        return m_isFull;
    }
}
