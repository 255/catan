package shared.communication;

/**
 * Abstract class for parameter objects used when playing a game
 * These adds a userId and a gameId, which are found in the client's cookies.
 *
 * Created by jeffreybacon on 11/4/14.
 */
public abstract class AbstractGameParams implements IGameParams {

    private int userId;
    private int gameId;

    /**
     * Change the user id
     * @param userId the new user id
     */
    @Override
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Change the game id
     * @param gameId the new game id
     */
    @Override
    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
}
