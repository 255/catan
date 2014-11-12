package server.command;

import shared.model.IGame;
import shared.model.IGameManager;

/**
 * Class that represents the CreateGame request
 *
 * @author StevenBarnett
 */
public class CreateGameCommand {
    private IGameManager m_manager;
    private String m_name;
    private boolean m_randomPorts;
    private boolean m_randomTiles;
    private boolean m_randomNumbers;

    public CreateGameCommand(IGameManager manager, String name, boolean randomPorts, boolean randomTiles, boolean randomNumbers) {
        m_manager = manager;
        m_name = name;
        m_randomPorts = randomPorts;
        m_randomTiles = randomTiles;
        m_randomNumbers = randomNumbers;
    }

    /**
     * Creates a new game in the IGameManager, based on the
     * specifications in the request.
     */
    public void execute() {
        m_manager.createGame(m_name, m_randomPorts, m_randomTiles, m_randomNumbers);
    }
}
