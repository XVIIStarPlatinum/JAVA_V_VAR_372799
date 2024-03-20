package ru.itmo.se.commands;

import lombok.ToString;
import ru.itmo.se.exceptions.EmptyHistoryException;
import ru.itmo.se.exceptions.InvalidArgumentCountException;
import ru.itmo.se.utilities.CommandManager;
import ru.itmo.se.utilities.Console;

/**
 * This class implements the command History. It outputs the 10 most recently used commands without their arguments.
 * -- TOSTRING --
 * This method is a custom implementation of the toString() method in the History class.
 */
@ToString
public class History extends CommandImpl {
    /**
     * This field holds an instance of a CommandManager which is responsible for operations with commands.
     */
    private CommandManager commandManager;
    /**
     * Constructs a History with the specified CommandManager.
     * @param commandManager the specified CommandManager.
     */
    public History(CommandManager commandManager) {
        super("History", "Outputs the 10 last used commands");
        this.commandManager = commandManager;
    }
    /**
     * This method is an implementation of the abstract apply() method for the History command.
     * @param arg the argument (unnecessary).
     * @return true if the command was successfully executed, <p>false if the command encountered an error.
     */
    @Override
    public boolean apply(String arg) {
        try {
            if (!arg.isEmpty()) {
                throw new InvalidArgumentCountException("You don't need an argument here.", new RuntimeException());
            }
            if (commandManager.commandHistory.length == 0) {
                throw new EmptyHistoryException("You just started this session, of course the History is empty.", new RuntimeException());
            }
            Console.println("Recently used commands:");
            for (String s : commandManager.commandHistory) {
                if (s != null) Console.println(" " + s);
            }
            return true;
        } catch (EmptyHistoryException e) {
            Console.printError("Not a single command was executed yet.");
        } catch (InvalidArgumentCountException e) {
            Console.println("Usage: '" + getName() + "'");
        }
        return false;
    }
}
