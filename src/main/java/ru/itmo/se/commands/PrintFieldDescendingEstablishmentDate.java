package ru.itmo.se.commands;

import lombok.ToString;
import ru.itmo.se.exceptions.EmptyCollectionException;
import ru.itmo.se.exceptions.InvalidArgumentCountException;
import ru.itmo.se.utilities.CollectionManager;
import ru.itmo.se.utilities.Console;

/**
 * This class implements the command print_field_descending_establishment_date. It outputs all element's establishment date by descending order.
 * -- TOSTRING --
 * This method is a custom implementation of the toString() method in the print_field_descending_establishment_date class.
 */
@ToString
public class PrintFieldDescendingEstablishmentDate extends CommandImpl {
    /**
     * This field holds an instance of a CollectionManager which is responsible for operations with the collection.
     */
    private final CollectionManager collectionManager;

    /**
     * Constructs a print_field_descending_establishment_date with the specified CollectionManager.
     *
     * @param collectionManager the specified CollectionManager.
     */
    public PrintFieldDescendingEstablishmentDate(CollectionManager collectionManager) {
        super("print_field_descending_establishment_date", "Outputs all elements by descending order of establishment date");
        this.collectionManager = collectionManager;
    }

    /**
     * This method is an implementation of the abstract apply() method for the print_field_descending_establishment_date command.
     * @param arg the argument (unnecessary).
     * @return true if the command was successfully executed, <p>false if the command encountered an error.
     */
    @Override
    public boolean apply(String arg) {
        try {
            if (!arg.isEmpty()) {
                throw new InvalidArgumentCountException("You don't need an argument here.", new RuntimeException());
            }
            if (collectionManager.collectionSize() == 0) {
                throw new EmptyCollectionException("Empty collection.", new RuntimeException());
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
