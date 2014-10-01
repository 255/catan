package shared.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ModelInitializerTest {
    private IGame game;

    @Before
    public void setUp() throws Exception {
        // call
        game = GameFacade.getFacadeInstance().getGame();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testConvertJSONtoModel() throws Exception {

    }
}