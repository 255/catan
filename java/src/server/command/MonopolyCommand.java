package server.command;

import shared.model.IGame;
import shared.model.IPlayer;

/**
 * Class that represents the Monopoly request
 *
 * @author StevenBarnett
 */
public class MonopolyCommand extends AbstractCommand {

    public MonopolyCommand(IGame game, IPlayer player) {
        super(game);
    }
    /**
     * Performs a monopoly request. Takes the resource that
     * the player requested from all other player's hands
     * and adds them to the playing player's hand.
     */
    public void execute() {

    }
}
