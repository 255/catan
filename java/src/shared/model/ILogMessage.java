package shared.model;

import java.util.List;

/**
 * Represents a message in the gameplay log or chat history.
 *
 * @author StevenBarnett
 */
public interface ILogMessage {
    /**
     * Get the player that this message is from.
     * @return the player this message is from
     */
    public IPlayer player();

    /**
     * Get the contents of the message.
     * @return the contents of the message
     */
    public String message();
}