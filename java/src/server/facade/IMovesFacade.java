package server.facade;

import shared.communication.*;
import shared.model.Game;

/**
 * A facade to support /moves operations.
 * Created by Spencer Weight - 11/4/2014.
 */
public interface IMovesFacade {

    /**
     * Sends a chat message
     * Swagger URL Equivalent: /moves/sendChat
     *
     * @param sendChat JSON wrapper with parameters to send a chat message
     * @return Game object containing a pointer to the model
     */
    public Game sendChat(SendChatParams sendChat);

    /**
     * Used to roll a number at the beginning of a turn
     * Swagger URL Equivalent: /moves/rollNumber
     *
     * @param rollNumber JSON wrapper with parameters to roll a number
     * @return Game object containing a pointer to the model
     */
    public Game rollNumber(RollNumberParams rollNumber);

    /**
     * Moves the robber to a new location and selects a player to rob
     * Swagger URL Equivalent: /moves/robPlayer
     *
     * @param robbing JSON wrapper with parameters to rob a player
     * @return Game object containing a pointer to the model
     */
    public Game robPlayer(RobbingParams robbing);

    /**
     * Used to finish a turn
     * Swagger URL Equivalent: /moves/finishTurn
     *
     * @param playerIndex JSON wrapper with parameters to finish a turn
     * @return Game object containing a pointer to the model
     */
    public Game finishTurn(PlayerIndexParam playerIndex);

    /**
     * Buys a development card for yourself
     * Swagger URL Equivalent: /moves/buyDevCard
     *
     * @param playerIndex JSON wrapper with parameters to buy a dev card
     * @return Game object containing a pointer to the model
     */
    public Game buyDevCard(PlayerIndexParam playerIndex);

    /**
     * Plays a Year of Plenty card and its effects
     * Swagger URL Equivalent: /moves/Year_of_Plenty
     *
     * @param yearOfPlenty JSON wrapper with parameters to play a year of plenty card
     * @return Game object containing a pointer to the model
     */
    public Game yearOfPlenty(YearOfPlentyParams yearOfPlenty);

    /**
     * Plays a Road Building card and its effects
     * Swagger URL Equivalent: /moves/Road_Building
     *
     * @param roadBuilding JSON wrapper with parameters to play a road building card
     * @return Game object containing a pointer to the model
     */
    public Game roadBuilding(RoadBuildingParams roadBuilding);

    /**
     * Plays a Soldier card and its effects
     * Swagger URL Equivalent: /moves/Soldier
     *
     * @param robbing JSON wrapper with parameters to play a soldier card
     * @return Game object containing a pointer to the model
     */
    public Game soldier(RobbingParams robbing);

    /**
     * Plays a Monopoly card and its effects
     * Swagger URL Equivalent: /moves/Monopoly
     *
     * @param monopoly JSON wrapper with parameters to play a monopoly card
     * @return Game object containing a pointer to the model
     */
    public Game monopoly(MonopolyParams monopoly);

    /**
     * Plays a Monument card and its effects
     * Swagger URL Equivalent: /moves/Monument
     *
     *
     * @param playerIndex JSON wrapper with parameters to play a monument card
     *  @return Game object containing a pointer to the model
     */
    public Game monument(PlayerIndexParam playerIndex);

    /**
     * Builds a road for a player at a specified spot;
     * Set free to true if it is the setup stage
     * Swagger URL Equivalent: /moves/buildRoad
     *
     * @param buildRoad JSON wrapper with parameters to build a road
     * @return Game object containing a pointer to the model
     */
    public Game buildRoad(BuildRoadParams buildRoad);

    /**
     * Builds a settlement for a player at a specified spot;
     * Set free to true if it is the setup stage
     * Swagger URL Equivalent: /moves/buildSettlement
     *
     * @param buildSettlement JSON wrapper with parameters to build a settlement
     * @return Game object containing a pointer to the model
     */
    public Game buildSettlement(BuildSettlementParams buildSettlement);

    /**
     * Builds a city for a player at a specified spot;
     * Set free to true if it is the setup stage
     * Swagger URL Equivalent: /moves/buildCity
     *
     * @param buildCity JSON wrapper with parameters to build a city
     * @return Game object containing a pointer to the model
     */
    public Game buildCity(BuildCityParams buildCity);

    /**
     * Offers a trade to another player
     * Swagger URL Equivalent: /moves/offerTrade
     *
     * @param offerTrade JSON wrapper with parameters to offer a trade
     * @return Game object containing a pointer to the model
     */
    public Game offerTrade(OfferTradeParams offerTrade);

    /**
     * Accept or reject a trade offered to you
     * Swagger URL Equivalent: /moves/acceptTrade
     *
     * @param acceptTrade JSON wrapper with parameters to accept a trade
     * @return Game object containing a pointer to the model
     */
    public Game acceptTrade(AcceptTradeParams acceptTrade);

    /**
     * Execute a maritime trade operation
     * Swagger URL Equivalent: /moves/maritimeTrade
     *
     * @param maritimeTrade JSON wrapper with parameters to do a maritime trade
     * @return Game object containing a pointer to the model
     */
    public Game maritimeTrade(MaritimeTradeParams maritimeTrade);

    /**
     * Discards selected cards because of a rolled 7
     * Swagger URL Equivalent: /moves/discardCards
     *
     * @param discardCards JSON wrapper with parameters to discard card from the player hand
     * @return Game object containing a pointer to the model
     */
    public Game discardCards(DiscardCardParams discardCards);
}
