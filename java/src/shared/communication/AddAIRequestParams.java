package shared.communication;

/**
 * Class that represents the AddAIRequest params
 *
 * Created by jeffreybacon on 11/4/14.
 */
public class AddAIRequestParams extends AbstractGameParams{
    public final String AIType;

    public AddAIRequestParams(String AIType) {
        this.AIType = AIType;
    }
}
