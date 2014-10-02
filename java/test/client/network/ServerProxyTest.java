package client.network;

import client.network.HttpCommunicator;
import client.network.IHttpCommunicator;
import client.network.IServerProxy;
import client.network.ServerProxy;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import shared.definitions.ResourceType;
import shared.locations.*;
import shared.model.ResourceBank;

import static org.junit.Assert.*;

public class ServerProxyTest {

    private IHttpCommunicator m_httpCommunicator;
    private IServerProxy m_serverProxy;

    @Before
    public void setUp() throws Exception {
        m_httpCommunicator = new HttpCommunicator();
        m_serverProxy = new ServerProxy(m_httpCommunicator);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testSendChat() throws Exception {
        String response = m_serverProxy.sendChat(0, "Test Message");
        assertNotNull("Communication with server was unsuccessful", response);
    }

//    @Test
//    public void testAcceptTrade() throws Exception {
//        String response = m_serverProxy.acceptTrade(0, true);
//        assertNotNull("Communication with server was unsuccessful", response);
//        response = m_serverProxy.acceptTrade(0, false);
//        assertNotNull("Communication with server was unsuccessful", response);
//    }
//
//    @Test
//    public void testDiscardCards() throws Exception {
//        String response = m_serverProxy.discardCards(1, new ResourceBank(1, 2, 3, 4, 5));
//        assertNotNull("Communication with server was unsuccessful", response);
//    }
//
//    @Test
//    public void testRollNumber() throws Exception {
//        String response = m_serverProxy.rollNumber(3, 7);
//        assertNotNull("Communication with server was unsuccessful", response);
//    }
//
//    @Test
//    public void testBuildRoad() throws Exception {
//        String response = m_serverProxy.buildRoad(2, new EdgeLocation(new HexLocation(0, 1), EdgeDirection.NorthWest), true);
//        assertNotNull("Communication with server was unsuccessful", response);
//    }
//
//    @Test
//    public void testBuildSettlement() throws Exception {
//        String response = m_serverProxy.buildSettlement(2, new VertexLocation(new HexLocation(1, 0), VertexDirection.SouthEast), true);
//        assertNotNull("Communication with server was unsuccessful", response);
//    }
//
//    @Test
//    public void testBuildCity() throws Exception {
//        String response = m_serverProxy.buildCity(0, new VertexLocation(new HexLocation(1, 2), VertexDirection.NorthEast));
//        assertNotNull("Communication with server was unsuccessful", response);
//    }
//
//    @Test
//    public void testOfferTrade() throws Exception {
//        String response = m_serverProxy.offerTrade(0, new ResourceBank(5, 4, 3, 2, 1), 1);
//        assertNotNull("Communication with server was unsuccessful", response);
//    }
//
//    @Test
//    public void testMaritimeTrade() throws Exception {
//        String response = m_serverProxy.maritimeTrade(1, 2, ResourceType.BRICK, ResourceType.SHEEP);
//        assertNotNull("Communication with server was unsuccessful", response);
//    }
//
//    @Test
//    public void testFinishTurn() throws Exception {
//        String response = m_serverProxy.finishTurn(0);
//        assertNotNull("Communication with server was unsuccessful", response);
//    }
//
//    @Test
//    public void testBuyDevCard() throws Exception {
//        String response = m_serverProxy.buyDevCard(3);
//        assertNotNull("Communication with server was unsuccessful", response);
//    }
//
//    @Test
//    public void testPlayYearOfPlenty() throws Exception {
//        String response = m_serverProxy.playYearOfPlenty(3, ResourceType.WOOD, ResourceType.ORE);
//        assertNotNull("Communication with server was unsuccessful", response);
//    }
//
//    @Test
//    public void testPlayRoadBuilding() throws Exception {
//        String response = m_serverProxy.playRoadBuilding(0, new EdgeLocation(new HexLocation(2, 6), EdgeDirection.NorthEast), new EdgeLocation(new HexLocation(5, 2), EdgeDirection.North));
//        assertNotNull("Communication with server was unsuccessful", response);
//    }
//
//    @Test
//    public void testPlaySoldier() throws Exception {
//        String response = m_serverProxy.playSoldier(2, new HexLocation(4, 4), 1);
//        assertNotNull("Communication with server was unsuccessful", response);
//    }
//
//    @Test
//    public void testPlayMonopoly() throws Exception {
//        String response = m_serverProxy.playMonopoly(1, ResourceType.WHEAT);
//        assertNotNull("Communication with server was unsuccessful", response);
//    }
//
//    @Test
//    public void testPlayMonument() throws Exception {
//        String response = m_serverProxy.playMonument(3);
//        assertNotNull("Communication with server was unsuccessful", response);
//    }
//
//    @Test
//    public void testPlayRobber() throws Exception {
//        String response = m_serverProxy.robPlayer(0, 1, new HexLocation(2, 6));
//        assertNotNull("Communication with server was unsuccessful", response);
//    }
}