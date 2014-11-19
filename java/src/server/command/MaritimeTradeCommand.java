package server.command;

import shared.definitions.ResourceType;
import shared.model.IGame;
import shared.model.IPlayer;

/**
 * Class that represents the MaritimeTrade request
 *
 * @author StevenBarnett
 */
public class MaritimeTradeCommand extends AbstractCommand {

    //private int playerIndex;
    private int ratio;
    private ResourceType inputResource;
    private ResourceType outputResource;

    public MaritimeTradeCommand (IGame theGame, IPlayer player, ResourceType inputResource, ResourceType outputResource, int ratio) throws IllegalCommandException {

        super(theGame, player, "performed a maritime trade");
        this.ratio = ratio;
        this.inputResource = inputResource;
        this.outputResource = outputResource;

        // get the player object from the player index
        //IPlayer p = getGame().getPlayers().get(playerIndex);

        // check to see if the command is valid
        // is it the player's turn?
        if(!getGame().isPlayersTurn(player)) {
            throw new IllegalCommandException(
                    "It is not this player's turn: " + player.getName()
            );
        }

        // does the player have enough resources?
        if(!player.getResources().canAfford(ratio, inputResource)) {
            throw new IllegalCommandException(
                    "Player " + player.getName() + " does not have enough " + inputResource.name()
            );
        }

        // does the game bank have enough to meet the request
        if(!getGame().getResourceBank().canAfford(1, outputResource)) {
            throw new IllegalCommandException(
                    "The bank is unable to give 1 " + outputResource.name() + " to the player"
            );
        }
    }

    /**
     * Makes a maritime trade request. Resources that the
     * player requests are added to their hand. Resources that
     * the player trades in are taken from their hand.
     */
    public void performAction() {
        // get the player from the index
        //IPlayer p = getGame().getPlayers().get(playerIndex);

        // remove the input resources from the player
        getPlayer().getResources().subtract(ratio, inputResource);

        // give the input resources to the game bank
        getGame().getResourceBank().add(ratio, inputResource);

        // add the output resources to the player
        getPlayer().getResources().add(1, outputResource);

        // remove output resources from the game bank
        getGame().getResourceBank().subtract(1, outputResource);

        assert (getGame().verifyResourceAmount());
    }
}
