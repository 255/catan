package server.command;

import shared.model.IGame;
import shared.model.IPlayer;

/**
 * Class that represents the SendChat request
 *
 * @author StevenBarnett
 */
public class SendChatCommand extends AbstractCommand {

    private String m_message;

    /**
     * A constructor for a command that does not produce a log message.
     *
     * @param game
     * @param player
     */
    public SendChatCommand(IGame game, IPlayer player, String message) throws IllegalCommandException {
        super(game, player);

        m_message = message;
    }

    /**
     * Adds a new chat to the chat history.
     */
    public void performAction() {

        getGame().getChatHistory().addMessage(getPlayer(), m_message);
    }
}
