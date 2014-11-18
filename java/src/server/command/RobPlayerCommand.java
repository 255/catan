package server.command;

import shared.locations.HexLocation;
import shared.model.IGame;
import shared.model.IPlayer;

/**
 * Class that represents the RobPlayer request
 *
 * @author StevenBarnett
 */
public class RobPlayerCommand extends AbstractCommand {

    private IPlayer m_victim;
    private HexLocation m_hexLocation;


    public RobPlayerCommand(IGame game, IPlayer player, IPlayer victim, HexLocation location) throws IllegalCommandException {
        super(game, player, "robbed " + victim.getName());

        if (getGame().getMap().canPlaceRobber(location)) {
            throw new IllegalCommandException(("Player " + player.getName() + "tried to place the robber " +
                    "on hex location " + location.toString() + " but the robber is already there"));
        }

        if (victim.getResources().getCount() == 0) {
            throw new IllegalCommandException("Player " + player.getName() + " tried to rob from " + victim.getName() + " but they had no cards");
        }

        m_victim = victim;
        m_hexLocation = location;
    }

    /**
     * Takes a random card from the robbed player's hand
     * and moves that card to the robbing player's hand.
     */
    public void performAction() {
        getGame().robPlayer(getPlayer(), m_victim, m_hexLocation);
    }
}
