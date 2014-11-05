package server.facade;

import shared.communication.*;
import shared.model.Game;

/**
 * Created by Spencer Weight - 11/4/2014.
 */
public interface IMovesFacade {

    /**
     * Sends a chat message
     * Swagger URL Equivalent: /moves/sendChat
     */
    public Game sendChat(SendChatParams sendChat);

    /**
     * Used to roll a number at the beginning of a turn
     * Swagger URL Equivalent: /moves/rollNumber
     */
    public Game rollNumber(RollNumberParams rollNumber);

    /**
     * Moves the robber to a new location and selects a player to rob
     * Swagger URL Equivalent: /moves/robPlayer
     */
    public Game robPlayer(RobPlayerParams robPlayer);

    /**
     * Used to finish a turn
     * Swagger URL Equivalent: /moves/finishTurn
     */
    public Game finishTurn(FinishTurnParams finishTurn);

    /**
     * Buys a development card for yourself
     * Swagger URL Equivalent: /moves/buyDevCard
     */
    public Game buyDevCard(BuyDevCardParams buyDevCard);

    /**
     * Plays a Year of Plenty card and its effects
     * Swagger URL Equivalent: /moves/Year_of_Plenty
     */
    public Game yearOfPlenty(YearOfPlentyParams yearOfPlenty);

    /**
     * Plays a Road Building card and its effects
     * Swagger URL Equivalent: /moves/Road_Building
     */
    public Game roadBuilding(RoadBuildingParams roadBuilding);

    /**
     * Plays a Soldier card and its effects
     * Swagger URL Equivalent: /moves/Soldier
     */
    public Game soldier(SoldierParams soldier);

    /**
     * Plays a Monopoly card and its effects
     * Swagger URL Equivalent: /moves/Monopoly
     */
    public Game monopoly(MonopolyParams monopoly);

    /**
     * Plays a Monument card and its effects
     * Swagger URL Equivalent: /moves/Monument
     */
    public Game monument(MonumentParams monument);

    /**
     * Builds a road for a player at a specified spot;
     * Set free to true if it is the setup stage
     * Swagger URL Equivalent: /moves/buildRoad
     */
    public Game buildRoad(BuildRoadParams buildRoad);

    /**
     * Builds a settlement for a player at a specified spot;
     * Set free to true if it is the setup stage
     * Swagger URL Equivalent: /moves/buildSettlement
     */
    public Game buildSettlement(BuildSettlementParams buildSettlement);

    /**
     * Builds a city for a player at a specified spot;
     * Set free to true if it is the setup stage
     * Swagger URL Equivalent: /moves/buildCity
     */
    public Game buildCity(BuildCityParams buildCity);

    /**
     * Offers a trade to another player
     * Swagger URL Equivalent: /moves/offerTrade
     */
    public Game offerTrade(OfferTradeParams offerTrade);

    /**
     * Accept or reject a trade offered to you
     * Swagger URL Equivalent: /moves/acceptTrade
     */
    public Game acceptTrade(AcceptTradeParams acceptTrade);

    /**
     * Execute a maritime trade operation
     * Swagger URL Equivalent: /moves/maritimeTrade
     */
    public Game maritimeTrade(MaritimeTradeParams maritimeTrade);

    /**
     * Discards selected cards because of a rolled 7
     * Swagger URL Equivalent: /moves/discardCards
     */
    public Game discardCards(DiscardCardParams discardCards);
}
