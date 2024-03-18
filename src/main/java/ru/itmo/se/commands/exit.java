package ru.itmo.se.commands;

import lombok.ToString;
import ru.itmo.se.exceptions.InvalidElementException;
import ru.itmo.se.utilities.Console;

@ToString
public class exit extends CommandImpl {

    public exit() {
        super("exit", "Gracefully terminates the console application");
    }

    @Override
    public boolean apply(String arg) {
        try {
            if (!arg.isEmpty()) {
                throw new InvalidElementException("What did you want to execute?", new RuntimeException());
            }
            return true;
        } catch (InvalidElementException e) {
            Console.println("Usage: '" + arg + "'");
        }
        return false;
    }
}
