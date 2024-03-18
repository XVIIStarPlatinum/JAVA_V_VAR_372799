package ru.itmo.se.commands;

import lombok.ToString;
import ru.itmo.se.exceptions.EmptyCollectionException;
import ru.itmo.se.exceptions.InvalidArgumentCountException;
import ru.itmo.se.utilities.CollectionManager;
import ru.itmo.se.utilities.Console;

@ToString
public class shuffle extends CommandImpl {

    private final CollectionManager collectionManager;

    public shuffle(CollectionManager collectionManager) {
        super("shuffle", "Randomly shuffles the collection");
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean apply(String arg) {
        try {
            if (!arg.isEmpty()) {
                throw new InvalidArgumentCountException("What did you want to execute?", new RuntimeException());
            }
            if (collectionManager.collectionSize() == 0) {
                throw new EmptyCollectionException("Empty collection.", new RuntimeException());
            }
            collectionManager.shuffleCollection();
            Console.println("Collection successfully scrambled.");
            return true;
        } catch (InvalidArgumentCountException e) {
            Console.println("Usage: '" + getName() + "'");
        } catch (EmptyCollectionException e) {
            Console.printError("Empty collection.");
        }
        return false;
    }
}
