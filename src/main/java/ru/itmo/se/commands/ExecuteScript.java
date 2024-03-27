package ru.itmo.se.commands;

import lombok.ToString;
import ru.itmo.se.exceptions.InvalidArgumentCountException;
import ru.itmo.se.utilities.Console;

/**
 * This class implements the command execute_script. It executes a sequence of commands in a file.
 * -- TOSTRING --
 * This method is a custom implementation of the toString() method in the execute_script class.
 */
@ToString
public class ExecuteScript extends CommandImpl {
    /**
     * Constructs an execute_script.
     */
    public ExecuteScript() {
        super("execute_script <file_name>", "Executes a script from a given file");
    }
    /**
     * This method is an implementation of the abstract apply() method for the execute_script command.
     *
     * @param arg the argument (necessary).
     * @return true if the command was successfully executed, <p>false if the command encountered an error.
     */
    @Override
    public boolean apply(String arg) {
        try {
            if (arg.isEmpty()) {
                throw new InvalidArgumentCountException("You need an argument here.", new RuntimeException());
            } else {
                Console.println("Executing script '" + arg + "' right now...");
            }
            return true;
        } catch (InvalidArgumentCountException e) {
            Console.println("Usage: '" + getName() + "'");
        }
        return false;
    }
}
