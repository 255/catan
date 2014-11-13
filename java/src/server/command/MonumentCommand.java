package server.command;

import shared.model.IGame;
import shared.model.IPlayer;

/**
 * Class that represents the Monument request
 *
 * @author StevenBarnett
 */
public class MonumentCommand extends AbstractCommand {

    public IPlayer m_player;

    public MonumentCommand(IGame game, IPlayer player) throws IllegalCommandException {

        super(game, player, "played a monument card");

        if (!getGame().canPlayMonument(m_player)) {
            throw new IllegalCommandException("Player attempting to play monument card cannot do that");
        }
    }
    /**
     * Request representing a player playing a Monument
     * dev card. Moves the dev card from the old dev cards hand.
     * Adds a victory point to the player's total.
     */
    public void performAction() {

    }
}
