package server.command;

import shared.locations.HexLocation;
import shared.model.IGame;
import shared.model.IPlayer;

/**
 * Class that represents the RobPlayer request
 *
 * @author StevenBarnett
 */
public class RobPlayerCommand extends AbstractCommand {

    public RobPlayerCommand(IGame game, IPlayer player, IPlayer victim, HexLocation location) {
        super(game, player, "robbed " + victim.getName());
        // TODO: implement
    }

    /**
     * Takes a random card from the robbed player's hand
     * and moves that card to the robbing player's hand.
     */
    public void performAction() {
        // TODO: implement

    }
}
