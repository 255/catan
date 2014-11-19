package server.facade;

import com.google.gson.Gson;
import shared.communication.*;
import shared.model.*;

import java.io.*;

/**
 * Created by Spencer Weight - 11/5/2014.
 */
public class JoinGameFacade implements IJoinGameFacade{
    private IGameManager m_gameManager;
    private IUserManager m_userManager;

    public JoinGameFacade(IGameManager gameManager, IUserManager userManager) {
        m_gameManager = gameManager;
        m_userManager = userManager;
    }

    /**
     * Get a list of all games in progress
     * Swagger URL Equivalent: /games/list (get request)
     *
     * @return GamesList object containing the list of current running games on the server
     */
    @Override
    public GameInfo[] list() {
        return GameInfo.toGameInfoArray(m_gameManager.listGames());
    }

    /**
     * Creates a game and puts it in the Games List
     * Swagger URL Equivalent: /games/create
     *
     * @param params the JSON wrapper with the parameters for creating a game
     * @return Game object containing a pointer to the created game
     */
    @Override
    public GameInfo create(CreateGameRequestParams params) throws ModelException {
        return new GameInfo(m_gameManager.createGame(params.name, params.randomPorts, params.randomTiles, params.randomNumbers));
    }

    /**
     * Join or re-join a game (modifies cookie)
     * Swagger URL Equivalent: /games/join
     *
     * @param params the JSON wrapper with the parameters for joining a game
     * @return boolean containing true or false depending on if the join was successful
     */
    @Override
    public Integer join(JoinGameRequestParams params) throws ModelException {
        boolean joined = m_gameManager.joinGame(params.id, m_userManager.getUser(params.getUserId()), params.color);
        return (joined ? params.id : null);
    }

    /**
     * Saves a game
     * Swagger URL Equivalent: /games/save
     *
     * @param saveGame the JSON wrapper with the parameters for saving a game
     */
    @Override
    public void save(SaveGameRequestParams saveGame) throws IOException, ModelException {
        IGame game = m_gameManager.getGame(saveGame.id);

        try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(saveGame.name))) {
            writer.writeObject(game);
        }
    }

    /**
     * Loads a game
     * Swagger URL Equivalent: /games/load
     *
     * @param loadGame the JSON wrapper with the parameters for loading a game
     */
    @Override
    public void load(LoadGameRequestParams loadGame) throws IOException {
        try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(loadGame.name))) {
            m_gameManager.loadGame((IGame)reader.readObject());
        } catch (ClassNotFoundException e) {
            throw new IOException("Failed reading game from disk.", e);
        }
    }
}
