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

//        if (getGame().canPlaySocanPlayS)
    }

    /**
     * Player plays a Soldier DevCard.
     */
    public void performAction() {
        //TODO: implement
    }
}
