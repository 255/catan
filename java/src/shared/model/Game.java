package shared.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Created by jeffreybacon on 9/25/14.
 */
public class Game extends Observable implements IGame {
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

    private Game() {
        reset();
    }

    private static Game m_game = null;

    public static Game getInstance() {
        if (m_game == null) {
            m_game = new Game();
        }

        return m_game;
    }

    @Override
    public void reset() {
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
    public void newGame() {
        m_game = null;
    }

    /**
     * Get whether the game has been initialized yet.
     * This must be called before initializing from the model.
     * @return true if the game is ready-to-go, false if not
     */    @Override
    public boolean isNotInitialized() {
        // only tradeOffer, winner, longestRoad, and largestArmy can be null
        return (
                m_state == null
             || m_currentPlayer == null
             || m_localPlayer == null
             || m_players == null
             || m_resourceBank == null
             || m_devCards == null
             || m_map == null
             || m_gameplayLog == null
             || m_chatHistory == null
             || m_version == -1
        );
    }

    @Override
    public IDevCardHand getDevCards() {
        return m_devCards;
    }

    @Override
    public void setDevCards(IDevCardHand devCards) {
        assert devCards != null;
        this.m_devCards = devCards;
        setChanged();
    }

    @Override
    public GameState getGameState() {
        return m_state;
    }

    @Override
    public void setGameState(GameState state) {
        assert state != null;
        m_state = state;
        setChanged();
    }

    @Override
    public IPlayer getCurrentPlayer() {
        return m_currentPlayer;
    }

    @Override
    public void setCurrentPlayer(IPlayer currentPlayer) {
        assert currentPlayer != null;
        m_currentPlayer = currentPlayer;
        setChanged();
    }

    @Override
    public IPlayer getLocalPlayer() {
        return m_localPlayer;
    }

    @Override
    public void setLocalPlayer(IPlayer localPlayer) {
        assert localPlayer != null;
        m_localPlayer = localPlayer;
        setChanged();
    }

    @Override
    public List<IPlayer> getPlayers() {
        return m_players;
    }

    @Override
    public void setPlayers(List<IPlayer> players) {
        assert players != null;
        m_players = players;
        setChanged();
    }

    /**
     * Get the list of players in turn order excluding the local player.
     *
     * @return the list of non-local players in turn order
     */
    @Override
    public List<IPlayer> getNonLocalPlayers() {
        List<IPlayer> nonLocalPlayers = new ArrayList<>(m_players.size() - 1);

        for (IPlayer player : m_players) {
            if (!player.equals(m_localPlayer)) {
                nonLocalPlayers.add(player);
            }
        }

        assert nonLocalPlayers.size() == 3 : "There should be 3 non-local players, but there are not!";

        return nonLocalPlayers;
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
        setChanged();
    }

    @Override
    public ICatanMap getMap() {
        return m_map;
    }

    @Override
    public void setMap(ICatanMap map) {
        assert map != null;
        m_map = map;
        setChanged();
    }

    @Override
    public IPlayer getLongestRoad() {
        return m_longestRoad;
    }

    @Override
    public void setLongestRoad(IPlayer longestRoad) {
        m_longestRoad = longestRoad;
        setChanged();
    }

    @Override
    public IPlayer getLargestArmy() {
        return m_largestArmy;
    }

    @Override
    public void setLargestArmy(IPlayer largestArmy) {
        m_largestArmy = largestArmy;
        setChanged();
    }

    @Override
    public ITradeOffer getTradeOffer() {
        return m_tradeOffer;
    }

    @Override
    public void setTradeOffer(ITradeOffer tradeOffer) {
        assert tradeOffer != null;
        m_tradeOffer = tradeOffer;
        setChanged();
    }

    @Override
    public ILog getGameplayLog() {
        return m_gameplayLog;
    }

    @Override
    public void setGameplayLog(ILog gameplayLog) {
        assert gameplayLog != null;
        m_gameplayLog = gameplayLog;
        setChanged();
    }

    @Override
    public ILog getChatHistory() {
        return m_chatHistory;
    }

    @Override
    public void setChatHistory(ILog chatHistory) {
        assert chatHistory != null;
        m_chatHistory = chatHistory;
        setChanged();
    }

    @Override
    public int getVersion(int version) {
        return m_version;
    }

    @Override
    public void setVersion(int version) {
        m_version = version;
        setChanged();
    }

    @Override
    public IPlayer getWinner() {
        return m_winner;
    }

    @Override
    public void setWinner(IPlayer winner) {
        assert winner != null;
        m_winner = winner;
        setChanged();
    }

    /**
     * Return true if it is the local player's turn.
     *
     * @return true if it is the local player's turn
     */
    @Override
    public boolean isLocalPlayersTurn() {
        return m_currentPlayer.equals(m_localPlayer);
    }

    @Override
    public boolean localPlayerIsOfferingTrade() {
        ITradeOffer tradeOffer = Game.getInstance().getTradeOffer();

        return tradeOffer != null && m_localPlayer.equals(tradeOffer.getSender());
    }

    @Override
    public boolean localPlayerIsBeingOfferedTrade() {
        ITradeOffer tradeOffer = Game.getInstance().getTradeOffer();

        return tradeOffer != null && m_localPlayer.equals(tradeOffer.getReceiver());
    }

    /**
     * Get whether it is the local player's turn and game state is playing, so the player can play cards, etc.
     * @return true / false
     */
    @Override
    public boolean localPlayerIsPlaying() {
        return localPlayerAndGameState(GameState.PLAYING);
    }

    @Override
    public boolean localPlayerIsDiscarding() {
        return (m_state == GameState.DISCARDING) && m_localPlayer.needsToDiscard();
    }

    @Override
    public boolean localPlayerIsRolling() {
        return localPlayerAndGameState(GameState.ROLLING);
    }

    @Override
    public boolean localPlayerIsRobbing() {
        return localPlayerAndGameState(GameState.ROBBING);
    }

    @Override
    public boolean localPlayerIsPlacingInitialPieces() {
        return gameHasStarted()
               && (localPlayerAndGameState(GameState.FIRST_ROUND) || localPlayerAndGameState(GameState.SECOND_ROUND));
    }

    @Override
    public boolean gameHasStarted() {
        return m_players.size() == CatanConstants.NUM_PLAYERS;
    }

    private boolean localPlayerAndGameState(GameState state) {
        return m_localPlayer.equals(m_currentPlayer) && m_state == state;
    }

    /**
     * The ModelInitializer needs to tell the Game object when it is done updating.
     */
    @Override
    public void updateComplete() {
        assert !isNotInitialized();
        notifyObservers();
    }
}
