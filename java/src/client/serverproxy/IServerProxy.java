package client.serverproxy;

import client.data.*;


public interface IServerProxy {

    //params with password
    PlayerInfo login();

    //params with password
    PlayerInfo register();

    //import List
    List<GameInfo> listGames();

    //params with 3 booleans and game name
    GameInfo createGame();

    //done
    void joinGame(PlayerInfo player);

    //return GameState
    getGameState();

    //return GameState
    resetGame();

    //params List<Command> - return List<Command>
    sendCommands();

    //return unknown
    executeCommands();

    //done
    List<String> listAI();

    //done
    void addAI(String name);

    //done
    void changeLogLevel(String logLevel);

    //params chat obj with playerIndex and chat content
    void sendChat();

    //params playerIndex and bool willAccept
    void acceptTrade();

    //params playerIndex and resourceHand discardedCards
    void discardCards();

    //params playerIndex and int number
    void rollNumber();

    //params int playerIndex, EdgeLocation edgeLoc, bool free
    void buildRoad();

    //params playerIndex, VertexLocation verLoc, bool free
    void buildSettlement();

    //params int playerIndex, VertexLocation verLoc, bool free
    void buildCity();

    //params int playerIndex, ResourceHand offer,int receiver
    void offerTrade();

    //params int playerIndex, int ratio, Resource input, Resource output
    void maritimeTrade();

    //done
    void finishTurn();

    //params int playerIndex
    void buyDevCard();

    //params int playerIndex, Resource resource1, Resource resource2
    void playYear_of_Plenty();

    //params int playerIndex, EdgeLocation spot1, EdgeLocation spot2
    void playRoad_Building();

    //params int playerIndex, HexLocation, int victim
    void playSoldier();

    //params int playerIndex, Resource resource
    void playMonopoly();

    //params int playerIndex
    void playMonument();
}
