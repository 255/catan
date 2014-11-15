package server.command;

import shared.model.IGame;
import shared.model.IPlayer;
import shared.model.ResourceBank;

/**
 * Class that represents the DiscardCards request
 *
 * @author StevenBarnett
 */
public class DiscardCardsCommand extends AbstractCommand {

    private ResourceBank discardedCards;

    public DiscardCardsCommand(IGame theGame, IPlayer player, ResourceBank discardedCards) throws IllegalCommandException {
        super(theGame, player);
        this.discardedCards = discardedCards;

        // does the player have enough cards to merit a discard operation
        final int DISCARD_THRESHOLD = 7;
        //IPlayer p = getGame().getPlayers().get(playerIndex);
        if(player.getResources().getCount() <= DISCARD_THRESHOLD) {
            throw new IllegalCommandException(
                    "The player has less than " + DISCARD_THRESHOLD +
                    " resource cards and should not have to discard"
            );
        }
    }

    /**
     * Subtracts the specified cards from the hand
     * of the player who has to discard cards.
     */
    public void performAction() {
        // remove the discarded cards from the provided player index
        //IPlayer p = getGame().getPlayers().get(playerIndex);
        getPlayer().getResources().subtract(discardedCards);

        getPlayer().setDiscarded(true);

        // add the resources back to the bank
        getGame().getResourceBank().add(discardedCards);
    }
}
