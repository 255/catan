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
    private IBank m_bank;
    private IMap m_map;
    private IPlayer m_longestRoad;
    private IPlayer m_largestArmy;
    private ITradeOffer m_tradeOffer;

    public Game(GameState state, IPlayer currentPlayer, IPlayer localPlayer, List<IPlayer> players, IBank bank,
                IMap map, IPlayer longestRoad, IPlayer largestArmy, ITradeOffer tradeOffer) {
        setGameState(state);
        setCurrentPlayer(currentPlayer);
        setLocalPlayer(localPlayer);
        setPlayers(players);
        setBank(bank);
        setMap(map);
        setLongestRoad(longestRoad);
        setLargestArmy(largestArmy);
        setTradeOffer(tradeOffer);
    }

    @Override
    public GameState getGameState() {
        return m_state;
    }

    private void setGameState(GameState state) {
        m_state = state;
    }

    @Override
    public IPlayer getCurrentPlayer() {
        return m_currentPlayer;
    }

    private void setCurrentPlayer(IPlayer currentPlayer) {
        m_currentPlayer = currentPlayer;
    }

    @Override
    public IPlayer getLocalPlayer() {
        return m_localPlayer;
    }

    private void setLocalPlayer(IPlayer localPlayer) {
        m_localPlayer = localPlayer;
    }

    @Override
    public List<IPlayer> getPlayers() {
        return m_players;
    }

    private void setPlayers(List<IPlayer> players) {
        m_players = players;
    }

    @Override
    public IResourceBundle getResourceBank() {
        return m_bank.getResources();
    }

    private void setBank(IBank bank) {
        m_bank = bank;
    }

    @Override
    public IMap getMap() {
        return m_map;
    }

    private void setMap(IMap map) {
        m_map = map;
    }

    public IPlayer getLongestRoad() {
        return m_longestRoad;
    }

    private void setLongestRoad(IPlayer longestRoad) {
        m_longestRoad = longestRoad;
    }

    public IPlayer getLargestArmy(IPlayer largestArmy) {
        return m_largestArmy;
    }

    private void setLargestArmy(IPlayer largestArmy) {
        m_largestArmy = largestArmy;
    }

    public ITradeOffer getTradeOffer() {
        return m_tradeOffer;
    }

    private void setTradeOffer(ITradeOffer tradeOffer) {
        m_tradeOffer = tradeOffer;
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
