package ru.itmo.se.utilities;

import ru.itmo.se.console.Main;
import ru.itmo.se.exceptions.NullValueException;
import ru.itmo.se.exceptions.RecursionException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Console {

    private final CommandManager commandManager;

    private final Scanner userScanner;

    private final MusicBandValidator musicBandValidator;

    private final List<String> scriptStack = new ArrayList<>();

    List<String> executeStringAliases = new ArrayList<>();

    {
        executeStringAliases.add("execute_script");
        executeStringAliases.add("exs");
        executeStringAliases.add("учусгеу_ыскшзе");
        executeStringAliases.add("учы");
    }

    public Console(CommandManager commandManager, Scanner scanner, MusicBandValidator musicBandValidator) {
        this.commandManager = commandManager;
        this.userScanner = scanner;
        this.musicBandValidator = musicBandValidator;
    }

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

    public int scriptMode(String arg) {
        String[] userCommand;
        int commandStatus;
        scriptStack.add(arg);
        try (Scanner scrSc = new Scanner(new File(arg))) {
            if (!scrSc.hasNext()) throw new NoSuchElementException();
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
            if (commandStatus == 1 && !(executeStringAliases.contains(userCommand[0])
                    && !userCommand[1].isEmpty())) {
                Console.printError("Execution error: Please debug your script.");
                return commandStatus;
            } else if (commandStatus == 2 && userCommand[0].equals("exit") && userCommand[1].isEmpty()) {
                System.exit(0);
            }
        } catch (FileNotFoundException e) {
            Console.printError("Script file not found.\nIf there is one, then tyr changing the permission of the file.\nMaybe chmod 777, idk.");
        } catch (NullValueException e) {
            Console.printError("Script file is empty.");
        } catch (RecursionException e) {
            Console.printError("Critical error: Recursion detected in script file.");
        } finally {
            scriptStack.remove(scriptStack.size() - 1);
        }
        return 1;
    }

    private int executeCommand(String[] userCommand) {
        String arg = userCommand[0].toLowerCase();
        switch (arg) {
            case "":
                break;
            case "clear", "сдуфк":
                if (!commandManager.clear(userCommand[1])) return 1;
                break;
            case "execute_script", "учусгеу_ыскшзе", "exs", "учы":
                if (!commandManager.executeScript(userCommand[1])) return 1;
                else return scriptMode(userCommand[1]);
            case "exit", "учше":
                if (!commandManager.exit(userCommand[1])) return 1;
                else return 2;
            case "filter_less_than_number", "ашдеук_дуыы_ерфт_тгьиук", "fltn", "адет":
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
                commandManager.noSuchCommand(userCommand[0]);
                return 1;
        }
        return 0;
    }

    public static void print(Object toOut) {
        System.out.print("\033[1;35m" + toOut + "\u001B[0m");
    }

    public static void println(Object toOut) {
        System.out.println("\u001B[32m" + toOut + "\u001B[0m");
    }

    public static void printError(Object toOut) {
        System.out.println("\u001B[41m" + "\u001B[30m" + toOut + "\u001B[0m");
    }

    public static void printTable(Object e1, Object e2) {
        System.out.printf("\u001B[36m" + "| %-64s | %-88s | %n", e1, e2);
        System.out.printf("\u001B[35m" + "=-".repeat(79) + "=%n");
    }

    @Override
    public String toString() {
        return "Console: CLI class";
    }
}
