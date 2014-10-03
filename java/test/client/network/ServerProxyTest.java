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
    private IGameAdminServerProxy m_gameAdminProxy;

    @Before
    public void setUp() throws Exception {
        m_httpCommunicator = new HttpCommunicator();
        m_serverProxy = new ServerProxy(m_httpCommunicator);
        m_gameAdminProxy = new GameAdminServerProxy((m_httpCommunicator));
        m_gameAdminProxy.login("Sam", "sam");
        m_gameAdminProxy.joinGame(0, "orange");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testSendChat() throws Exception {
        String response = m_serverProxy.sendChat(0, "Test Message");
        assertNotNull("Communication with server was unsuccessful", response);
    }

    @Test
    public void testAcceptTrade() throws Exception {
        String response = null;
        try {
            response = m_serverProxy.acceptTrade(0, true);
        } catch (NetworkException ex) {

        }
        assertNull("No trade offer was present, so this was an illegal move", response);
    }

    @Test
    public void testDiscardCards() throws Exception {
        String response = m_serverProxy.discardCards(0, new ResourceBank(0, 0, 0, 0, 0));
        assertNotNull("Communication with server was unsuccessful", response);
    }

    @Test
    public void testRollNumber() throws Exception {
        String response = m_serverProxy.rollNumber(0, 7);
        assertNotNull("Communication with server was unsuccessful", response);
    }

    @Test
    public void testBuildRoad() throws Exception {
        String response = null;
        try {
            response = m_serverProxy.buildRoad(0, new EdgeLocation(new HexLocation(0, 1), EdgeDirection.NorthWest), true);
        } catch (NetworkException ex) {

        }
        assertNull("Communicated with server, but the move was illegal because of a lack of resources", response);
    }

    @Test
    public void testBuildSettlement() throws Exception {
        String response = null;
        try {
            response = m_serverProxy.buildSettlement(0, new VertexLocation(new HexLocation(1, 0), VertexDirection.SouthEast), true);
        } catch (NetworkException ex) {

        }
        assertNull("Communicated with server, but the move was illegal because of a lack of resources", response);
    }

    @Test
    public void testBuildCity() throws Exception {
        String response = null;
        try {
            response = m_serverProxy.buildCity(0, new VertexLocation(new HexLocation(1, 2), VertexDirection.NorthEast));
        } catch (NetworkException ex) {

        }
        assertNull("Communicated with server, but the move was illegal because of a lack of resources", response);
    }

    @Test
    public void testOfferTrade() throws Exception {
        String response = m_serverProxy.offerTrade(0, new ResourceBank(0, 0, 0, 0, -1), 1);
        assertNotNull("Communication with server was unsuccessful", response);
    }

    @Test
    public void testMaritimeTrade() throws Exception {
        String response = null;
        try {
            response = m_serverProxy.maritimeTrade(0, 4, ResourceType.BRICK, ResourceType.ORE);
        } catch (NetworkException ex) {

        }
        assertNull("Communication with server was successful, but this move was illegal", response);
    }


    @Test
    public void testFinishTurn() throws Exception {
        String response = m_serverProxy.finishTurn(0);
        assertNotNull("Communication with server was unsuccessful", response);
    }

    @Test
    public void testBuyDevCard() throws Exception {
        String response = m_serverProxy.buyDevCard(0);
        assertNotNull("Communication with server was successful", response);
    }

    @Test
    public void testPlayYearOfPlenty() throws Exception {
        String response = null;
        try {
            response = m_serverProxy.playYearOfPlenty(0, ResourceType.WOOD, ResourceType.ORE);
        } catch (NetworkException ex) {

        }
        assertNull("Communication with server was successful, but this move was illegal", response);
    }

    @Test
    public void testPlayRoadBuilding() throws Exception {
        String response = null;
        try {
            response = m_serverProxy.playRoadBuilding(0, new EdgeLocation(new HexLocation(2, 6), EdgeDirection.NorthEast), new EdgeLocation(new HexLocation(5, 2), EdgeDirection.North));
        } catch (NetworkException ex) {

        }
        assertNull("Communication with server was successful, but this move was illegal", response);
    }

    @Test
    public void testPlaySoldier() throws Exception {
        String response = null;
        try {
            response = m_serverProxy.playSoldier(0, new HexLocation(4, 4), 1);
        } catch (NetworkException ex) {

        }
        assertNull("Communication with server was successful, but this move was illegal", response);
    }

    @Test
    public void testPlayMonopoly() throws Exception {
        String response = null;
        try {
            response = m_serverProxy.playMonopoly(0, ResourceType.WHEAT);
        } catch (NetworkException ex) {
        }
        assertNull("Move was illegal, so an exception should be thrown", response);
    }

    @Test
    public void testPlayMonument() throws Exception {
        String response = m_serverProxy.playMonument(0);
        assertNotNull("Communication with server was unsuccessful", response);
    }
}