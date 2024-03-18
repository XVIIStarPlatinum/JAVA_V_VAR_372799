package ru.itmo.se.commands;

import lombok.ToString;
import ru.itmo.se.exceptions.InvalidArgumentCountException;
import ru.itmo.se.utilities.CollectionManager;
import ru.itmo.se.utilities.Console;

@ToString
public class show extends CommandImpl {

    private final CollectionManager collectionManager;

    public show(CollectionManager collectionManager) {
        super("show", "Outputs all elements of the collection");
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean apply(String arg) {
        try {
            if (!arg.isEmpty()) {
                throw new InvalidArgumentCountException("What did you want to execute?", new RuntimeException());
            }
            Console.println(collectionManager);
            return true;
        } catch (InvalidArgumentCountException e) {
            Console.println("Usage: '" + getName() + "'");
        }
        return false;
    }
}
