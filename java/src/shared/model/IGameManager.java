package shared.model;

import shared.definitions.CatanColor;

/**
 * @author StevenBarnett
 */
public interface IGameManager {

    public boolean joinGame(int gameIndex, IUser user, CatanColor playerColor);

    public Game createGame(String gameName, boolean randomPorts, boolean randomTiles, boolean randomNumbers);
}
