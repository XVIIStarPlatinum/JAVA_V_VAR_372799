package ru.itmo.se.commands;

import lombok.ToString;
import ru.itmo.se.exceptions.InvalidArgumentCountException;
import ru.itmo.se.utilities.CollectionManager;
import ru.itmo.se.utilities.Console;

@ToString
public class save extends CommandImpl {

    private final CollectionManager collectionManager;

    public save(CollectionManager collectionManager) {
        super("save", "Saves the changes made during a session into a given file");
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean apply(String arg) {
        try {
            if (!arg.isEmpty()) {
                throw new InvalidArgumentCountException("What did you want to execute?", new RuntimeException());
            }
            collectionManager.saveCollection();
            Console.println("File may be successfully saved. If there's an error message above this, then your changes have not been saved.");
            return true;
        } catch (InvalidArgumentCountException e) {
            Console.println("Usage: '" + getName() + "'");
        }
        return false;
    }
}
