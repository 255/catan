package client.communication;

import client.base.*;
import com.sun.corba.se.spi.activation.Server;
import shared.model.GameModelFacade;
import shared.model.ModelException;
import shared.model.ServerModelFacade;

import java.util.Observable;


/**
 * Chat controller implementation
 */
public class ChatController extends Controller implements IChatController {

	public ChatController(IChatView view) {
		
		super(view);

        //(Game)(GameModelFacade.getInstance().getGame()).addObserver(this);
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
            // TODO: What should we do about this exception?
        }
    }

    @Override
    public void update(Observable o, Object arg) {
//        try {
//            getView().setEntries(GameModelFacade.getInstance().getChatHistory());
//        } catch (ModelException ex) {
//            // TODO: What should we do about this exception?
//        }
    }
}

