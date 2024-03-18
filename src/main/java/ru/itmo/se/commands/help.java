package ru.itmo.se.commands;

import lombok.ToString;
import ru.itmo.se.exceptions.InvalidArgumentCountException;
import ru.itmo.se.utilities.Console;

@ToString
public class help extends CommandImpl {

    public help() {
        super("help", "Outputs a table of available commands");
    }

    @Override
    public boolean apply(String arg) {
        try {
            if (!arg.isEmpty()) {
                throw new InvalidArgumentCountException("What did you want to execute?", new RuntimeException());
            }
            return true;
        } catch (InvalidArgumentCountException e) {
            Console.println("Usage: '" + getName() + "'");
        }
        return false;
    }
}
