package server.command;

import org.junit.After;
import org.junit.Before;
import shared.model.GameModelFacade;
import shared.model.IGame;
import shared.model.ModelInitializer;

import static org.junit.Assert.*;

public class MaritimeTradeCommandTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {

    }

    // Test trying to trade when it is not your turn.
    // Test 4:1 trade when you don't have enough resources to do it.
    // Test trading 4:1 of the same resource.
    // Test 3:1 when you don't have a 3:1 port.
    // Test 3:1 when you don't have enough resources.
    // Test 2:1 when you don't have a 2:1 port.
    // Test 2:1 when you don't have enough resources.
    // Test

    private IGame initAGame(String jsonTestFile) throws Exception {
        String emptyBoardJSON = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(jsonTestFile)));
        ModelInitializer initModel = new ModelInitializer();
        initModel.initializeClientModel(emptyBoardJSON, 0);
        return GameModelFacade.instance().getGame();
    }


}