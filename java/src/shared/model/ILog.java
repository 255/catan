package shared.model;

import client.communication.LogEntry;

import java.io.Serializable;
import java.util.List;

/**
 * Represents the the gameplay log or chat history.
 *
 * @author StevenBarnett, Wyatt
 */
public interface ILog extends Serializable {
    /**
     * Add a message to the log. The message is added as the last entry.
     * @param player the player who originated the message
     * @param message the contents of the message
     */
    public void addMessage(IPlayer player, String message);

    /**
     * Get the list of messages in the log.
     * The messages are ordered oldest to newest.
     * @return an unmodifiable list of messages
     */
    public List<LogEntry> getLogEntries();

    /**
     * An individual message in a
     */
    public interface ILogMessage extends Serializable {
        /**
         * Get the player that this message is from.
         *
         * @return the player this message is from
         */
        public IPlayer getPlayer();

        /**
         * Get the contents of the message.
         *
         * @return the contents of the message
         */
        public String getMessage();
    }
}