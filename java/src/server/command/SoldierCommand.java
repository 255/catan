package server.command;

import shared.definitions.DevCardType;
import shared.locations.HexLocation;
import shared.model.IGame;
import shared.model.IPlayer;

import java.util.logging.Logger;

/**
 * Class that represents the playing a soldier dev card
 *
 * @author StevenBarnett
 */
public class SoldierCommand extends AbstractCommand {

    private final static Logger logger = Logger.getLogger("catan");

    private IPlayer m_victim;
    private HexLocation m_hexLocation;

    public SoldierCommand(IGame game, IPlayer player, IPlayer victim, HexLocation hexLocation) throws IllegalCommandException {
        super(game, player, "played a soldier card and " + (victim != null ? "robbed " + victim.getName() : "moved the robber"));

        if (!getGame().canPlaySoldier(hexLocation, player)) {
            throw new IllegalCommandException("Player " + player.getName() + " tried to play a soldier card and move the " +
                                                "the robber to location " + hexLocation.toString());
        }

        m_victim = victim;
        m_hexLocation = hexLocation;
    }

    /**
     * Player plays a Soldier DevCard.
     */
    public void performAction() {
        getPlayer().playDevCard(DevCardType.SOLDIER);

        //Increments the player's soldier count
        getPlayer().incrementSoldiers();

        // Performs the robbing of the player
        getGame().robPlayer(getPlayer(), m_victim, m_hexLocation);

        getGame().calculateVictoryPoints();
        assert (getGame().verifyResourceAmount());
    }
}
