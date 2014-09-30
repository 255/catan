package shared.model;

import client.communication.LogEntry;

import java.util.List;

/**
 * Handles all the calls to the ServerProxy
 */
public class ServerFacade implements IServerFacade {
    /**
     * Returns the chat history of the game
     *
     * @return the log initialized with all the chat messages
     */
    @Override
    public List<LogEntry> getChatHistory() {
        return null;
    }

    /**
     * Returns the move/action history of the game
     *
     * @return the log initialized with all the move/action messages
     */
    @Override
    public List<LogEntry> getMoveHistory() {
        return null;
    }

    /**
     * Takes in a chat message to be added to the chat log
     *
     * @param message the string of text to add to the chat log
     */
    @Override
    public void sendChat(String message) {

    }
}
