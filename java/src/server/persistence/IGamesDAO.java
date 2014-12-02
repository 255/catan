package server.persistence;

/**
 * Created by Spencer Weight - 12/1/2014.
 */
public interface IGamesDAO {

    public void saveGame(String json);

    public void saveCommand(String json);
}
