package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Calendar;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import server.command.FinishTurnCommand;
import server.command.IllegalCommandException;
import server.command.RobPlayerCommand;
import server.debug.SwaggerHandler;
import server.facade.*;
import server.handler.*;
import shared.communication.*;

import com.sun.net.httpserver.HttpServer;
import shared.definitions.DevCardType;
import shared.model.*;

/**
 * The main class of the Settlers of Catan server.
 * This class starts an HttpServer.
 */
public class Server {
	private static Logger logger;
	private static int MAX_WAITING_CONNECTIONS = 0; // take the default value
	
	// Initialize the logger for the server
	static {
		initializeLog();
	}
	
	/**
	 * Initialize the logger for this project.
	 */
	public static void initializeLog() {
		try {
			final Level logLevel = Level.FINEST;
			
			logger = Logger.getLogger("catanserver");
			logger.setLevel(logLevel);
			logger.setUseParentHandlers(false);
			
			Handler consoleHandler = new ConsoleHandler();
			consoleHandler.setLevel(logLevel);
			consoleHandler.setFormatter(new SimpleFormatter());
			logger.addHandler(consoleHandler);

			String startTime = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(Calendar.getInstance().getTime());
			
			if (!Files.exists(Paths.get("logs"))) {
				Files.createDirectory(Paths.get("logs"));
			}
			FileHandler fileHandler = new FileHandler("logs/log_" + startTime + ".txt", false);
			fileHandler.setLevel(logLevel);
			fileHandler.setFormatter(new SimpleFormatter());
			logger.addHandler(fileHandler);
			
			logger.log(Level.FINEST, "Logging began at " + startTime + ".");
		} catch (IOException e) {
			System.err.println("There was a problem initializing the log: " + e.getMessage());
		}
	}
	
	// The server
	private HttpServer server;
	
	/** No public constructor */
	private Server() {}
	
	private void run(int portNumber) {
		logger.entering("server.Server", "run");
		
		try {
			logger.info("Initializing server.");
			server = HttpServer.create(new InetSocketAddress(portNumber), MAX_WAITING_CONNECTIONS);
		}
        catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			logger.severe("The server cannot intialize. Terminating.");
			logger.exiting("server.Server", "run");
			return;
		}

        setupHandlers(server);

		logger.info("Starting server on port " + portNumber + ".");
		server.start();
		
		logger.exiting("server.Server", "run");
	}

    /**
     * Create and add the handlers for HTTP requests.
     * @param server the server object
     */
    private void setupHandlers(HttpServer server) {
        // define the facades
        IGameManager gameManager = new GameManager();
        IUserManager userManager = new UserManager();

        final IUserFacade userFacade = new UserFacade(userManager);
        final IJoinGameFacade joinGameFacade = new JoinGameFacade(gameManager, userManager);
        final IGameFacade gameFacade = new GameFacade(gameManager);
        final IMovesFacade movesFacade = new MovesFacade(gameManager);
        final IUtilityFacade utilityFacade = new UtilityFacade();

        // Create HTTPHandlers for each type of request

        //
        // user
        //
        server.createContext("/user/login", new AbstractUserHandler(userFacade) {
            @Override
            protected IUser exchangeData(CredentialsParams requestData) throws MissingCookieException, IllegalCommandException, ModelException {
                return getFacade().login(requestData);
            }
        });

        server.createContext("/user/register", new AbstractUserHandler(userFacade) {
            @Override
            protected IUser exchangeData(CredentialsParams requestData) throws MissingCookieException, IllegalCommandException, ModelException {
                return getFacade().register(requestData);
            }
        });

        //
        // games (joining)
        //
		server.createContext("/games/join", new JoinHandler(joinGameFacade));

        server.createContext("/games/list", new AbstractHandler<Void, GameInfo[], IJoinGameFacade>(Void.class, joinGameFacade) {
            @Override
            protected GameInfo[] exchangeData(Void requestData) throws MissingCookieException, IllegalCommandException, ModelException {
                return getFacade().list();
            }
        });

        server.createContext("/games/create", new AbstractHandler<CreateGameRequestParams, GameInfo, IJoinGameFacade>(CreateGameRequestParams.class, joinGameFacade) {
            @Override
            protected GameInfo exchangeData(CreateGameRequestParams requestData) throws MissingCookieException, IllegalCommandException, ModelException {
                return getFacade().create(requestData);
            }
        });

        //
        // game
        //
        server.createContext("/game/model", new GameModelHandler(gameFacade));

        server.createContext("/game/listAI", new AbstractHandler<Void, String[], IGameFacade>(Void.class, gameFacade) {
            @Override
            protected String[] exchangeData(Void requestData) throws MissingCookieException, IllegalCommandException, ModelException {
                return getFacade().listAI();
            }
        });

        server.createContext("/game/addAI", new AbstractInGameHandler<AddAIRequestParams, IGameFacade>(AddAIRequestParams.class, gameFacade) {
            @Override
            protected IGame exchangeData(AddAIRequestParams requestData) throws MissingCookieException, IllegalCommandException, ModelException {
                getFacade().addAI(requestData); // TODO: this is testing code
                return null;
            }
        });

        //
        // moves
        //
		server.createContext("/moves/sendChat", new AbstractMovesHandler<SendChatParams>(SendChatParams.class, movesFacade) {
            @Override
            protected IGame exchangeData(SendChatParams requestData) throws MissingCookieException, IllegalCommandException, ModelException {
                return getFacade().sendChat(requestData);
            }
        });

	    server.createContext("/moves/rollNumber", new AbstractMovesHandler<RollNumberParams>(RollNumberParams.class, movesFacade) {
            @Override
            protected IGame exchangeData(RollNumberParams requestData) throws MissingCookieException, IllegalCommandException, ModelException {
                return getFacade().rollNumber(requestData);
            }
        });

	    server.createContext("/moves/robPlayer", new AbstractMovesHandler<RobbingParams>(RobbingParams.class, movesFacade) {
            @Override
            protected IGame exchangeData(RobbingParams requestData) throws MissingCookieException, IllegalCommandException, ModelException {
                return getFacade().robPlayer(requestData);
            }
        });

	    server.createContext("/moves/finishTurn", new AbstractMovesHandler<PlayerIndexParam>(PlayerIndexParam.class, movesFacade) {
            @Override
            protected IGame exchangeData(PlayerIndexParam requestData) throws MissingCookieException, IllegalCommandException, ModelException {
                return getFacade().finishTurn(requestData);
            }
        });

	    server.createContext("/moves/buyDevCard", new AbstractMovesHandler<PlayerIndexParam>(PlayerIndexParam.class, movesFacade) {
            @Override
            protected IGame exchangeData(PlayerIndexParam requestData) throws MissingCookieException, IllegalCommandException, ModelException {
                return getFacade().finishTurn(requestData);
            }
        });

	    server.createContext("/moves/Year_of_Plenty", new AbstractMovesHandler<YearOfPlentyParams>(YearOfPlentyParams.class, movesFacade) {
            @Override
            protected IGame exchangeData(YearOfPlentyParams requestData) throws MissingCookieException, IllegalCommandException, ModelException {
                return getFacade().yearOfPlenty(requestData);
            }
        });

	    server.createContext("/moves/Road_Building", new AbstractMovesHandler<RoadBuildingParams>(RoadBuildingParams.class, movesFacade) {
            @Override
            protected IGame exchangeData(RoadBuildingParams requestData) throws MissingCookieException, IllegalCommandException, ModelException {
                return getFacade().roadBuilding(requestData);
            }
        });

	    server.createContext("/moves/Soldier", new AbstractMovesHandler<RobbingParams>(RobbingParams.class, movesFacade) {
            @Override
            protected IGame exchangeData(RobbingParams requestData) throws MissingCookieException, IllegalCommandException, ModelException {
                return getFacade().soldier(requestData);
            }
        });

	    server.createContext("/moves/Monopoly", new AbstractMovesHandler<MonopolyParams>(MonopolyParams.class, movesFacade) {
            @Override
            protected IGame exchangeData(MonopolyParams requestData) throws MissingCookieException, IllegalCommandException, ModelException {
                return getFacade().monopoly(requestData);
            }
        });

	    server.createContext("/moves/Monument", new AbstractMovesHandler<PlayerIndexParam>(PlayerIndexParam.class, movesFacade) {
            @Override
            protected IGame exchangeData(PlayerIndexParam requestData) throws MissingCookieException, IllegalCommandException, ModelException {
                return getFacade().monument(requestData);
            }
        });

	    server.createContext("/moves/buildRoad", new AbstractMovesHandler<BuildRoadParams>(BuildRoadParams.class, movesFacade) {
            @Override
            protected IGame exchangeData(BuildRoadParams requestData) throws MissingCookieException, IllegalCommandException, ModelException {
                return getFacade().buildRoad(requestData);
            }
        });

	    server.createContext("/moves/buildSettlement", new AbstractMovesHandler<BuildSettlementParams>(BuildSettlementParams.class, movesFacade) {
            @Override
            protected IGame exchangeData(BuildSettlementParams requestData) throws MissingCookieException, IllegalCommandException, ModelException {
                return getFacade().buildSettlement(requestData);
            }
        });

	    server.createContext("/moves/buildCity", new AbstractMovesHandler<BuildCityParams>(BuildCityParams.class, movesFacade) {
            @Override
            protected IGame exchangeData(BuildCityParams requestData) throws MissingCookieException, IllegalCommandException, ModelException {
                return getFacade().buildCity(requestData);
            }
        });

	    server.createContext("/moves/offerTrade", new AbstractMovesHandler<OfferTradeParams>(OfferTradeParams.class, movesFacade) {
            @Override
            protected IGame exchangeData(OfferTradeParams requestData) throws MissingCookieException, IllegalCommandException, ModelException {
                return getFacade().offerTrade(requestData);
            }
        });

	    server.createContext("/moves/acceptTrade", new AbstractMovesHandler<AcceptTradeParams>(AcceptTradeParams.class, movesFacade) {
            @Override
            protected IGame exchangeData(AcceptTradeParams requestData) throws MissingCookieException, IllegalCommandException, ModelException {
                return getFacade().acceptTrade(requestData);
            }
        });

	    server.createContext("/moves/maritimeTrade", new AbstractMovesHandler<MaritimeTradeParams>(MaritimeTradeParams.class, movesFacade) {
            @Override
            protected IGame exchangeData(MaritimeTradeParams requestData) throws MissingCookieException, IllegalCommandException, ModelException {
                return getFacade().maritimeTrade(requestData);
            }
        });

	    server.createContext("/moves/discardCards", new AbstractMovesHandler<DiscardCardParams>(DiscardCardParams.class, movesFacade) {
            @Override
            protected IGame exchangeData(DiscardCardParams requestData) throws MissingCookieException, IllegalCommandException, ModelException {
                return getFacade().discardCards(requestData);
            }
        });

        //
        // util
        //
        server.createContext("/util/changeLogLevel", new AbstractHandler<Level, String, IUtilityFacade>(Level.class, utilityFacade) {
            @Override
            protected String exchangeData(Level requestData) throws MissingCookieException, IllegalCommandException, ModelException {
                getFacade().changeLogLevel(requestData);
                return "Logging level changed to " + requestData;
            }
        });
        // TODO: fill out the rest of the handlers

        // Handlers for the Swagger UI
        server.createContext("/docs/api/data", new SwaggerHandler.JSONAppender(""));
        server.createContext("/docs/api/view", new SwaggerHandler.BasicFile(""));
    }
	
	/**
	 * Start and run the server.
	 * @param args provide the port number for the server to run on
	 */
	public static void main(String[] args) {
		// Check if there are extra arguments
		if (args.length > 1) {
			System.err.println("Usage:\njava Server PORT_NUMBER");
			System.exit(1);
		}
		
		try {
			int portNumber;
			
			if (args.length == 0) {
				portNumber = 8081;
				System.out.println("No port number provided, using default (" + portNumber + ").");
			}
            else {
				portNumber = Integer.parseInt(args[0]);
				if (portNumber < 1024 || portNumber > 65535)
					throw new NumberFormatException();
			}
			
			new Server().run(portNumber);
		}
        catch (NumberFormatException e) {
			System.err.println("\"" + args[0] + "\" is not a valid port number.");
			System.err.println("The port number must be an integer greater than 1023 and "
				+ "less than 65536.");
			System.err.println("Usage:\n\tjava Server PORT_NUMBER");
		}
	}
}
