package server.command;

import shared.model.IGame;
import shared.model.IPlayer;

/**
 * Class that represents the RoadBuilding request
 *
 * @author StevenBarnett
 */
public class RoadBuildingCommand extends AbstractCommand {

    public RoadBuildingCommand(IGame game, IPlayer player, String actionDescription) {
        super(game, player, actionDescription);
        // TODO: implement
    }

    /**
     * Places two new roads on the map for the player
     * playing the RoadBuilding card.
     */
    public void performAction() {
        // TODO: implement

    }
}
