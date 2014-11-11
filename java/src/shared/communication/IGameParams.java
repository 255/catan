package shared.communication;

/**
 * Parameter objects with a user ID and game ID attached to them.
 * Created by jeffreybacon on 11/4/14.
 */
public interface IGameParams {
    /**
     * Change the user id
     * @param userId the new user id
     */
    public void setUserId(int userId);

    /**
     * Get the user ID of the user who made the request
     * @return the user ID
     */
    public int getUserId();

    /**
     * Change the game id
     * @param gameId the new game id
     */
    public void setGameId(int gameId);

    /**
     * Get the game ID of the game this request is for
     * @return the game ID
     */
    public int getGameId();
}
