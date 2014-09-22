package shared.model;

/**
 * This is the interface for the Serializer which is in charge of converting a
 * JSON string into a Game object and a Game object into a JSON string
 */
public interface ISerializer {
    /**
     * Takes in a String of text formatted as JSON, parses it and stores it in
     *  the game object which keeps track of the status of the game pieces
     *
     * @param json a string of text that is formatted as JSON
     *
     * @return the Game object that has all the JSON values parsed and stored in it
     */
    public IGame convertJSONtoModel(String json);

    /**
     * Takes in a String of text formatted as JSON, parses it and stores it in
     *  the game object which keeps track of the status of the game pieces
     *
     * @param theGame an object that stores all the information about the current state of the game
     *
     * @return the string of JSON generated by retrieving all the values from the game object
     */
    public String convertModelToJSON(IGame theGame);

}
