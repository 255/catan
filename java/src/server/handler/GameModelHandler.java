package server.handler;

import com.sun.net.httpserver.HttpExchange;
import server.ServerException;
import server.command.IllegalCommandException;
import server.facade.IGameFacade;
import shared.communication.GameModelParam;
import shared.model.IGame;

import java.io.IOException;

/**
 * The handler for getting the game model.
 * This is different because there is no request body, and the only optional parameter is in the URL.
 */
public class GameModelHandler extends AbstractInGameHandler<GameModelParam, IGameFacade> {
    /**
     * Setup a new GameModelHandler.
     *
     * @param facade
     */
    public GameModelHandler(IGameFacade facade) {
        super(GameModelParam.class, facade);
    }

    /**
     * Extract the data from the request and return the result.
     *
     * @param requestData the data from the HTTP request
     * @return the data to return to the requester
     * @throws server.ServerException if there was an error
     *                                in which case handle() sends back an empty error (500) response
     */
    @Override
    public IGame exchangeData(GameModelParam requestData) throws ServerException, IllegalCommandException {
        return getFacade().model(requestData.version);
    }

    /**
     * Extract the parameters of the request from the HttpExchange object.
     * This is typically extracted from the body of the request using Gson, but this can be overridden
     * (i.e. for getting the game model where the model number is passed in the URL).
     *
     * @param exch the http exchange
     * @return the data that was extracted
     * @throws java.io.IOException
     */
    @Override
    protected GameModelParam getRequestParameters(HttpExchange exch) throws IOException {
        String query = exch.getRequestURI().getQuery();
        if (query == null || query.isEmpty()) {
            return new GameModelParam(null);
        }
        else {
            int number = Integer.parseInt(query.substring(query.indexOf('=') + 1));
            return new GameModelParam(number);
        }
    }
}
