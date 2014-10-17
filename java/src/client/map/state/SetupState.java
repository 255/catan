package client.map.state;

import client.map.MapController;
import shared.definitions.PieceType;
import shared.locations.EdgeLocation;
import shared.model.Game;
import shared.model.GameModelFacade;
import shared.model.ModelException;
import shared.model.ServerModelFacade;

/**
 * The setup state for the first or second round.
 * It is the local player's turn and the game state is first or second round.
 */
public class SetupState extends PlaceSettlementsOrRoadsState {
    /**
     * This method is called when the user requests to place a piece on the map
     * (road, city, or settlement)
     *  @param pieceType         The type of piece to be placed
     *
     */
    @Override
    public void startMove(MapController controller, PieceType pieceType) {
        // cannot cancel
        controller.getView().startDrop(pieceType, Game.getInstance().getLocalPlayer().getColor(), false);
    }

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
     * This method is called when the user clicks the mouse to place a road.
     *
     * @param controller
     * @param edgeLoc The road location
     */
    @Override
    public void placeRoad(MapController controller, EdgeLocation edgeLoc) throws ModelException {
        ServerModelFacade.getInstance().placeRoad(edgeLoc);
    }
}
