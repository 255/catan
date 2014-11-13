package server.command;

import shared.model.IGame;
import shared.model.IPlayer;

/**
 * Class that represents the RollNumber request
 *
 * @author StevenBarnett
 */
public class RollNumberCommand extends AbstractCommand {
    private int m_playerIndex;
    private int m_number;

    public RollNumberCommand(IGame game, int playerIndex, int number) throws IllegalCommandException{
        super(game);
        m_playerIndex = playerIndex;
        m_number = number;

        IPlayer player = getGame().getPlayers().get(m_playerIndex);

        if(!getGame().isPlayersTurn(player)) {
            throw new IllegalCommandException(
                    "It is not this player's turn: " + m_playerIndex
            );
        }
    }

    /**
     * Determines what resources need to be given to
     * what players based on the number rolled, and
     * adds those resources to the player's hands.
     */
    public void execute() {

    }
}
