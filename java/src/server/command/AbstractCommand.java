package server.command;

import shared.communication.AbstractGameParams;
import shared.model.IGame;

/**
 * Interface that facades require to execute certain commands on the Server Model
 *
 * @author StevenBarnett
 */
public abstract class AbstractCommand implements ICommand {
    private IGame m_game;

    //DEFAULT CONSTRUCTOR if you want to try compiling
    //public AbstractCommand() { assert false; }

    public AbstractCommand(IGame game) {
        m_game = game;
    }

    protected IGame getGame() {
        return m_game;
    }
}
