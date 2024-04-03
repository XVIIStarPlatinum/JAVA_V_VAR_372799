package ru.itmo.se.console;

import ru.itmo.se.commands.*;
import ru.itmo.se.utilities.Console;
import ru.itmo.se.utilities.*;
import sun.misc.Signal;
import sun.misc.SignalHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Scanner;

/**
 * Driver class for the CLI application.
 */
public class Main {
    /**
     * String for CLI indentation.
     */
    public static final String CS1 = "$ ";
    /**
     * String for CLI indentation.
     */
    public static final String CS2 = "> ";
    /**
     * CLI argument is passed onto this variable.
     */
    public static String cliArgument = null;
    /**
     * The margin of error used for comparing float values.
     */
    public static final float EPSILON = 0.00000001F;
    /**
     * The driver method used to launch the CLI application.
     *
     * @param args file name to be worked with.
     * @throws IOException if there's an interruption with I/O operations involving the file.
     */
    public static void main(String[] args) throws IOException {
        Console.println("W E L C O M E.");
        Scanner userScanner = new Scanner(System.in, StandardCharsets.UTF_8);
        if (args.length == 0) {
            Console.printError("There must be an argument. Please try again.");
            System.exit(0);
        } else {
            SignalHandler handler = sig -> {
                System.out.println();
                Console.printError("Ctrl+C? How dare you!\u001B[0m");
                System.exit(0);
            };
            Signal.handle(new Signal("INT"), handler);
            Signal.handle(new Signal("ABRT"), handler);
            Signal.handle(new Signal("TERM"), handler);
            cliArgument = args[0];
            File file = new File(cliArgument);
            if (!Files.isReadable(file.toPath())) {
                Console.printError("You don't have access to this file. Try again, maybe after doing chmod 777 or something.");
                System.exit(1);
            }
            if (!file.isFile()) {
                try {
                    if (file.createNewFile()) {
                        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
                        outputStreamWriter.write("{}");
                        Console.println("File successfully created.");
                        outputStreamWriter.close();
                    }
                } catch (FileNotFoundException e) {
                    Console.printError("File cannot be created.");
                    e.printStackTrace();
                }
            }
        }
        FileManager fileManager = new FileManager(cliArgument);
        MusicBandValidator musicBandValidator = new MusicBandValidator(userScanner);
        CollectionManager collectionManager = new CollectionManager(fileManager);
        CommandManager commandManager = new CommandManager() {{
            addCommand("add", new Add(collectionManager, musicBandValidator));
            addCommand("clear", new Clear(collectionManager));
            addCommand("execute_script", new ExecuteScript());
            addCommand("exit", new Exit());
            addCommand("filter_less_than_number_of_participants", new FilterLessThanNumberOfParticipants(collectionManager));
            addCommand("group_counting_by_establishment_date", new GroupCountingByEstablishmentDate(collectionManager));
            addCommand("help", new Help(this));
            addCommand("history", new History(this));
            addCommand("info", new Info(collectionManager));
            addCommand("print_field_descending_establishment_date", new PrintFieldDescendingEstablishmentDate(collectionManager));
            addCommand("remove_at", new RemoveAt(collectionManager));
            addCommand("remove_by_id", new RemoveByID(collectionManager));
            addCommand("save", new Save(collectionManager));
            addCommand("show", new Show(collectionManager));
            addCommand("shuffle", new Shuffle(collectionManager));
            addCommand("update", new UpdateID(collectionManager, musicBandValidator));
        }};
        Console console = new Console(commandManager, userScanner, musicBandValidator);
        console.InteractiveMode();
        Console.println("             We'll meet again");
        Console.println("    Don't know where, don't know when");
        Console.println("But I know we'll meet again some sunny day.");
    }
}