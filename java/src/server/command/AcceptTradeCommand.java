package server.command;

import shared.model.IGame;
import shared.model.IPlayer;

/**
 * Class that represents the AcceptTrade request
 *
 * @author StevenBarnett
 */
public class AcceptTradeCommand extends AbstractCommand {
    //private int playerIndex;
    private IPlayer player;
    private boolean willAccept;

    public AcceptTradeCommand(
            //int playerIndex,
            IPlayer player,
            boolean willAccept,
            IGame game) throws IllegalCommandException {
        super(game);
        //this.playerIndex = playerIndex;
        this.player = player;
        this.willAccept = willAccept;

        // can the trade be accepted?
        //IPlayer p = getGame().getPlayers().get(playerIndex);
        if(!player.canAcceptTrade(getGame().getTradeOffer().getOffer())) {
            throw new IllegalCommandException(
                    "The player is unable to accept the current trade offer"
            );
        }
    }

    /**
     * Player indicates whether they accept the trade or not. If so,
     * resources are moved to complete the transaction.
     */
    public void execute() {
        // get the player object
        //IPlayer p = getGame().getPlayers().get(playerIndex);

        // mark the player as accepting/rejecting the trade
        if(willAccept) {

        }
        else {

        }
    }
}
