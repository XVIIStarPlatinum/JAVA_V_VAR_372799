package ru.itmo.se.utilities;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import ru.itmo.se.console.Main;
import ru.itmo.se.data.MusicBand;
import ru.itmo.se.exceptions.IllegalStateException;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.NoSuchElementException;


public class FileManager {

    public Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).create();

    public FileManager(String ConsoleArg) {
        Main.cliArgument = ConsoleArg;
    }

    LinkedList<MusicBand> collection;

    public void writeCollection(LinkedList<MusicBand> collection) {
        try {
            Writer writer = new OutputStreamWriter(new FileOutputStream(Main.cliArgument), StandardCharsets.UTF_8);
            String json = gson.toJson(collection);
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            ru.itmo.se.utilities.Console.printError("File cannot be opened.");
        }
    }

    public LinkedList<MusicBand> readCollection() {
        if (Main.cliArgument != null) {
            try (FileReader fileReader = new FileReader(Main.cliArgument)) {
                final Type collectionType = new TypeToken<LinkedList<MusicBand>>() {
                }.getType();
                collection = gson.fromJson(fileReader, collectionType);
                return collection;
            } catch (FileNotFoundException e) {
                ru.itmo.se.utilities.Console.printError("File not found.");
            } catch (NoSuchElementException e) {
                ru.itmo.se.utilities.Console.printError("The file is empty.");
            } catch (JsonParseException | NullPointerException e) {
                ru.itmo.se.utilities.Console.printError("No collection detected.");
            } catch (IllegalStateException e) {
                ru.itmo.se.utilities.Console.printError("Unknown error. Stopping the session...");
                System.exit(0);
            } catch (IOException e) {
                ru.itmo.se.utilities.Console.printError("I/O operation interrupted.");
            }
        } else {
            Console.printError("JSON file not found.");
        }
        return new LinkedList<>();
    }

    @Override
    public String toString() {
        return "FileManager (utility class for file management)";
    }
}
