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

@ToString
public class add extends CommandImpl {

    private final CollectionManager collectionManager;

    private final MusicBandValidator musicBandValidator;

    public add(CollectionManager collectionManager, MusicBandValidator musicBandValidator) {
        super("add {element}", "Adds new elements to the collection");
        this.collectionManager = collectionManager;
        this.musicBandValidator = musicBandValidator;
    }

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
            Console.println("\u001b[3m" + "\"A fine addition to my collection.\" \u2014 General Grievous" + "\u001b[0m");
            return true;
        } catch (InvalidArgumentCountException e) {
            Console.println("Usage: '" + getName() + "'");
        } catch (IncorrectScriptException e) {
            Console.printError("Execution error: Please debug your script.");
        }
        return false;
    }
}
