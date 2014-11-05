package server.command;

/**
 * Interface that facades require to execute certain commands on the Server Model
 *
 * @author StevenBarnett
 */
public interface ICommand {

    /**
     * Implements all the functionality and interaction with the Server Model that
     * this command requires
     */
    public void execute();
}
