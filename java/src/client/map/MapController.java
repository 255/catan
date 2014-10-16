package client.map;

import java.util.*;

import client.map.state.IMapState;
import shared.definitions.*;
import shared.locations.*;
import client.base.*;
import client.data.*;
import shared.model.*;


/**
 * Implementation for the map controller
 */
public class MapController extends Controller implements IMapController {
	
	private IRobView robView;
    private IMapState state;
	
	public MapController(IMapView view, IRobView robView) {
		
		super(view);
		
		setRobView(robView);

        // TODO: I hate stuff like this... why program to interfaces if you have to
        // cast them down to the concrete class anyway????
        // The getGame should return a Game since we can't see the observer methods
        // on the interface.
        ((Game)GameModelFacade.getInstance().getGame()).addObserver(this);
		
		initFromModel();
	}
	
	public IMapView getView() {
		
		return (IMapView)super.getView();
	}
	
	private IRobView getRobView() {
		return robView;
	}
	private void setRobView(IRobView robView) {
		this.robView = robView;
	}
	
	protected void initFromModel() {
        IGame game = GameModelFacade.getInstance().getGame();
        if (game.isNotInitialized()) {
            System.err.println("GAME NOT INITIALIZED. NOT UPDATING.");
            return; // do nothing if the game object has not been created yet
        }
        System.err.println("GAME IS INITIALIZED!!!!!!!!!!");

        ICatanMap map = game.getMap();

        // add the tiles
        for (ITile tile : map.getTiles()) {
            getView().addHex(tile.location(), tile.type());
            getView().addNumber(tile.location(), tile.numberToken());
        }

        // add the ports
        for (Map.Entry<EdgeLocation, PortType> port : map.getPorts().entrySet()) {
            getView().addPort(port.getKey(), port.getValue());
        }

        // add the settlements
        for (ITown settlement : map.getSettlements()) {
            getView().placeSettlement(settlement.getLocation(), settlement.getOwner().getColor());
        }

        // add the cities
        for (ITown city : map.getCities()) {
            getView().placeCity(city.getLocation(), city.getOwner().getColor());
        }

        // add the roads
        for (IRoad road : map.getRoads()) {
            getView().placeRoad(road.getLocation(), road.getOwner().getColor());
        }

        getView().placeRobber(map.getRobber());
	}

	public boolean canPlaceRoad(EdgeLocation edgeLoc) {
		
		return true;
	}

	public boolean canPlaceSettlement(VertexLocation vertLoc) {
		
		return true;
	}

	public boolean canPlaceCity(VertexLocation vertLoc) {
		
		return true;
	}

	public boolean canPlaceRobber(HexLocation hexLoc) {
		
		return true;
	}

	public void placeRoad(EdgeLocation edgeLoc) {
		
		getView().placeRoad(edgeLoc, CatanColor.ORANGE);
	}

	public void placeSettlement(VertexLocation vertLoc) {
		
		getView().placeSettlement(vertLoc, CatanColor.ORANGE);
	}

	public void placeCity(VertexLocation vertLoc) {
		
		getView().placeCity(vertLoc, CatanColor.ORANGE);
	}

	public void placeRobber(HexLocation hexLoc) {
		
		getView().placeRobber(hexLoc);
		
		getRobView().showModal();
	}
	
	public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected) {	
		
		getView().startDrop(pieceType, CatanColor.ORANGE, true);
	}
	
	public void cancelMove() {

	}
	
	public void playSoldierCard() {	
		
	}
	
	public void playRoadBuildingCard() {	
		
	}
	
	public void robPlayer(RobPlayerInfo victim) {	
		
	}

    @Override
    public void update(Observable o, Object arg) {
        System.err.println("MAP CONTROLLER NOTIFIED") ; //TODO: remove
        initFromModel();
    }
}

