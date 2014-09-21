package client.serverproxy;

import client.data.*;
import shared.locations.VertexLocation;
import shared.definitions.*;
import shared.locations.*;
import shared.model.*;
import java.util.List;


public interface IServerProxy {

    /**
     *
     * @param player
     * @return player info
     */
    public IPlayer login(IPlayer player);

    /**
     *
     * @param player
     * @return player info
     */
    public IPlayer register(IPlayer player);

    /**
     *
     * @return list of games
     */
    public List<Game> listGames();

    /**
     *
     * @param randTiles
     * @param randNum
     * @param randPorts
     * @param name
     * @return game info
     */
    public Game createGame(boolean randTiles, boolean randNum, boolean randPorts, String name);

    /**
     *
     * @param player
     */
    public void joinGame(PlayerInfo player);

    /**
     *
     * @return current game state
     */
    public Game getGameState();

    /**
     *
     * @return current game state
     */
    public Game resetGame();

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
    public void discardCards(int playerIndex, ResourceBundle discardedCards);

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
    public void offerTrade(int playerIndex, ResourceBundle offer, int receiver);

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
     * @param playerIndex
     */
    public void buyDevCard(int playerIndex);

    /**
     *
     * @param playerIndex
     * @param resource1
     * @param resource2
     */
    public void playYear_of_Plenty(int playerIndex, ResourceType resource1, ResourceType resource2);

    /**
     *
     * @param playerIndex
     * @param location1
     * @param location2
     */
    public void playRoad_Building(int playerIndex, EdgeLocation location1, EdgeLocation location2);

    /**
     *
     * @param playerIndex
     * @param location
     * @param victim
     */
    public void playSoldier(int playerIndex, HexLocation location, int victim);

    /**
     *
     * @param playerIndex
     * @param resource
     */
    public void playMonopoly(int playerIndex, ResourceType resource);

    /**
     *
     * @param playerIndex
     */
    public void playMonument(int playerIndex);
}
