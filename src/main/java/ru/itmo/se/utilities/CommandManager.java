package ru.itmo.se.utilities;


import lombok.Getter;
import ru.itmo.se.commands.CommandImpl;
import ru.itmo.se.exceptions.EmptyHistoryException;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {

    private static final int COMMAND_HISTORY_SIZE = 10;

    private final String[] COMMAND_HISTORY = new String[COMMAND_HISTORY_SIZE];

    @Getter
    private final List<CommandImpl> commands = new ArrayList<>();

    private final CommandImpl add;

    private final CommandImpl clear;

    private final CommandImpl executeScript;

    private final CommandImpl exit;

    private final CommandImpl filterLessThanNumberOfParticipants;

    private final CommandImpl groupCountingByEstablishmentDate;

    private final CommandImpl help;

    private final CommandImpl history;

    private final CommandImpl info;

    private final CommandImpl printFieldDescendingEstablishmentDate;

    private final CommandImpl removeAt;

    private final CommandImpl removeByID;

    private final CommandImpl save;

    private final CommandImpl show;

    private final CommandImpl shuffle;

    private final CommandImpl updateID;

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

    public void addToHistory(String recentCommand) {
        for (CommandImpl command : commands) {
            if (command.getName().split(" ")[0].equals(recentCommand)) {
                for (int i = COMMAND_HISTORY_SIZE - 1; i > 0; i--) {
                    COMMAND_HISTORY[i] = COMMAND_HISTORY[i - 1];
                }
                COMMAND_HISTORY[0] = recentCommand;
            }
        }
    }

    public void noSuchCommand(String arg) {
        Console.println("Command '" + arg + "' not found. Use command 'help' for advice.");
    }

    public boolean executeScript(String arg) {
        return executeScript.apply(arg);
    }

    public boolean help(String arg) {
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

    public boolean show(String arg) {
        return show.apply(arg);
    }

    public boolean info(String arg) {
        return info.apply(arg);
    }

    public boolean save(String arg) {
        return save.apply(arg);
    }

    public boolean exit(String arg) {
        return exit.apply(arg);
    }

    public boolean add(String arg) {
        return add.apply(arg);
    }

    public boolean printFieldDescendingEstablishmentDate(String arg) {
        return printFieldDescendingEstablishmentDate.apply(arg);
    }

    public boolean history(String arg) {
        if (history.apply(arg)) {
            try {
                if (COMMAND_HISTORY.length == 0) {
                    throw new EmptyHistoryException("You just started this session, of course the history is empty.", new RuntimeException());
                }
                Console.println("Recent used commands:");
                for (String s : COMMAND_HISTORY) {
                    if (s != null) Console.println(" " + s);
                }
                return true;
            } catch (EmptyHistoryException e) {
                Console.printError("Not a single command was executed yet.");
            }
        }
        return false;
    }

    public boolean updateID(String arg) {
        return updateID.apply(arg);
    }

    public boolean clear(String arg) {
        return clear.apply(arg);
    }

    public boolean filterLessThanNumberOfParticipants(String arg) {
        return filterLessThanNumberOfParticipants.apply(arg);
    }

    public boolean groupCountingByEstablishmentDate(String arg) {
        return groupCountingByEstablishmentDate.apply(arg);
    }

    public boolean removeAt(String arg) {
        return removeAt.apply(arg);
    }

    public boolean removeByID(String arg) {
        return removeByID.apply(arg);
    }

    public boolean shuffle(String arg) {
        return shuffle.apply(arg);
    }

    @Override
    public String toString() {
        return "CommandManager (utility class for command logic).";
    }
}
