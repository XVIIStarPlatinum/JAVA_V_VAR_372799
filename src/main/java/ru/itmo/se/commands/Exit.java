package ru.itmo.se.commands;

import lombok.ToString;
import ru.itmo.se.exceptions.InvalidArgumentCountException;
import ru.itmo.se.utilities.Console;

/**
 * This class implements the command Exit. It gracefully terminates the CLI application.
 * -- TOSTRING --
 * This method is a custom implementation of the toString() method in the Exit class.
 */
@ToString
public class Exit extends CommandImpl {
    /**
     * Constructs an Exit.
     */
    public Exit() {
        super("exit", "Gracefully terminates the console application");
    }

    /**
     * This method is an implementation of the abstract apply() method for the Exit command.
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
            Console.println("Usage: '" + arg + "'");
        }
        return false;
    }
}
