package server.command;

import shared.model.IGame;
import shared.model.IPlayer;

/**
 * Class that represents the RollNumber request
 *
 * @author StevenBarnett
 */
public class RollNumberCommand extends AbstractCommand {
    private int m_number;

    public RollNumberCommand(IGame game, IPlayer player, int number) throws IllegalCommandException{
        super(game, player, "rolled a " + number);
        m_number = number;

        if(!getGame().isPlayersTurn(player)) {
            throw new IllegalCommandException(
                    "It is not this player's turn: " + getPlayer().getName()
            );
        }
    }

    /**
     * Determines what resources need to be given to
     * what players based on the number rolled, and
     * adds those resources to the player's hands.
     */
    public void performAction() {
        getGame().rollNumber(m_number);
    }
}
