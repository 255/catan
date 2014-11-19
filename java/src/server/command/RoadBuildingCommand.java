package server.command;

import shared.locations.EdgeLocation;
import shared.model.IGame;
import shared.model.IPlayer;

/**
 * Class that represents the RoadBuilding request
 *
 * @author StevenBarnett
 */
public class RoadBuildingCommand extends AbstractCommand {

    private EdgeLocation m_location1;
    private EdgeLocation m_location2;

    public RoadBuildingCommand(IGame game, IPlayer player, EdgeLocation edge1, EdgeLocation edge2) throws IllegalCommandException {
        super(game, player, "played a road building card");

        if (!getGame().canPlayRoadBuilding(getPlayer(), edge1, edge2)) {
            throw new IllegalCommandException("Player " + player.getName() + " cannot build roads at " + edge1 + " and " + edge2);
        }

        m_location1 = edge1;
        m_location2 = edge2;
    }

    /**
     * Places two new roads on the map for the player
     * playing the RoadBuilding card.
     */
    public void performAction() {
        getGame().getMap().placeRoad(getPlayer().buildRoad(true), m_location1);
        getGame().getMap().placeRoad(getPlayer().buildRoad(true), m_location2);

        getPlayer().setPlayedDevCard(true);

        getGame().calculateVictoryPoints();
    }
}
