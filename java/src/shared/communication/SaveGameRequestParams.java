package shared.communication;

/**
 * Class that represents the SaveGameRequest params
 *
 * Created by jeffreybacon on 11/4/14.
 */
public class SaveGameRequestParams extends AbstractJoinGameParams{
    public final int id;
    public final String name;

    public SaveGameRequestParams(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
