package server.facade;

import server.command.*;
import server.persistence.IPersistenceManager;
import server.persistence.PersistenceException;
import shared.communication.*;
import shared.model.IGame;
import shared.model.IGameManager;
import shared.model.ModelException;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Spencer Weight - 11/5/2014.
 */
public class MovesFacade implements IMovesFacade {
    private static Logger logger = Logger.getLogger("catanserver");

    private IPersistenceManager m_persistenceManager;
    private IGameManager gameManager;

    public MovesFacade(IGameManager gameManager, IPersistenceManager persistenceManager) {
        this.gameManager = gameManager;
        m_persistenceManager = persistenceManager;
    }

    @Override
    public IGame sendChat(SendChatParams params) throws IllegalCommandException, ModelException {
        IGame game = gameManager.getGame(params.getGameId());
        ICommand command = new SendChatCommand(game, game.getPlayerByIndex(params.playerIndex), params.content);
        serializeCommand(m_persistenceManager, command);
        command.execute();
        return game;
    }

    @Override
    public IGame rollNumber(RollNumberParams rollNumber) throws IllegalCommandException, ModelException {
        IGame game = gameManager.getGame(rollNumber.getGameId());
        ICommand command = new RollNumberCommand(game, game.getPlayerByIndex(rollNumber.playerIndex), rollNumber.number);
        serializeCommand(m_persistenceManager, command);
        command.execute();
        return game;
    }

    @Override
    public IGame robPlayer(RobbingParams robbing) throws IllegalCommandException, ModelException {
        IGame game = gameManager.getGame(robbing.getGameId());
        ICommand command = new RobPlayerCommand(game, game.getPlayerByIndex(robbing.playerIndex), game.getPlayerByIndex(robbing.victimIndex), robbing.location);
        serializeCommand(m_persistenceManager, command);
        command.execute();
        return game;
    }

    @Override
    public IGame finishTurn(PlayerIndexParam playerIndex) throws IllegalCommandException, ModelException {
        IGame game = gameManager.getGame(playerIndex.getGameId());
        ICommand command = new FinishTurnCommand(game, game.getPlayerByIndex(playerIndex.playerIndex));
        serializeCommand(m_persistenceManager, command);
        command.execute();
        return game;
    }

    @Override
    public IGame buyDevCard(PlayerIndexParam playerIndex) throws IllegalCommandException, ModelException {
        IGame game = gameManager.getGame(playerIndex.getGameId());
        ICommand command = new BuyDevCardCommand(game, game.getPlayerByIndex(playerIndex.playerIndex));
        serializeCommand(m_persistenceManager, command);
        command.execute();
        return game;
    }

    @Override
    public IGame yearOfPlenty(YearOfPlentyParams yearOfPlenty) throws IllegalCommandException, ModelException {
        IGame game = gameManager.getGame(yearOfPlenty.getGameId());
        ICommand command = new YearOfPlentyCommand(game, game.getPlayerByIndex(yearOfPlenty.playerIndex), yearOfPlenty.resource1, yearOfPlenty.resource2);
        serializeCommand(m_persistenceManager, command);
        command.execute();
        return game;
    }

    @Override
    public IGame roadBuilding(RoadBuildingParams roadBuilding) throws IllegalCommandException, ModelException {
        IGame game = gameManager.getGame(roadBuilding.getGameId());
        ICommand command = new RoadBuildingCommand(game, game.getPlayerByIndex(roadBuilding.playerIndex), roadBuilding.spot1, roadBuilding.spot2);
        serializeCommand(m_persistenceManager, command);
        command.execute();
        return game;
    }

    @Override
    public IGame soldier(RobbingParams robbing) throws IllegalCommandException, ModelException {
        IGame game = gameManager.getGame(robbing.getGameId());
        ICommand command = new SoldierCommand(game, game.getPlayerByIndex(robbing.playerIndex), game.getPlayerByIndex(robbing.victimIndex), robbing.location);
        serializeCommand(m_persistenceManager, command);
        command.execute();
        return game;
    }

    @Override
    public IGame monopoly(MonopolyParams monopoly) throws IllegalCommandException, ModelException {
        IGame game = gameManager.getGame(monopoly.getGameId());
        ICommand command = new MonopolyCommand(game, game.getPlayerByIndex(monopoly.playerIndex), monopoly.resource);
        serializeCommand(m_persistenceManager, command);
        command.execute();
        return game;
    }

    @Override
    public IGame monument(PlayerIndexParam playerIndex) throws IllegalCommandException, ModelException {
        IGame game = gameManager.getGame(playerIndex.getGameId());
        ICommand command = new MonumentCommand(game, game.getPlayerByIndex(playerIndex.playerIndex));
        serializeCommand(m_persistenceManager, command);
        command.execute();
        return game;
    }

    @Override
    public IGame buildRoad(BuildRoadParams buildRoad) throws IllegalCommandException, ModelException {
        IGame game = gameManager.getGame(buildRoad.getGameId());
        ICommand command = new BuildRoadCommand(game, game.getPlayerByIndex(buildRoad.playerIndex), buildRoad.roadLocation, buildRoad.free);
        serializeCommand(m_persistenceManager, command);
        command.execute();
        return game;
    }

    @Override
    public IGame buildSettlement(BuildSettlementParams params) throws IllegalCommandException, ModelException {
        IGame game = gameManager.getGame(params.getGameId());
        ICommand command = new BuildSettlementCommand(game, game.getPlayerByIndex(params.playerIndex), params.vertexLocation, params.free);
        serializeCommand(m_persistenceManager, command);
        command.execute();
        return game;
    }

    @Override
    public IGame buildCity(BuildCityParams buildCity) throws IllegalCommandException, ModelException {
        IGame game = gameManager.getGame(buildCity.getGameId());
        ICommand command = new BuildCityCommand(game, game.getPlayerByIndex(buildCity.playerIndex), buildCity.vertexLocation);
        serializeCommand(m_persistenceManager, command);
        command.execute();
        return game;
    }

    @Override
    public IGame offerTrade(OfferTradeParams offerTrade) throws IllegalCommandException, ModelException {
        IGame game = gameManager.getGame(offerTrade.getGameId());
        ICommand command = new OfferTradeCommand(game, game.getPlayerByIndex(offerTrade.playerIndex), game.getPlayerByIndex(offerTrade.receiver), offerTrade.offer);
        serializeCommand(m_persistenceManager, command);
        command.execute();
        return game;
    }

    @Override
    public IGame acceptTrade(AcceptTradeParams acceptTrade) throws IllegalCommandException, ModelException {
        IGame game = gameManager.getGame(acceptTrade.getGameId());
        ICommand command = new AcceptTradeCommand(game, game.getPlayerByIndex(acceptTrade.playerIndex), acceptTrade.willAccept);
        serializeCommand(m_persistenceManager, command);
        command.execute();
        return game;
    }

    @Override
    public IGame maritimeTrade(MaritimeTradeParams maritimeTrade) throws IllegalCommandException, ModelException {
        IGame game = gameManager.getGame(maritimeTrade.getGameId());
        ICommand command = new MaritimeTradeCommand(game, game.getPlayerByIndex(maritimeTrade.playerIndex), maritimeTrade.inputResource, maritimeTrade.outputResource, maritimeTrade.ratio);
        serializeCommand(m_persistenceManager, command);
        command.execute();
        return game;
    }

    @Override
    public IGame discardCards(DiscardCardParams discardCards) throws IllegalCommandException, ModelException {
        IGame game = gameManager.getGame(discardCards.getGameId());
        ICommand command = new DiscardCardsCommand(game, game.getPlayerByIndex(discardCards.playerIndex), discardCards.discardedCards);
        serializeCommand(m_persistenceManager, command);
        command.execute();
        return game;
    }

    /**
     * Write a command to file.
     * @param command the command to save
     */
    public static void serializeCommand(IPersistenceManager manager, ICommand command) {
        try {
            manager.startTransaction();
            manager.createCommandsDAO().saveCommand(command);
            manager.endTransaction(true);
            logger.finer("Successfully serialize command " + command.getGame().getModelVersion() + " in game " + command.getGame().getID());
        }
        catch (PersistenceException e) {
            manager.endTransaction(false);
            logger.log(Level.SEVERE, "Failed to serialize command " + command.getGame().getModelVersion() + " in game " + command.getGame().getID(), e);
        }
    }
}
