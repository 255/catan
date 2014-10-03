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
    private int m_version;
    private IPlayer m_winner;

    public Game(GameState state, IPlayer currentPlayer, IPlayer localPlayer, List<IPlayer> players, IResourceBank resourceBank,
                ICatanMap map, IPlayer longestRoad, IPlayer largestArmy, ITradeOffer tradeOffer, ILog gameplayLog, ILog chatHistory) {
        assert state != null && currentPlayer != null && localPlayer != null && players != null && resourceBank != null
                && map != null && longestRoad != null && largestArmy != null && tradeOffer != null && gameplayLog != null && chatHistory != null;
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
        m_version = -1;
        m_winner = null;
    }

    @Override
    public IDevCardHand getDevCards() {
        return m_devCards;
    }

    @Override
    public void setDevCards(IDevCardHand devCards) {
        assert devCards != null;
        this.m_devCards = devCards;
    }

    @Override
    public GameState getGameState() {
        return m_state;
    }

    @Override
    public void setGameState(GameState state) {
        assert state != null;
        m_state = state;
    }

    @Override
    public IPlayer getCurrentPlayer() {
        return m_currentPlayer;
    }

    @Override
    public void setCurrentPlayer(IPlayer currentPlayer) {
        assert currentPlayer != null;
        m_currentPlayer = currentPlayer;
    }

    @Override
    public IPlayer getLocalPlayer() {
        return m_localPlayer;
    }

    @Override
    public void setLocalPlayer(IPlayer localPlayer) {
        assert localPlayer != null;
        m_localPlayer = localPlayer;
    }

    @Override
    public List<IPlayer> getPlayers() {
        return m_players;
    }

    @Override
    public void setPlayers(List<IPlayer> players) {
        assert players != null;
        m_players = players;
    }

    /**
     * Get the available resources.
     *
     * @return the available resources
     */
    @Override
    public IResourceBank getResourceBank() {
        return m_resourceBank;
    }

    @Override
    public void setResourceBank(IResourceBank bank) {
        assert bank != null;
        m_resourceBank = bank;
    }

    @Override
    public ICatanMap getMap() {
        return m_map;
    }

    @Override
    public void setMap(ICatanMap map) {
        assert map != null;
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
    public IPlayer getLargestArmy() {
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
        assert tradeOffer != null;
        m_tradeOffer = tradeOffer;
    }

    @Override
    public ILog getGameplayLog() {
        return m_gameplayLog;
    }

    @Override
    public void setGameplayLog(ILog gameplayLog) {
        assert gameplayLog != null;
        m_gameplayLog = gameplayLog;
    }

    @Override
    public ILog getChatHistory() {
        return m_chatHistory;
    }

    @Override
    public void setChatHistory(ILog chatHistory) {
        assert chatHistory != null;
        m_chatHistory = chatHistory;
    }

    @Override
    public int getVersion(int version) {
        return m_version;
    }

    @Override
    public void setVersion(int version) {
        m_version = version;
    }

    @Override
    public IPlayer getWinner() {
        return m_winner;
    }

    @Override
    public void setWinner(IPlayer winner) {
        assert winner != null;
        m_winner = winner;
    }
}
