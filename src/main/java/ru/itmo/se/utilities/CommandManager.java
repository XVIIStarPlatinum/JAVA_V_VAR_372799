package ru.itmo.se.utilities;

import lombok.Getter;
import ru.itmo.se.commands.CommandImpl;
import ru.itmo.se.exceptions.EmptyHistoryException;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class used for operations with commands and their management. Some of them are implemented here.
 */
public class CommandManager {
    /**
     * This field stores the capacity of command history.
     */
    private static final int COMMAND_HISTORY_SIZE = 10;
    /**
     * This field stores the most recent 10 commands.
     */
    private final String[] commandHistory = new String[COMMAND_HISTORY_SIZE];
    /**
     * This field stores all instances of commands.
     * -- GETTER --
     * Getter method for all instances of commands.
     */
    @Getter
    private final List<CommandImpl> commands = new ArrayList<>();
    /**
     * This field is for the command add.
     */
    private final CommandImpl add;
    /**
     * This field is for the command clear.
     */
    private final CommandImpl clear;
    /**
     * This field is for the command execute_script.
     */
    private final CommandImpl executeScript;
    /**
     * This field is for the command exit.
     */
    private final CommandImpl exit;
    /**
     * This field is for the command filter_less_than_number_of_participants.
     */
    private final CommandImpl filterLessThanNumberOfParticipants;
    /**
     * This field is for the command group_counting_by_establishment_date.
     */
    private final CommandImpl groupCountingByEstablishmentDate;
    /**
     * This field is for the command help.
     */
    private final CommandImpl help;
    /**
     * This field is for the command history.
     */
    private final CommandImpl history;
    /**
     * This field is for the command info.
     */
    private final CommandImpl info;
    /**
     * This field is for the command print_field_descending_establishment_date.
     */
    private final CommandImpl printFieldDescendingEstablishmentDate;
    /**
     * This field is for the command remove_at.
     */
    private final CommandImpl removeAt;
    /**
     * This field is for the command remove_by_id.
     */
    private final CommandImpl removeByID;
    /**
     * This field is for the command save.
     */
    private final CommandImpl save;
    /**
     * This field is for the command show.
     */
    private final CommandImpl show;
    /**
     * This field is for the command shuffle.
     */
    private final CommandImpl shuffle;
    /**
     * This field is for the command update.
     */
    private final CommandImpl updateID;

    /**
     * Constructs a CommandManager with the specified commands.
     *
     * @param add                                   the command add.
     * @param clear                                 the command clear.
     * @param executeScript                         the command execute_script.
     * @param exit                                  the command exit.
     * @param filterLessThanNumberOfParticipants    the command filter_less_than_number_of_participants.
     * @param groupCountingByEstablishmentDate      the command group_counting_by_establishment_date.
     * @param help                                  the command help.
     * @param history                               the command history.
     * @param info                                  the command info.
     * @param printFieldDescendingEstablishmentDate the command print_field_descending_establishment_date.
     * @param removeAt                              the command remove_at.
     * @param removeByID                            the command remove_by_id.
     * @param save                                  the command save.
     * @param show                                  the command show.
     * @param shuffle                               the command shuffle.
     * @param updateID                              the command update.
     */
    public CommandManager(CommandImpl add, CommandImpl clear, CommandImpl executeScript, CommandImpl exit, CommandImpl filterLessThanNumberOfParticipants, CommandImpl groupCountingByEstablishmentDate, CommandImpl help, CommandImpl history, CommandImpl info, CommandImpl printFieldDescendingEstablishmentDate, CommandImpl removeAt, CommandImpl removeByID, CommandImpl save, CommandImpl show, CommandImpl shuffle, CommandImpl updateID) {
        this.add = add;
        this.clear = clear;
        this.executeScript = executeScript;
        this.exit = exit;
        this.filterLessThanNumberOfParticipants = filterLessThanNumberOfParticipants;
        this.groupCountingByEstablishmentDate = groupCountingByEstablishmentDate;
        this.help = help;
        this.history = history;
        this.info = info;
        this.printFieldDescendingEstablishmentDate = printFieldDescendingEstablishmentDate;
        this.removeAt = removeAt;
        this.removeByID = removeByID;
        this.save = save;
        this.show = show;
        this.shuffle = shuffle;
        this.updateID = updateID;

        commands.add(add);
        commands.add(clear);
        commands.add(executeScript);
        commands.add(exit);
        commands.add(filterLessThanNumberOfParticipants);
        commands.add(groupCountingByEstablishmentDate);
        commands.add(help);
        commands.add(history);
        commands.add(info);
        commands.add(printFieldDescendingEstablishmentDate);
        commands.add(removeAt);
        commands.add(removeByID);
        commands.add(save);
        commands.add(show);
        commands.add(shuffle);
        commands.add(updateID);
    }

    /**
     * This method is used to register the most recently used command into the history.
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
     * This method is used to signify to the user that the command is unavailable.
     * @param arg unavailable command.
     */
    static void noSuchCommand(String arg) {
        Console.println("Command '" + arg + "' not found. Use command 'help' for advice.");
    }

    /**
     * This method is used as a trigger for the execute_script command.
     * @param arg the script file.
     * @return whether the command was executed or not.
     */
    boolean executeScript(String arg) {
        return executeScript.apply(arg);
    }
    /**
     * This method is used as a trigger for the help command.
     * @param arg the argument.
     * @return whether the command was executed or not.
     */
    boolean help(String arg) {
        if (help.apply(arg)) {
            Console.printTable("                          COMMAND NAME", "                                  COMMAND SPECIFICATION");
            for (CommandImpl command : commands) {
                Console.printTable(command.getName(), command.getSpec());
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method is used as a trigger for the show command.
     * @param arg the argument.
     * @return whether the command was executed or not.
     */
    boolean show(String arg) {
        return show.apply(arg);
    }

    /**
     * This method is used as a trigger for the info command.
     * @param arg the argument.
     * @return whether the command was executed or not.
     */
    boolean info(String arg) {
        return info.apply(arg);
    }

    /**
     * This method is used as a trigger for the save command.
     * @param arg the argument.
     * @return whether the command was executed or not.
     */
    boolean save(String arg) {
        return save.apply(arg);
    }
    /**
     * This method is used as a trigger for the exit command.
     * @param arg the argument.
     * @return whether the command was executed or not.
     */
    boolean exit(String arg) {
        return exit.apply(arg);
    }

    /**
     * This method is used as a trigger for the add command.
     * @param arg the music band to be added.
     * @return whether the command was executed or not.
     */
    public boolean add(String arg) {
        return add.apply(arg);
    }

    /**
     * This method is used as a trigger for the print_field_descending_establishment_date command.
     * @param arg the argument.
     * @return whether the command was executed or not.
     */
    boolean printFieldDescendingEstablishmentDate(String arg) {
        return printFieldDescendingEstablishmentDate.apply(arg);
    }
    /**
     * This method is used as a trigger for the history command.
     * @param arg the argument.
     * @return whether the command was executed or not.
     */
    boolean history(String arg) {
        if (history.apply(arg)) {
            try {
                if (commandHistory.length == 0) {
                    throw new EmptyHistoryException("You just started this session, of course the history is empty.", new RuntimeException());
                }
                Console.println("Recently used commands:");
                for (String s : commandHistory) {
                    if (s != null) Console.println(" " + s);
                }
                return true;
            } catch (EmptyHistoryException e) {
                Console.printError("Not a single command was executed yet.");
            }
        }
        return false;
    }

    /**
     * This method is used as a trigger for the add command.
     * @param arg the music band to be updated.
     * @return whether the command was executed or not.
     */
    boolean updateID(String arg) {
        return updateID.apply(arg);
    }

    /**
     * This method is used as a trigger for the clear command.
     * @param arg the argument.
     * @return whether the command was executed or not.
     */
    boolean clear(String arg) {
        return clear.apply(arg);
    }

    /**
     * This method is used as a trigger for the filter_less_than_number_of_participants command.
     * @param arg the number of participants.
     * @return whether the command was executed or not.
     */
    boolean filterLessThanNumberOfParticipants(String arg) {
        return filterLessThanNumberOfParticipants.apply(arg);
    }

    /**
     * This method is used as a trigger for the group_counting_by_establishment_date command.
     * @param arg the argument.
     * @return whether the command was executed or not.
     */
    boolean groupCountingByEstablishmentDate(String arg) {
        return groupCountingByEstablishmentDate.apply(arg);
    }

    /**
     * This method is used as a trigger for the remove_at command.
     * @param arg the index of the music band.
     * @return whether the command was executed or not.
     */
    boolean removeAt(String arg) {
        return removeAt.apply(arg);
    }

    /**
     * This method is used as a trigger for the remove_by_id command.
     * @param arg the ID of the music band.
     * @return whether the command was executed or not.
     */
    boolean removeByID(String arg) {
        return removeByID.apply(arg);
    }
    /**
     * This method is used as a trigger for the shuffle command.
     * @param arg the argument.
     * @return whether the command was executed or not.
     */
    boolean shuffle(String arg) {
        return shuffle.apply(arg);
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
