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
 * This is the interface for the GameModelFacade which handles all the manipulations of the game object
 */
public interface IGameModelFacade {
    /**
     * Set the game object the Facade uses
     * @param game
     */
    public void setGame(Game game);

    /**
     * Takes an edge location and determines if a road can be placed on it
     *
     * @param edge the location of the side of a terrain hex
     *
     * @return a boolean value that reports if the user can place a road
     */
    public boolean canPlaceRoad(EdgeLocation edge);

    /**
     * Takes a vertex location and determines if a settlement can be placed on it
     *
     * @param vertex the location of a corner/intersection of terrain hexes
     *
     * @return a boolean value that reports if the user can place a settlement
     */
    public boolean canPlaceSettlement(VertexLocation vertex);

    /**
     * Takes a vertex location and determines if a city can be placed on it
     *
     * @param vertex the location of a corner/intersection of terrain hexes
     *
     * @return a boolean value that reports if the user can place a city at the specified location
     */
    public boolean canPlaceCity(VertexLocation vertex);

    /**
     * Have enough money to buy a city and have place to put it and a piece to use.
     * @return true if can buy city, false if not
     */
    public boolean canBuyCity();

    /**
     * Have enough money to buy a road and a piece to use.
     * @return true if can buy road, false if not
     */
    public boolean canBuyRoad();

    /**
     * Have enough money to buy a settlement and a piece to use.
     * @return true if can buy a settlement, false if not
     */
    public boolean canBuySettlement();

    /**
     * Have enough money to buy a dev card and a card is available to buy
     * @return true if can buy
     */
    public boolean canBuyDevCard();

    /**
     * Return true if the player has enough resources for a trade currently being offered to them.
     * @return true if can accept trade, false if not enough resources (or no trade is offered currently)
     */
    public boolean canAcceptTrade();

    /**
     * Whether the local player can play any dev cards.
     * @return true if the user can play a dev card
     */
    public boolean canPlayDevCard();

    /**
     * Get whether the local player can play this specific dev card.
     * @return true if the user can play this card
     */
    public boolean canPlayMonopoly(ResourceType resource);

    /**
     * Get whether the local player can play this specific dev card.
     * @return true if the user can play this card
     */
    public boolean canPlaySoldier(HexLocation destination);

    /**
     * Get whether the local player can play this specific dev card.
     * @return true if the user can play this card
     * @param r1
     * @param r2
     */
    public boolean canPlayYearOfPlenty(ResourceType r1, ResourceType r2);

    /**
     * Get whether the local player can play this specific dev card.
     * @return true if the user can play this card
     */
    public boolean canPlayMonument();

    /**
     * Get whether the local player can play this specific dev card.
     * @return true if the user can play this card
     * @param edge1
     * @param edge2
     */
    public boolean canPlayRoadBuilding(EdgeLocation edge1, EdgeLocation edge2);

    /**
     * Get a list of players around a hex that can be robbed (all with towns except local player)
     * @param location the location being robbed
     * @return a collection of players (may be empty)
     */
    public Collection<IPlayer> getRobbablePlayers(HexLocation location);


    /**
     * Returns the info for the current player
     *
     * @return the PlayerInfo object initialized with all the player card/piece counts
     */
    public IPlayer getCurrentPlayer();

    /**
     * Returns the info for the local player's resource counts
     *
     * @return the ResourceBundle object containing the counts for the current player
     */
    public IResourceBank getPlayerResources();

    /**
     * Returns the ports the current player has
     *
     * @return the ports that the local player has
     */
    public Set<PortType> getPlayerPorts();

    /**
     * Returns the chat history of the game
     *
     * @return the log initialized with all the chat messages
     */
    public ILog getChatHistory();

    /**
     * Returns the move/action history of the game
     *
     * @return the log initialized with all the move/action messages
     */
    public ILog getMoveHistory();

    boolean playerHasLongestRoad(IPlayer player);

    boolean playerHasLargestArmy(IPlayer player);

    boolean isPlayersTurn(IPlayer player);

    // this method is just for determining from the GameState if it is a free round
    public boolean isFreeRound();

    /**
     * Get the local player's color.
     * @return the local player's color
     */
    public CatanColor getLocalColor();
}
