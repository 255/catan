package client.map.state;

import client.data.RobPlayerInfo;
import shared.locations.HexLocation;
import shared.model.GameModelFacade;
import shared.model.ICatanMap;

/**
 * The state for when the player is robbing.
 */
public class RobbingState extends MapState {
    /**
     * This method is called by the Rob View when a player to rob is selected
     * via a button click.
     *
     * @param victim The player to be robbed
     */
    @Override
    public void robPlayer(RobPlayerInfo victim) {
        // TODO: test / finish implementing
        ICatanMap map = GameModelFacade.getInstance().getGame().getMap();
        // TODO: rob player needs to take a RobPlayerInfo or I need to lookup the player
        //ServerModelFacade.getInstance().robPlayer(map.getRobber(), victim);
        assert false : "NOT IMPLEMENTED";
    }

    /**
     * This method is called when the user clicks the mouse to place the robber.
     *
     * @param hexLoc The robber location
     */
    @Override
    public void placeRobber(HexLocation hexLoc) {
        ICatanMap map = GameModelFacade.getInstance().getGame().getMap();
        map.moveRobber(hexLoc);
        // TODO: test / finish implementing
        assert false : "NOT IMPLEMENTED";
    }
}
