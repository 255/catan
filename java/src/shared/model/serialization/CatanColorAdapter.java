package shared.model.serialization;

import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.MalformedJsonException;
import shared.definitions.CatanColor;

import java.io.IOException;

/**
 * Convert between CatanColor objects and JSON.
 */
public class CatanColorAdapter extends TypeAdapter<CatanColor> {
    @Override
    public void write(JsonWriter jsonWriter, CatanColor catanColor) throws IOException {
        jsonWriter.value(catanColor.toString().toLowerCase());
    }

    @Override
    public CatanColor read(JsonReader reader) throws IOException {
        try {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
                return null;
            }
            else {
                return CatanColor.valueOf(reader.nextString().toUpperCase());
            }
        }
        catch (IllegalArgumentException | JsonSyntaxException | MalformedJsonException e) {
            throw new MalformedJsonException("Unrecognized CatanColor.", e);
        }
    }
}
