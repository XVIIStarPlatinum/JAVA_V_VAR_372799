package ru.itmo.se.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


@Getter
@AllArgsConstructor
public class MusicBand implements Comparable<MusicBand> {

    private Integer id;

    private String name;

    private Coordinates coordinates;

    private Date creationDate;

    private Long numberOfParticipants;

    private LocalDateTime establishmentDate;

    private MusicGenre musicGenre;

    private Studio studio;

    public int compareTo(MusicBand musicBand) {
        return id.compareTo(musicBand.getId());
    }

    public int compareToEstablishmentDate(MusicBand musicBand) {
        if (establishmentDate.isEqual(musicBand.getEstablishmentDate())) return -1;
        if (establishmentDate.isAfter(musicBand.getEstablishmentDate())) return 1;
        else return -1;
    }

    @Override
    public String toString() {
        String output = "";
        output += "MusicBand №" + id;
        output += " (added " + LocalDate.now() + ")";
        output += "\n   Name: " + name;
        output += "\n   Coordinates: " + coordinates;
        output += "\n   Number of participants: " + numberOfParticipants;
        output += "\n   Establishment date: " + establishmentDate;
        output += "\n   Musical genre: " + musicGenre;
        output += "\n   Studio: " + studio;
        return output;
    }

    @Override
    public int hashCode() {
        return id.hashCode() +
                name.hashCode() +
                coordinates.hashCode() +
                creationDate.hashCode() +
                numberOfParticipants.hashCode() +
                establishmentDate.hashCode() +
                musicGenre.hashCode() +
                studio.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof MusicBand musicBand) {
            return name.equals(musicBand.getName()) &&
                    coordinates.equals(musicBand.getCoordinates()) &&
                    creationDate.equals(musicBand.getCreationDate()) &&
                    numberOfParticipants == musicBand.getNumberOfParticipants() &&
                    establishmentDate.isEqual(musicBand.getEstablishmentDate()) &&
                    musicGenre.equals(musicBand.getMusicGenre()) &&
                    studio.equals(musicBand.getStudio());
        }
        return false;
    }
}
