package server.command;

import shared.locations.EdgeLocation;
import shared.model.IGame;
import shared.model.IPlayer;
import shared.model.IRoad;
import shared.model.Road;

/**
 * Class that represents the BuildRoad request
 *
 * @author StevenBarnett
 */
public class BuildRoadCommand extends AbstractCommand {

    public BuildRoadCommand(IGame game, IPlayer player, EdgeLocation location) throws IllegalCommandException {
        super(game);

        // TODO: integrity check

        IRoad road = new Road(player, location);
        player.buildRoad(road);
        game.getMap().placeRoad()
    }

    /**
     * Places a road on the map for the player that made this
     * request. Reduces the price of a road from that
     * player's resources.
     */
    public void execute() {

    }
}
