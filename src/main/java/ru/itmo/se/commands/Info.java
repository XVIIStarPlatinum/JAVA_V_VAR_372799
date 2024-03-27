package ru.itmo.se.commands;

import lombok.ToString;
import ru.itmo.se.exceptions.InvalidArgumentCountException;
import ru.itmo.se.utilities.CollectionManager;
import ru.itmo.se.utilities.Console;

import java.time.LocalDateTime;

/**
 * This class implements the command Info. It outputs all necessary information about the CLI application, for example the collection's type and size, last session time and last Save time.
 * -- TOSTRING --
 * This method is a custom implementation of the toString() method in the Info class.
 */
@ToString
public class Info extends CommandImpl {
    /**
     * This field holds an instance of a CollectionManager which is responsible for operations with the collection.
     */
    private final CollectionManager collectionManager;

    /**
     * Constructs an Info with the specified CollectionManager.
     *
     * @param collectionManager the specified collectionManager.
     */
    public Info(CollectionManager collectionManager) {
        super("info", "Gives information about the collection");
        this.collectionManager = collectionManager;
    }

    /**
     * This method is an implementation of the abstract apply() method for the Info command.
     * @param arg the argument (unnecessary).
     * @return true if the command was successfully executed, <p>false if the command encountered an error.
     */
    @Override
    public boolean apply(String arg) {
        try {
            if (!arg.isEmpty()) {
                throw new InvalidArgumentCountException("You don't need an argument here.", new RuntimeException());
            }
            LocalDateTime lastInitTime = collectionManager.getLastInitTime();
            String strLastInitTime = (lastInitTime == null) ? "Initialization has not happened yet." : lastInitTime.toLocalDate().toString() + " " + lastInitTime.toLocalTime().toString();
            LocalDateTime lastSaveTime = collectionManager.getLastSaveTime();
            String strLastSaveTime = (lastSaveTime == null) ? "You haven't saved yet during this session." : lastSaveTime.toLocalDate().toString() + " " + lastSaveTime.toLocalTime().toString();
            Console.println("Information about this collection:");
            Console.println("Collection type: " + collectionManager.getCollectionType());
            Console.println("Number of elements: " + collectionManager.collectionSize());
            Console.println("Last saved: " + strLastSaveTime);
            Console.println("Last session: " + strLastInitTime);
            return true;
        } catch (InvalidArgumentCountException e) {
            Console.println("Usage: " + getName() + "'");
        }
        return false;
    }
}
