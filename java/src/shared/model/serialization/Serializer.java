package shared.model.serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import shared.definitions.CatanColor;
import shared.model.Game;

import java.io.Reader;
import java.io.Writer;

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
        gsonBuilder.registerTypeAdapter(Game.class, new GameSerializer()).create();
        gsonBuilder.registerTypeAdapter(CatanColor.class, new CatanColorAdapter()).create();

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
}
