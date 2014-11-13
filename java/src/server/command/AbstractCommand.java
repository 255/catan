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

    //DEFAULT CONSTRUCTOR if you want to try compiling
    //public AbstractCommand() { assert false; }

    public AbstractCommand(IGame game, IPlayer player, String actionDescription) {
        m_game = game;
        m_player = player;
        m_action = actionDescription;
    }

    /**
     * Implements all the functionality and interaction with the Server Model that
     * this command requires
     */
    @Override
    public final void execute() {
        logger.entering(this.getClass().getCanonicalName(), "execute");
        logAction();
        performAction();
        logger.exiting(this.getClass().getCanonicalName(), "execute");
    }

    /**
     * Perform the action associated with this command.
     */
    protected abstract void performAction();

    private void logAction() {
        String message = m_player.getName() + " " + m_action;
        getGame().getGameplayLog().addMessage(m_player.getColor(), message);
        logger.info(message);
    }

    protected IPlayer getPlayer() {
        return m_player;
    }

    protected IGame getGame() {
        return m_game;
    }
}
