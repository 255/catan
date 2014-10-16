package client.map.state;

import shared.locations.EdgeLocation;
import shared.locations.VertexLocation;
import shared.model.GameModelFacade;
import shared.model.ModelException;
import shared.model.ServerModelFacade;

/**
 * An abstract parent class of the two classes that can place settlements or roads:
 * PlayingState and SetupState.
 */
public abstract class PlaceSettlementsOrRoadsState extends MapState {
    /**
     * This method is called whenever the user is trying to place a road on the
     * map. It is called by the view for each "mouse move" event. The returned
     * value tells the view whether or not to allow the road to be placed at the
     * specified location.
     *
     * @param edgeLoc The proposed road location
     * @return true if the road can be placed at edgeLoc, false otherwise
     */
    @Override
    public boolean canPlaceRoad(EdgeLocation edgeLoc) {
        return GameModelFacade.getInstance().canPlaceRoad(edgeLoc);
    }

    /**
     * This method is called whenever the user is trying to place a settlement
     * on the map. It is called by the view for each "mouse move" event. The
     * returned value tells the view whether or not to allow the settlement to
     * be placed at the specified location.
     *
     * @param vertLoc The proposed settlement location
     * @return true if the settlement can be placed at vertLoc, false otherwise
     */
    @Override
    public boolean canPlaceSettlement(VertexLocation vertLoc) {
        return GameModelFacade.getInstance().canPlaceSettlement(vertLoc);
    }

    /**
     * This method is called when the user clicks the mouse to place a road.
     *
     * @param edgeLoc The road location
     */
    @Override
    public void placeRoad(EdgeLocation edgeLoc) throws ModelException {
        ServerModelFacade.getInstance().placeRoad(edgeLoc);
    }

    /**
     * This method is called when the user clicks the mouse to place a
     * settlement.
     *
     * @param vertLoc The settlement location
     */
    @Override
    public void placeSettlement(VertexLocation vertLoc) throws ModelException {
        ServerModelFacade.getInstance().placeSettlement(vertLoc);
    }
}
