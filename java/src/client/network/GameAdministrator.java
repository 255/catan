package client.network;

import com.google.gson.JsonObject;
import client.network.NetworkException;
import com.google.gson.stream.JsonReader;
import shared.definitions.CatanColor;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author StevenBarnett
 */
public class GameAdministrator implements IGameAdministrator {

    private IGameAdminServerProxy m_gameAdminServerProxy;

    public GameAdministrator(IGameAdminServerProxy gameAdminServerProxy) {
        m_gameAdminServerProxy = gameAdminServerProxy;
    }

    @Override
    public boolean login(String username, String password) throws NetworkException {
        return m_gameAdminServerProxy.login(username, password);
    }

    @Override
    public boolean register(String username, String password) throws NetworkException {
        return m_gameAdminServerProxy.register(username, password);
    }

    @Override
    public List<IGameInfo> listGames() throws NetworkException, IOException {
        JsonReader reader = new JsonReader(new StringReader(m_gameAdminServerProxy.listGames()));
        return readGameList(reader);
    }

    @Override
    public boolean joinGame(int gameIndex, CatanColor playerColor) throws NetworkException {
        return m_gameAdminServerProxy.joinGame(gameIndex, playerColor);
    }

    @Override
    public IGameInfo createGame(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String gameName) throws NetworkException, IOException {
        JsonReader reader = new JsonReader(new StringReader(m_gameAdminServerProxy.createGame(randomTiles, randomNumbers, randomPorts, gameName)));
        return readGame(reader);
    }

    private List<IGameInfo> readGameList(JsonReader reader) throws IOException {
        List<IGameInfo> gameInfoList = new ArrayList<IGameInfo>();

        reader.beginArray();

        while (reader.hasNext()) {
            gameInfoList.add(readGame(reader));
        }

        reader.endArray();

        return gameInfoList;
    }

    private IGameInfo readGame(JsonReader reader) throws IOException {
        reader.beginObject();

        int gameIndex = 0;
        String gameName = null;
        List<String> listOfPlayerNames = null;

        while (reader.hasNext()) {
            String name = reader.nextName();

            if (name.equals("title")) {
                gameName = reader.nextString();
            } else if (name.equals("id")) {
                gameIndex = reader.nextInt();
            } else if (name.equals("players")) {
                listOfPlayerNames = readPlayers(reader);
            } else {
                reader.skipValue();
            }
        }

        reader.endObject();
        return new GameInfo(gameIndex, gameName, listOfPlayerNames);
    }

    private List<String> readPlayers(JsonReader reader) throws IOException {
        List<String> playerNames = new ArrayList<String>();

        reader.beginArray();

        while (reader.hasNext()) {
            String playerName = readPlayerName(reader);
            if (playerName != null) {
                playerNames.add(readPlayerName(reader));
            }
        }
        reader.endArray();

        return playerNames;
    }

    private String readPlayerName(JsonReader reader) throws IOException {
        reader.beginObject();

        String playerName = null;

        while (reader.hasNext()) {
            String name = reader.nextName();

            if (name.equals("name")) {
                playerName = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();

        return playerName;
    }
}
