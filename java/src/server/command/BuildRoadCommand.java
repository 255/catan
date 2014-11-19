package server.command;

import shared.locations.EdgeLocation;
import shared.model.IGame;
import shared.model.IPlayer;
import shared.model.Prices;

/**
 * Class that represents the BuildRoad request
 *
 * @author StevenBarnett
 */
public class BuildRoadCommand extends AbstractCommand {
    private EdgeLocation m_location;
    private boolean m_free;

    public BuildRoadCommand(IGame game, IPlayer player, EdgeLocation location, boolean free) throws IllegalCommandException {
        super(game, player, "built a road");

        assert location != null;

        if (!getGame().canPlaceRoad(player, location)) {
            throw new IllegalCommandException("Player " + player.getName() + " cannot build a road at " + location);
        }

        m_location = location;
        m_free = free;
    }

    /**
     * Places a road on the map for the player that made this
     * request. Reduces the price of a road from that
     * player's resources.
     */
    public void performAction() {
        getGame().getMap().placeRoad(getPlayer().buildRoad(m_free), m_location);

        if (!m_free) getGame().getResourceBank().add(Prices.ROAD); // player gave resources to bank

        getGame().calculateVictoryPoints();

        assert (getGame().verifyResourceAmount());
    }
}
