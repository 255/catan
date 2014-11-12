package server.command;

import shared.definitions.ResourceType;
import shared.model.Game;

/**
 * Class that represents the MaritimeTrade request
 *
 * @author StevenBarnett
 */
public class MaritimeTradeCommand implements ICommand {

    private int playerIndex;
    private int ratio;
    private ResourceType inputResource;
    private ResourceType outputResource;
    private Game theGame;

    MaritimeTradeCommand(
            int playerIndex,
            int ratio,
            ResourceType inputResource,
            ResourceType outputResource,
            Game theGame) {
        this.playerIndex = playerIndex;
        this.ratio = ratio;
        this.inputResource = inputResource;
        this.outputResource = outputResource;
        this.theGame = theGame;
    }

    /**
     * Makes a maritime trade request. Resources that the
     * player requests are added to their hand. Resources that
     * the player trades in are taken from their hand.
     */
    public void execute() {


        // remove the input resources from the player

        // add the output resources to the player
    }
}
