package server.command;

import shared.locations.VertexLocation;
import shared.model.IGame;
import shared.model.IPlayer;

/**
 * Class that represents the BuildCity request
 *
 * @author StevenBarnett
 */
public class BuildCityCommand extends AbstractCommand {
    private VertexLocation m_location;

    public BuildCityCommand(IGame game, IPlayer player, VertexLocation location) throws IllegalCommandException {
        super(game, player, "built a city");

        if (!getGame().canBuildCity(player, location)) {
            throw new IllegalCommandException("Player " + player.getName() + " cannot build a city at " + location);
        }

        m_location = location;
    }

    /**
     * Places a city on the map for the player that made this
     * request. Reduces the price of the city from the
     * player's resources.
     */
    public void performAction() {
        getGame().getMap().placeCity(getPlayer().buildCity(), m_location);

        getGame().checkForVictory();
    }
}
