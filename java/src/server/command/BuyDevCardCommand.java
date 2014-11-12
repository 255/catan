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
public class BuyDevCardCommand implements ICommand {

    private IGame m_game;
    private IPlayer m_player;

    public BuyDevCardCommand(IGame game, IPlayer player) {
        m_game = game;
        m_player = player;

        // Check that player has enough resources
        if (!m_player.canAfford(Prices.DEV_CARD)) {
       //     throw new IllegalCommandException();
        }

        // Check that the game has enough dev cards in the bank
        if (m_game.getDevCards().getCount() == 0) {
//            thrown new Exception();
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
        DevCardType devCard = m_game.getDevCards().drawCard();

        // Put it in the player's newDevCards hand
        m_player.getNewDevCards().add(devCard);

        // Remove resources from the player
        m_player.removeResources(Prices.DEV_CARD);

        // Put removed resources in the Game ResourceBank
        m_game.getResourceBank().add(Prices.DEV_CARD);
    }
}
