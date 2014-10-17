package client.map.state;

import client.data.RobPlayerInfo;
import client.map.MapController;
import shared.definitions.PieceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.model.IGame;
import shared.model.ModelException;

import java.util.logging.Logger;

/**
 * The parent MapState class that provides default "false" implementations.
 */
public abstract class MapState implements IMapState {
    private final static Logger logger = Logger.getLogger("catan");
    /**
     * Determine the game state based on the provided game object.
     * A null game means that gameplay has not started yet.
     * @param game the game object for the current game, or null if no game is ongoing
     * @return the the correct state object
     */
    public static MapState determineState(IGame game) {
        assert game != null : "Game is null! How did that happen?";

        if (game.isNotInitialized() || !game.isLocalPlayersTurn()) {
            logger.finer("MapState is NotPlaying.");
            return new NotPlayingState();
        }
        else { // isLocalPlayersTurn()
            switch (game.getGameState()) {
                case PLAYING:
                    logger.finer("MapState is Playing.");
                    return new PlayingState();
                case FIRST_ROUND:
                case SECOND_ROUND:
                    logger.finer("MapState is Setup.");
                    return new SetupState();
                case ROBBING:
                    logger.finer("MapState is Robbing.");
                    return new RobbingState();
                case ROLLING:
                case DISCARDING:
                    logger.finer("MapState is NotPlaying.");
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
     * @param controller
     * @param edgeLoc The road location
     */
    @Override
    public void placeRoad(MapController controller, EdgeLocation edgeLoc) throws ModelException {

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
     * @param controller
     * @param hexLoc The robber location
     */
    @Override
    public void placeRobber(MapController controller, HexLocation hexLoc) {

    }

    /**
     * This method is called when the user requests to place a piece on the map
     * (road, city, or settlement)
     *  @param pieceType         The type of piece to be placed
     */
    @Override
    public void startMove(MapController controller, PieceType pieceType) {

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
     * @param controller
     */
    @Override
    public void playRoadBuildingCard(MapController controller) {

    }

    /**
     * This method is called by the Rob View when a player to rob is selected
     * via a button click.
     *
     * @param victim The player to be robbed
     */
    @Override
    public void robPlayer(RobPlayerInfo victim) throws ModelException {

    }

    /**
     * Set up the state of the robbing dialog, based on game state.
     * @param controller the controller whose dialogs to initialize
     */
    @Override
    public void initializeDialogs(MapController controller) {

    }

    /**
     * If a move is ongoing, cancel it.
     * @param controller
     */
    @Override
    public void cancelMove(MapController controller) {

    }
}
