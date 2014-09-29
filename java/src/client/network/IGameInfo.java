package client.network;

import java.util.List;

/**
 * Interface to represent basic information about a game
 *
 * @author StevenBarnett
 */
public interface IGameInfo {
    /**
     * Returns the index of this game
     *
     * @return the index of the game
     */
    public int gameIndex();

    /**
     * Returns the name of this game
     *
     * @return name of this game
     */
    public String gameName();

    /**
     * Returns a list of players that have joined this game
     *
     * @return list of strings of names of players
     */
    public List<String> listOfPlayers();

    /**
     * Determines if the game is full or not
     *
     * @return a flag indicating if the game is full or not
     */
    public boolean isFull();
}
