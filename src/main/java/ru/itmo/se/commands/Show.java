package ru.itmo.se.commands;

import lombok.ToString;
import ru.itmo.se.exceptions.InvalidArgumentCountException;
import ru.itmo.se.utilities.CollectionManager;
import ru.itmo.se.utilities.Console;

/**
 * This class implements the command Show. It outputs all the elements of the collection.
 * -- TOSTRING --
 * This method is a custom implementation of the toString() method in the Show class.
 */
@ToString
public class Show extends CommandImpl {
    /**
     * This field holds an instance of a CollectionManager which is responsible for operations with the collection.
     */
    private final CollectionManager collectionManager;

    /**
     * Constructs a Show with the specified CollectionManager.
     *
     * @param collectionManager the specified CollectionManager.
     */
    public Show(CollectionManager collectionManager) {
        super("Show", "Outputs all elements of the collection");
        this.collectionManager = collectionManager;
    }

    /**
     * This method is an implementation of the abstract apply() method for the Show command.
     * @param arg the argument (unnecessary).
     * @return true if the command was successfully executed, <p>false if the command encountered an error.
     */
    @Override
    public boolean apply(String arg) {
        try {
            if (!arg.isEmpty()) {
                throw new InvalidArgumentCountException("You don't need an argument here.", new RuntimeException());
            }
            Console.println(collectionManager);
            return true;
        } catch (InvalidArgumentCountException e) {
            Console.println("Usage: '" + getName() + "'");
        }
        return false;
    }
}
