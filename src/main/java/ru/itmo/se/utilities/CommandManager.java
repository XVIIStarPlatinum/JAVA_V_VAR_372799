package ru.itmo.se.utilities;

import lombok.Getter;
import ru.itmo.se.commands.CommandImpl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Utility class used for operations with commands and their management. Some of them are implemented here.
 */
public class CommandManager {
    /**
     * This field stores the capacity of command History.
     */
    private static final int COMMAND_HISTORY_SIZE = 10;
    /**
     * This field stores the most recent 10 commands.
     */
    public final String[] commandHistory = new String[COMMAND_HISTORY_SIZE];
    /**
     * This field stores all instances of commands.
     * -- GETTER --
     * Getter method for all instances of commands.
     */
    @Getter
    private final List<CommandImpl> commands = new ArrayList<>();
    /**
     * This structure maps all aliases to their corresponding commands.
     */
    public Map<String, CommandImpl> commandMap = new LinkedHashMap<>();
    /**
     * This structure maps all typos caused by not switching from the Russian ᠱ"ЙЦУКЕН" layout to their actual commands.
     */
    private Map<String, String> typoCommandMap = new LinkedHashMap<>();
    /**
     * This structure maps all shorthand input (for long commands only, typing them is a pain in the ass) to their actual commands.
     */
    private Map<String, String> shortHandCommandMap = new LinkedHashMap<>();

    /**
     * This method add an alias with its corresponding command.
     * @param commandAlias the command alias (full and shorthand).
     * @param command the command itself.
     */
    protected void addCommand(String commandAlias, CommandImpl command) {
        this.commandMap.put(commandAlias, command);
    }

    {
        typoCommandMap.put("фвв", "add");
        typoCommandMap.put("сдуфк", "clear");
        typoCommandMap.put("учусгеу_ыскшзе", "execute_script");
        typoCommandMap.put("учы", "exs");
        typoCommandMap.put("учше", "exit");
        typoCommandMap.put("ашдеук_дуыы_ерфт_тгьиук_ща_зфкешсшзфтеы", "filter_less_than_number_of_participants");
        typoCommandMap.put("адетщз", "fltnop");
        typoCommandMap.put("пкщгз_сщгтештп_ин_уыефидшырьуте_вфеу", "group_counting_by_establishment_date");
        typoCommandMap.put("псиув", "gcbed");
        typoCommandMap.put("рудз", "help");
        typoCommandMap.put("ршыещкн", "history");
        typoCommandMap.put("штащ", "info");
        typoCommandMap.put("зкште_ашудв_вуысутвштп_уыефидшырьуте_вфеу", "print_field_descending_establishment_date");
        typoCommandMap.put("завув", "pfded");
        typoCommandMap.put("куьщму_фе", "remove_at");
        typoCommandMap.put("куьщму_ин_шв", "remove_by_id");
        typoCommandMap.put("ыфму", "save");
        typoCommandMap.put("ырщц", "show");
        typoCommandMap.put("ыргааду", "shuffle");
        typoCommandMap.put("гзвфеу", "update");
        shortHandCommandMap.put("exs", "execute_script");
        shortHandCommandMap.put("fltnop", "filter_less_than_number_of_participants");
        shortHandCommandMap.put("gcbed", "group_counting_by_establishment_date");
        shortHandCommandMap.put("pfded", "print_field_descending_establishment_date");
        shortHandCommandMap.put("r_at", "remove_at");
        shortHandCommandMap.put("r_id", "remove_by_id");
    }
    /**
     * This method is used to register the most recently used command into the History.
     * @param recentCommand the most recent command.
     */
    void addToHistory(String recentCommand) {
        for (CommandImpl command : commands) {
            if (command.getName().split(" ")[0].equals(recentCommand)) {
                for (int i = COMMAND_HISTORY_SIZE - 1; i > 0; i--) {
                    commandHistory[i] = commandHistory[i - 1];
                }
                commandHistory[0] = recentCommand;
            }
        }
    }

    /**
     * This method corrects a typo caused by wrong keyboard layout (or a shorthand) into the actual command.
     *
     * @param typo the typo in Russian (or a shorthand).
     * @return autocorrected command.
     */
    String typoTranscript(String typo) {
        return (Pattern.matches(".*\\p{InCyrillic}.*", typo)) ? typoCommandMap.get(typo) : shortHandCommandMap.getOrDefault(typo, typo);
    }
    /**
     * This method is used to signify to the user that the command is unavailable.
     * @param arg unavailable command.
     */
    static void noSuchCommand(String arg) {
        Console.println("Command '" + arg + "' not found. Use command 'Help' for advice.");
    }
    /**
     * A custom implementation of the toString() method in CommandManager.
     * @return information about this class.
     */
    @Override
    public String toString() {
        return "CommandManager (utility class for command logic).";
    }
}
