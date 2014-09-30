package shared.model;

import client.communication.LogEntry;

import java.util.List;

/**
 * Interface for ServerFacade
 */
public interface IServerFacade {
    /**
     * Returns the chat history of the game
     *
     * @return the log initialized with all the chat messages
     */
    public List<LogEntry> getChatHistory();

    /**
     * Returns the move/action history of the game
     *
     * @return the log initialized with all the move/action messages
     */
    public List<LogEntry> getMoveHistory();

    /**
     * Takes in a chat message to be added to the chat log
     *
     * @param message the string of text to add to the chat log
     */
    public void sendChat(String message);
}
