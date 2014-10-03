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

        String[] request = new String[] {"sendChat", Integer.toString(playerIndex), message };
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
        String request =
                "{" +
                    "\"type\":" + "willAccept" + "," +
                    "\"playerIndex\":" + playerIndex + "," +
                    "\"willAccept\":" + willAccept + "," +
                "}";

        String response = m_httpCommunicator.post("/moves/acceptTrade", request);

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
        String request =
                "{" +
                    "\"type\":" + "deiscardCards" + "," +
                    "\"playerIndex\":" + playerIndex + "," +
                    "\"discardedCards\": " + "{" +
                        "\"brick\":" + discardedCards.getBrick() + "," +
                        "\"ore\":" + discardedCards.getOre() + "," +
                        "\"sheep\":" + discardedCards.getSheep() +
                        "\"wheat\":" + discardedCards.getWheat() +
                        "\"wood\":" + discardedCards.getWood() +
                    "}" +
                "}";

        String response = m_httpCommunicator.post("/moves/discardCards", request);

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
        String request =
                "{" +
                    "\"type\":" + "rollNumber" + "," +
                    "\"playerIndex\":" + playerIndex + "," +
                    "\"number\":" + number +
                "}";

        String response = m_httpCommunicator.post("/moves/rollNumber", request);

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
        String request =
                "{" +
                    "\"type\":" + "buildRoad" + "," +
                    "\"playerIndex\":" + playerIndex + "," +
                    "\"vertexLocation\":" + "{" +
                        "\"x\":" + edgeLoc.getHexLoc().getX() +
                        "\"y\":" + edgeLoc.getHexLoc().getY() +
                        "\"direction\":" + edgeLoc.getDir().name() +
                    "}," +
                    "\"free\":" + free +
                "}";

        String response = m_httpCommunicator.post("/moves/buildRoad", request);

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
        String request =
                "{" +
                    "\"type\":" + "buildSettlement" + "," +
                    "\"playerIndex\":" + playerIndex + "," +
                    "\"vertexLocation\":" + "{" +
                        "\"x\":" + location.getHexLoc().getX() +
                        "\"y\":" + location.getHexLoc().getY() +
                        "\"direction\":" + location.getDir().name() +
                    "}," +
                    "\"free\":" + free +
                "}";

        String response = m_httpCommunicator.post("/moves/buildSettlement", request);

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
        String request =
                "{" +
                    "\"type\":" + "buildCity" + "," +
                    "\"playerIndex\":" + playerIndex + "," +
                    "\"vertexLocation\":" + "{" +
                        "\"x\":" + location.getHexLoc().getX() +
                        "\"y\":" + location.getHexLoc().getY() +
                        "\"direction\":" + location.getDir().name() +
                    "}" +
                "}";

        String response = m_httpCommunicator.post("/moves/buildCity", request);

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
        String request =
                "{" +
                    "\"type\":" + "offerTrade" + "," +
                    "\"playerIndex\":" + playerIndex + "," +
                    "\"spot1\": " + "{" +
                        "\"brick\":" + offer.getBrick() + "," +
                        "\"ore\":" + offer.getOre() + "," +
                        "\"sheep\":" + offer.getSheep() +
                        "\"wheat\":" + offer.getWheat() +
                        "\"wood\":" + offer.getWood() +
                    "}" +
                    "\"receiver\":" + receiver + "," +
                "}";

        String response = m_httpCommunicator.post("/moves/offerTrade", request);

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
        String request =
                "{" +
                    "\"type\":" + "maritimeTrade" + "," +
                    "\"playerIndex\":" + playerIndex + "," +
                    "\"ratio\":" + ratio + "," +
                    "\"inputResource\":" + input.name() + "," +
                    "\"outputResource\":" + output.name() + "," +
                "}";

        String response = m_httpCommunicator.post("/moves/maritimeTrade", request);

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
        String request =
                "{" +
                    "\"type\":" + "finishTurn" + "," +
                    "\"playerIndex\":" + playerIndex + "," +
                "}";

        String response = m_httpCommunicator.post("/moves/finishTurn", request);

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
        String request =
                "{" +
                    "\"type\":" + "buyDevCard" + "," +
                    "\"playerIndex\":" + playerIndex +
                "}";

        String response = m_httpCommunicator.post("/moves/buyDevCard", request);

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
        String request =
                "{" +
                    "\"type\":" + "Year_of_Plenty" + "," +
                    "\"playerIndex\":" + playerIndex + "," +
                    "\"resource1\":" + resource1.name() + "," +
                    "\"resource2\":" + resource2.name() +
                "}";

        String response = m_httpCommunicator.post("/moves/Year_of_Plenty", request);

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
        String request =
                "{" +
                    "\"type\":" + "Road_Building" + "," +
                    "\"playerIndex\":" + playerIndex + "," +
                    "\"spot1\": " + "{" +
                        "\"x\":" + location1.getHexLoc().getX() + "," +
                        "\"y\":" + location1.getHexLoc().getY() +
                    "}" +
                    "\"spot2\": " + "{" +
                        "\"x\":" + location2.getHexLoc().getX() + "," +
                        "\"y\":" + location2.getHexLoc().getY() +
                    "}" +
                "}";

        String response = m_httpCommunicator.post("/moves/Road_Building", request);

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
        String request =
                "{" +
                    "\"type\":" + "Soldier" + "," +
                    "\"playerIndex\":" + playerIndex + "," +
                    "\"victimIndex\":" + victim + "," +
                    "\"location\": " + "{" +
                        "\"x\":" + location.getX() + "," +
                        "\"y\":" + location.getY() +
                    "}" +
                "}";

        String response = m_httpCommunicator.post("/moves/Soldier", request);

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
        String request =
                "{" +
                    "\"type\":" + "Monopoly" + "," +
                    "\"resource\":" + resource.name() + "," +
                    "\"playerIndex\":" + playerIndex + "," +
                "}";

        String response = m_httpCommunicator.post("/moves/Monopoly", request);

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
        String request =
                "{" +
                    "\"type\":" + "Monument" + "," +
                    "\"playerIndex\":" + playerIndex + "," +
                "}";

        String response = m_httpCommunicator.post("/moves/Monument", request);

        return response;
    }

    @Override
    public String robPlayer(int robbingPlayerIndex, int victimIndex, HexLocation hex) throws NetworkException {
        String request =
                "{" +
                    "\"type\": \"robPlayer\"," +
                    "\"playerIndex\": \"" + robbingPlayerIndex + "\"," +
                    "\"victimIndex\": \"" + victimIndex + "\"," +
                    "\"location\": {" +
                        "\"x\": \"" + hex.getX() + "\"," +
                        "\"y\": \"" + hex.getY() + "\"" +
                    "}" +
                "}";

        String response = m_httpCommunicator.post("/moves/robPlayer", request);

        return response;

    }

    @Override
    public int getPlayerId() {
        return m_httpCommunicator.getPlayerId();
    }
}
