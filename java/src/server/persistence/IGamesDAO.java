package server.persistence;

/**
 * Created by Spencer Weight - 12/1/2014.
 */
public interface IGamesDAO {

    /**
     * Saves a game to the data persistence implementation
     *
     * @param json the json representation of the game model
     */
    public void saveGame(String json);

    /**
     * Saves a command to the data persistence implementation
     *
     * @param json the json representation of the game model
     */
    public void saveCommand(String json);
}
