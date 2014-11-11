package shared.communication;

/**
 * Class that wraps the user credentials params
 *
 * Created by jeffreybacon on 11/4/14.
 */
public class CredentialsParams extends AbstractJoinGameParams{
    public final String username;
    public final String password;

    public CredentialsParams(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
