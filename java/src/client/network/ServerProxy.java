package client.network;

import com.google.gson.JsonObject;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.model.IResourceBank;

/**
 * Class that implements the IServerProxy interface.
 * Allows the client to talk to the server by
 * mimicking the server's API
 *
 * @author StevenBarnett
 */
public class ServerProxy implements IServerProxy {

    private IHttpCommunicator m_httpCommunicator;

    public ServerProxy(IHttpCommunicator httpCommunicator) {
        m_httpCommunicator = httpCommunicator;
    }

    /**
     * Returns the current Catan Model in a JSON object
     *
     * @return a JSON object representing the current Catan Model after this method has been called
     * @throws client.network.NetworkException something went wrong when attempting to communicate with the server
     */
    @Override
    public String getGameState() throws NetworkException {
        String response = m_httpCommunicator.get("/game/model");

        return response;
    }

    /**
     * Resets the game to how it was after all the players joined.
     *
     * @return a JSON object representing the current Catan Model after this method has been called
     * @throws client.network.NetworkException indicates that an error occurred while attempting to communicate with the server
     */
    @Override
    public String resetGame() throws NetworkException {
        String request =
                "{" +
                "}";

        String response = m_httpCommunicator.post("/game/reset", request);

        return response;
    }

    /**
     * Sets the server's logging level
     *
     * @param logLevel new logging level to set the server's logging level to
     * @return flag indicating whether the log level was set correctly
     * @throws client.network.NetworkException indicates that an error occurred while attempting to communicate with the server
     */
    @Override
    public boolean changeLogLevel(String logLevel) throws NetworkException {
        String request =
                "{" +
                    "\"logLevel\":" + logLevel +
                "}";

        String response = m_httpCommunicator.post("/util/changeLogLevel", request);

        return (response != null ? true : false);
    }

    /**
     * Sends a chat message to the rest of the players on the chat log
     *
     * @param playerIndex index of player sending the chat
     * @param message     message the player wants to send
     * @return a JSON object representing the current Catan Model after this method has been called
     * @throws client.network.NetworkException indicates that an error occurred while attempting to communicate with the server
     */
    @Override
    public String sendChat(int playerIndex, String message) throws NetworkException {

        JsonObject json = new JsonObject();
        json.addProperty("type", "sendChat");
        json.addProperty("playerIndex", playerIndex);
        json.addProperty("content", message);

        String response = m_httpCommunicator.post("/moves/sendChat", json.toString());

        return response;
    }

    /**
     * A player who has received a trade offer indicates whether or not they
     * accept the trade.
     *
     * @param playerIndex index of player accepting the trade
     * @param willAccept  flag indicating whether or not the player accepts the trade
     * @return a JSON object representing the current Catan Model after this method has been called
     * @throws client.network.NetworkException indicates that an error occurred while attempting to communicate with the server
     */
    @Override
    public String acceptTrade(int playerIndex, boolean willAccept) throws NetworkException {
        JsonObject json = new JsonObject();
        json.addProperty("type", "willAccept");
        json.addProperty("playerIndex", playerIndex);
        json.addProperty("willAccept", willAccept);

        String response = m_httpCommunicator.post("/moves/acceptTrade", json.toString());

        return response;
    }

    /**
     * Player is forced to discard a certain number of cards back into the bank.
     *
     * @param playerIndex    index of player discarding cards
     * @param discardedCards bundle of cards that the player has chosen to discard
     * @return a JSON object representing the current Catan Model after this method has been called
     * @throws client.network.NetworkException indicates that an error occurred while attempting to communicate with the server
     */
    @Override
    public String discardCards(int playerIndex, IResourceBank discardedCards) throws NetworkException {
        JsonObject json = new JsonObject();
        json.addProperty("type", "discardCards");
        json.addProperty("playerIndex", playerIndex);
        JsonObject innerObject = new JsonObject();
        innerObject.addProperty("brick", discardedCards.getBrick());
        innerObject.addProperty("ore", discardedCards.getOre());
        innerObject.addProperty("sheep", discardedCards.getSheep());
        innerObject.addProperty("wheat", discardedCards.getWheat());
        innerObject.addProperty("wood", discardedCards.getWood());
        json.add("discardedCards", innerObject);

        String response = m_httpCommunicator.post("/moves/discardCards", json.toString());

        return response;
    }

    /**
     * Takes care of all the processes that need to occur after the dice is
     * rolled.
     *
     * @param playerIndex index of player whose turn it is
     * @param number      number that the player rolls
     * @return a JSON object representing the current Catan Model after this method has been called
     * @throws client.network.NetworkException indicates that an error occurred while attempting to communicate with the server
     */
    @Override
    public String rollNumber(int playerIndex, int number) throws NetworkException {
        JsonObject json = new JsonObject();
        json.addProperty("type", "rollNumber");
        json.addProperty("playerIndex", playerIndex);
        json.addProperty("number", number);

        String response = m_httpCommunicator.post("/moves/rollNumber", json.toString());

        return response;
    }

    /**
     * Player builds a road on the specified edge location. Player must have one resource card
     * for brick and wood, as well as a road connected to the specified edge location.
     *
     * @param playerIndex index of player building the road
     * @param edgeLoc     edge location where road should be built
     * @param free        flag indicating if this piece is free, as is the case during setup
     * @return a JSON object representing the current Catan Model after this method has been called
     * @throws client.network.NetworkException indicates that an error occurred while attempting to communicate with the server
     */
    @Override
    public String buildRoad(int playerIndex, EdgeLocation edgeLoc, boolean free) throws NetworkException {
        JsonObject json = new JsonObject();
        json.addProperty("type", "buildRoad");
        json.addProperty("playerIndex", playerIndex);
        JsonObject roadLocObject = new JsonObject();
        roadLocObject.addProperty("x", edgeLoc.getHexLoc().getX());
        roadLocObject.addProperty("y", edgeLoc.getHexLoc().getY());
        roadLocObject.addProperty("direction", edgeLoc.getDir().toAbbreviation());
        json.add("roadLocation", roadLocObject);
        json.addProperty("free", free);

        String response = m_httpCommunicator.post("/moves/buildRoad", json.toString());

        return response;
    }

    /**
     * Player desires to build a settlement at the specified vertex location. Player
     * must have one resource card for wood, brick, wheat, and sheep in order to
     * build the settlement, as well as a road that connects to the specified vertex.
     *
     * @param playerIndex index of player building the settlement
     * @param location    vertex where the settlement should be built
     * @param free        flag indicating if this piece is free, as is the case during setup
     * @return a JSON object representing the current Catan Model after this method has been called
     * @throws client.network.NetworkException indicates that an error occurred while attempting to communicate with the server
     */
    @Override
    public String buildSettlement(int playerIndex, VertexLocation location, boolean free) throws NetworkException {
        JsonObject json = new JsonObject();
        json.addProperty("type", "buildSettlement");
        json.addProperty("playerIndex", playerIndex);
        JsonObject vertexLocObject = new JsonObject();
        vertexLocObject.addProperty("x", location.getHexLoc().getX());
        vertexLocObject.addProperty("y", location.getHexLoc().getY());
        vertexLocObject.addProperty("direction", location.getDir().toAbbreviation());
        json.add("vertexLocation", vertexLocObject);
        json.addProperty("free", free);

        String response = m_httpCommunicator.post("/moves/buildSettlement", json.toString());

        return response;
    }

    /**
     * One player upgrades to a city from a previously built settlement. Player must have
     * two wheat resource cards and three ore resource cards.
     *
     * @param playerIndex index of player upgrading to a city
     * @param location    location where the city should be built
     * @return a JSON object representing the current Catan Model after this method has been called
     * @throws client.network.NetworkException indicates that an error occurred while attempting to communicate with the server
     */
    @Override
    public String buildCity(int playerIndex, VertexLocation location) throws NetworkException {
        JsonObject json = new JsonObject();
        json.addProperty("type", "buildCity");
        json.addProperty("playerIndex", playerIndex);
        JsonObject vertexLocObject = new JsonObject();
        vertexLocObject.addProperty("x", location.getHexLoc().getX());
        vertexLocObject.addProperty("y", location.getHexLoc().getY());
        vertexLocObject.addProperty("direction", location.getDir().toAbbreviation());
        json.add("vertexLocation", vertexLocObject);

        String response = m_httpCommunicator.post("/moves/buildCity", vertexLocObject.toString());

        return response;
    }

    /**
     * One player makes a trade offer to another player, indicating what resource
     * cards he or she would like to give and what resources he or she would like
     * to receive in return.
     *
     * @param playerIndex index of player offering the trade
     * @param offer       bundle of resource cards the player is willing to give and cards they would like to receive
     * @param receiver    the index of the player who receives the offer
     * @return a JSON object representing the current Catan Model after this method has been called
     * @throws client.network.NetworkException indicates that an error occurred while attempting to communicate with the server
     */
    @Override
    public String offerTrade(int playerIndex, IResourceBank offer, int receiver) throws NetworkException {
        JsonObject json = new JsonObject();
        json.addProperty("type", "offerTrade");
        json.addProperty("playerIndex", playerIndex);
        JsonObject tradeObject = new JsonObject();
        tradeObject.addProperty("brick", offer.getBrick());
        tradeObject.addProperty("ore", offer.getOre());
        tradeObject.addProperty("sheep", offer.getSheep());
        tradeObject.addProperty("wheat", offer.getWheat());
        tradeObject.addProperty("wood", offer.getWood());
        json.add("offer", tradeObject);
        json.addProperty("receiver", receiver);

        String response = m_httpCommunicator.post("/moves/offerTrade", json.toString());

        return response;
    }

    /**
     * A player makes trades resource cards in their hand for resource cards
     * from the bank using a port that he or she owns, or by using the default
     * 4:1 ratio.
     *
     * @param playerIndex index of player making the trade
     * @param ratio       ratio of cards given to cards received
     * @param input       type of resource the player is trading in
     * @param output      type of resource the player desires to receive
     * @return a JSON object representing the current Catan Model after this method has been called
     * @throws client.network.NetworkException indicates that an error occurred while attempting to communicate with the server
     */
    @Override
    public String maritimeTrade(int playerIndex, int ratio, ResourceType input, ResourceType output) throws NetworkException {
        JsonObject json = new JsonObject();
        json.addProperty("type", "maritimeTrade");
        json.addProperty("playerIndex", playerIndex);
        json.addProperty("ratio", ratio);
        json.addProperty("inputResource", input.name());
        json.addProperty("outputResource", output.name());

        String response = m_httpCommunicator.post("/moves/maritimeTrade", json.toString());

        return response;
    }

    /**
     * Indicates that a player has finished their turn, and
     * the next player should start their next turn.
     *
     * @return a JSON object representing the current Catan Model after this method has been called
     * @throws client.network.NetworkException indicates that an error occurred while attempting to communicate with the server
     */
    @Override
    public String finishTurn(int playerIndex) throws NetworkException {
        JsonObject json = new JsonObject();
        json.addProperty("type", "finishTurn");
        json.addProperty("playerIndex", 0);

        String response = m_httpCommunicator.post("/moves/finishTurn", json.toString());

        return response;
    }

    /**
     * Player buys a development card. Player must have
     * one resource card of ore, wheat, and sheep. There also
     * must be development cards left in the bank.
     *
     * @param playerIndex index of player buying the development card
     * @return a JSON object representing the current Catan Model after this method has been called
     * @throws client.network.NetworkException indicates that an error occurred while attempting to communicate with the server
     */
    @Override
    public String buyDevCard(int playerIndex) throws NetworkException {
        JsonObject json = new JsonObject();
        json.addProperty("type", "buyDevCard");
        json.addProperty("playerIndex", playerIndex);

        String response = m_httpCommunicator.post("/moves/buyDevCard", json.toString());

        return response;
    }

    /**
     * One player plays the year of plenty card. This player can then
     * take resource cards of his choosing from the
     * bank.
     *
     * @param playerIndex index of player playing the card
     * @param resource1   the first type of resource
     * @param resource2   the second type of resource
     * @return a JSON object representing the current Catan Model after this method has been called
     * @throws client.network.NetworkException indicates that an error occurred while attempting to communicate with the server
     */
    @Override
    public String playYearOfPlenty(int playerIndex, ResourceType resource1, ResourceType resource2) throws NetworkException {
        JsonObject json = new JsonObject();
        json.addProperty("type", "Year_of_Plenty");
        json.addProperty("playerIndex", playerIndex);
        json.addProperty("resource1", resource1.name());
        json.addProperty("resource2", resource2.name());

        String response = m_httpCommunicator.post("/moves/Year_of_Plenty", json.toString());

        return response;
    }

    /**
     * One player plays a road building card. The player can then place
     * two roads on the board free of charge.
     *
     * @param playerIndex index of player playing the card
     * @param location1   location of the first road to be placed
     * @param location2   location of the second road to be placed
     * @return a JSON object representing the current Catan Model after this method has been called
     * @throws client.network.NetworkException indicates that an error occurred while attempting to communicate with the server
     */
    @Override
    public String playRoadBuilding(int playerIndex, EdgeLocation location1, EdgeLocation location2) throws NetworkException {
        JsonObject json = new JsonObject();
        json.addProperty("type", "Road_Building");
        json.addProperty("playerIndex", playerIndex);
        JsonObject spot1 = new JsonObject();
        spot1.addProperty("x", location1.getHexLoc().getX());
        spot1.addProperty("y", location1.getHexLoc().getY());
        JsonObject spot2 = new JsonObject();
        spot2.addProperty("x", location2.getHexLoc().getX());
        spot2.addProperty("y", location2.getHexLoc().getY());
        json.add("spot1", spot1);
        json.add("spot2", spot2);

        String response = m_httpCommunicator.post("/moves/Road_Building", json.toString());

        return response;
    }

    /**
     * One player plays a soldier development card. The player moves the
     * robber to a different hex location, and steals a card from one
     * of the players whose town resides on that hex location
     *
     * @param playerIndex index of player playing the card
     * @param location    location of hex to move the robber to
     * @param victim      index of player getting a card taken from them
     * @return a JSON object representing the current Catan Model after this method has been called
     * @throws client.network.NetworkException indicates that an error occurred while attempting to communicate with the server
     */
    @Override
    public String playSoldier(int playerIndex, HexLocation location, int victim) throws NetworkException {
        JsonObject json = new JsonObject();
        json.addProperty("type", "Soldier");
        json.addProperty("playerIndex", playerIndex);
        json.addProperty("victimIndex", victim);
        JsonObject locObject = new JsonObject();
        locObject.addProperty("x", location.getX());
        locObject.addProperty("y", location.getY());
        json.add("location", locObject);

        String response = m_httpCommunicator.post("/moves/Soldier", json.toString());

        return response;
    }

    /**
     * One player plays a monopoly development card. This player gains a
     * resource card of the specified type from each player in the
     * game
     *
     * @param playerIndex index of player playing the card
     * @param resource    the type of resource card the player selects
     * @return a JSON object representing the current Catan Model after this method has been called
     * @throws client.network.NetworkException indicates that an error occurred while attempting to communicate with the server
     */
    @Override
    public String playMonopoly(int playerIndex, ResourceType resource) throws NetworkException {
        JsonObject json = new JsonObject();
        json.addProperty("type", "Monopoly");
        json.addProperty("resource", resource.name());
        json.addProperty("playerIndex", playerIndex);

        String response = m_httpCommunicator.post("/moves/Monopoly", json.toString());

        return response;
    }

    /**
     * One player plays a monument development card, and therefore
     * increments his victory point total by one.
     *
     * @param playerIndex index of player playing the card
     * @return a JSON object representing the current Catan Model after this method has been called
     * @throws client.network.NetworkException indicates that an error occurred while attempting to communicate with the server
     */
    @Override
    public String playMonument(int playerIndex) throws NetworkException {
        JsonObject json = new JsonObject();
        json.addProperty("type", "Monument");
        json.addProperty("playerIndex", playerIndex);

        String response = m_httpCommunicator.post("/moves/Monument", json.toString());

        return response;
    }

    @Override
    public String robPlayer(int robbingPlayerIndex, int victimIndex, HexLocation hex) throws NetworkException {
        JsonObject json = new JsonObject();
        json.addProperty("type", "robPlayer");
        json.addProperty("playerIndex", robbingPlayerIndex);
        json.addProperty("victimIndex", victimIndex);
        JsonObject locObject = new JsonObject();
        locObject.addProperty("x", hex.getX());
        locObject.addProperty("y", hex.getY());
        json.add("location", locObject);

        String response = m_httpCommunicator.post("/moves/robPlayer", json.toString());

        return response;

    }

    @Override
    public int getPlayerId() {
        return m_httpCommunicator.getPlayerId();
    }
}
