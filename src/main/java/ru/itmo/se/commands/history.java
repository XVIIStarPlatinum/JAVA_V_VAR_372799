package ru.itmo.se.commands;

import lombok.ToString;
import ru.itmo.se.exceptions.InvalidArgumentCountException;
import ru.itmo.se.utilities.Console;

/**
 * This class implements the command history. It outputs the 10 most recently used commands without their arguments.
 * -- TOSTRING --
 * This method is a custom implementation of the toString() method in the history class.
 */
@ToString
public class history extends CommandImpl {
    /**
     * Constructs a history.
     */
    public history() {
        super("history", "Outputs the 10 last used commands");
    }

    /**
     * This method is an implementation of the abstract apply() method for the history command.
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
