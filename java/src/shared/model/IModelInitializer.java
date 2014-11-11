package shared.model;

/**
 * Build a brand new collection of model classes in memory.
 * This is the interface for the Serializer which is in charge of converting a
 * JSON string into a Game object and a Game object into a JSON string
 */
public interface IModelInitializer {
    /**
     * Takes in a String of text formatted as JSON, parses it and stores it in
     * the game object which keeps track of the status of the game pieces
     *
     * @param clientModelJson a the client model as JSON-formatted text
     * @param localPlayerID the player ID for the person sitting in front of the screen
     */
    public void initializeClientModel(String clientModelJson, int localPlayerID) throws ModelException;
}
