package shared.model;

import client.network.IServerProxy;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

/**
 * Facade for methods that need to access both the model and the server.
 */
public interface IServerModelFacade {

    /**
     * Set the game object the Facade uses
     */
    public void setServerProxy(IServerProxy game);

    /**
     * Takes in a chat message to be added to the chat log
     *
     * @param message the string of text to add to the chat log
     */
    public void sendChat(String message) throws ModelException;

    /**
     * Takes an edge location and places a road on it
     *
     * @param edge the location of the side of a terrain hex
     */
    public void placeRoad(EdgeLocation edge) throws ModelException;

    /**
     * Takes a vertex location and places a settlement on it
     *
     * @param vertex the location of an intersection of terrain hexes
     */
    public void placeSettlement(VertexLocation vertex) throws ModelException;

    /**
     * Takes a vertex location and places a city on it
     *
     * @param vertex the location of an intersection of terrain hexes
     */
    public void placeCity(VertexLocation vertex) throws ModelException;

    /**
     * Current player buys a DevCard
     */
    public void buyDevCard() throws ModelException;

    /**
     * Tells the server to play a soldier card
     * @param hex is the location to put the robber
     * @param victim is the player who is robbed, or null if no one is being robbed
     */
    public void playSoldier(HexLocation hex, IPlayer victim) throws ModelException;

    /**
     * Play the Year of Plenty card
     * @param r1 the first resource to take from the bank
     * @param r2 the second resource to take from the bank
     */
    public void playYearOfPlenty(ResourceType r1, ResourceType r2) throws ModelException;

    /**
     * Play the Road Building card
     * @param e1 location of the first road
     * @param e2 location of the second road
     */
    public void playRoadBuilding(EdgeLocation e1, EdgeLocation e2) throws ModelException;

    /**
     * Play the Monopoly card
     * @param rType the type of resource to monopolize
     */
    public void playMonopoly(ResourceType rType) throws ModelException;

    /**
     * Play the Monument card
     */
    public void playMonument() throws ModelException;

    /**
     * Tells the server to rob a player
     * @param hex the hex to place the robber on
     * @param victimIndex is the player who is robbed, or null if no one is being robbed
     */
    public void robPlayer(HexLocation hex, int victimIndex) throws ModelException;


    // trading
    /**
     * accept an incoming trade
     * @param willAccept is true if the the trade is accepted, false otherwise
     *
     */
    public void acceptTrade(boolean willAccept) throws ModelException;

    /**
     * offer a trade to another player
     * @param offer the bundle of resources you are offering
     * @param recipientPlayer the index of the player receiving the trade offer
     */
    public void offerTrade(IResourceBank offer, IPlayer recipientPlayer) throws ModelException;

    /**
     * Trade with a port
     * @param ratio the ratio of trade. 2, 3, or 4 resources for one of any kind
     * @param giving the bundle of resources that are being given up
     * @param getting the bundle of resources that are being received
     */
    public void maritimeTrade(int ratio, ResourceType giving, ResourceType getting) throws ModelException;

    /**
     * The current player will discard some cards
     * @param discardedCards the bundle of resource cards to discard
     */
    public void discardCards(IResourceBank discardedCards) throws ModelException;

    /**
     * The current player has rolled a number
     */
    public int rollNumber() throws ModelException;

    /**
     * Finish up the current player's turn
     */
    public void finishTurn() throws ModelException;
}
