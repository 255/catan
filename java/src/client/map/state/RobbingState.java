package client.map.state;

import client.data.RobPlayerInfo;
import client.map.MapController;
import shared.definitions.PieceType;
import shared.locations.HexLocation;
import shared.model.*;

import java.util.Collection;

/**
 * The state for when the player is robbing.
 */
public class RobbingState extends MapState {
    private HexLocation m_newRobberLocation = null;
    private boolean m_startedRobbing = false;

    /**
     * This method is called by the Rob View when a player to rob is selected
     * via a button click.
     *
     * @param victim The player to be robbed
     */
    @Override
    public void robPlayer(RobPlayerInfo victim) throws ModelException {
        assert m_newRobberLocation != null;

        try {
            ServerModelFacade.getInstance().robPlayer(m_newRobberLocation, victim.getPlayerIndex());
        }
        finally {
            m_newRobberLocation = null;
        }
    }

    /**
     * This method is called when the user clicks the mouse to place the robber.
     *
     * @param controller
     * @param hexLoc The robber location
     */
    @Override
    public void placeRobber(MapController controller, HexLocation hexLoc) {
        assert hexLoc != null && Game.getInstance().getMap().canPlaceRobber(hexLoc);

        m_newRobberLocation = hexLoc;
        Collection<IPlayer> players = GameModelFacade.getInstance().getRobbablePlayers(m_newRobberLocation);
        controller.getRobView().setPlayers(RobPlayerInfo.fromPlayers(players));
        controller.getRobView().showModal();
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
        return Game.getInstance().getMap().canPlaceRobber(hexLoc);
    }

    /**
     * Set up the state of the robbing dialog, based on game state.
     *
     * @param controller
     */
    @Override
    public void initializeDialogs(MapController controller) {
        if (!m_startedRobbing) {
            m_startedRobbing = true;
            controller.startMove(PieceType.ROBBER);
        }
    }

    /**
     * This method is called when the user requests to place a piece on the map
     * (road, city, or settlement)
     *
     * @param controller
     * @param pieceType  The type of piece to be placed
     */
    @Override
    public void startMove(MapController controller, PieceType pieceType) {
        assert pieceType == PieceType.ROBBER;
        // cannot cancel
        controller.getView().startDrop(PieceType.ROBBER, null, false);
    }
}
