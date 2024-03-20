package ru.itmo.se.commands;

import ru.itmo.se.exceptions.InvalidArgumentCountException;
import ru.itmo.se.utilities.Console;

import lombok.ToString;

/**
 * This class implements the command exit. It gracefully terminates the CLI application.
 * -- TOSTRING --
 * This method is a custom implementation of the toString() method in the exit class.
 */
@ToString
public class exit extends CommandImpl {
    /**
     * Constructs an exit.
     */
    public exit() {
        super("exit", "Gracefully terminates the console application");
    }

    /**
     * This method is an implementation of the abstract apply() method for the exit command.
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
