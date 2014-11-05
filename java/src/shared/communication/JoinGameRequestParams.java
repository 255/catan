package shared.communication;

/**
 * Created by jeffreybacon on 11/4/14.
 */
public class JoinGameRequestParams extends AbstractJoinGameParams{
    public final int id;
    public final String color;

    public JoinGameRequestParams(int id, String color) {
        this.id = id;
        this.color = color;
    }
}
