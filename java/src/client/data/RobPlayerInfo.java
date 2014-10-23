package client.data;

import shared.definitions.CatanColor;
import shared.model.IPlayer;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Used to pass player information into the rob view<br>
 * <br>
 * PROPERTIES:<br>
 * <ul>
 * <li>Id: Unique player ID</li>
 * <li>PlayerIndex: Player's order in the game [0-3]</li>
 * <li>Name: Player's name (non-empty string)</li>
 * <li>Color: Player's color (cannot be null)</li>
 * <li>NumCards: Number of resource cards the player has (>= 0)</li>
 * </ul>
 * 
 */
public class RobPlayerInfo extends PlayerInfo
{
    public final static RobPlayerInfo NO_PLAYER = new RobPlayerInfo(); // -1 ID

	private int numCards;

    public RobPlayerInfo(IPlayer player) {
        super(player.getId(), player.getIndex(), player.getName(), player.getColor());
        this.numCards = player.getResources().getCount();
    }

	public RobPlayerInfo()
	{
		super();
	}
	
	public int getNumCards()
	{
		return numCards;
	}
	
	public void setNumCards(int numCards)
	{
		this.numCards = numCards;
	}

    /**
     * Get an array of PlayerInfos from a collection of Player objects.
     */
    public static RobPlayerInfo[] fromPlayers(Collection<IPlayer> players) {
        Collection<RobPlayerInfo> playerInfos = new ArrayList<>(players.size());
        for (IPlayer player : players) {
            playerInfos.add(new RobPlayerInfo(player));
        }

        assert playerInfos.size() == players.size();
        return playerInfos.toArray(new RobPlayerInfo[playerInfos.size()]);
    }
}

