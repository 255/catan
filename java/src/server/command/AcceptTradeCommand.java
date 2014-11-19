package server.command;

import shared.model.IGame;
import shared.model.IPlayer;
import shared.model.ITradeOffer;

/**
 * Class that represents the AcceptTrade request
 *
 * @author StevenBarnett
 */
public class AcceptTradeCommand extends AbstractCommand {
    //private int playerIndex;
    private boolean willAccept;

    public AcceptTradeCommand(IGame game, IPlayer player, boolean willAccept) throws IllegalCommandException {
        super(game, player, (willAccept ? "accepted the trade" : "rejected the trade"));
        //this.playerIndex = playerIndex;
        this.willAccept = willAccept;

        // can the trade be accepted?
        //IPlayer p = getGame().getPlayers().get(playerIndex);
        if(willAccept && !getGame().canAcceptTrade(getPlayer()))/*player.canAcceptTrade(getGame().getTradeOffer().getOffer()))*/ {
            throw new IllegalCommandException(
                    "The player is unable to accept the current trade offer"
            );
        }
    }

    /**
     * Player indicates whether they accept the trade or not. If so,
     * resources are moved to complete the transaction.
     */
    public void performAction() {
        // get the player object
        //IPlayer p = getGame().getPlayers().get(playerIndex);

        // complete the trade if the player as accepts the trade
        if(willAccept) {
            // get the trade offer
            ITradeOffer to = getGame().getTradeOffer();

            // subtract the trade offer from the sending player resource bank
            to.getSender().getResources().subtract(to.getOffer());

            // add the trade offer to the receiving player resource bank
            to.getReceiver().getResources().add(to.getOffer());
        }

        // clear the trade offer
        getGame().setTradeOffer(null);
    }
}
