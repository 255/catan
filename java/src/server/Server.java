package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import server.facade.*;
import server.handler.*;
import shared.communication.*;

import com.sun.net.httpserver.HttpServer;
import shared.model.Game;
import shared.model.IUser;

/**
 * The main class of the record indexer server.
 */
public class Server {
	private static Logger logger;
	private static int MAX_WAITING_CONNECTIONS = 0; // take the default value
	
	// Initialize the logger for the server
	static {
		initializeLog();
	}
	
	/**
	 * Initialize the logger for this project. The logging level is hard-coded.
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
        final IUserFacade userFacade = new UserFacade();
        final IJoinGameFacade joinGameFacade = new JoinGameFacade();
        final IGameFacade gameFacade = new GameFacade();
        final IMovesFacade movesFacade = new MovesFacade();
        final IUtilityFacade utilityFacade = new UtilityFacade();

        // Create HTTPHandlers for each type of request

        //
        // user
        //
        server.createContext("/user/login", new AbstractUserHandler(userFacade) {
            @Override
            protected IUser exchangeData(CredentialsParams requestData) throws ServerException {
                return getFacade().login(requestData);
            }
        });

        server.createContext("/user/register", new AbstractUserHandler(userFacade) {
            @Override
            protected IUser exchangeData(CredentialsParams requestData) throws ServerException {
                return getFacade().register(requestData);
            }
        });

        //
        // games
        //
		server.createContext("/games/join", new JoinHandler(joinGameFacade));

        //
        // game
        //
        server.createContext("/game/model", new GameModelHandler(gameFacade));

        //
        // moves
        //
		server.createContext("/moves/sendChat", new AbstractMovesHandler<SendChatParams>(SendChatParams.class, movesFacade) {
            @Override
            protected Game exchangeData(SendChatParams requestData) throws ServerException {
                return getFacade().sendChat(requestData);
            }
        });

	    server.createContext("/moves/rollNumber", new AbstractMovesHandler<RollNumberParams>(RollNumberParams.class, movesFacade) {
            @Override
            protected Game exchangeData(RollNumberParams requestData) throws ServerException {
                return getFacade().rollNumber(requestData);
            }
        });

        //
        // util
        //
        server.createContext("/util/changeLogLevel", new AbstractHandler<Level, String, IUtilityFacade>(Level.class, utilityFacade) {
            @Override
            protected String exchangeData(Level requestData) throws ServerException {
                getFacade().changeLogLevel(requestData);
                return "Logging level changed to " + requestData;
            }
        });
        // TODO: fill out the rest of the handlers
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