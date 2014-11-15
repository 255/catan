package server.facade;

import server.command.*;
import shared.communication.*;
import shared.model.Game;
import shared.model.IGame;
import shared.model.IGameManager;
import shared.model.ModelException;

/**
 * Created by Spencer Weight - 11/5/2014.
 */
public class MovesFacade implements IMovesFacade {
    IGameManager gameManager;

    public MovesFacade(IGameManager gameManager) {
        this.gameManager = gameManager;
    }

    /**
     * Sends a chat message
     * Swagger URL Equivalent: /moves/sendChat
     *
     * @param params JSON wrapper with parameters to send a chat message
     * @return Game object containing a pointer to the model
     */
    @Override
    public IGame sendChat(SendChatParams params) throws IllegalCommandException, ModelException {
        IGame game = gameManager.getGame(params.getGameId());
        new SendChatCommand(game, game.getPlayer(params.playerIndex), params.content).execute();
        return game;
    }

    /**
     * Used to roll a number at the beginning of a turn
     * Swagger URL Equivalent: /moves/rollNumber
     *
     * @param rollNumber JSON wrapper with parameters to roll a number
     * @return Game object containing a pointer to the model
     */
    @Override
    public IGame rollNumber(RollNumberParams rollNumber) throws IllegalCommandException, ModelException {
        IGame game = gameManager.getGame(rollNumber.getGameId());
        new RollNumberCommand(game, game.getPlayer(rollNumber.playerIndex), rollNumber.number);
        return game;
    }

    /**
     * Moves the robber to a new location and selects a player to rob
     * Swagger URL Equivalent: /moves/robPlayer
     *
     * @param robbing JSON wrapper with parameters to rob a player
     * @return Game object containing a pointer to the model
     */
    @Override
    public IGame robPlayer(RobbingParams robbing) throws IllegalCommandException, ModelException {
        IGame game = gameManager.getGame(robbing.getGameId());
        new RobPlayerCommand(game, game.getPlayer(robbing.playerIndex), game.getPlayer(robbing.victimIndex), robbing.location);
        return game;
    }

    /**
     * Used to finish a turn
     * Swagger URL Equivalent: /moves/finishTurn
     *
     * @param playerIndex JSON wrapper with parameters to finish a turn
     * @return Game object containing a pointer to the model
     */
    @Override
    public IGame finishTurn(PlayerIndexParam playerIndex) throws IllegalCommandException {
        return null;
    }

    /**
     * Buys a development card for yourself
     * Swagger URL Equivalent: /moves/buyDevCard
     *
     * @param playerIndex JSON wrapper with parameters to buy a dev card
     * @return Game object containing a pointer to the model
     */
    @Override
    public IGame buyDevCard(PlayerIndexParam playerIndex) throws IllegalCommandException, ModelException {
        IGame game = gameManager.getGame(playerIndex.getGameId());
        new BuyDevCardCommand(game, game.getPlayer(playerIndex.playerIndex));
        return game;
    }

    /**
     * Plays a Year of Plenty card and its effects
     * Swagger URL Equivalent: /moves/Year_of_Plenty
     *
     * @param yearOfPlenty JSON wrapper with parameters to play a year of plenty card
     * @return Game object containing a pointer to the model
     */
    @Override
    public IGame yearOfPlenty(YearOfPlentyParams yearOfPlenty) throws IllegalCommandException, ModelException {
        IGame game = gameManager.getGame(yearOfPlenty.getGameId());
        new YearOfPlentyCommand(game, game.getPlayer(yearOfPlenty.playerIndex), yearOfPlenty.resource1, yearOfPlenty.resource2);
        return game;
    }

    /**
     * Plays a Road Building card and its effects
     * Swagger URL Equivalent: /moves/Road_Building
     *
     * @param roadBuilding JSON wrapper with parameters to play a road building card
     * @return Game object containing a pointer to the model
     */
    @Override
    public IGame roadBuilding(RoadBuildingParams roadBuilding) throws IllegalCommandException, ModelException {
        IGame game = gameManager.getGame(roadBuilding.getGameId());
        new RoadBuildingCommand(game, game.getPlayer(roadBuilding.playerIndex), roadBuilding.spot1, roadBuilding.spot2);
        return game;
    }

    /**
     * Plays a Soldier card and its effects
     * Swagger URL Equivalent: /moves/Soldier
     *
     * @param robbing JSON wrapper with parameters to play a soldier card
     * @return Game object containing a pointer to the model
     */
    @Override
    public IGame soldier(RobbingParams robbing) throws IllegalCommandException, ModelException {
        IGame game = gameManager.getGame(robbing.getGameId());
        //TODO: IMPLEMENT SOLDIERCOMMAND THEN UNCOMMENT LINE BELOW
//        new SoldierCommand(game, game.getPlayer(robbing.playerIndex), game.getPlayer(robbing.victimIndex), robbing.location);
        return game;
    }

    /**
     * Plays a Monopoly card and its effects
     * Swagger URL Equivalent: /moves/Monopoly
     *
     * @param monopoly JSON wrapper with parameters to play a monopoly card
     * @return Game object containing a pointer to the model
     */
    @Override
    public IGame monopoly(MonopolyParams monopoly) throws IllegalCommandException, ModelException {
        IGame game = gameManager.getGame(monopoly.getGameId());
        new MonopolyCommand(game, game.getPlayer(monopoly.playerIndex), monopoly.resource);
        return game;
    }

    /**
     * Plays a Monument card and its effects
     * Swagger URL Equivalent: /moves/Monument
     *
     * @param playerIndex JSON wrapper with parameters to play a monument card
     * @return Game object containing a pointer to the model
     */
    @Override
    public IGame monument(PlayerIndexParam playerIndex) throws IllegalCommandException, ModelException {
        IGame game = gameManager.getGame(playerIndex.getGameId());
        new MonumentCommand(game, game.getPlayer(playerIndex.playerIndex));
        return game;
    }

    /**
     * Builds a road for a player at a specified spot;
     * Set free to true if it is the setup stage
     * Swagger URL Equivalent: /moves/buildRoad
     *
     * @param buildRoad JSON wrapper with parameters to build a road
     * @return Game object containing a pointer to the model
     */
    @Override
    public IGame buildRoad(BuildRoadParams buildRoad) throws IllegalCommandException, ModelException {
        IGame game = gameManager.getGame(buildRoad.getGameId());
        new BuildRoadCommand(game, game.getPlayer(buildRoad.playerIndex), buildRoad.roadLocation, buildRoad.free).execute();
        return game;
    }

    /**
     * Builds a settlement for a player at a specified spot;
     * Set free to true if it is the setup stage
     * Swagger URL Equivalent: /moves/buildSettlement
     *
     * @param params JSON wrapper with parameters to build a settlement
     * @return Game object containing a pointer to the model
     */
    @Override
    public IGame buildSettlement(BuildSettlementParams params) throws IllegalCommandException, ModelException {
        IGame game = gameManager.getGame(params.getGameId());
        new BuildSettlementCommand(game, game.getPlayer(params.playerIndex),  params.vertexLocation, params.free).execute();
        return game;
    }

    /**
     * Builds a city for a player at a specified spot;
     * Set free to true if it is the setup stage
     * Swagger URL Equivalent: /moves/buildCity
     *
     * @param buildCity JSON wrapper with parameters to build a city
     * @return Game object containing a pointer to the model
     */
    @Override
    public IGame buildCity(BuildCityParams buildCity) throws IllegalCommandException, ModelException {
        IGame game = gameManager.getGame(buildCity.getGameId());
        new BuildCityCommand(game, game.getPlayer(buildCity.playerIndex), buildCity.vertexLocation);
        return game;
    }

    /**
     * Offers a trade to another player
     * Swagger URL Equivalent: /moves/offerTrade
     *
     * @param offerTrade JSON wrapper with parameters to offer a trade
     * @return Game object containing a pointer to the model
     */
    @Override
    public IGame offerTrade(OfferTradeParams offerTrade) throws IllegalCommandException, ModelException {
        IGame game = gameManager.getGame(offerTrade.getGameId());
        new OfferTradeCommand(game, game.getPlayer(offerTrade.playerIndex), game.getPlayer(offerTrade.receiver), offerTrade.offer);
        return game;
    }

    /**
     * Accept or reject a trade offered to you
     * Swagger URL Equivalent: /moves/acceptTrade
     *
     * @param acceptTrade JSON wrapper with parameters to accept a trade
     * @return Game object containing a pointer to the model
     */
    @Override
    public IGame acceptTrade(AcceptTradeParams acceptTrade) throws IllegalCommandException, ModelException {
        IGame game = gameManager.getGame(acceptTrade.getGameId());
        new AcceptTradeCommand(game, game.getPlayer(acceptTrade.playerIndex), acceptTrade.willAccept);
        return game;
    }

    /**
     * Execute a maritime trade operation
     * Swagger URL Equivalent: /moves/maritimeTrade
     *
     * @param maritimeTrade JSON wrapper with parameters to do a maritime trade
     * @return Game object containing a pointer to the model
     */
    @Override
    public IGame maritimeTrade(MaritimeTradeParams maritimeTrade) throws IllegalCommandException, ModelException {
        IGame game = gameManager.getGame(maritimeTrade.getGameId());
        new MaritimeTradeCommand(game, game.getPlayer(maritimeTrade.playerIndex), maritimeTrade.inputResource, maritimeTrade.outputResource, maritimeTrade.ratio);
        return game;
    }

    /**
     * Discards selected cards because of a rolled 7
     * Swagger URL Equivalent: /moves/discardCards
     *
     * @param discardCards JSON wrapper with parameters to discard card from the player hand
     * @return Game object containing a pointer to the model
     */
    @Override
    public IGame discardCards(DiscardCardParams discardCards) throws IllegalCommandException, ModelException {
        IGame game = gameManager.getGame(discardCards.getGameId());
        new DiscardCardsCommand(game, game.getPlayer(discardCards.playerIndex), discardCards.discardedCards);
        return game;
    }
}
