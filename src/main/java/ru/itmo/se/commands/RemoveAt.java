package ru.itmo.se.commands;

import lombok.ToString;
import ru.itmo.se.data.MusicBand;
import ru.itmo.se.exceptions.EmptyCollectionException;
import ru.itmo.se.exceptions.InvalidArgumentCountException;
import ru.itmo.se.exceptions.InvalidInputException;
import ru.itmo.se.exceptions.NullMusicBandException;
import ru.itmo.se.utilities.CollectionManager;
import ru.itmo.se.utilities.Console;

/**
 * This class implements the command remove_at. It removes an element from the collection through its index.
 * -- TOSTRING --
 * This method is a custom implementation of the toString() method in the add class.
 */
@ToString
public class RemoveAt extends CommandImpl {
    /**
     * This field holds an instance of a CollectionManager which is responsible for operations with the collection.
     */
    private final CollectionManager collectionManager;

    /**
     * Constructs a remove_at with the specified CollectionManager.
     *
     * @param collectionManager the specified CollectionManager.
     */
    public RemoveAt(CollectionManager collectionManager) {
        super("remove_at <index>", "removes an element by its index in the collection");
        this.collectionManager = collectionManager;
    }

    /**
     * This method is an implementation of the abstract apply() method for the remove_at command.
     * @param arg the argument (necessary).
     * @return true if the command was successfully executed, <p>false if the command encountered an error.
     */
    @Override
    public boolean apply(String arg) {
        try {
            if (arg.isEmpty()) {
                throw new InvalidArgumentCountException("You need an argument here.", new RuntimeException());
            }
            if (collectionManager.collectionSize() == 0) {
                throw new EmptyCollectionException("Empty collection.", new RuntimeException());
            }
            MusicBand musicBandToRemove = collectionManager.getByIndex(Integer.parseInt(arg));
            collectionManager.removeFromCollection(musicBandToRemove);
            Console.println("Music band successfully removed.");
            return true;
        } catch (InvalidArgumentCountException e) {
            Console.println("Usage: '" + getName() + "'");
        } catch (EmptyCollectionException e) {
            Console.printError("Empty collection.");
        } catch (NullMusicBandException e) {
            Console.printError("No music band with given index.");
        } catch (InvalidInputException e) {
            Console.printError("What am I supposed to remove? Please enter an index.");
        }
        return false;
    }
}
