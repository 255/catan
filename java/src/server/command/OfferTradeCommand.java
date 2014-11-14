package server.command;

import shared.definitions.ResourceType;
import shared.model.*;

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
        if(getGame().getTradeOffer() == null) {
            throw new IllegalCommandException(
                    "The trade being offered is null." +
                    "Perhaps a trade offer hasn't been posted."
            );
        }
    }

    /**
     * Offers a trade to the specified player. Shows the
     * player the requested resources and the resources
     * to be given.
     */
    public void performAction() {
        // create a new trade offer with the parameters
        ITradeOffer trade = new TradeOffer(getPlayer(), receiver, offer);

        // set the game object to point at the trade offer
        getGame().setTradeOffer(trade);
    }
}
