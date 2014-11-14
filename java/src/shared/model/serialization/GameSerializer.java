package shared.model.serialization;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import shared.model.Game;

import java.io.IOException;

/**
 * A type adapter that tells a Gson object how to serialize a Game object.
 */
public class GameSerializer extends TypeAdapter<Game> {
    /**
     * Convert the given Game to JSON.
     * @param jsonWriter where the JSON is written
     * @param game the game object to serialize
     * @throws IOException
     */
    @Override
    public void write(JsonWriter jsonWriter, Game game) throws IOException {

    }

    /**
     * This will not be implemented, since we already have Game deserialization code.
     * @param jsonReader
     * @return the game object
     * @throws IOException
     */
    @Override
    public Game read(JsonReader jsonReader) throws IOException {
        return null;
    }
}