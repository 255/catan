package server.command;

import shared.model.IGame;
import shared.model.IPlayer;

/**
 * Class that represents the FinishTurn request
 *
 * @author StevenBarnett
 */
public class FinishTurnCommand extends AbstractCommand {

    public FinishTurnCommand(IGame game, IPlayer player, String actionDescription) {
        super(game, player, actionDescription);
        // TODO: implement
    }

    /**
     * Moves the turn to the next player in the
     * specified game.
     */
    public void performAction() {

    }
}
