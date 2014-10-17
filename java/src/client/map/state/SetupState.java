package client.map.state;

import client.map.MapController;
import shared.definitions.PieceType;
import shared.model.Game;

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
}
