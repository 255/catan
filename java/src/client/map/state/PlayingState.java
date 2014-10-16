package client.map.state;

import client.map.IMapController;
import shared.definitions.PieceType;
import shared.locations.VertexLocation;
import shared.model.GameModelFacade;
import shared.model.ModelException;
import shared.model.ServerModelFacade;

/**
 * The playing state.
 * The game has started, it is the local player's turn, and the state is "playing"
 */
public class PlayingState extends PlaceSettlementsOrRoadsState {
    /**
     * This method is called whenever the user is trying to place a city on the
     * map. It is called by the view for each "mouse move" event. The returned
     * value tells the view whether or not to allow the city to be placed at the
     * specified location.
     *
     * @param vertLoc The proposed city location
     * @return true if the city can be placed at vertLoc, false otherwise
     */
    @Override
    public boolean canPlaceCity(VertexLocation vertLoc) {
        return GameModelFacade.getInstance().canPlaceCity(vertLoc);
    }

    /**
     * This method is called when the user clicks the mouse to place a city.
     *
     * @param vertLoc The city location
     */
    @Override
    public void placeCity(VertexLocation vertLoc) throws ModelException {
        ServerModelFacade.getInstance().placeCity(vertLoc);
    }

    /**
     * This method is called when the user requests to place a piece on the map
     * (road, city, or settlement)
     *
     * @param pieceType         The type of piece to be placed
     * @param isFree            true if the piece should not cost the player resources, false
     *                          otherwise. Set to true during initial setup and when a road
     *                          building card is played.
     * @param allowDisconnected true if the piece can be disconnected, false otherwise. Set to
     */
    @Override
    public void startMove(IMapController controller, PieceType pieceType, boolean isFree, boolean allowDisconnected) {
        controller.startMove(pieceType, isFree, allowDisconnected);
    }

    /**
     * This method is called when the user plays a "soldier" development card.
     * It should initiate robber placement.
     */
    @Override
    public void playSoldierCard() {
        // TODO: implement (need to have the player place the robber)
        //ServerModelFacade.getInstance().playSoldier(hex, victim);
        assert false : "NOT IMPLEMENTED!";
    }

    /**
     * This method is called when the user plays a "road building" progress
     * development card. It should initiate the process of allowing the player
     * to place two roads.
     */
    @Override
    public void playRoadBuildingCard() {
        // TODO: implement (need to have the player place the two roads)
        //ServerModelFacade.getInstance().playRoadBuilding(edge1, edge2);
        assert false : "NOT IMPLEMENTED!";
    }
}
