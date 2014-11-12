package server.command;

import server.ServerException;
import shared.definitions.DevCardType;
import shared.model.IGame;
import shared.model.IPlayer;
import shared.model.Prices;

/**
 * Class that represents the BuyDevCard request
 *
 * @author StevenBarnett
 */
public class BuyDevCardCommand extends AbstractCommand {

    private IPlayer m_player;

    public BuyDevCardCommand(IGame game, IPlayer player) throws IllegalCommandException {

        super(game);

        m_player = player;

        // Check that player has enough resources
        if (!m_player.canAfford(Prices.DEV_CARD)) {
            throw new IllegalCommandException("Player tried to buy DevCard, but player does not have the resources to buy one");
        }

        // Check that the game has enough dev cards in the bank
        if (getGame().getDevCards().getCount() == 0) {
            throw new IllegalCommandException("Player tried to buy DevCard, but game does not have any DevCards");
        }
    }

    /**
     * Takes a development card from the development card
     * pile, and adds it to the development card hand of the
     * player who made this request. The card is added to
     * the player's new dev card hand until the next turn.
     * The price of a development card is reduced from the
     * player's resources.
     */
    public void execute() {

        // Randomly pick a dev card from the dev card hand
        DevCardType devCard = getGame().getDevCards().drawCard();

        // Put it in the player's newDevCards hand
        m_player.getNewDevCards().add(devCard);

        // Remove resources from the player
        m_player.removeResources(Prices.DEV_CARD);

        // Put removed resources in the Game ResourceBank
        getGame().getResourceBank().add(Prices.DEV_CARD);
    }
}
