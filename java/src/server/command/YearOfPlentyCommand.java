package server.command;

import shared.model.IGame;
import shared.model.IPlayer;

/**
 * Class that represents the YearOfPlenty request
 *
 * @author StevenBarnett
 */
public class YearOfPlentyCommand extends AbstractCommand {

    public YearOfPlentyCommand(IGame game, IPlayer player) {
        super(game, player, "played a year of plenty card");
    }

    /**
     * Gives the playing player two resource cards
     * of their choosing.
     */
    public void performAction() {
        // TODO: implement
    }
}
