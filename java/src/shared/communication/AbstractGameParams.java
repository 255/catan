package shared.communication;

/**
 * Created by jeffreybacon on 11/4/14.
 */
public abstract class AbstractGameParams implements IAbstractGameParams{

    private int userId;
    private int gameId;

    @Override
    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
}
