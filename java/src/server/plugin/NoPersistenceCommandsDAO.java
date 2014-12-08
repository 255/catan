package server.plugin;

import server.command.ICommand;
import server.persistence.ICommandsDAO;
import shared.model.IGame;

import java.util.ArrayList;
import java.util.List;

/**
 * A CommandsDAO that does nothing. It returns an empty list of commands for loadCommands.
 */
public class NoPersistenceCommandsDAO implements ICommandsDAO {
    @Override
    public void saveCommand(ICommand command) {
    }

    @Override
    public void loadCommands(IGame game) {

    }
}