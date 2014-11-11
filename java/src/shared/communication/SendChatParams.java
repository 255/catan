package shared.communication;

/**
 * Class that represents the SendChat params
 *
 * Created by jeffreybacon on 11/4/14.
 */
public class SendChatParams extends AbstractGameParams{
    public final int playerIndex;
    public final String content;

    public SendChatParams(int playerIndex, String content) {
        this.playerIndex = playerIndex;
        this.content = content;
    }
}
