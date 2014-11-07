package shared.communication;

/**
 * Parameter objects with a user ID attached to them.
 * Created by jeffreybacon on 11/4/14.
 */
public interface IJoinGameParams {
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
}
