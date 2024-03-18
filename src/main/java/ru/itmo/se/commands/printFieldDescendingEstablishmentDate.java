package ru.itmo.se.commands;

import lombok.ToString;
import ru.itmo.se.exceptions.EmptyCollectionException;
import ru.itmo.se.exceptions.InvalidArgumentCountException;
import ru.itmo.se.utilities.CollectionManager;
import ru.itmo.se.utilities.Console;

@ToString
public class printFieldDescendingEstablishmentDate extends CommandImpl {

    private final CollectionManager collectionManager;

    public printFieldDescendingEstablishmentDate(CollectionManager collectionManager) {
        super("print_field_descending_establishment_date", "Outputs all elements by descending order of establishment date");
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean apply(String arg) {
        try {
            if (!arg.isEmpty()) {
                throw new InvalidArgumentCountException("Inappropriate argument count", new RuntimeException());
            }
            if (collectionManager.collectionSize() == 0) {
                throw new EmptyCollectionException("Empty collection", new RuntimeException());
            }
            collectionManager.printFieldDescendingEstablishmentDate();
            return true;
        } catch (InvalidArgumentCountException e) {
            Console.println("Usage: '" + getName() + "'");
        } catch (EmptyCollectionException e) {
            Console.printError("Empty collection.");
        }
        return false;
    }
}
