package shared.model;

import client.communication.LogEntry;

import java.util.List;

/**
 * Interface for ServerFacade
 */
public interface IServerFacade {
    /**
     * Takes in a chat message to be added to the chat log
     *
     * @param message the string of text to add to the chat log
     */
    public void sendChat(String message);
}
