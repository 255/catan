package server.command;

/**
 * Class that represents the BuyDevCard request
 *
 * @author StevenBarnett
 */
public class BuyDevCardCommand implements ICommand {

    /**
     * Takes a development card from the development card
     * pile, and adds it to the development card hand of the
     * player who made this request. The card is added to
     * the player's new dev card hand until the next turn.
     * The price of a development card is reduced from the
     * player's resources.
     */
    public void execute() {

    }
}
