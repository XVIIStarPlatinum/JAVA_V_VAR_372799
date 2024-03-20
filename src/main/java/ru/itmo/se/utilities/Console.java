package ru.itmo.se.utilities;

import ru.itmo.se.console.Main;
import ru.itmo.se.exceptions.NullValueException;
import ru.itmo.se.exceptions.RecursionException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Utility class used for operations with the CLI.
 */
public class Console {
    /**
     * This field holds the instance of a CommandManager which is responsible for operations with commands.
     */
    private final CommandManager commandManager;
    /**
     * This field holds the scanner via which the application will receive inputs.
     */
    private final Scanner userScanner;
    /**
     * This field holds the instance of a MusicBandValidator which is responsible for all validations of data.
     */
    private final MusicBandValidator musicBandValidator;
    /**
     * This field stores the commands from a script.
     */
    private final List<String> scriptStack = new ArrayList<>();
    /**
     * This field stores all possible aliases for the command execute_script. It is used to catch out any unchecked recursions.
     */
    private Collection<String> executeStringAliases = new ArrayList<>(Arrays.asList("execute_script", "exs", "учусгеу_ыскшзе", "учы"));
    /**
     * Constructs a Console with the specified CommandManager, Scanner and MusicBandValidator.
     *
     * @param commandManager     a CommandManager instance.
     * @param scanner            a Scanner instance which takes input from the user.
     * @param musicBandValidator a MusicBandValidator which validates all data.
     */
    public Console(CommandManager commandManager, Scanner scanner, MusicBandValidator musicBandValidator) {
        this.commandManager = commandManager;
        this.userScanner = scanner;
        this.musicBandValidator = musicBandValidator;
    }

    /**
     * This method is used to switch the console input mode to user.
     */
    public void InteractiveMode() {
        String[] userCommands;
        int commandStatus;
        try {
            do {
                Console.print(Main.CS1);
                userCommands = (userScanner.nextLine().trim() + " ").split(" ", 2);
                userCommands[1] = userCommands[1].trim();
                commandManager.addToHistory(userCommands[0]);
                commandStatus = executeCommand(userCommands);
            } while (commandStatus != 2);
        } catch (NoSuchElementException e) {
            Console.printError("User input not detected.");
        }
    }

    /**
     * This method is used to switch the console input to script mode (invoked with execute_script).
     *
     * @param arg a script file.
     * @return 1 if there's an execution error with the script, <p>2 if the command Exit occurs.
     */
    private int scriptMode(String arg) {
        String[] userCommand;
        int commandStatus;
        scriptStack.add(arg);
        try (Scanner scrSc = new Scanner(new File(arg), StandardCharsets.UTF_8)) {
            if (!scrSc.hasNext()) throw new NoSuchElementException("There are no remaining commands.", new RuntimeException());
            Scanner tmp = musicBandValidator.getUserScanner();
            musicBandValidator.setUserScanner(scrSc);
            musicBandValidator.setFileMode(true);
            do {
                userCommand = (scrSc.nextLine().trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();
                while (scrSc.hasNextLine() && userCommand[0].isEmpty()) {
                    userCommand = (scrSc.nextLine().trim() + " ").split(" ", 2);
                    userCommand[1] = userCommand[1].trim();
                }
                Console.println(Main.CS1 + String.join(" ", userCommand));
                if (executeStringAliases.contains(userCommand[0])) {
                    for (String script : scriptStack) {
                        if (userCommand[1].equals(script)) {
                            throw new RecursionException("Unchecked recursion detected.", new RuntimeException());
                        }
                    }
                }
                commandStatus = executeCommand(userCommand);
            } while (commandStatus == 0 && scrSc.hasNextLine());
            musicBandValidator.setUserScanner(tmp);
            musicBandValidator.setFileMode(false);
            if (commandStatus == 1 && !(executeStringAliases.contains(userCommand[0]) && !userCommand[1].isEmpty())) {
                Console.printError("Execution error: Please debug your script.");
                return commandStatus;
            } else if (commandStatus == 2 && userCommand[0].equals("Exit") && userCommand[1].isEmpty()) {
                System.exit(0);
            }
        } catch (FileNotFoundException e) {
            Console.printError("Script file not found.\nIf there is one, then tyr changing the permission of the file.\nMaybe chmod 777, idk.");
        } catch (NullValueException e) {
            Console.printError("Script file is empty.");
        } catch (RecursionException e) {
            Console.printError("Critical error: Recursion detected in script file.");
        } catch (IOException e) {
            Console.printError("Critical error: the file cannot be opened.");
        } finally {
            scriptStack.remove(scriptStack.size() - 1);
        }
        return 1;
    }

    /**
     * This method is responsible for command execution.
     * @param userCommand the command to be executed.
     * @return 0 if the command was executed, <p>1 if the command was not executed and <p>2 if the command was Exit.
     */
    private int executeCommand(String[] userCommand) {
        String arg = userCommand[0].toLowerCase(Locale.ROOT);
        switch (arg) {
            case "":
                break;
            case "clear", "сдуфк":
                if (!commandManager.clear(userCommand[1])) return 1;
                break;
            case "execute_script", "учусгеу_ыскшзе", "exs", "учы":
                if (commandManager.executeScript(userCommand[1])) return scriptMode(userCommand[1]);
                else return 1;
            case "exit", "учше":
                if (commandManager.exit(userCommand[1])) return 2;
                else return 1;
            case "filter_less_than_number_of_participants", "ашдеук_дуыы_ерфт_тгьиук_ща_зфкешсшзфтеы", "fltnop", "адетщз":
                if (!commandManager.filterLessThanNumberOfParticipants(userCommand[1])) return 1;
                break;
            case "help", "рудз":
                if (commandManager.help(userCommand[1])) return 1;
                break;
            case "info", "штащ":
                if (!commandManager.info(userCommand[1])) return 1;
                break;
            case "add", "фвв":
                if (!commandManager.add(userCommand[1])) return 1;
                break;
            case "history", "ршыещкн":
                if (!commandManager.history(userCommand[1])) return 1;
                break;
            case "print_field_descending_establishment_date", "зкште_ашудв_вуысутвштп_уыефидшырьуте_вфеу", "pfded", "завув":
                if (!commandManager.printFieldDescendingEstablishmentDate(userCommand[1])) return 1;
                break;
            case "remove_at", "куьщму_фе", "r@", "к\"":
                if (!commandManager.removeAt(userCommand[1])) return 1;
                break;
            case "remove_by_id", "куьщму_ин_шв", "rb_id", "ки_шв":
                if (!commandManager.removeByID(userCommand[1])) return 1;
                break;
            case "save", "ыфму":
                if (!commandManager.save(userCommand[1])) return 1;
                break;
            case "show", "ырщц":
                if (!commandManager.show(userCommand[1])) return 1;
                break;
            case "group_counting_by_establishment_date", "пкщгз_сщгтештп_ин_уыефидшырьуте_вфеу", "gcbed", "псиув":
                if (!commandManager.groupCountingByEstablishmentDate(userCommand[1])) return 1;
                break;
            case "shuffle", "ыргааду":
                if (!commandManager.shuffle(userCommand[1])) return 1;
                break;
            case "update", "гзвфеу":
                if (!commandManager.updateID(userCommand[1])) return 1;
                break;
            default:
                CommandManager.noSuchCommand(userCommand[0]);
                return 1;
        }
        return 0;
    }

    /**
     * This method is a custom implementation of the print() method. The output is colored purple with the Help of a corresponding ANSI code.
     *
     * @param toOut the object to be printed.
     */
    static void print(Object toOut) {
        System.out.print("\033[1;35m" + toOut + "\u001B[0m");
    }

    /**
     * This method is a custom implementation of the println() method. The output is colored green with the Help of a corresponding ANSI code.
     * @param toOut the object to be printed.
     */
    public static void println(Object toOut) {
        System.out.println("\u001B[32m" + toOut + "\u001B[0m");
    }

    /**
     * This method is a custom implementation of the err.print() method. The output is colored black with red background with the Help of corresponding ANSI codes.
     * @param toOut the error that was raised.
     */
    public static void printError(Object toOut) {
        System.out.println("\u001B[41m" + "\u001B[30m" + toOut + "\u001B[0m");
    }

    /**
     * This method is used to output all the commands in a table format. The row border is colored purple, the object and the column border is colored cyan with the Help of corresponding ANSI codes.
     *
     * @param e1 first column object.
     * @param e2 second column object.
     */
    static void printTable(Object e1, Object e2) {
        System.out.printf("\u001B[36m" + "| %-64s | %-88s | %n", e1, e2);
        System.out.print("\u001B[35m" + "=-".repeat(79) + "=\n");
    }

    /**
     * This method is a custom implementation of the toString() method in Console.
     * @return information about this class.
     */
    @Override
    public String toString() {
        return "Console: CLI class";
    }
}
