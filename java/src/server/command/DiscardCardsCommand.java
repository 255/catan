package server.command;

import shared.model.GameState;
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
        if (getGame().getGameState() != GameState.Discarding
                || !player.needsToDiscard()
                || !player.canAfford(discardedCards)) {
            throw new IllegalCommandException("Invalid discard command.");
        }
    }

    /**
     * Subtracts the specified cards from the hand
     * of the player who has to discard cards.
     */
    public void performAction() {
        getGame().discardCards(getPlayer(), discardedCards);

        assert (getGame().verifyResourceAmount());
    }
}
