package shared.communication;

/**
 * Class that represents the LoadGameRequest params
 *
 * Created by jeffreybacon on 11/4/14.
 */
public class LoadGameRequestParams extends AbstractJoinGameParams{
    public final String name;

    public LoadGameRequestParams(String name) {
        this.name = name;
    }
}
