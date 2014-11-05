package shared.communication;

/**
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
