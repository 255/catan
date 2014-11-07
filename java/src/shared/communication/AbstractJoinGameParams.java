package shared.communication;

/**
 * Abstract class for parameter objects used when administering a game
 *
 * Created by jeffreybacon on 11/4/14.
 */
public abstract class AbstractJoinGameParams implements IJoinGameParams {

    private int userId;

    /**
     * Change the user id
     * @param userId the new user id
     */
    @Override
    public void setUserId(int userId) {
        this.userId = userId;
    }
}
