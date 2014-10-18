package client.communication;

import client.base.*;
import shared.model.Game;
import shared.model.ModelException;
import shared.model.ServerModelFacade;

import java.util.Observable;
import java.util.logging.Logger;


/**
 * Chat controller implementation
 */
public class ChatController extends Controller implements IChatController {

    private final static Logger logger = Logger.getLogger("catan");

    public ChatController(IChatView view) {
		
		super(view);

        Game.getInstance().addObserver(this);
	}

	@Override
	public IChatView getView() {
		return (IChatView)super.getView();
	}

	@Override
	public void sendMessage(String message) {
        try {
            ServerModelFacade.getInstance().sendChat(message);
        } catch (ModelException ex) {
            logger.fine("Sending the chat, " + message + ", did not work");
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        getView().setEntries(Game.getInstance().getChatHistory().getMessages());
    }
}

