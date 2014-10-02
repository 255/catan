package client.network;

import shared.locations.VertexLocation;
import shared.definitions.*;
import shared.locations.*;
import shared.model.*;

/**
 * Interface for interacting with the server. This interface
 * largely mimics the server api.
 */
public interface IServerProxy {
    /**
     * Returns the current Catan Model in a JSON object
     *
     * @return a JSON object representing the current Catan Model after this method has been called
     *
     * @throws NetworkException something went wrong when attempting to communicate with the server
     */
    public String getGameState() throws NetworkException;

    /**
     * Resets the game to how it was after all the players joined.
     *
     * @return a JSON object representing the current Catan Model after this method has been called
     *
     * @throws NetworkException indicates that an error occurred while attempting to communicate with the server
     */
    public String resetGame() throws NetworkException;

    /**
     * Sets the server's logging level
     *
     * @param logLevel new logging level to set the server's logging level to
     * @return flag indicating whether the log level was set correctly
     *
     * @throws NetworkException indicates that an error occurred while attempting to communicate with the server
     */
    public boolean changeLogLevel(String logLevel) throws NetworkException;

    /**
     * Sends a chat message to the rest of the players on the chat log
     *
     * @param playerIndex index of player sending the chat
     * @param message message the player wants to send
     * @return a JSON object representing the current Catan Model after this method has been called
     *
     * @throws NetworkException indicates that an error occurred while attempting to communicate with the server
     */
    public String sendChat(int playerIndex, String message) throws NetworkException;

    /**
     * A player who has received a trade offer indicates whether or not they
     * accept the trade.
     *
     * @param playerIndex index of player accepting the trade
     * @param willAccept flag indicating whether or not the player accepts the trade
     * @return a JSON object representing the current Catan Model after this method has been called
     *
     * @throws NetworkException indicates that an error occurred while attempting to communicate with the server
     */
    public String acceptTrade(int playerIndex, boolean willAccept) throws NetworkException;

    /**
     * Player is forced to discard a certain number of cards back into the bank.
     *
     * @param playerIndex index of player discarding cards
     * @param discardedCards bundle of cards that the player has chosen to discard
     * @return a JSON object representing the current Catan Model after this method has been called
     *
     * @throws NetworkException indicates that an error occurred while attempting to communicate with the server
     */
    public String discardCards(int playerIndex, IResourceBank discardedCards) throws NetworkException;

    /**
     * Takes care of all the processes that need to occur after the dice is
     * rolled.
     *
     * @param playerIndex index of player whose turn it is
     * @param number number that the player rolls
     * @return a JSON object representing the current Catan Model after this method has been called
     *
     * @throws NetworkException indicates that an error occurred while attempting to communicate with the server
     */
    public String rollNumber(int playerIndex, int number) throws NetworkException;

    /**
     * Player builds a road on the specified edge location. Player must have one resource card
     * for brick and wood, as well as a road connected to the specified edge location.
     *
     * @param playerIndex index of player building the road
     * @param edgeLoc edge location where road should be built
     * @param free flag indicating if this piece is free, as is the case during setup
     * @return a JSON object representing the current Catan Model after this method has been called
     *
     * @throws NetworkException indicates that an error occurred while attempting to communicate with the server
     */
    public String buildRoad(int playerIndex, EdgeLocation edgeLoc, boolean free) throws NetworkException;

    /**
     * Player desires to build a settlement at the specified vertex location. Player
     * must have one resource card for wood, brick, wheat, and sheep in order to
     * build the settlement, as well as a road that connects to the specified vertex.
     *
     * @param playerIndex index of player building the settlement
     * @param location vertex where the settlement should be built
     * @param free flag indicating if this piece is free, as is the case during setup
     * @return a JSON object representing the current Catan Model after this method has been called
     *
     * @throws NetworkException indicates that an error occurred while attempting to communicate with the server
     */
    public String buildSettlement(int playerIndex, VertexLocation location, boolean free) throws NetworkException;

    /**
     * One player upgrades to a city from a previously built settlement. Player must have
     * two wheat resource cards and three ore resource cards.
     *
     * @param playerIndex index of player upgrading to a city
     * @param location location where the city should be built
     * @return a JSON object representing the current Catan Model after this method has been called
     *
     * @throws NetworkException indicates that an error occurred while attempting to communicate with the server
     */
    public String buildCity(int playerIndex, VertexLocation location) throws NetworkException;

    /**
     * One player makes a trade offer to another player, indicating what resource
     * cards he or she would like to give and what resources he or she would like
     * to receive in return.
     *
     * @param playerIndex index of player offering the trade
     * @param offer bundle of resource cards the player is willing to give and cards they would like to receive
     * @param receiver the index of the player who receives the offer
     * @return a JSON object representing the current Catan Model after this method has been called
     *
     * @throws NetworkException indicates that an error occurred while attempting to communicate with the server
     */
    public String offerTrade(int playerIndex, IResourceBank offer, int receiver) throws NetworkException;

    /**
     * A player makes trades resource cards in their hand for resource cards
     * from the bank using a port that he or she owns, or by using the default
     * 4:1 ratio.
     *
     * @param playerIndex index of player making the trade
     * @param ratio ratio of cards given to cards received
     * @param input type of resource the player is trading in
     * @param output type of resource the player desires to receive
     * @return a JSON object representing the current Catan Model after this method has been called
     *
     * @throws NetworkException indicates that an error occurred while attempting to communicate with the server
     */
    public String maritimeTrade(int playerIndex, int ratio, ResourceType input, ResourceType output) throws NetworkException;

    /**
     * Indicates that a player has finished their turn, and
     * the next player should start their next turn.
     * @param playerIndex index of player finishing their turn
     * @return a JSON object representing the current Catan Model after this method has been called
     *
     * @throws NetworkException indicates that an error occurred while attempting to communicate with the server
     */
    public String finishTurn(int playerIndex) throws NetworkException;

    /**
     * Player buys a development card. Player must have
     * one resource card of ore, wheat, and sheep. There also
     * must be development cards left in the bank.
     *
     * @param playerIndex index of player buying the development card
     * @return a JSON object representing the current Catan Model after this method has been called
     *
     * @throws NetworkException indicates that an error occurred while attempting to communicate with the server
     */
    public String buyDevCard(int playerIndex) throws NetworkException;

    /**
     * One player plays the year of plenty card. This player can then
     * take resource cards of his choosing from the
     * bank.
     *
     * @param playerIndex index of player playing the card
     * @param resource1 the first type of resource
     * @param resource2 the second type of resource
     * @return a JSON object representing the current Catan Model after this method has been called
     *
     * @throws NetworkException indicates that an error occurred while attempting to communicate with the server
     */
    public String playYearOfPlenty(int playerIndex, ResourceType resource1, ResourceType resource2) throws NetworkException;

    /**
     * One player plays a road building card. The player can then place
     * two roads on the board free of charge.
     *
     * @param playerIndex index of player playing the card
     * @param location1 location of the first road to be placed
     * @param location2 location of the second road to be placed
     * @return a JSON object representing the current Catan Model after this method has been called
     *
     * @throws NetworkException indicates that an error occurred while attempting to communicate with the server
     */
    public String playRoadBuilding(int playerIndex, EdgeLocation location1, EdgeLocation location2) throws NetworkException;

    /**
     * One player plays a soldier development card. The player moves the
     * robber to a different hex location, and steals a card from one
     * of the players whose town resides on that hex location
     *
     * @param playerIndex index of player playing the card
     * @param location location of hex to move the robber to
     * @param victim index of player getting a card taken from them
     * @return a JSON object representing the current Catan Model after this method has been called
     *
     * @throws NetworkException indicates that an error occurred while attempting to communicate with the server
     */
    public String playSoldier(int playerIndex, HexLocation location, int victim) throws NetworkException;

    /**
     * One player plays a monopoly development card. This player gains a
     * resource card of the specified type from each player in the
     * game
     *
     * @param playerIndex index of player playing the card
     * @param resource the type of resource card the player selects
     * @return a JSON object representing the current Catan Model after this method has been called
     *
     * @throws NetworkException indicates that an error occurred while attempting to communicate with the server
     */
    public String playMonopoly(int playerIndex, ResourceType resource) throws NetworkException;

    /**
     * One player plays a monument development card, and therefore
     * increments his victory point total by one.
     *
     * @param playerIndex index of player playing the card
     * @return a JSON object representing the current Catan Model after this method has been called
     *
     * @throws NetworkException indicates that an error occurred while attempting to communicate with the server
     */
    public String playMonument(int playerIndex) throws NetworkException;

    /**
     * A player rolls a 7 and moves the robber to a hex location, and stesls from players with towns on that hex
     *
     * @param robbingPlayerIndex index of player who rolled the 7
     * @param victimIndex index of player getting a card taken from them
     * @param hex location of hex to move the robber to
     * @return a JSON object representing the current Catan Model after this method has been called
     *
     * @throws NetworkException indicates that an error occurred while attempting to communicate with the server
     */
    public String robPlayer(int robbingPlayerIndex, int victimIndex, HexLocation hex) throws NetworkException;
}
