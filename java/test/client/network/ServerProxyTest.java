package client.network;

import client.network.HttpCommunicator;
import client.network.IHttpCommunicator;
import client.network.IServerProxy;
import client.network.ServerProxy;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import shared.definitions.ResourceType;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.model.ResourceBundle;

import static org.junit.Assert.*;

public class ServerProxyTest {

    private IHttpCommunicator m_httpCommunicator;
    private IServerProxy m_serverProxy;

    @Before
    public void setUp() throws Exception {
        m_httpCommunicator = new HttpCommunicator();
        m_serverProxy = new ServerProxy(m_httpCommunicator);
        Process process = Runtime.getRuntime().exec("java -jar server.jar");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testSendChat() throws Exception {
        String response = m_serverProxy.sendChat(0, "Test Message");
        assert (response != null);
    }

    @Test
    public void testAcceptTrade() throws Exception {
        String response = m_serverProxy.acceptTrade(0, true);
        assert (response != null);
        response = m_serverProxy.acceptTrade(0, false);
        assert (response != null);
    }

    @Test
    public void testDiscardCards() throws Exception {
        String response = m_serverProxy.discardCards(1, new ResourceBundle(1, 2, 3, 4, 5));
        assert (response != null);
    }

    @Test
    public void testRollNumber() throws Exception {
        String response = m_serverProxy.rollNumber(3, 7);
        assert (response != null);
    }

    @Test
    public void testBuildRoad() throws Exception {
        String response = m_serverProxy.buildRoad(2, new EdgeLocation(new HexLocation(3, 4), Northwest));
        assert (response != null);
    }

    @Test
    public void testBuildSettlement() throws Exception {
        String response = m_serverProxy.buildSettlement(2, new VertexLocation(new HexLocation(4, 7), SouthEast));
        assert (response != null);
    }

    @Test
    public void testBuildCity() throws Exception {
        String response = m_serverProxy.buildCity(0, new VertexLocation(new HexLocation(1, 2), NorthEast));
        assert (response != null);
    }

    @Test
    public void testOfferTrade() throws Exception {
        String response = m_serverProxy.offerTrade(0, new ResourceBundle(5, 4, 3, 2, 1), 1);
        assert (response != null);
    }

    @Test
    public void testMaritimeTrade() throws Exception {
        String response = m_serverProxy.maritimeTrade(1, 2, Brick, Wool);
        assert (response != null);
    }

    @Test
    public void testFinishTurn() throws Exception {
        String response = m_serverProxy.finishTurn(0);
        assert (response != null);
    }

    @Test
    public void testBuyDevCard() throws Exception {
        String response = m_serverProxy.buyDevCard(3);
        assert (response != null);
    }

    @Test
    public void testPlayYearOfPlenty() throws Exception {
        String response = m_serverProxy.playYearOfPlenty(3, Wood, Ore);
        assert (response != null);
    }

    @Test
    public void testPlayRoadBuilding() throws Exception {
        String response = m_serverProxy.playRoadBuilding(0, new EdgeLocation(new HexLocation(2, 6), EdgeDirection.NorthEast), new EdgeLocation(new HexLocation(5, 2), EdgeDirection.North));
        assert (response != null);
    }

    @Test
    public void testPlaySoldier() throws Exception {
        String response = m_serverProxy.playSoldier(2, new HexLocation(4, 4), 1);
        assert (response != null);
    }

    @Test
    public void testPlayMonopoly() throws Exception {
        String response = m_serverProxy.playMonopoly(1, ResourceType.WHEAT);
        assert (response != null);
    }

    @Test
    public void testPlayMonument() throws Exception {
        String response = m_serverProxy.playMonument(3);
        assert (response != null);
    }
}