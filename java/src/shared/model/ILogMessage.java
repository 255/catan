package shared.model;

import java.util.List;

/**
 * Created by sdaltonb on 9/20/14.
 *
 * Represents a message in the gameplay log or chat history.
 */
public interface ILogMessage {
    /**
     * Get the player that this message is from.
     * @return the player this message is from
     */
    public IPlayer getPlayer();

    /**
     * Get the contents of the message.
     * @return the contents of the message
     */
    public String getMessage();
}