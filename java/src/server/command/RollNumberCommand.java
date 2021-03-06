package server.command;

import shared.model.GameState;
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

        if(!getGame().isPlayersTurn(player) || getGame().getGameState() != GameState.Rolling) {
            throw new IllegalCommandException("Player " + getPlayer().getName() + " cannot roll right now.");
        }
        else if (number < 2 || number > 12) {
            throw new IllegalCommandException("Illegal roll: " + number);
        }
    }

    /**
     * Determines what resources need to be given to
     * what players based on the number rolled, and
     * adds those resources to the player's hands.
     */
    public void performAction() {
        getGame().rollNumber(m_number);

        assert getGame().verifyResourceAmount();
    }
}
