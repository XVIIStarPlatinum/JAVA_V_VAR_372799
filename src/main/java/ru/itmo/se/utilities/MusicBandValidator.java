package ru.itmo.se.utilities;

import lombok.Getter;
import lombok.Setter;
import ru.itmo.se.console.Main;
import ru.itmo.se.data.Coordinates;
import ru.itmo.se.data.MusicGenre;
import ru.itmo.se.data.Studio;
import ru.itmo.se.exceptions.IllegalStateException;
import ru.itmo.se.exceptions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.*;

@Getter
@Setter
public class MusicBandValidator {

    private static final float MIN_X = -584.0F;

    private Scanner userScanner;

    private boolean fileMode;

    private static Set<Integer> IDset = new TreeSet<>();

    private static final DateTimeFormatterBuilder formats = new DateTimeFormatterBuilder()
            .append(DateTimeFormatter.ofPattern("[uuuu-MM-dd]" + "[uuuu/MM/dd]" + "[uuuu MM dd]" + "[uuuu.MM.dd]"));

    private static DateTimeFormatter dateTimeFormatter = formats.toFormatter();

    public MusicBandValidator(Scanner userScanner) {
        this.userScanner = userScanner;
        fileMode = false;
    }

    public String askName() throws InvalidInputException {
        String name;
        while (true) {
            try {
                Console.println("Enter name:");
                Console.print(Main.CS2);
                name = userScanner.nextLine().trim();
                if (fileMode) Console.println(name);
                if (checkName(name)) throw new NullValueException("Name cannot be null", new RuntimeException());
                break;
            } catch (NoSuchElementException e) {
                Console.printError("Name not found.");
            } catch (NullValueException e) {
                Console.printError("Name must not be empty.");
            } catch (IllegalStateException e) {
                Console.printError("Unknown error. Stopping the session...");
                System.exit(0);
            }
        }
        return name;
    }

    public float askX() throws InvalidInputException {
        String strX;
        float x;
        while (true) {
            try {
                Console.println("Enter coordinate X:");
                Console.print(Main.CS2);
                strX = userScanner.nextLine().trim();
                if (fileMode) Console.println(strX);
                x = Float.parseFloat(strX);
                if (x < MIN_X)
                    throw new ValueRangeException("Your input is less than our allowed value.", new RuntimeException());
                if (checkX(x))
                    throw new IllegalArgumentException("Your input has not passed validation.", new RuntimeException());
                break;
            } catch (IllegalArgumentException e) {
                Console.printError("Coordinate x is null.");
                if (fileMode) throw new NullValueException("Coordinate X is null.", new RuntimeException());
            } catch (NoSuchElementException e) {
                Console.printError("Coordinate X not recognized.");
                if (fileMode) throw new InvalidInputException("Coordinate X not recognized.", new RuntimeException());
            } catch (ValueRangeException e) {
                Console.printError("Coordinate X cannot be less than -584. Please try again.");
                if (fileMode)
                    throw new InvalidInputException("Coordinate X cannot be less than -584. Please try again.", new RuntimeException());
            } catch (NullPointerException | IllegalStateException e) {
                Console.printError("Unknown error. Stopping the session...");
                System.exit(0);
            }
        }
        return x;
    }

    public float askY() throws InvalidInputException {
        String strY;
        float y;
        while (true) {
            try {
                Console.println("Enter coordinate Y:");
                Console.print(Main.CS2);
                strY = userScanner.nextLine().trim();
                if (fileMode) Console.println(strY);
                y = Float.parseFloat(strY);
                if (checkY(y))
                    throw new ValueRangeException("The value of coordinate Y must be within float range.", new RuntimeException());
                break;
            } catch (NoSuchElementException nsee) {
                Console.printError("Coordinate Y not recognized");
                if (fileMode)
                    throw new InvalidInputException("The value of coordinate Y must be within float range.", new RuntimeException());
            } catch (NumberFormatException nfe) {
                Console.printError("Coordinate Y must be a number");
                if (fileMode)
                    throw new InvalidInputException("The amount of coordinate Y must be within float range.", new RuntimeException());
            } catch (NullPointerException | java.lang.IllegalStateException exception) {
                Console.printError("Unknown error. Stopping the session...");
                System.exit(0);
            }
        }
        return y;
    }

    public Coordinates askCoordinates() throws InvalidInputException {
        return new Coordinates(askX(), askY());
    }

    public Long askNumberOfParticipants() throws InvalidInputException {
        String strNumberOfParticipants;
        Long numberOfParticipants;
        while (true) {
            try {
                Console.println("Enter the number of participants:");
                Console.print(Main.CS2);
                strNumberOfParticipants = userScanner.nextLine().trim();
                if (strNumberOfParticipants.isEmpty()) return 0L;
                if (fileMode) Console.println(strNumberOfParticipants);
                numberOfParticipants = Long.parseLong(strNumberOfParticipants);
                if (checkNumberOfParticipants(numberOfParticipants)) {
                    throw new NullValueException("Your input has not passed validation.", new RuntimeException());
                }
                break;
            } catch (NullValueException e) {
                Console.printError("Number of participants is null");
                if (fileMode) throw new NullValueException("Number of participants is null", new RuntimeException());
            } catch (NoSuchElementException e) {
                Console.printError("Number of participants is not recognized");
                if (fileMode)
                    throw new InvalidInputException("Number of participants is not recognized", new RuntimeException());
            } catch (NumberFormatException e) {
                Console.printError("Number of participants must be a \'Long\' number");
                if (fileMode)
                    throw new InvalidInputException("Number of participants must be a \'Long\' number", new RuntimeException());
            } catch (NullPointerException | IllegalStateException e) {
                Console.printError("Unknown error. Stopping the session...");
                System.exit(0);
            }
        }
        return numberOfParticipants;
    }

    public LocalDateTime askEstablishmentDate() {
        String strEstablishmentDate;
        LocalDateTime establishmentDate;
        while (true) {
            try {
                Console.println("Enter establishment date with the following format: yyyy-MM-dd, yyyy.MM.dd, yyyy/MM/dd:");
                Console.print(Main.CS2);
                strEstablishmentDate = userScanner.nextLine().trim();
                establishmentDate = LocalDate.parse(strEstablishmentDate, dateTimeFormatter).atStartOfDay();
                if (fileMode) Console.println(strEstablishmentDate);
                break;
            } catch (NoSuchElementException e) {
                Console.printError("Date not recognized.");
            } catch (DateTimeParseException e) {
                Console.printError("Your input cannot be parsed. Please try again.");
            } catch (IllegalStateException e) {
                Console.printError("Unknown error. Stopping the session...");
                System.exit(0);
            }
        }
        return establishmentDate;
    }

    public MusicGenre askMusicGenre() {
        String strMusicGenre;
        MusicGenre musicGenre;
        while (true) {
            try {
                System.out.println("\033[1;34m" + "List of music genres:\n" + MusicGenre.nameList() + "\u001B[0m");
                Console.println("Enter music genre:");
                Console.print(Main.CS2);
                strMusicGenre = userScanner.nextLine().trim();
                if (strMusicGenre.isEmpty()) return MusicGenre.NULL;
                if (fileMode) Console.println(strMusicGenre);
                musicGenre = MusicGenre.valueOf(strMusicGenre.toUpperCase());
                if (checkMusicGenre(musicGenre))
                    throw new InvalidInputException("A music genre can be blank, but should be from available genres.", new RuntimeException());
                break;
            } catch (InvalidInputException e) {
                Console.printError("A music genre can be blank, but should be from available genres.");
                if (fileMode)
                    throw new InvalidInputException("A music genre can be blank, but should be from available genres.", new RuntimeException());
            } catch (InvalidTypeException e) {
                Console.printError("Genre not recognized.");
                if (fileMode) throw new InvalidInputException("Genre not recognized.", new RuntimeException());
            } catch (IllegalArgumentException e) {
                Console.printError("There's no such type.");
                if (fileMode) throw new InvalidInputException("There's no such type.", new RuntimeException());
            } catch (IllegalStateException e) {
                Console.printError("Unknown error. Stopping the session...");
                System.exit(0);
            }
        }
        return musicGenre;
    }

    public String askAddress() {
        String address;
        while (true) {
            try {
                Console.println("Enter address:");
                Console.print(Main.CS2);
                address = userScanner.nextLine().trim();
                if (fileMode) Console.println(address);
                if (checkAddress(address))
                    throw new InvalidInputException("Your input has not passed validation.", new RuntimeException());
                break;
            } catch (NoSuchElementException e) {
                Console.printError("Address not recognized.");
                if (fileMode) throw new InvalidInputException("Address not recognized.", new RuntimeException());
            } catch (InvalidInputException e) {
                Console.printError("Studio address cannot be null.");
            } catch (IllegalStateException e) {
                Console.printError("Unknown error. Stopping the session...");
                System.exit(0);
            }
        }
        return address;
    }

    public Studio askStudio() throws InvalidInputException {
        return new Studio(askAddress());
    }

    public boolean askQuestion(String question) throws InvalidInputException {
        String finalQuestion = question + " (+/-)";
        String answer;
        while (true) {
            try {
                Console.println(finalQuestion);
                Console.print(Main.CS2);
                answer = userScanner.nextLine().trim();
                if (fileMode) Console.println(answer);
                if (!answer.equals("+") && !answer.equals("-"))
                    throw new InvalidInputException("User input must be either '+' or '-'", new IllegalArgumentException());
                break;
            } catch (NoSuchElementException e) {
                Console.printError("Answer not recognized.");
                if (fileMode) throw new InvalidInputException("Answer not recognized.", new RuntimeException());
            } catch (InvalidInputException exception) {
                Console.printError("Answer must be either '+' or '-'.");
                if (fileMode)
                    throw new InvalidInputException("Answer must be either '+' or '-'.", new IllegalArgumentException());
            } catch (java.lang.IllegalStateException exception) {
                Console.printError("Unknown error. Stopping the session...");
                System.exit(0);
            }
        }
        return answer.equals("+");
    }

    protected static boolean checkID(Integer ID) {
        return ID == null || ID <= 0;
    }

    protected static boolean checkUniqueID(Integer ID) {
        boolean unique;
        if (IDset.contains(ID)) {
            unique = true;
        } else {
            IDset.add(ID);
            unique = false;
        }
        return unique;
    }

    static boolean checkName(String name) {
        return name == null || name.isEmpty();
    }

    static boolean checkX(float x) {
        return x < MIN_X || x > Float.MAX_VALUE;
    }

    static boolean checkY(float y) {
        return y < Float.MIN_VALUE || y > Float.MAX_VALUE;
    }

    static boolean checkDate(Date creationDate) {
        return creationDate == null || creationDate.toString().isEmpty();
    }

    static boolean checkNumberOfParticipants(long numberOfParticipants) {
        return numberOfParticipants < 0L || numberOfParticipants > Long.MAX_VALUE;
    }

    static boolean checkEstablishmentDate(LocalDateTime establishmentDate) {
        return LocalDateTime.parse(establishmentDate.toString()).toString().isEmpty();
    }

    static boolean checkMusicGenre(MusicGenre genre) {
        if (genre.name().isEmpty() || genre.name().equals("null")) {
            return false;
        }
        for (MusicGenre musicGenre : MusicGenre.values()) {
            if (genre.name().equalsIgnoreCase(musicGenre.name())) return false;
        }
        return true;
    }

    static boolean checkAddress(String address) {
        return address == null || address.isEmpty();
    }
}
