package shared.model;

import java.util.List;

/**
 * Created by jeffreybacon on 9/25/14.
 */
public class Game implements IGame {
    private GameState m_state;
    private IPlayer m_currentPlayer;
    private IPlayer m_localPlayer;
    private List<IPlayer> m_players;
    private IResourceBank m_resourceBank;
    private IDevCardHand m_devCards;
    private ICatanMap m_map;
    private IPlayer m_longestRoad;
    private IPlayer m_largestArmy;
    private ITradeOffer m_tradeOffer;
    private ILog m_gameplayLog;
    private ILog m_chatHistory;

    public Game(GameState state, IPlayer currentPlayer, IPlayer localPlayer, List<IPlayer> players, IResourceBank resourceBank,
                ICatanMap map, IPlayer longestRoad, IPlayer largestArmy, ITradeOffer tradeOffer, ILog gameplayLog, ILog chatHistory) {
        setGameState(state);
        setCurrentPlayer(currentPlayer);
        setLocalPlayer(localPlayer);
        setPlayers(players);
        setResourceBank(resourceBank);
        setMap(map);
        setLongestRoad(longestRoad);
        setLargestArmy(largestArmy);
        setTradeOffer(tradeOffer);
        setGameplayLog(gameplayLog);
        setChatHistory(chatHistory);
    }

    public Game() {
        m_state = null;
        m_currentPlayer = null;
        m_localPlayer = null;
        m_players = null;
        m_resourceBank = null;
        m_devCards = null;
        m_map = null;
        m_longestRoad = null;
        m_largestArmy = null;
        m_tradeOffer = null;
        m_gameplayLog = null;
        m_chatHistory = null;
    }

    @Override
    public GameState getGameState() {
        return m_state;
    }

    @Override
    public void setGameState(GameState state) {
        m_state = state;
    }

    @Override
    public IPlayer getCurrentPlayer() {
        return m_currentPlayer;
    }

    @Override
    public void setCurrentPlayer(IPlayer currentPlayer) {
        m_currentPlayer = currentPlayer;
    }

    @Override
    public IPlayer getLocalPlayer() {
        return m_localPlayer;
    }

    @Override
    public void setLocalPlayer(IPlayer localPlayer) {
        m_localPlayer = localPlayer;
    }

    @Override
    public List<IPlayer> getPlayers() {
        return m_players;
    }

    @Override
    public void setPlayers(List<IPlayer> players) {
        m_players = players;
    }

    @Override
    public IResourceBundle getResourceBank() {
        return m_resourceBank.getResources();
    }

    @Override
    public void setResourceBank(IResourceBank bank) {
        m_resourceBank = bank;
    }

    @Override
    public ICatanMap getMap() {
        return m_map;
    }

    @Override
    public void setMap(ICatanMap map) {
        m_map = map;
    }

    @Override
    public IPlayer getLongestRoad() {
        return m_longestRoad;
    }

    @Override
    public void setLongestRoad(IPlayer longestRoad) {
        m_longestRoad = longestRoad;
    }

    @Override
    public IPlayer getLargestArmy(IPlayer largestArmy) {
        return m_largestArmy;
    }

    @Override
    public void setLargestArmy(IPlayer largestArmy) {
        m_largestArmy = largestArmy;
    }

    @Override
    public ITradeOffer getTradeOffer() {
        return m_tradeOffer;
    }

    @Override
    public void setTradeOffer(ITradeOffer tradeOffer) {
        m_tradeOffer = tradeOffer;
    }

    @Override
    public ILog getGameplayLog() {
        return m_gameplayLog;
    }

    @Override
    public void setGameplayLog(ILog gameplayLog) {
        m_gameplayLog = gameplayLog;
    }

    @Override
    public ILog getChatHistory() {
        return m_chatHistory;
    }

    @Override
    public void setChatHistory(ILog chatHistory) {
        m_chatHistory = chatHistory;
    }
}
