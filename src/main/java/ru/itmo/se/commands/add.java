package ru.itmo.se.commands;

import lombok.ToString;
import ru.itmo.se.data.MusicBand;
import ru.itmo.se.exceptions.IncorrectScriptException;
import ru.itmo.se.exceptions.InvalidArgumentCountException;
import ru.itmo.se.utilities.CollectionManager;
import ru.itmo.se.utilities.Console;
import ru.itmo.se.utilities.MusicBandValidator;

import java.time.Instant;
import java.util.Date;

/**
 * This class implements the command add. It adds a new element to the collection.
 * -- TOSTRING --
 * This method is a custom implementation of the toString() method in the add class.
 */
@ToString
public class add extends CommandImpl {
    /**
     * This field holds an instance of a CollectionManager which is responsible for operations with the collection.
     */
    private final CollectionManager collectionManager;
    /**
     * This field holds an instance of a MusicBandValidator which is responsible for all validations.
     */
    private final MusicBandValidator musicBandValidator;

    /**
     * Constructs an add with the specified CollectionManager and FileManager.
     *
     * @param collectionManager  the specified CollectionManager.
     * @param musicBandValidator the specified MusicBandValidator.
     */
    public add(CollectionManager collectionManager, MusicBandValidator musicBandValidator) {
        super("add {element}", "Adds a new element to the collection");
        this.collectionManager = collectionManager;
        this.musicBandValidator = musicBandValidator;
    }

    /**
     * This method is an implementation of the abstract apply() method for the add command.
     * @param arg the argument (unnecessary).
     * @return true if the command was successfully executed, <p>false if the command encountered an error.
     */
    @Override
    public boolean apply(String arg) {
        try {
            if (!arg.isEmpty()) {
                throw new InvalidArgumentCountException("You don't need an argument here.", new RuntimeException());
            }
            collectionManager.addToCollection(new MusicBand(
                    collectionManager.generateNextID(),
                    musicBandValidator.askName(),
                    musicBandValidator.askCoordinates(),
                    Date.from(Instant.now()),
                    musicBandValidator.askNumberOfParticipants(),
                    musicBandValidator.askEstablishmentDate(),
                    musicBandValidator.askMusicGenre(),
                    musicBandValidator.askStudio()
            ));
            Console.println("\u001b[3m" + "\"A fine addition to my collection.\" — General Grievous" + "\u001b[0m");
            return true;
        } catch (InvalidArgumentCountException e) {
            Console.println("Usage: '" + getName() + "'");
        } catch (IncorrectScriptException e) {
            Console.printError("Execution error: Please debug your script.");
        }
        return false;
    }
}