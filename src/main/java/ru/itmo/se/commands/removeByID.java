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
public class removeByID extends CommandImpl {

    private final CollectionManager collectionManager;

    public removeByID(CollectionManager collectionManager) {
        super("remove_by_id <id>", "Removes an element of the collection through its ID value");
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean apply(String arg) {
        try {
            if (arg.isEmpty()) {
                throw new InvalidArgumentCountException("Inappropriate argument count.", new RuntimeException());
            }
            if (collectionManager.collectionSize() == 0) {
                throw new EmptyCollectionException("Empty collection", new RuntimeException());
            }
            MusicBand musicBandToRemove = collectionManager.getByID(Integer.parseInt(arg));
            if (musicBandToRemove == null) {
                throw new NullMusicBandException("There's no such music band with this ID.", new RuntimeException());
            }
            collectionManager.removeFromCollection(musicBandToRemove);
            Console.println("Music band successfully deleted.");
            return true;
        } catch (InvalidArgumentCountException e) {
            Console.println("Usage: '" + getName() + "'");
        } catch (EmptyCollectionException e) {
            Console.printError("Empty collection.");
        } catch (NullMusicBandException e) {
            Console.printError("There's no such music band with this ID.");
        } catch (InvalidInputException e) {
            Console.printError("What am I supposed to remove? Give me an ID");
        }
        return false;
    }
}
