package server.command;

import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.model.IGame;
import shared.model.IPlayer;

/**
 * Class that represents the YearOfPlenty request
 *
 * @author StevenBarnett
 */
public class YearOfPlentyCommand extends AbstractCommand {

    private ResourceType m_firstType;
    private ResourceType m_secondType;

    public YearOfPlentyCommand(IGame game, IPlayer player, ResourceType firstType, ResourceType secondType) throws IllegalCommandException {
        super(game, player, "played a year of plenty card");

        if (!getGame().canPlayYearOfPlenty(getPlayer(), firstType, secondType)) {
            throw new IllegalCommandException("Player" + player.getName() + " attempted to play YearOfPlenty DevCard, requesting " + firstType.name() + " and "
                    + secondType.name() + " but that move is not allowed");
        }
    }

    /**
     * Gives the playing player two resource cards
     * of their choosing.
     */
    public void performAction() {
        getPlayer().playDevCard(DevCardType.YEAR_OF_PLENTY);

        // Remove the resource cards from the game's resource bank
        getGame().getResourceBank().subtract(1, m_firstType);
        getGame().getResourceBank().subtract(1, m_secondType);

        // Add the resource cards to the player's hand
        getPlayer().addResources(1, m_firstType);
        getPlayer().addResources(1, m_secondType);
    }
}
