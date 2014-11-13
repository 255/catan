package server.command;

import shared.model.IGame;
import shared.model.IPlayer;

/**
 * Class that represents the Soldier request
 *
 * @author StevenBarnett
 */
public class SoldierCommand extends AbstractCommand {

    public SoldierCommand(IGame game, IPlayer player) {
        super(game, player, "played a soldier card");
    }

    /**
     * Moves the robber to the selected location. Lets the
     * player choose which player to rob from if multiple
     * players are on that tile location.
     */
    public void performAction() {
        //TODO: implement
    }
}
