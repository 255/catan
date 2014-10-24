package client.main;

import javax.swing.*;

import client.catan.*;
import client.login.*;
import client.join.*;
import client.misc.*;
import client.base.*;
import client.network.*;
import client.points.GameFinishedView;
import client.poller.ServerPoller;
import shared.model.ServerModelFacade;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.*;

/**
 * Main entry point for the Catan program
 */
@SuppressWarnings("serial")
public class Catan extends JFrame
{
	private static Logger logger;

    static {
        initializeLog();
    }

    /**
     * Initialize the logger for this project. The logging level is hard-coded.
     */
    private static void initializeLog() {
        try {
            final Level logLevel = Level.FINE;

            logger = Logger.getLogger("catan");
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
            FileHandler fileHandler = new FileHandler("logs/log_" + startTime + ".log", false);
            fileHandler.setLevel(logLevel);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);

            logger.info("Catan Client started at " + startTime + ".");
        } catch (IOException e) {
            System.err.println("There was a problem initializing the log: " + e.getMessage());
        }
    }

    private CatanPanel catanPanel;

    public Catan()
    {

        client.base.OverlayView.setWindow(this);

        this.setTitle("Settlers of Catan");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        catanPanel = new CatanPanel();
        this.setContentPane(catanPanel);
		
		display();
	}
	
	private void display()
	{
		pack();
		setVisible(true);
	}
	
	//
	// Main
	//
	
	public static void main(final String[] args)
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run()
			{
				new Catan();

                // Initialize the HTTP communicator
                IHttpCommunicator communicator = new HttpCommunicator();
                GameAdminServerProxy gameAdminProxy = new GameAdminServerProxy(communicator);
                GameAdministrator.getInstance().setGameAdminServerProxy(gameAdminProxy);

                // initialize server proxies
                TestServerProxy testProxy = new TestServerProxy();
                ServerProxy proxy = new ServerProxy(communicator);
                ServerModelFacade.getInstance().setServerProxy(proxy);
                if(args.length == 1 && args[0].equals("--fake-poller")) {
                    ServerPoller poller = ServerPoller.getInstance();
                    poller.setProxy(testProxy);
                    poller.startPolling();
                    poller.updateGame();
                }
				
				PlayerWaitingView playerWaitingView = new PlayerWaitingView();
				final PlayerWaitingController playerWaitingController = new PlayerWaitingController(playerWaitingView);
				playerWaitingView.setController(playerWaitingController);
				
				JoinGameView joinView = new JoinGameView();
				NewGameView newGameView = new NewGameView();
				SelectColorView selectColorView = new SelectColorView();
				MessageView joinMessageView = new MessageView();
				final JoinGameController joinController = new JoinGameController(
																				 joinView,
																				 newGameView,
																				 selectColorView,
																				 joinMessageView);
				joinController.setJoinAction(new IAction() {
					@Override
					public void execute()
					{
                        ServerPoller.getInstance().setProxy(proxy);
                        ServerPoller.getInstance().updateGame();
                        ServerPoller.getInstance().startPolling();
                        playerWaitingController.start();
					}
				});
				joinView.setController(joinController);
				newGameView.setController(joinController);
				selectColorView.setController(joinController);
				joinMessageView.setController(joinController);
				
				LoginView loginView = new LoginView();
				MessageView loginMessageView = new MessageView();
				LoginController loginController = new LoginController(loginView,
																	  loginMessageView);
				loginController.setLoginAction(new IAction() {
					@Override
					public void execute()
					{
						joinController.start();
					}
				});
				loginView.setController(loginController);

                // TODO: enable when ready for testing
                if(args.length == 0)
				    loginController.start();
                else if(args.length == 1 && !args[0].equals("--fake-poller"))
				    loginController.start();
			}
		});
	}
	
}

