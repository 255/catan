package server.command;

import shared.definitions.DevCardType;
import shared.locations.HexLocation;
import shared.model.IGame;
import shared.model.IPlayer;
import shared.model.ModelException;

import java.nio.channels.IllegalChannelGroupException;
import java.util.logging.Logger;

/**
 * Class that represents the Soldier request
 *
 * @author StevenBarnett
 */
public class SoldierCommand extends AbstractCommand {

    private final static Logger logger = Logger.getLogger("catan");

    private IPlayer m_victim;
    private HexLocation m_hexLocation;

    public SoldierCommand(IGame game, IPlayer player, IPlayer victim, HexLocation hexLocation) throws IllegalCommandException {
        super(game, player, "played a soldier card");

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

        //Remove Soldier DevCard from player's DevCardHand
        try {
            getPlayer().getPlayableDevCards().remove(DevCardType.SOLDIER);
        } catch (ModelException ex) {
            logger.fine("When " + getPlayer().getName() + " played a soldier development card, removing that card from the " +
                    "player's hand caused an exception to be thrown");
        }

        //Increments the player's soldier count
        getPlayer().incrementSoldiers();

        // Performs the robbing of the player
        getGame().robPlayer(getPlayer(), m_victim, m_hexLocation);
    }
}
