package shared.model.serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;
import shared.definitions.CatanColor;
import shared.definitions.ResourceType;
import shared.model.CatanMap;
import shared.model.Game;
import shared.model.Log;
import shared.model.Player;

import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;

/**
 * Created by Wyatt on 11/13/2014.
 */
public class Serializer {
    private static Serializer serializer = new Serializer();
    private Gson gson;

    private Serializer() {
        initializeGson();
    }

    private void initializeGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Game.class, new GameAdapter());
        gsonBuilder.registerTypeAdapter(CatanColor.class, new EnumAdapter<>(CatanColor.class));
        gsonBuilder.registerTypeAdapter(ResourceType.class, new EnumAdapter<>(ResourceType.class));
        gsonBuilder.registerTypeAdapter(Log.class, new LogAdapter());
        gsonBuilder.registerTypeAdapter(CatanMap.class, new MapAdapter());
        gsonBuilder.registerTypeAdapter(Player.class, new PlayerAdapter());

        gson = gsonBuilder.create();
    }

    public static Serializer instance() {
        assert serializer != null;
        return serializer;
    }

    public String toJson(Object object) {
        return gson.toJson(object);
    }

    public void toJson(Object object, Writer writer) {
        gson.toJson(object, writer);
    }

    public <T> T fromJson(Reader reader, Class<T> destinationClass) {
        return gson.fromJson(reader, destinationClass);
    }

    public void toJson(Object src, JsonWriter jsonWriter) {
        gson.toJson(src, src.getClass(), jsonWriter);
    }
}
