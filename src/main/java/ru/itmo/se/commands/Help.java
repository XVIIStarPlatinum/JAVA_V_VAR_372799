package ru.itmo.se.commands;

import lombok.ToString;
import ru.itmo.se.exceptions.InvalidArgumentCountException;
import ru.itmo.se.utilities.CommandManager;
import ru.itmo.se.utilities.Console;

/**
 * This class implements the command Help. It outputs all commands and their specifications in a table format.
 * -- TOSTRING --
 * This method is a custom implementation of the toString() method in the Help class.
 */
@ToString
public class Help extends CommandImpl {
    /**
     * This field holds an instance of a CollectionManager which is responsible for operations with commands.
     */
    private CommandManager commandManager;
    /**
     * Constructs a Help.
     * @param commandManager the specified CommandManager.
     */
    public Help(CommandManager commandManager) {
        super("help", "Outputs a table of available commands");
        this.commandManager = commandManager;
    }

    /**
     * This method is an implementation of the abstract apply() method for the Help command.
     *
     * @param arg the argument (unnecessary).
     * @return true if the command was successfully executed, <p>false if the command encountered an error.
     */
    @Override
    public boolean apply(String arg) {
        try {
            if (!arg.isEmpty()) {
                throw new InvalidArgumentCountException("You don't need an argument here.", new RuntimeException());
            }
            Console.printTable("                          COMMAND NAME", "                                  COMMAND SPECIFICATION");
            for (CommandImpl command : commandManager.commandMap.values()) {

                Console.printTable(command.getName(), command.getSpec());
            }
            return true;
        } catch (InvalidArgumentCountException e) {
            Console.println("Usage: '" + getName() + "'");
        }
        return false;
    }
}
