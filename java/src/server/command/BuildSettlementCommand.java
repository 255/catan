package server.command;

import shared.locations.VertexLocation;
import shared.model.IGame;
import shared.model.IPlayer;

/**
 * Class that represents the BuildSettlement request
 *
 * @author StevenBarnett
 */
public class BuildSettlementCommand extends AbstractCommand {
    private IPlayer m_player;
    private VertexLocation m_location;
    private boolean m_free;

    public BuildSettlementCommand(IGame game, IPlayer player, VertexLocation location, boolean free) throws IllegalCommandException {
        super(game);

        if (!getGame().playerCanPlaceSettlement(player, location)) {
            throw new IllegalCommandException("Player " + player.getName() + " cannot build a settlement at " + location);
        }

        m_player = player;
        m_location = location;
        m_free = free;
    }

    /**
     * Places a settlement on the map for the player that made
     * this request. Reduces the price of a settlement from the
     * player's resources.
     */
    public void execute() {
        getGame().getMap().placeSettlement(m_player.buildSettlement(m_free), m_location);
    }
}
