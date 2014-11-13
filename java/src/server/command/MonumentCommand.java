package server.command;

import shared.definitions.DevCardType;
import shared.model.IGame;
import shared.model.IPlayer;
import shared.model.ModelException;

import java.util.logging.Logger;

/**
 * Class that represents the Monument request
 *
 * @author StevenBarnett
 */
public class MonumentCommand extends AbstractCommand {

    private final static Logger logger = Logger.getLogger("catan");

    public IPlayer m_player;

    public MonumentCommand(IGame game, IPlayer player) throws IllegalCommandException {

        super(game, player, "Played a monument card");

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

        // Remove dev card from player's hand
        try {
            m_player.getPlayableDevCards().remove(DevCardType.MONUMENT);
        } catch (ModelException ex) {
            logger.fine("Tried to remove a Monument DevCard from a player's hand after it was played in " +
                    "MonumentCommand class. Exception message is as follows: " + ex.getMessage());
        }

        // Increment player's Monuments
        m_player.incrementMonuments();

        // Increment player's Victory Points
        m_player.addVictoryPoints(1);
    }
}
