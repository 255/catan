package shared.communication;

/**
 * Created by jeffreybacon on 11/4/14.
 */
public class AddAIRequestParams extends AbstractGameParams{
    public final String AIType;

    public AddAIRequestParams(String AIType) {
        this.AIType = AIType;
    }
}
