package ru.itmo.se.utilities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
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

/**
 * Utility class used for validating all data and conforming them to necessary constraints.
 */
@Getter
@Setter
@ToString
public class MusicBandValidator {
    /**
     * This field holds the minimum limit value of coordinate X.
     */
    public final static float MIN_X = -584.0F;
    /**
     * This field holds the scanner via which the application will receive inputs.
     */
    private Scanner userScanner;
    /**
     * This field holds the value of whether the validator is working in file mode (in script) or not.
     */
    private boolean fileMode;
    /**
     * This field stores all the values of ID's. Set is used here to ensure the IDs' uniqueness constraint.
     */
    private static Set<Integer> IDset = new TreeSet<>();
    /**
     * This field stores all possible date formats for the field EstablishmentDate.
     */
    private static final DateTimeFormatterBuilder formats = new DateTimeFormatterBuilder()
            .append(DateTimeFormatter.ofPattern("[uuuu-MM-dd]" + "[uuuu/MM/dd]" + "[uuuu MM dd]" + "[uuuu.MM.dd]", Locale.ROOT));
    /**
     * This field converts the possible date formats into a formatter.
     */
    private static DateTimeFormatter dateTimeFormatter = formats.toFormatter();

    /**
     * Constructs a MusicBandValidator with the specified Scanner.
     *
     * @param userScanner a Scanner instance which takes input from the user.
     */
    public MusicBandValidator(Scanner userScanner) {
        this.userScanner = userScanner;
        fileMode = false;
    }

    /**
     * This method is used to retrieve a music band's name from the user.
     *
     * @return music band's name.
     */
    public String askName() {
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

    /**
     * This method is used to retrieve a music band's coordinate (X) from the user.
     *
     * @return abscissa value.
     */
    private float askX() {
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
                    throw new InvalidInputException("Your input has not passed validation.", new RuntimeException());
                break;
            } catch (InvalidInputException e) {
                Console.printError("Coordinate X is null.");
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

    /**
     * This method is used to retrieve a music band's coordinate (Y) from the user.
     *
     * @return ordinate value.
     */
    private float askY() {
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
            } catch (NoSuchElementException e) {
                Console.printError("Coordinate Y not recognized");
                if (fileMode)
                    throw new InvalidInputException("The value of coordinate Y must be within float range.", new RuntimeException());
            } catch (NumberFormatException e) {
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

    /**
     * This method is used to retrieve a music band's coordinates from the user.
     *
     * @return coordinates set.
     */
    public Coordinates askCoordinates() {
        return new Coordinates(askX(), askY());
    }

    /**
     * This method is used to retrieve a music band's number of participants from the user.
     *
     * @return number of band members.
     */
    public Long askNumberOfParticipants() {
        String strNumberOfParticipants;
        Long numberOfParticipants;
        while (true) {
            try {
                Console.println("Enter the number of participants:");
                Console.print(Main.CS2);
                strNumberOfParticipants = userScanner.nextLine().trim();
                if (strNumberOfParticipants.isEmpty()) return null;
                if (fileMode) Console.println(strNumberOfParticipants);
                numberOfParticipants = Long.parseLong(strNumberOfParticipants);
                if (checkNumberOfParticipants(numberOfParticipants)) {
                    throw new NullValueException("Your input has not passed validation.", new RuntimeException());
                }
                break;
            } catch (NullValueException e) {
                Console.printError("Your input has not passed validation.");
                if (fileMode) throw new NullValueException("Your input has not passed validation.", new RuntimeException());
            } catch (NoSuchElementException e) {
                Console.printError("Number of participants is not recognized");
                if (fileMode)
                    throw new InvalidInputException("Number of participants is not recognized", new RuntimeException());
            } catch (NumberFormatException e) {
                Console.printError("Number of participants must be a 'Long' number");
                if (fileMode)
                    throw new InvalidInputException("Number of participants must be a 'Long' number", new RuntimeException());
            } catch (NullPointerException | IllegalStateException e) {
                Console.printError("Unknown error. Stopping the session...");
                System.exit(0);
            }
        }
        return numberOfParticipants;
    }

    /**
     * This method is used to retrieve a music band's establishment date from the user.
     * @return the music band's establishment date.
     */
    public LocalDateTime askEstablishmentDate() {
        String strEstablishmentDate;
        LocalDateTime establishmentDate;
        while (true) {
            try {
                Console.println("Enter establishment date with the following format: yyyy-MM-dd, yyyy.MM.dd, yyyy/MM/dd, yyyy MM dd:");
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

    /**
     * This method is used to retrieve a music band's genre from the user.
     * @return music band's genre as an enum value.
     */
    public MusicGenre askMusicGenre() {
        String strMusicGenre;
        MusicGenre musicGenre;
        while (true) {
            try {
                System.out.println("\033[1;34m" + "List of music genres:\n" + MusicGenre.nameList() + "\u001B[0m");
                Console.println("Enter music genre:");
                Console.print(Main.CS2);
                strMusicGenre = userScanner.nextLine().trim();
                if (strMusicGenre.isEmpty()) return null;
                if (fileMode) Console.println(strMusicGenre);
                musicGenre = MusicGenre.valueOf(strMusicGenre.toUpperCase(Locale.ROOT));
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

    /**
     * This method is used to retrieve a music band's studio address from the user.
     * @return music band's studio address.
     */
    private String askAddress() {
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

    /**
     * This method is used to retrieve a music band's studio from the user.
     * @return music band's studio.
     */
    public Studio askStudio() {
        return new Studio(askAddress());
    }

    /**
     * This method is used to update a music band's data. Every question is optional.
     *
     * @param question whether the user wants to update a field.
     * @return true if yes ("+"), <p> false if not ("-").
     */
    public boolean askQuestion(String question) {
        String finalQuestion = question + " (+/-)";
        String answer;
        while (true) {
            try {
                Console.println(finalQuestion);
                Console.print(Main.CS2);
                answer = userScanner.nextLine().trim();
                if (fileMode) Console.println(answer);
                if (!answer.equals("+") && !answer.equals("-"))
                    throw new InvalidInputException("User input must be either '+' or '-'", new RuntimeException());
                break;
            } catch (NoSuchElementException e) {
                Console.printError("Answer not recognized.");
                if (fileMode) throw new InvalidInputException("Answer not recognized.", new RuntimeException());
            } catch (InvalidInputException exception) {
                Console.printError("Answer must be either '+' or '-'.");
                if (fileMode)
                    throw new InvalidInputException("Answer must be either '+' or '-'.", new RuntimeException());
            } catch (java.lang.IllegalStateException exception) {
                Console.printError("Unknown error. Stopping the session...");
                System.exit(0);
            }
        }
        return answer.equals("+");
    }

    /**
     * This method is used to check whether an ID conforms to the required constraints.
     *
     * @param ID the ID to be checked.
     * @return true if the ID doesn't meet the requirements, <p>false if it does.
     */
    static boolean checkID(Integer ID) {
        return ID == null || ID <= 0 || checkUniqueID(ID);
    }

    /**
     * This method is used to check whether an ID is unique or not.
     * @param ID the ID to be checked.
     * @return true if the ID is not unique, <p>false if it is.
     */
    private static boolean checkUniqueID(Integer ID) {
        boolean unique;
        if (IDset.contains(ID)) {
            unique = true;
        } else {
            IDset.add(ID);
            unique = false;
        }
        return unique;
    }

    /**
     * This method is used to check whether a name conforms to the required constraints.
     * @param name the ID to be checked.
     * @return true if the name doesn't meet the requirements, <p>false if it does.
     */
    static boolean checkName(CharSequence name) {
        return name == null || name.isEmpty();
    }

    /**
     * This method is used to check whether an X value conforms to the required constraints.
     * @param x the abscissa value to be checked.
     * @return true if the abscissa doesn't meet the requirements, <p>false if it does.
     */
    static boolean checkX(float x) {
        return x < MIN_X || x > Float.MAX_VALUE;
    }

    /**
     * This method is used to check whether a Y value conforms to the required constraints.
     * @param y the ordinate value to be checked.
     * @return true if the ordinate doesn't meet the requirements, <p>false if it does.
     */
    static boolean checkY(float y) {
        return Math.abs(y) > Float.MAX_VALUE;
    }

    /**
     * This method is used to check whether a creation date conforms to the required constraints.
     * @param creationDate the creation date to be checked.
     * @return true if the creation date doesn't meet the requirements, <p>false if it does.
     */
    static boolean checkDate(Date creationDate) {
        return creationDate == null || creationDate.toString().isEmpty();
    }

    /**
     * This method is used to check whether a number of participants value conforms to the required constraints.
     *
     * @param numberOfParticipants the creation date to be checked.
     * @return true if the number of participants doesn't meet the requirements, <p>false if it does.
     */
    static boolean checkNumberOfParticipants(Long numberOfParticipants) {
        if (numberOfParticipants == null) return false;
        return numberOfParticipants <= 0L;
    }

    /**
     * This method is used to check whether an establishment date conforms to the required constraints.
     * @param establishmentDate the establishment date to be checked.
     * @return true if the establishment date doesn't meet the requirements, <p>false if it does.
     */
    static boolean checkEstablishmentDate(LocalDateTime establishmentDate) {
        if(establishmentDate == null) return false;
        return LocalDateTime.parse(establishmentDate.toString()).toString().isEmpty();
    }

    /**
     * This method is used to check whether a music band's genre conforms to the required constraints.
     * @param genre the genre value to be checked.
     * @return true if the genre doesn't meet the requirements, <p>false if it does.
     */
    static boolean checkMusicGenre(MusicGenre genre) {
        if(genre == null) return false;
        for (MusicGenre musicGenre : MusicGenre.values()) {
            if (genre.name().equalsIgnoreCase(musicGenre.name())) return false;
        }
        return true;
    }

    /**
     * This method is used to check whether a music band's studio address conforms to the required constraints.
     * @param address the address to be checked.
     * @return true if the address doesn't meet the requirements, <p>false if it does.
     */
    static boolean checkAddress(CharSequence address) {
        return address == null || address.isEmpty();
    }
}
