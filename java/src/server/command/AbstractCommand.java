package server.command;

import shared.model.IGame;
import shared.model.IPlayer;

import java.util.logging.Logger;

/**
 * Interface that facades require to execute certain commands on the Server Model
 *
 * @author StevenBarnett
 */
public abstract class AbstractCommand implements ICommand {
    private static Logger logger = Logger.getLogger("catanserver");

    private IPlayer m_player;
    private IGame m_game;
    private String m_action;

    public AbstractCommand(IGame game, IPlayer player, String actionDescription) throws IllegalCommandException {
        if (player == null) {
            throw new IllegalCommandException("No player was specified.");
        }

        assert game != null;
        m_game = game;
        m_player = player;
        m_action = actionDescription;
    }

    /**
     * A constructor for a command that does not produce a log message.
     *
     * @param game
     * @param player
     */
    public AbstractCommand(IGame game, IPlayer player) throws IllegalCommandException {
        this(game, player, null);
    }

    /**
     * Implements all the functionality and interaction with the Server Model that
     * this command requires
     */
    @Override
    public final void execute() {
        logger.entering(this.getClass().getCanonicalName(), "execute");
        getGame().incrementVersion();
        logAction();
        performAction();
        logger.exiting(this.getClass().getCanonicalName(), "execute");
    }

    /**
     * Perform the action associated with this command.
     */
    protected abstract void performAction();

    /**
     * Add the game play log entry for this command.
     * If no message is specified (e.g. for send chat), the message is not logged.
     */
    private void logAction() {
        if (m_action != null) {
            String message = m_player.getName() + " " + m_action;
            getGame().getGameplayLog().addMessage(m_player, message);
            logger.info(message);
        }
    }

    protected IPlayer getPlayer() {
        return m_player;
    }

    protected IGame getGame() {
        return m_game;
    }
}
