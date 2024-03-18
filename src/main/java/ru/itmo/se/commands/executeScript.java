package ru.itmo.se.commands;

import lombok.ToString;
import ru.itmo.se.exceptions.InvalidArgumentCountException;
import ru.itmo.se.utilities.Console;

@ToString
public class executeScript extends CommandImpl {

    public executeScript() {
        super("execute_script <file_name>", "Executes a script from a given file");
    }

    @Override
    public boolean apply(String arg) {
        try {
            if (arg.isEmpty()) {
                throw new InvalidArgumentCountException("What did you want to execute?", new RuntimeException());
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
