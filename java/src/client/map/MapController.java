package client.map;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import client.map.state.IMapState;
import client.map.state.MapState;
import client.map.state.NotPlayingState;
import shared.definitions.*;
import shared.locations.*;
import client.base.*;
import client.data.*;
import shared.model.*;


/**
 * Implementation for the map controller
 */
public class MapController extends Controller implements IMapController {
    private final static Logger logger = Logger.getLogger("catan");

	private IRobView robView;
    private IMapState m_state;

	public MapController(IMapView view, IRobView robView) {
		super(view);
		
		setRobView(robView);

        Game.getInstance().addObserver(this);
        m_state = new NotPlayingState();
	}
	
	public IMapView getView() {
		
		return (IMapView)super.getView();
	}

	public IRobView getRobView() {
		return robView;
	}

	private void setRobView(IRobView robView) {
		this.robView = robView;
	}
	
	protected void initFromModel() {
        logger.entering("client.map.MapController", "initFromModel");

        IGame game = Game.getInstance();
        assert game != null;
        if (game.isNotInitialized()) {
            logger.fine("Not intializing MapController: the game object has not been initialized");
            return; // do nothing if the game object has not been created yet
        }
        logger.fine("Initializing MapController.");

        ICatanMap map = game.getMap();

        // add the tiles
        for (ITile tile : map.getTiles()) {
            getView().addHex(tile.location(), tile.type());

            int numberToken = tile.numberToken();
            if (numberToken != Tile.DESERT_NUMBER) {
                getView().addNumber(tile.location(), numberToken);
            }
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

        // determine the state
        m_state = MapState.determineState(game);
        m_state.initializeDialogs(this);
        logger.exiting("client.map.MapController", "initFromModel");
	}

	public boolean canPlaceRoad(EdgeLocation edgeLoc) {
		return m_state.canPlaceRoad(edgeLoc);
	}

	public boolean canPlaceSettlement(VertexLocation vertLoc) {
		return m_state.canPlaceSettlement(vertLoc);
	}

	public boolean canPlaceCity(VertexLocation vertLoc) {
		return m_state.canPlaceCity(vertLoc);
	}

	public boolean canPlaceRobber(HexLocation hexLoc) {
		return m_state.canPlaceRobber(hexLoc);
	}

	public void placeRoad(EdgeLocation edgeLoc) {
        try {
            m_state.placeRoad(this, edgeLoc);
        }
        catch (ModelException e) {
            // TODO: should we display a popup to the user?
            logger.log(Level.WARNING, "Placing road failed.", e);
        }
    }

	public void placeSettlement(VertexLocation vertLoc) {
        try {
            m_state.placeSettlement(vertLoc);
        }
        catch (ModelException e) {
            logger.log(Level.WARNING, "Placing settlement failed.", e);
        }
    }

	public void placeCity(VertexLocation vertLoc) {
        try {
            m_state.placeCity(vertLoc);
        }
        catch (ModelException e) {
            logger.log(Level.WARNING, "Placing settlement failed.", e);
        }
	}

	public void placeRobber(HexLocation hexLoc) {
        m_state.placeRobber(this, hexLoc);
	}
	
	public void startMove(PieceType pieceType) {
        m_state.startMove(this, pieceType);
	}
	
	public void cancelMove() {
        m_state.cancelMove(this);
	}
	
	public void playSoldierCard() {	
        m_state.playSoldierCard(this);
	}
	
	public void playRoadBuildingCard() {	
		m_state.playRoadBuildingCard(this);
	}
	
	public void robPlayer(RobPlayerInfo victim) {
        try {
            m_state.robPlayer(victim);
        }
        catch (ModelException e) {
            logger.log(Level.WARNING, "Robbing player failed.", e);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        logger.entering("client.map.MapController", "update", o);
        initFromModel();
        // TODO: THIS IS TEMP
        playSoldierCard();
        logger.exiting("client.map.MapController", "update");
    }
}

