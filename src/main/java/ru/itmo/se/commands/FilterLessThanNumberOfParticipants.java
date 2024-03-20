package ru.itmo.se.commands;

import ru.itmo.se.exceptions.EmptyCollectionException;
import ru.itmo.se.exceptions.InvalidArgumentCountException;
import ru.itmo.se.utilities.CollectionManager;
import ru.itmo.se.utilities.Console;

import lombok.ToString;

/**
 * This class implements the command filter_less_than_number_of_participants (handful, I know). It outputs all the elements with a fewer number of participants than given.
 * -- TOSTRING --
 * This method is a custom implementation of the toString() method in the filter_less_than_number_of_participants class.
 */
@ToString
public class FilterLessThanNumberOfParticipants extends CommandImpl {
    /**
     * This field holds an instance of a CollectionManager which is responsible for operations with the collection.
     */
    private final CollectionManager collectionManager;

    /**
     * Constructs a filter_less_than_number_of_participants with the specified CollectionManager.
     *
     * @param collectionManager the specified CollectionManager.
     */
    public FilterLessThanNumberOfParticipants(CollectionManager collectionManager) {
        super("filter_less_than_number_of_participants <number_of_participants>", "Outputs elements which have less participants than given");
        this.collectionManager = collectionManager;
    }

    /**
     * This method is an implementation of the abstract apply() method for the filter_less_than_number_of_participants command.
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
            Long numberOfParticipants = Long.parseLong(arg);
            if (numberOfParticipants <= 0L) {
                throw new IllegalArgumentException("Why are you expecting a negative number of participants?", new RuntimeException());
            }
            String filteredInfo = collectionManager.musicBandParticipantsFilteredInfo(numberOfParticipants);
            if (filteredInfo.isEmpty()) {
                Console.println("No music bands with less than " + numberOfParticipants + " participants has been found.");
            } else {
                Console.println(filteredInfo);
                return true;
            }
        } catch (InvalidArgumentCountException e) {
            Console.println("Usage: '" + getName() + "'");
        } catch (EmptyCollectionException e) {
            Console.printError("Empty collection.");
        } catch (IllegalArgumentException e) {
            Console.printError("Why are you expecting a negative number of participants?");
        }
        return false;
    }
}
