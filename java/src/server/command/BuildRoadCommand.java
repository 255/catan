package server.command;

import shared.locations.EdgeLocation;
import shared.model.IGame;
import shared.model.IPlayer;

/**
 * Class that represents the BuildRoad request
 *
 * @author StevenBarnett
 */
public class BuildRoadCommand extends AbstractCommand {
    private IPlayer m_player;
    private EdgeLocation m_location;
    private boolean m_free;

    public BuildRoadCommand(IGame game, IPlayer player, EdgeLocation location, boolean free) throws IllegalCommandException {
        super(game);

        if (!getGame().playerCanPlaceRoad(player, location)) {
            throw new IllegalCommandException("Player " + player.getName() + " cannot build a road at " + location);
        }

        m_player = player;
        m_location = location;
        m_free = free;
    }

    /**
     * Places a road on the map for the player that made this
     * request. Reduces the price of a road from that
     * player's resources.
     */
    public void execute() {
        getGame().getMap().placeRoad(m_player.buildRoad(m_free), m_location);
    }
}
