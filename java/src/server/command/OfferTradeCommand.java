package server.command;

import shared.definitions.ResourceType;
import shared.model.Game;
import shared.model.IPlayer;
import shared.model.ResourceBank;

/**
 * Class that represents the OfferTrade request
 *
 * @author StevenBarnett
 */
public class OfferTradeCommand extends AbstractCommand {

    private ResourceBank offer;
    private IPlayer receiver;

    OfferTradeCommand(
            IPlayer player,
            ResourceBank offer,
            IPlayer receiver,
            Game theGame) throws IllegalCommandException {

        super(theGame, player, "offered a trade to " + receiver.getName());
        this.offer = offer;
        this.receiver = receiver;

        // is the trade offer null?
        // TODO what else can be broken on the trade offer
    }

    /**
     * Offers a trade to the specified player. Shows the
     * player the requested resources and the resources
     * to be given.
     */
    public void performAction() {

    }
}
