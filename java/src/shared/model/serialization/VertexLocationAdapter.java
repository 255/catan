package shared.model.serialization;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import shared.locations.VertexLocation;

import java.io.IOException;

/**
 * Output a VertexLocation
 */
public class VertexLocationAdapter extends TypeAdapter<VertexLocation> {
    @Override
    public void write(JsonWriter writer, VertexLocation location) throws IOException {
        writer.beginObject();
        writer.name("direction").value(location.getDir().toAbbreviation());
        writer.name("x").value(location.getHexLoc().getX());
        writer.name("y").value(location.getHexLoc().getY());
        writer.endObject();
    }

    @Override
    public VertexLocation read(JsonReader jsonReader) throws IOException {
        return null;
    }
}
