package server.command;

import shared.locations.HexLocation;
import shared.model.IGame;
import shared.model.IPlayer;
import shared.model.ModelException;
import shared.model.Player;

/**
 * Class that represents the RobPlayer request
 *
 * @author StevenBarnett
 */
public class RobPlayerCommand extends AbstractCommand {
    private IPlayer m_victim;
    private HexLocation m_hexLocation;

    public RobPlayerCommand(IGame game, IPlayer player, IPlayer victim, HexLocation location) throws IllegalCommandException {
        super(game, player, "moved the robber" + (victim != null ? " and robbed " + victim.getName() : ""));

        if (!getGame().getMap().canPlaceRobber(location)) {
            throw new IllegalCommandException(("Player " + player.getName() + "tried to place the robber " +
                    "on hex location " + location.toString() + " but the robber is already there"));
        }

        if (victim != null && victim.getResources().getCount() == 0) {
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

        assert (getGame().verifyResourceAmount());
    }

    @Override
    public void setGameAndPlayers(IGame game) throws ModelException {
        super.setGameAndPlayers(game);

        if (m_victim != null && m_victim.getId() != Player.NO_PLAYER) {
            m_victim = getGame().getPlayerByIndex(m_victim.getIndex());
        }
    }
}
