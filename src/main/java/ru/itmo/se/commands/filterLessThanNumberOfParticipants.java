package ru.itmo.se.commands;

import lombok.ToString;
import ru.itmo.se.exceptions.EmptyCollectionException;
import ru.itmo.se.exceptions.InvalidArgumentCountException;
import ru.itmo.se.utilities.CollectionManager;
import ru.itmo.se.utilities.Console;

@ToString
public class filterLessThanNumberOfParticipants extends CommandImpl {

    private final CollectionManager collectionManager;

    public filterLessThanNumberOfParticipants(CollectionManager collectionManager) {
        super("filter_less_than_number_of_participants <number_of_participants>", "Outputs elements which have less participants than given");
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
            Long numberOfParticipants = Long.parseLong(arg);
            if (numberOfParticipants <= 0L) {
                throw new IllegalArgumentException("Why are you expecting a negative number of participants?", new RuntimeException());
            }
            String filteredInfo = collectionManager.musicBandParticipantsFilteredInfo(numberOfParticipants);
            if (filteredInfo.isEmpty()) {
                Console.println("No music bands with less amount of participants has been found.");
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
