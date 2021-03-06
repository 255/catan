package client.map.state;

import client.data.RobPlayerInfo;
import client.map.IMapController;
import client.map.MapController;
import shared.definitions.PieceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.model.ModelException;

/**
 * The Map State
 */
public interface IMapState {
    /**
     * This method is called whenever the user is trying to place a road on the
     * map. It is called by the view for each "mouse move" event. The returned
     * value tells the view whether or not to allow the road to be placed at the
     * specified location.
     *
     * @param edgeLoc The proposed road location
     * @return true if the road can be placed at edgeLoc, false otherwise
     */
    public boolean canPlaceRoad(EdgeLocation edgeLoc);

    /**
     * This method is called whenever the user is trying to place a settlement
     * on the map. It is called by the view for each "mouse move" event. The
     * returned value tells the view whether or not to allow the settlement to
     * be placed at the specified location.
     *
     * @param vertLoc The proposed settlement location
     * @return true if the settlement can be placed at vertLoc, false otherwise
     */
    public boolean canPlaceSettlement(VertexLocation vertLoc);

    /**
     * This method is called whenever the user is trying to place a city on the
     * map. It is called by the view for each "mouse move" event. The returned
     * value tells the view whether or not to allow the city to be placed at the
     * specified location.
     *
     * @param vertLoc The proposed city location
     * @return true if the city can be placed at vertLoc, false otherwise
     */
    public boolean canPlaceCity(VertexLocation vertLoc);

    /**
     * This method is called whenever the user is trying to place the robber on
     * the map. It is called by the view for each "mouse move" event. The
     * returned value tells the view whether or not to allow the robber to be
     * placed at the specified location.
     *
     * @param hexLoc The proposed robber location
     * @return true if the robber can be placed at hexLoc, false otherwise
     */
    public boolean canPlaceRobber(HexLocation hexLoc);

    /**
     * This method is called when the user clicks the mouse to place a road.
     *
     * @param controller
     * @param edgeLoc The road location
     */
    public void placeRoad(MapController controller, EdgeLocation edgeLoc) throws ModelException;

    /**
     * This method is called when the user clicks the mouse to place a
     * settlement.
     *
     * @param vertLoc The settlement location
     */
    public void placeSettlement(VertexLocation vertLoc) throws ModelException;

    /**
     * This method is called when the user clicks the mouse to place a city.
     *
     * @param vertLoc The city location
     */
    public void placeCity(VertexLocation vertLoc) throws ModelException;

    /**
     * This method is called when the user clicks the mouse to place the robber.
     *
     * @param controller
     * @param hexLoc The robber location
     */
    public void placeRobber(MapController controller, HexLocation hexLoc);

    /**
     * This method is called when the user requests to place a piece on the map
     * (road, city, or settlement)
     *  @param controller        The controller that should start the move
     * @param pieceType         The type of piece to be placed
     */
    public void startMove(MapController controller, PieceType pieceType);

    /**
     * This method is called when the user plays a "soldier" development card.
     * It should initiate robber placement.
     * @param controller
     */
    public void playSoldierCard(MapController controller);

    /**
     * This method is called when the user plays a "road building" progress
     * development card. It should initiate the process of allowing the player
     * to place two roads.
     * @param controller
     */
    public void playRoadBuildingCard(MapController controller);

    /**
     * This method is called by the Rob View when a player to rob is selected
     * via a button click.
     *
     * @param victim The player to be robbed
     */
    public void robPlayer(RobPlayerInfo victim) throws ModelException;

    /**
     * Set up the state of the robbing dialog, based on game state.
     * @param controller
     */
    public void initializeDialogs(MapController controller);

    /**
     * If a move is ongoing, cancel it.
     * @param controller
     */
    public void cancelMove(MapController controller);
}
