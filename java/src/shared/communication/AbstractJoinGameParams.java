package shared.communication;

/**
 * Created by jeffreybacon on 11/4/14.
 */
public abstract class AbstractJoinGameParams implements IJoinGameParams {

    private int userId;

    @Override
    public void setUserId(int userId) {
        this.userId = userId;
    }
}