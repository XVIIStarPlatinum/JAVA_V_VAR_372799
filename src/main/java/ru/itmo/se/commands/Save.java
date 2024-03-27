package ru.itmo.se.commands;

import lombok.ToString;
import ru.itmo.se.exceptions.InvalidArgumentCountException;
import ru.itmo.se.utilities.CollectionManager;
import ru.itmo.se.utilities.Console;

/**
 * This class implements the command Save. It writes the collection into a file.
 * -- TOSTRING --
 * This method is a custom implementation of the toString() method in the Save class.
 */
@ToString
public class Save extends CommandImpl {
    /**
     * This field holds an instance of a CollectionManager which is responsible for operations with the collection.
     */
    private final CollectionManager collectionManager;

    /**
     * Constructs a Save with the specified CollectionManager.
     *
     * @param collectionManager the specified CollectionManager.
     */
    public Save(CollectionManager collectionManager) {
        super("save", "Saves the changes made during a session into a given file");
        this.collectionManager = collectionManager;
    }

    /**
     * This method is an implementation of the abstract apply() method for the Save command.
     * @param arg the argument (unnecessary).
     * @return true if the command was successfully executed, <p>false if the command encountered an error.
     */
    @Override
    public boolean apply(String arg) {
        try {
            if (!arg.isEmpty()) {
                throw new InvalidArgumentCountException("You don't need an argument here.", new RuntimeException());
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
