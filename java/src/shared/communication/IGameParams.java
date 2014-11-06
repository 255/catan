package shared.communication;

/**
 * Created by jeffreybacon on 11/4/14.
 */
public interface IGameParams {
    /**
     * Change the user id
     * @param userId the new user id
     */
    public void setUserId(int userId);

    /**
     * Change the game id
     * @param gameId the new game id
     */
    public void setGameId(int gameId);
}
