package shared.communication;

/**
 * Class that represents the SetLogLevelRequest params
 *
 * Created by jeffreybacon on 11/4/14.
 */
public class SetLogLevelRequestParams extends AbstractGameParams{
    public final String logLevel;

    public SetLogLevelRequestParams(String logLevel) {
        this.logLevel = logLevel;
    }
}
