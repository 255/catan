package client.map.state;

import client.map.MapController;
import shared.definitions.PieceType;
import shared.locations.EdgeLocation;
import shared.locations.VertexLocation;
import shared.model.GameModelFacade;
import shared.model.ModelException;
import shared.model.ServerModelFacade;

/**
 * The setup state for the first or second round.
 * It is the local player's turn and the game state is first or second round.
 * This class is abstract: the concrete first or second round classes implement it.
 * This is done so that the classes go through the same sequence.
 */
public abstract class SetupState extends MapState {
    private boolean m_startedPlaceRoad = false;

    /**
     * This method is called when the user requests to place a piece on the map
     * (road, city, or settlement)
     *  @param pieceType         The type of piece to be placed
     *
     */
    @Override
    public void startMove(MapController controller, PieceType pieceType) {
        // cannot cancel
        controller.getView().startDrop(pieceType, GameModelFacade.instance().getGame().getLocalPlayer().getColor(), false);
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
        return GameModelFacade.instance().canPlaceRoad(edgeLoc);
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

        // place initial settlement
        controller.startMove(PieceType.SETTLEMENT);
    }

    /**
     * Set up the state of the robbing dialog, based on game state.
     *
     * @param controller the controller whose dialogs to initialize
     */
    @Override
    public void initializeDialogs(MapController controller) {
        // place initial road if the dialog is not already showing
        if (!m_startedPlaceRoad) {
            m_startedPlaceRoad = true;
            controller.startMove(PieceType.ROAD);
        }
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
        return GameModelFacade.instance().canPlaceSettlement(vertLoc);
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

        ServerModelFacade.getInstance().finishTurn();
    }
}
