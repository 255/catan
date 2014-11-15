package server.command;

import shared.model.*;

/**
 * Class that represents the OfferTrade request
 *
 * @author StevenBarnett
 */
public class OfferTradeCommand extends AbstractCommand {

    private ResourceBank offer;
    private IPlayer receiver;

    public OfferTradeCommand(IGame theGame, IPlayer player, IPlayer receiver, ResourceBank offer) throws IllegalCommandException {

        super(theGame, player, "offered a trade to " + receiver.getName());
        this.offer = offer;
        this.receiver = receiver;

        // is the trade offer null?
        if(getGame().getTradeOffer() != null) {
            throw new IllegalCommandException("There is already a trade being offered!");
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
