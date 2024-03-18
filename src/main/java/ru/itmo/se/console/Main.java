package ru.itmo.se.console;

import ru.itmo.se.commands.*;
import ru.itmo.se.utilities.Console;
import ru.itmo.se.utilities.*;
import sun.misc.Signal;
import sun.misc.SignalHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

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

    public static void main(String[] args) throws IOException {
        Console.println("W E L C O M E.");
        Scanner userScanner = new Scanner(System.in, StandardCharsets.UTF_8);
        SignalHandler handler = sig -> {
            Console.printError("Ctrl+C? How dare you!");
            System.exit(0);
        };
        Signal.handle(new Signal("INT"), handler);
        Signal.handle(new Signal("ABRT"), handler);
        Signal.handle(new Signal("TERM"), handler);
        if (args.length == 0) {
            Console.printError("There must be an argument. Please try again.");
            System.exit(0);
        } else {
            cliArgument = args[0];
            File file = new File(cliArgument);
            if (!file.exists()) {
                try {
                    if (file.createNewFile()) {
                        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
                        outputStreamWriter.write("{}");
                        Console.println("File successfully created.");
                        outputStreamWriter.close();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        FileManager fileManager = new FileManager(cliArgument);
        MusicBandValidator musicBandValidator = new MusicBandValidator(userScanner);
        CollectionManager collectionManager = new CollectionManager(fileManager);
        CommandManager commandManager = new CommandManager(
                new add(collectionManager, musicBandValidator),
                new clear(collectionManager),
                new executeScript(),
                new exit(),
                new filterLessThanNumberOfParticipants(collectionManager),
                new groupCountingByEstablishmentDate(collectionManager),
                new help(),
                new history(),
                new info(collectionManager),
                new printFieldDescendingEstablishmentDate(collectionManager),
                new removeAt(collectionManager),
                new removeByID(collectionManager),
                new save(collectionManager),
                new show(collectionManager),
                new shuffle(collectionManager),
                new updateID(collectionManager, musicBandValidator)
        );
        Console console = new Console(commandManager, userScanner, musicBandValidator);
        console.InteractiveMode();
        Console.println("             We'll meet again");
        Console.println("    Don't know where, don't know when");
        Console.println("But I know we'll meet again some sunny day.");
    }
}
