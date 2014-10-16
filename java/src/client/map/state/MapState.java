package client.map.state;

import client.data.RobPlayerInfo;
import client.map.IMapController;
import shared.definitions.PieceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.model.IGame;
import shared.model.ModelException;

/**
 * The parent MapState class that provides default "false" implementations.
 */
public abstract class MapState implements IMapState {
    /**
     * Determine the game state based on the provided game object.
     * A null game means that gameplay has not started yet.
     * @param game the game object for the current game, or null if no game is ongoing
     * @return the the correct state object
     */
    public static MapState determineState(IGame game) {
        if (game == null) {
            return new NotPlayingState();
        }
        else if (!game.isLocalPlayersTurn()) {
            return new NotPlayingState();
        }
        else { // isLocalPlayersTurn()
            switch (game.getGameState()) {
                case PLAYING:
                    return new PlayingState();
                case FIRST_ROUND:
                case SECOND_ROUND:
                    return new SetupState();
                case ROLLING:
                    return new RobbingState();
                case ROBBING:
                case DISCARDING:
                    return new NotPlayingState();
                default:
                    assert false : "Unknown game state.";
                    return null;
            }
        }
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
        return false;
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
        return false;
    }

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
        return false;
    }

    /**
     * This method is called whenever the user is trying to place the robber on
     * the map. It is called by the view for each "mouse move" event. The
     * returned value tells the view whether or not to allow the robber to be
     * placed at the specified location.
     *
     * @param hexLoc The proposed robber location
     * @return true if the robber can be placed at hexLoc, false otherwise
     */
    @Override
    public boolean canPlaceRobber(HexLocation hexLoc) {
        return false;
    }

    /**
     * This method is called when the user clicks the mouse to place a road.
     *
     * @param edgeLoc The road location
     */
    @Override
    public void placeRoad(EdgeLocation edgeLoc) throws ModelException {

    }

    /**
     * This method is called when the user clicks the mouse to place a
     * settlement.
     *
     * @param vertLoc The settlement location
     */
    @Override
    public void placeSettlement(VertexLocation vertLoc) throws ModelException {

    }

    /**
     * This method is called when the user clicks the mouse to place a city.
     *
     * @param vertLoc The city location
     */
    @Override
    public void placeCity(VertexLocation vertLoc) throws ModelException {

    }

    /**
     * This method is called when the user clicks the mouse to place the robber.
     *
     * @param hexLoc The robber location
     */
    @Override
    public void placeRobber(HexLocation hexLoc) {

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

    }

    /**
     * This method is called when the user plays a "soldier" development card.
     * It should initiate robber placement.
     */
    @Override
    public void playSoldierCard() {

    }

    /**
     * This method is called when the user plays a "road building" progress
     * development card. It should initiate the process of allowing the player
     * to place two roads.
     */
    @Override
    public void playRoadBuildingCard() {

    }

    /**
     * This method is called by the Rob View when a player to rob is selected
     * via a button click.
     *
     * @param victim The player to be robbed
     */
    @Override
    public void robPlayer(RobPlayerInfo victim) {

    }
}