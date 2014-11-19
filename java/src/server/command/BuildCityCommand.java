package server.command;

import shared.locations.VertexLocation;
import shared.model.IGame;
import shared.model.IPlayer;
import shared.model.ITown;
import shared.model.Prices;

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
        ITown replacing = getGame().getMap().getTownAt(m_location);
        getGame().getMap().placeCity(getPlayer().buildCity(replacing), m_location);
        getGame().getResourceBank().add(Prices.CITY); // the player gave these resources to the bank

        getGame().calculateVictoryPoints();

        assert (getGame().verifyResourceAmount());
    }
}
