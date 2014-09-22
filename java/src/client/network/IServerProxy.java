package client.network;

import shared.locations.VertexLocation;
import shared.definitions.*;
import shared.locations.*;
import shared.model.*;
import java.util.List;

/**
 * Interface for interacting with the server. This interface
 * largely mimics the server api.
 *
 */
public interface IServerProxy {

    /**
     * Logs an existing user into the game server
     *
     * @param username username of player attempting to login
     * @param password password of player attempting to login
     * @return an IPlayer object of the user with the matching username and password
     */
    public String login(String username, String password);

    /**
     * Creates a new user account and logs the new
     * user into the game server.
     *
     * @param username the username of the user attempting to register
     * @param password the password of the user attempting to register
     *
     * @return an IPlayer object of the newly registered user
     */
    public IPlayer register(String username, String password);

    /**
     * Returns information of all the current games on the server
     *
     * @return a list of games
     */
    public List<IGame> listGames();

    /**
     * Creates an empty game on the game server
     *
     * @param randTiles flag indicating whether the tiles in the game should be assigned randomly
     * @param randNum flag indicating whether the number tokens on the tiles should be assigned randomly
     * @param randPorts flag indicating whether the ports in the game should be assigned randomly
     * @param name name to give the game on the server
     * @return a JSON object representing the Catan Model
     */
    public String createGame(boolean randTiles, boolean randNum, boolean randPorts, String name);

    /**
     * Adds a player to the game and sets their game cookie
     *
     * @param username username of user attempting to join the game
     * @param password password of user attempting to join game
     * @return a JSON object representing the Catan Model
     */
    public String joinGame(String username, String password);

    /**
     *
     * @return current game state
     */
    public IGame getGameState();

    /**
     *
     * @return current game state
     */
    public IGame resetGame();

//    //params List<Command> - return List<Command>
//    public sendCommands();
//
//    //return unknown
//    public executeCommands();

    /**
     *
     * @return list of AI
     */
    public List<String> listAI();

    /**
     *
     * @param name
     */
    public void addAI(String name);

    /**
     *
     * @param logLevel
     */
    public void changeLogLevel(String logLevel);

    /**
     *
     * @param playerIndex
     * @param message
     */
    public void sendChat(int playerIndex, String message);

    /**
     *
     * @param playerIndex
     * @param willAccept
     */
    public void acceptTrade(int playerIndex, boolean willAccept);

    /**
     *
     * @param playerIndex
     * @param discardedCards
     */
    public void discardCards(int playerIndex, IResourceBundle discardedCards);

    /**
     *
     * @param playerIndex
     * @param number
     */
    public void rollNumber(int playerIndex, int number);

    /**
     *
     * @param playerIndex
     * @param edgeLoc
     * @param free
     */
    public void buildRoad(int playerIndex, EdgeLocation edgeLoc, boolean free);

    /**
     *
     * @param playerIndex
     * @param location
     * @param free
     */
    public void buildSettlement(int playerIndex, VertexLocation location, boolean free);

    /**
     *
     * @param playerIndex
     * @param location
     * @param free
     */
    public void buildCity(int playerIndex, VertexLocation location, boolean free);

    /**
     *
     * @param playerIndex
     * @param offer
     * @param receiver
     */
    public void offerTrade(int playerIndex, IResourceBundle offer, int receiver);

    /**
     *
     * @param playerIndex
     * @param ratio
     * @param input
     * @param output
     */
    public void maritimeTrade(int playerIndex, int ratio, ResourceType input, ResourceType output);

    /**
     *
     */
    public void finishTurn();

    /**
     *
     *
     * @param playerIndex
     */
    public void buyDevCard(int playerIndex);

    /**
     * One player plays the year of plenty card. This player can then
     * take resource cards of his choosing from the
     * bank.
     *
     * @param playerIndex index of player playing the card
     * @param resource1 the first type of resource
     * @param resource2 the second type of resource
     * @return a JSON object representing the Catan Model
     */
    public String playYearOfPlenty(int playerIndex, ResourceType resource1, ResourceType resource2);

    /**
     * One player plays a road building card. The player can then place
     * two roads on the board free of charge.
     *
     * @param playerIndex index of player playing the card
     * @param location1 location of the first road to be placed
     * @param location2 location of the second road to be placed
     * @return a JSON object representing the Catan Model
     */
    public String playRoadBuilding(int playerIndex, EdgeLocation location1, EdgeLocation location2);

    /**
     * One player plays a soldier development card. The player moves the
     * robber to a different hex location, and steals a card from one
     * of the players whose town resides on that hex location
     *
     * @param playerIndex index of player playing the card
     * @param location location of hex to move the robber to
     * @param victim index of player getting a card taken from them
     * @return a JSON object representing the Catan Model
     */
    public String playSoldier(int playerIndex, HexLocation location, int victim);

    /**
     * One player plays a monopoly development card. This player gains a
     * resource card of the specified type from each player in the
     * game
     *
     * @param playerIndex index of player playing the card
     * @param resource the type of resource card the player selects
     * @return a JSON object representing the Catan Model
     */
    public String playMonopoly(int playerIndex, ResourceType resource);

    /**
     * One player plays a monument development card, and therefore
     * increments his victory point total by one.
     *
     * @param playerIndex index of player playing the card
     * @return a JSON object representing the Catan Model
     */
    public String playMonument(int playerIndex);
}
