package server.command;

import shared.model.IGame;

/**
 * Class that represents the AcceptTrade request
 *
 * @author StevenBarnett
 */
public class AcceptTradeCommand extends AbstractCommand {
    public AcceptTradeCommand(IGame game) throws IllegalCommandException {
        super(game);
    }

    /**
     * Player indicates whether they accept the trade or not. If so,
     * resources are moved to complete the transaction.
     */
    public void execute() {

    }
}
