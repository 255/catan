package client.map.state;

import client.data.RobPlayerInfo;
import client.map.MapController;
import shared.definitions.DevCardType;
import shared.definitions.PieceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.model.*;

import java.util.Collection;

/**
 * The playing state.
 * The game has started, it is the local player's turn, and the state is "playing"
 */
public class PlayingState extends MapState {
    boolean m_isPlayingRoadBuildingCard = false;
    EdgeLocation m_road1 = null;

    boolean m_isPlayingSoldier = false;
    HexLocation m_newRobberLocation = null;

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
     *  @param pieceType         The type of piece to be placed
     *
     */
    @Override
    public void startMove(MapController controller, PieceType pieceType) {
        // allowed to cancel
        controller.getView().startDrop(pieceType, Game.getInstance().getLocalPlayer().getColor(), true);
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
        assert m_newRobberLocation == null;
        return Game.getInstance().getMap().canPlaceRobber(hexLoc);
    }

    /**
     * This method is called when the user clicks the mouse to place the robber.
     *
     * @param controller
     * @param hexLoc     The robber location
     */
    @Override
    public void placeRobber(MapController controller, HexLocation hexLoc) {
        assert hexLoc != null && Game.getInstance().getMap().canPlaceRobber(hexLoc);
        assert m_newRobberLocation == null;

        m_newRobberLocation = hexLoc;
        Collection<IPlayer> players = GameModelFacade.getInstance().getRobbablePlayers(m_newRobberLocation);
        controller.getRobView().setPlayers(RobPlayerInfo.fromPlayers(players));
        controller.getRobView().showModal();
    }

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
            ServerModelFacade.getInstance().playSoldier(m_newRobberLocation, victim.getPlayerIndex());
        }
        finally {
            m_newRobberLocation = null;
        }
    }

    /**
     * This method is called when the user plays a "soldier" development card.
     * It should initiate robber placement.
     * @param controller
     */
    @Override
    public void playSoldierCard(MapController controller) {
        assert m_isPlayingSoldier == false;
        assert Game.getInstance().getLocalPlayer().canPlayDevCard(DevCardType.SOLDIER);

        m_isPlayingSoldier = true;

        // TODO: implement (need to have the player place the robber)
        controller.startMove(PieceType.ROBBER);
        //ServerModelFacade.getInstance().playSoldier(hex, victim);
    }

    /**
     * This method is called when the user plays a "road building" progress
     * development card. It should initiate the process of allowing the player
     * to place two roads.
     * @param controller
     */
    @Override
    public void playRoadBuildingCard(MapController controller) {
        assert m_isPlayingRoadBuildingCard == false : "Wait -- already was playing road building card!";
        assert Game.getInstance().getLocalPlayer().canPlayDevCard(DevCardType.ROAD_BUILD);

        m_isPlayingRoadBuildingCard = true;
        controller.startMove(PieceType.ROAD);
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
        //if they are playing road build card && are on second road
        if (m_isPlayingRoadBuildingCard && m_road1 != null) {
            return GameModelFacade.getInstance().canPlayRoadBuilding(m_road1, edgeLoc);
        }
        else {
            return GameModelFacade.getInstance().canPlaceRoad(edgeLoc);
        }
    }

    /**
     * This method is called when the user clicks the mouse to place a road.
     *
     * @param controller
     * @param edgeLoc The road location
     */
    @Override
    public void placeRoad(MapController controller, EdgeLocation edgeLoc) throws ModelException {
        if (m_isPlayingRoadBuildingCard) {
            if (m_road1 == null) {
                assert GameModelFacade.getInstance().canPlaceRoad(edgeLoc);

                // if the only the first road, set the local variable
                m_road1 = edgeLoc;
                // and temporarily add to the view
                controller.getView().placeRoad(m_road1, GameModelFacade.getInstance().getLocalColor());
                // immediately choose the next road
                controller.startMove(PieceType.ROAD);
            }
            else { // this is the second road
                // reset the first road location before the request in case of exceptions
                EdgeLocation firstRoad = m_road1;
                m_road1 = null;
                ServerModelFacade.getInstance().playRoadBuilding(firstRoad, edgeLoc);
            }
        }
        else {
            ServerModelFacade.getInstance().placeRoad(edgeLoc);
        }
    }

    /**
     * If a move is ongoing, cancel it.
     * @param controller
     */
    @Override
    public void cancelMove(MapController controller) {
        if (m_isPlayingRoadBuildingCard) {
            m_isPlayingRoadBuildingCard = false;

            // if the road had been temporarily placed, unplace it
            if (m_road1 != null) {
                controller.getView().removeRoad(m_road1);
            }
            m_road1 = null;
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
        return GameModelFacade.getInstance().canPlaceSettlement(vertLoc);
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
