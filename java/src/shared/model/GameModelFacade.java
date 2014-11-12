package shared.model;

import shared.definitions.CatanColor;
import shared.definitions.PortType;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

import java.util.Collection;
import java.util.Set;

/**
 * handles all the manipulations of the game object
 */
public class GameModelFacade implements IGameModelFacade {
    private static GameModelFacade m_theFacade = null;

    // The one and only game pointer
    private IGame m_game;

    /**
     * This is a private constructor that is called only when the GameModelFacade has not been initialized yet
     */
    private GameModelFacade() {
        m_game = new Game();
    }

    /**
     * This static function is called to get the current instance of the GameModelFacade
     *
     * @return the current instance of the GameModelFacade
     */
    public static GameModelFacade instance() {
        if (m_theFacade == null) {
            m_theFacade = new GameModelFacade();
        }
        return m_theFacade;
    }

    @Override
    public IGame getGame() {
        assert m_game != null : "Attempted to get a null Game object!";
        return m_game;
    }

    @Override
    public void setGame(IGame game) {
        m_game = game;
    }

    /**
     * Takes an edge location and determines if a road can be placed on it
     * This does NOT check if the player can afford the road.
     *
     * @param edge the location of the side of a terrain hex
     * @return a boolean value that reports if the user can place a road
     */
    @Override
    public boolean canPlaceRoad(EdgeLocation edge) {
        return m_game.canPlaceRoad(edge);
    }

    /**
     * Takes a vertex location and determines if a settlement can be placed on it
     *
     * @param vertex the location of a corner/intersection of terrain hexes
     * @return a boolean value that reports if the user can place a settlement
     */
    @Override
    public boolean canPlaceSettlement(VertexLocation vertex) {
        return m_game.canPlaceSettlement(vertex);
    }

    /**
     * Takes a vertex location and determines if a city can be placed on it
     *
     * @param vertex the location of a corner/intersection of terrain hexes
     * @return a boolean value that reports if the user can place a city
     */
    @Override
    public boolean canPlaceCity(VertexLocation vertex) {
        return m_game.canPlaceCity(vertex);
    }

    /**
     * Returns the info for the current player
     *
     * @return the PlayerInfo object initialized with all the player card/piece counts
     */
    @Override
    public IPlayer getCurrentPlayer() {
        return m_game.getCurrentPlayer();
    }

    /**
     * Get a list of players around a hex that can be robbed (all with towns except local player)
     *
     * @param location the location being robbed
     * @return a collection of players (may be empty)
     */
    @Override
    public Collection<IPlayer> getRobbablePlayers(HexLocation location) {
        return m_game.getRobbablePlayers(location);
    }

    /**
     * Returns the info for the current player's resource counts
     *
     * @return the ResourceBank object containing the counts for the current player
     */
    @Override
    public IResourceBank getPlayerResources() {
        return m_game.getPlayerResources();
    }

    /**
     * Returns the ports the local player has
     *
     * @return the ports that the local player has
     */
    @Override
    public Set<PortType> getLocalPlayerPorts() {
        return m_game.getLocalPlayerPorts();
    }

    /**
     * Returns the chat history of the game
     *
     * @return the log initialized with all the chat messages
     */
    @Override
    public ILog getChatHistory() {
        return m_game.getChatHistory();
    }

    /**
     * Returns the move/action history of the game
     *
     * @return the log initialized with all the move/action messages
     */
    @Override
    public ILog getMoveHistory() {
        return m_game.getGameplayLog();
    }

    /**
     * Have enough money to buy a city and have place to put it and a piece to use.
     *
     * @return true if can buy city, false if not
     */
    @Override
    public boolean canBuyCity() {
        return m_game.canBuyCity();
    }

    /**
     * Have enough money to buy a road and a piece to use.
     *
     * @return true if can buy road, false if not
     */
    @Override
    public boolean canBuyRoad() {
        return m_game.canBuyRoad();
    }

    /**
     * Have enough money to buy a settlement and a piece to use.
     *
     * @return true if can buy a settlement, false if not
     */
    @Override
    public boolean canBuySettlement() {
        return m_game.canBuySettlement();
    }

    /**
     * Have enough money to buy a dev card and a card is available to buy
     *
     * @return true if can buy
     */
    @Override
    public boolean canBuyDevCard() {
        return m_game.canBuyDevCard();
    }

    /**
     * Return true if the player has enough resources for a trade currently being offered to them.
     *
     * @return true if can accept trade, false if not enough resources (or no trade is offered currently)
     */
    @Override
    public boolean canAcceptTrade() {
        return m_game.canAcceptTrade();
    }

    /**
     * Whether the local player can play any dev cards.
     *
     * @return true if the user can play a dev card
     */
    @Override
    public boolean canPlayDevCard() {
        return m_game.canPlayDevCard();
    }

    /**
     * Get whether the local player can play this specific dev card.
     *
     * @return true if the user can play this card
     */
    @Override
    public boolean canPlayMonopoly(ResourceType resource) {
        return m_game.canPlayMonopoly(resource);
    }

    /**
     * Get whether the local player can play this specific dev card.
     * @param robberDestination new location of the robber
     * @return true if the user can play this card
     */
    @Override
    public boolean canPlaySoldier(HexLocation robberDestination) {
        return m_game.canPlaySoldier(robberDestination);
    }

    /**
     * Get whether the local player can play this specific dev card.
     * NOTE: The preconditions specified in the document seem wrong to me. You need BOTH resources to play the card?
     *       What if there is only one kind of card left.
     * @return true if the user can play this card
     * @param r1 resource
     * @param r2 resource
     */
    @Override
    public boolean canPlayYearOfPlenty(ResourceType r1, ResourceType r2) {
        return m_game.canPlayYearOfPlenty(r1, r2);
    }

    /**
     * Get whether the local player can play this specific dev card.
     *
     * @return true if the user can play this card
     */
    @Override
    public boolean canPlayMonument() {
        return m_game.canPlayMonument();
    }

    /**
     * Get whether the local player can play this specific dev card.
     *
     * @return true if the user can play this card
     * @param edge1 first road
     * @param edge2 second road
     */
    @Override
    public boolean canPlayRoadBuilding(EdgeLocation edge1, EdgeLocation edge2) {
        return m_game.canPlayRoadBuilding(edge1, edge2);
    }

    /**
     * Get the local player's color.
     *
     * @return the local player's color
     */
    @Override
    public CatanColor getLocalColor() {
        return m_game.getLocalColor();
    }

    @Override
    public boolean playerHasLongestRoad(IPlayer player) {
        return m_game.playerHasLongestRoad(player);
    }

    @Override
    public boolean playerHasLargestArmy(IPlayer player) {
        return m_game.playerHasLargestArmy(player);
    }

    @Override
    public boolean isPlayersTurn(IPlayer player) {
        return m_game.isPlayersTurn(player);
    }

    /**
     * Create a new game object.
     */
    @Override
    public void newGame() {
        m_game = new Game();
    }

    // this method is just for determining from the GameState if it is a free round
    @Override
    public boolean isFreeRound() {
        return m_game.isFreeRound();
    }
}
