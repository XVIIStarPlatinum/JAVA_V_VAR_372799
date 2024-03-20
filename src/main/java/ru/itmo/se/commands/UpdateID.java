package ru.itmo.se.commands;

import lombok.ToString;
import ru.itmo.se.data.Coordinates;
import ru.itmo.se.data.MusicBand;
import ru.itmo.se.data.MusicGenre;
import ru.itmo.se.data.Studio;
import ru.itmo.se.exceptions.EmptyCollectionException;
import ru.itmo.se.exceptions.InvalidArgumentCountException;
import ru.itmo.se.exceptions.InvalidInputException;
import ru.itmo.se.exceptions.NullMusicBandException;
import ru.itmo.se.utilities.CollectionManager;
import ru.itmo.se.utilities.Console;
import ru.itmo.se.utilities.MusicBandValidator;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * This class implements the command update. It updates an element by removing it through its ID and creating a new one from the extracted data.
 * -- TOSTRING --
 * This method is a custom implementation of the toString() method in the update class.
 */
@ToString
public class UpdateID extends CommandImpl {
    /**
     * This field holds an instance of a CollectionManager which is responsible for operations with the collection.
     */
    private final CollectionManager collectionManager;
    /**
     * This field holds an instance of a MusicBandValidator which is responsible for all validations.
     */
    private final MusicBandValidator musicBandValidator;

    /**
     * Constructs an update with the specified CollectionManager and FileManager.
     *
     * @param collectionManager  the specified CollectionManager.
     * @param musicBandValidator the specified MusicBandValidator.
     */
    public UpdateID(CollectionManager collectionManager, MusicBandValidator musicBandValidator) {
        super("update <ID> {element}", "Updates an element of the collection with the givan ID");
        this.collectionManager = collectionManager;
        this.musicBandValidator = musicBandValidator;
    }

    /**
     * This method is an implementation of the abstract apply() method for the update command.
     * @param arg the argument (unnecessary).
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
            Integer id = Integer.parseInt(arg);
            MusicBand musicBandToUpdate = collectionManager.getByID(id);

            if (collectionManager.collectionContains(musicBandToUpdate)) {
                String name = musicBandToUpdate.getName();
                Coordinates coordinates = musicBandToUpdate.getCoordinates();
                Date creationDate = musicBandToUpdate.getCreationDate();
                Long numberOfParticipants = musicBandToUpdate.getNumberOfParticipants();
                LocalDateTime establishmentDate = musicBandToUpdate.getEstablishmentDate();
                MusicGenre musicGenre = musicBandToUpdate.getMusicGenre();
                Studio studio = musicBandToUpdate.getStudio();
                collectionManager.removeFromCollection(musicBandToUpdate);
                if (musicBandValidator.askQuestion("Do you want to change the name of the music band?")) {
                    name = musicBandValidator.askName();
                }
                if (musicBandValidator.askQuestion("Do you want to change the coordinates of the organization?")) {
                    coordinates = musicBandValidator.askCoordinates();
                }
                if (musicBandValidator.askQuestion("Do you want to change the number of participants?")) {
                    numberOfParticipants = musicBandValidator.askNumberOfParticipants();
                }
                if (musicBandValidator.askQuestion("Do you want to change the establishment date?")) {
                    establishmentDate = musicBandValidator.askEstablishmentDate();
                }
                if (musicBandValidator.askQuestion("Do you want to change the genre of music?")) {
                    musicGenre = musicBandValidator.askMusicGenre();
                }
                if (musicBandValidator.askQuestion("Do you want to change the studio?")) {
                    studio = musicBandValidator.askStudio();
                }
                collectionManager.addToCollection(new MusicBand(
                        id, name,
                        coordinates,
                        creationDate,
                        numberOfParticipants,
                        establishmentDate,
                        musicGenre, studio
                ));
            } else {
                throw new NullMusicBandException("There's no such music band.", new RuntimeException());
            }
            Console.println("Music band has been successfully updated.");
            return true;
        } catch (InvalidArgumentCountException e) {
            Console.println("Usage: '" + getName() + "'");
        } catch (EmptyCollectionException e) {
            Console.printError("Empty collection.");
        } catch (NumberFormatException e) {
            Console.printError("Negative number of participants???");
        } catch (NullMusicBandException e) {
            Console.printError("No such music band with given ID.");
        } catch (InvalidInputException e) {
            Console.printError("What am I supposed to update? Give me an ID.");
        }
        return false;
    }
}
