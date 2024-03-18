package ru.itmo.se.commands;

import lombok.ToString;
import ru.itmo.se.exceptions.InvalidArgumentCountException;
import ru.itmo.se.utilities.CollectionManager;
import ru.itmo.se.utilities.Console;

import java.time.LocalDateTime;

@ToString
public class info extends CommandImpl {

    private final CollectionManager collectionManager;

    public info(CollectionManager collectionManager) {
        super("info", "Gives information about the collection");
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean apply(String arg) {
        try {
            if (!arg.isEmpty()) {
                throw new InvalidArgumentCountException("Inappropriate argument count", new RuntimeException());
            }
            LocalDateTime lastInitTime = collectionManager.getLastInitTime();
            String strLastInitTime = (lastInitTime == null) ? "Initializaion has not happened yet." : lastInitTime.toLocalDate().toString() + " " + lastInitTime.toLocalTime().toString();
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
