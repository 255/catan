package server.command;

import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.model.IGame;
import shared.model.IPlayer;
import shared.model.ModelException;

import java.util.List;
import java.util.logging.Logger;

/**
 * Class that represents the Monopoly request
 *
 * @author StevenBarnett
 */
public class MonopolyCommand extends AbstractCommand {

    private final static Logger logger = Logger.getLogger("catan");

    private ResourceType m_resourceType;

    public MonopolyCommand(IGame game, IPlayer player, ResourceType resourceType) throws IllegalCommandException {

        super(game, player, "played a monopoly card");

        m_resourceType = resourceType;

        if (!getGame().canPlayMonopoly(getPlayer())) {
            throw new IllegalCommandException("Player cannot play the Monopoly DevCard");
        }
    }
    /**
     * Performs a monopoly request. Takes the resource that
     * the player requested from all other player's hands
     * and adds them to the playing player's hand.
     */
    public void performAction() {

        // Get all the players in the game
        List<IPlayer> players = getGame().getPlayers();

        int resourceCount = 0;

        // Loop through each player that is not the local player
        for (IPlayer player : players) {
            if (!getGame().isPlayersTurn(player)) {

                // Get count of resource type
                int playerHandCount = player.getResources().getCount(m_resourceType);

                // Increment the count to add to the playing player
                resourceCount += playerHandCount;

                // Decrement resource type from player
                player.getResources().subtract(playerHandCount, m_resourceType);
            }
        }

        getPlayer().getResources().add(resourceCount, m_resourceType);

        try {
            getPlayer().getPlayableDevCards().remove(DevCardType.MONOPOLY);
        } catch (ModelException ex) {
            logger.finer("ModelException was thrown in the MonopolyCommand class when attempting to remove a Monopoly DevCard from " +
                    "a player's hand after they played it. Exception message is as follows: " + ex.getMessage());
        }
    }
}
