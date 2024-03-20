package ru.itmo.se.commands;

import ru.itmo.se.exceptions.InvalidArgumentCountException;
import ru.itmo.se.utilities.Console;

import lombok.ToString;

/**
 * This class implements the command Help. It outputs all commands and their specifications in a table format.
 * -- TOSTRING --
 * This method is a custom implementation of the toString() method in the Help class.
 */
@ToString
public class Help extends CommandImpl {
    /**
     * Constructs a Help.
     */
    public Help() {
        super("Help", "Outputs a table of available commands");
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
            return true;
        } catch (InvalidArgumentCountException e) {
            Console.println("Usage: '" + getName() + "'");
        }
        return false;
    }
}
