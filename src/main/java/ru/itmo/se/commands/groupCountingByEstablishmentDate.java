package ru.itmo.se.commands;

import lombok.ToString;
import ru.itmo.se.exceptions.EmptyCollectionException;
import ru.itmo.se.exceptions.InvalidArgumentCountException;
import ru.itmo.se.utilities.CollectionManager;
import ru.itmo.se.utilities.Console;

@ToString
public class groupCountingByEstablishmentDate extends CommandImpl {

    private final CollectionManager collectionManager;

    public groupCountingByEstablishmentDate(CollectionManager collectionManager) {
        super("group_counting_by_establishment_dates", "Groups elements by establishment date and outputs instances for each amount");
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean apply(String arg) {
        try {
            if (!arg.isEmpty()) {
                throw new InvalidArgumentCountException("Inappropriate argument count.", new RuntimeException());
            }
            if (collectionManager.collectionSize() == 0) {
                throw new EmptyCollectionException("Empty collection.", new RuntimeException());
            }
            collectionManager.groupCountingByEstablishmentDate();
            return true;
        } catch (InvalidArgumentCountException e) {
            Console.println("Usage: " + getName() + "'");
        } catch (EmptyCollectionException e) {
            Console.printError("Empty collection.");
        }
        return false;
    }
}
