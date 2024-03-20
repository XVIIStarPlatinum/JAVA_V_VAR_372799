package ru.itmo.se.utilities;

import ru.itmo.se.console.Main;
import ru.itmo.se.data.Coordinates;
import ru.itmo.se.data.MusicBand;
import ru.itmo.se.data.MusicGenre;
import ru.itmo.se.data.Studio;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedList;

/**
 * Utility class used for validating the content of the file in case of an external change.
 */
class FileContentValidator {
    /**
     * This field holds an instance of a FileManager which is responsible for operations with files.
     */
    private FileManager fileManager = new FileManager(Main.cliArgument);

    /**
     * This method is used to validate the content of the file. If a violation was found, the corresponding object will be removed.
     *
     * @return validated collection.
     */
    LinkedList<MusicBand> validateFileContent() {
        LinkedList<MusicBand> musicBandLinkedList = fileManager.readCollection();
        for (MusicBand musicBand : musicBandLinkedList) {
            Integer id = musicBand.getId();
            String name = musicBand.getName();
            Coordinates coordinates = musicBand.getCoordinates();
            Date creationDate = musicBand.getCreationDate();
            Long numberOfParticipants = musicBand.getNumberOfParticipants();
            LocalDateTime establishmentDate = musicBand.getEstablishmentDate();
            MusicGenre musicGenre = musicBand.getMusicGenre();
            Studio studio = musicBand.getStudio();
            if (MusicBandValidator.checkID(id)) {
                Console.printError("It seems like the data was externally altered.");
                Console.printError("This field (ID: " + id + ") has violated the necessary constraints.");
                Console.printError("Therefore, this element is being removed.");
                musicBandLinkedList.remove(musicBand);
            }
            if (MusicBandValidator.checkName(name)) {
                Console.printError("It seems like the data was externally altered.");
                Console.printError("This field (Name: " + name + ") has violated the necessary constraints.");
                Console.printError("Therefore, this element is being removed.");
                musicBandLinkedList.remove(musicBand);
            }
            if (MusicBandValidator.checkX(coordinates.getX())) {
                Console.printError("It seems like the data was externally altered.");
                Console.printError("This field (Coordinates (X): " + coordinates.getX() + ") has violated the necessary constraints.");
                Console.printError("Therefore, this element is being removed.");
                musicBandLinkedList.remove(musicBand);
            }
            if (MusicBandValidator.checkY(coordinates.getY())) {
                Console.printError("It seems like the data was externally altered.");
                Console.printError("This field (Coordinates (Y): " + coordinates.getY() + ") has violated the necessary constraints.");
                Console.printError("Therefore, this element is being removed.");
                musicBandLinkedList.remove(musicBand);
            }
            if (MusicBandValidator.checkDate(creationDate)) {
                Console.printError("It seems like the data was externally altered.");
                Console.printError("This field (Creation date: " + creationDate + ") has violated the necessary constraints.");
                Console.printError("Therefore, this element is being removed.");
                musicBandLinkedList.remove(musicBand);
            }
            if (MusicBandValidator.checkNumberOfParticipants(numberOfParticipants)) {
                Console.printError("It seems like the data was externally altered.");
                Console.printError("This field (Number of participants: " + numberOfParticipants + ") has violated the necessary constraints.");
                Console.printError("Therefore, this element is being removed.");
                musicBandLinkedList.remove(musicBand);
            }
            if (MusicBandValidator.checkEstablishmentDate(establishmentDate)) {
                Console.printError("It seems like the data was externally altered.");
                Console.printError("This field (Establishment date: " + establishmentDate.toString() + ") has violated the necessary constraints.");
                Console.printError("Therefore, this element is being removed.");
                musicBandLinkedList.remove(musicBand);
            }
            if (MusicBandValidator.checkMusicGenre(musicGenre)) {
                Console.printError("It seems like the data was externally altered.");
                Console.printError("This field (Music genre: " + musicGenre + ") has violated the necessary constraints.");
                Console.printError("Therefore, this element is being removed.");
                musicBandLinkedList.remove(musicBand);
            }
            if (MusicBandValidator.checkAddress(studio.toString())) {
                Console.printError("It seems like the data was externally altered.");
                Console.printError("This field (Studio address: " + studio.toString() + ") has violated the necessary constraints.");
                Console.printError("Therefore, this element is being removed.");
                musicBandLinkedList.remove(musicBand);
            }
        }
        return musicBandLinkedList;
    }
}
