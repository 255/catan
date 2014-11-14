package shared.model.serialization;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import shared.definitions.HexType;
import shared.definitions.ResourceType;
import shared.model.CatanMap;
import shared.model.ITile;

import java.io.IOException;

/**
 * Created by Wyatt on 11/13/2014.
 */
public class MapAdapter extends TypeAdapter<CatanMap> {

    @Override
    public void write(JsonWriter jsonWriter, CatanMap map) throws IOException {
        // TODO: radius(?!), roads, cities, settlements

        jsonWriter.beginObject();

        jsonWriter.name("hexes");
        jsonWriter.beginArray();
        for (ITile tile : map.getTiles()) {
            writeTile(jsonWriter, tile);
        }
        jsonWriter.endArray();

        jsonWriter.name("robber");
        Serializer.instance().toJson(map.getRobber(), jsonWriter);

        jsonWriter.endObject();
    }

    private void writeTile(JsonWriter jsonWriter, ITile tile) throws IOException {
        jsonWriter.beginObject();

        HexType type = tile.type();
        if (type != HexType.DESERT) {
            jsonWriter.name("resource").value(tile.type().toString().toLowerCase());
            jsonWriter.name("number").value(tile.numberToken());
        }

        jsonWriter.name("location");
        Serializer.instance().toJson(tile.location(), jsonWriter);

        jsonWriter.endObject();
    }

    @Override
    public CatanMap read(JsonReader jsonReader) throws IOException {
        return null;
    }
}
