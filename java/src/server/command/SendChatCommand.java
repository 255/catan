package server.command;

import shared.model.IGame;
import shared.model.IPlayer;

/**
 * Class that represents the SendChat request
 *
 * @author StevenBarnett
 */
public class SendChatCommand extends AbstractCommand {

    /**
     * A constructor for a command that does not produce a log message.
     *
     * @param game
     * @param player
     */
    public SendChatCommand(IGame game, IPlayer player) {
        super(game, player);
    }

    /**
     * Adds a new chat to the chat history.
     */
    public void performAction() {
        //TODO: implement
    }
}
