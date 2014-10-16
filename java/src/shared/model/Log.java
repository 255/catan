package shared.model;

import client.communication.LogEntry;
import shared.definitions.CatanColor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The gameplay log or chat history.
 */
public class Log implements ILog {
    private List<LogEntry> m_log;

    /**
     * Create a new empty log.
     */
    public Log() {
        m_log = new ArrayList<>();
    }

    /**
     * Add a message to the log. The message is added as the last entry.
     *
     * @param playerColor  the color of the player who originated the message
     * @param message the contents of the message
     */
    @Override
    public void addMessage(CatanColor playerColor, String message) {
        assert playerColor != null && message != null;
        m_log.add(new LogEntry(playerColor, message));
    }

    /**
     * Get the list of messages in the log.
     * The messages are ordered oldest to newest.
     *
     * @return an unmodifiable list of messages
     */
    @Override
    public List<LogEntry> getMessages() {
        return Collections.unmodifiableList(m_log);
    }

    public class LogMessage implements ILogMessage {
        private IPlayer m_player;
        private String m_message;

        /**
         * Construct a new Log message.
         * Since log messages can't exist without a log, this constructor is private.
         */
        private LogMessage(IPlayer player, String message) {
            m_player = player;
            m_message = message;
        }

        /**
         * Get the player that this message is from.
         *
         * @return the player this message is from
         */
        @Override
        public IPlayer getPlayer() {
             return m_player;
        }

        /**
         * Get the contents of the message.
         *
         * @return the contents of the message
         */
        @Override
        public String getMessage() {
            return m_message;
        }
    }
}
