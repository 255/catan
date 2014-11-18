package server.command;

import shared.locations.VertexLocation;
import shared.model.*;

/**
 * Class that represents the BuildSettlement request
 *
 * @author StevenBarnett
 */
public class BuildSettlementCommand extends AbstractCommand {
    private VertexLocation m_location;
    private boolean m_free;

    public BuildSettlementCommand(IGame game, IPlayer player, VertexLocation location, boolean free) throws IllegalCommandException {
        super(game, player, "built a settlement");

        if (!getGame().canPlaceSettlement(player, location)) {
            throw new IllegalCommandException("Player " + player.getName() + " cannot build a settlement at " + location);
        }

        m_location = location;
        m_free = free;
    }

    /**
     * Places a settlement on the map for the player that made
     * this request. Reduces the price of a settlement from the
     * player's resources.
     */
    protected void performAction() {
        Settlement settlement = getPlayer().buildSettlement(m_free);
        getGame().getMap().placeSettlement(settlement, m_location);

        if (!m_free) getGame().getResourceBank().add(Prices.SETTLEMENT); // player gave resources to bank

        getGame().calculateVictoryPoints();

        if (getGame().getGameState() == GameState.SecondRound) {
            assert m_free : "SecondRound, so settlement should be free";
            getGame().getMap().distributeInitialResources(settlement);
        }
    }
}
