package ru.itmo.se.commands;

import lombok.ToString;
import ru.itmo.se.data.MusicBand;
import ru.itmo.se.exceptions.EmptyCollectionException;
import ru.itmo.se.exceptions.InvalidArgumentCountException;
import ru.itmo.se.exceptions.InvalidInputException;
import ru.itmo.se.exceptions.NullMusicBandException;
import ru.itmo.se.utilities.CollectionManager;
import ru.itmo.se.utilities.Console;

@ToString
public class removeAt extends CommandImpl {

    private final CollectionManager collectionManager;

    public removeAt(CollectionManager collectionManager) {
        super("remove_at <index>", "removes an element by its index in the collection");
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean apply(String arg) {
        try {
            if (arg.isEmpty()) {
                throw new InvalidArgumentCountException("Inappropriate argument count.", new RuntimeException());
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
