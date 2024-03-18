package ru.itmo.se.utilities;

import lombok.Getter;
import ru.itmo.se.data.MusicBand;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Getter
public class CollectionManager {

    private LocalDateTime lastInitTime;

    private LinkedList<MusicBand> musicBandCollection = new LinkedList<>();

    private LocalDateTime lastSaveTime;

    private final FileManager fileManager;

    public CollectionManager(FileManager fileManager) throws IOException {
        this.lastInitTime = null;
        this.lastSaveTime = null;
        this.fileManager = fileManager;
        loadCollection();
    }

    public int collectionSize() {
        return musicBandCollection.size();
    }

    public String getCollectionType() {
        return musicBandCollection.getClass().getName();
    }

    public MusicBand getFirst() {
        return musicBandCollection.getFirst();
    }

    public MusicBand getLast() {
        return musicBandCollection.getLast();
    }

    public boolean collectionContains(MusicBand musicBand) {
        return musicBandCollection.contains(musicBand);
    }

    private static void sortCollection(LinkedList<MusicBand> musicBands) {
        musicBands.sort(MusicBand::compareTo);
    }

    public void groupCountingByEstablishmentDate() {
        StringBuilder uniqueDates = new StringBuilder();
        Collection<LocalDateTime> dateSet = new TreeSet<>();
        for (MusicBand musicBand : musicBandCollection) {
            dateSet.add(musicBand.getEstablishmentDate());
        }
        uniqueDates.append("Collection count grouped by available dates:\n");
        for (LocalDateTime establishmentDate : dateSet) {
            uniqueDates.append(establishmentDate).append(": ").append(countByEstablishmentDate(establishmentDate)).append("\n");
        }
        uniqueDates.append("=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=");
        Console.println(uniqueDates.toString());
    }

    public MusicBand getByID(Integer id) {
        for (MusicBand musicBand : musicBandCollection) {
            if (Objects.equals(musicBand.getId(), id)) {
                return musicBand;
            }
        }
        return null;
    }

    public MusicBand getByValue(MusicBand musicBand) {
        for (MusicBand band : musicBandCollection) {
            if (band.equals(musicBand)) {
                return band;
            }
        }
        return null;
    }

    public MusicBand getByIndex(Integer index) {
        return musicBandCollection.get(index);
    }

    public int countByEstablishmentDate(LocalDateTime establishmentDate) {
        int count = 0;
        for (MusicBand musicBand : musicBandCollection) {
            if (Objects.equals(musicBand.getEstablishmentDate(), establishmentDate)) {
                count++;
            }
        }
        return count;
    }

    public MusicBand getByGenre(String type) {
        for (MusicBand musicBand : musicBandCollection) {
            if (musicBand.getMusicGenre().toString().equals(type.toUpperCase(Locale.ROOT))) {
                return musicBand;
            }
        }
        return null;
    }

    public void printFieldDescendingEstablishmentDate() {
        TreeSet<MusicBand> copy = new TreeSet<>(Collections.reverseOrder(MusicBand::compareToEstablishmentDate));
        ArrayList<LocalDateTime> arrayList = new ArrayList<>();
        copy.addAll(musicBandCollection);
        for (MusicBand musicBand : copy) {
            arrayList.add(musicBand.getEstablishmentDate());
        }
        Console.println(arrayList.toString().trim());
    }

    public String musicBandParticipantsFilteredInfo(Long numberOfParticipants) {
        StringBuilder info = new StringBuilder();
        for (MusicBand musicBand : musicBandCollection) {
            if (musicBand.getNumberOfParticipants() < numberOfParticipants) {
                info.append(musicBand).append("\n\n");
            }
        }
        return info.toString().trim();
    }

    public void addToCollection(MusicBand musicBand) {
        musicBandCollection.add(musicBand);
    }

    public void removeFromCollection(MusicBand musicBand) {
        musicBandCollection.remove(musicBand);
    }

    public void shuffleCollection() {
        Collections.shuffle(musicBandCollection);
    }

    public void clearCollection() {
        musicBandCollection.clear();
    }

    public final void loadCollection() {
        FileContentValidator fileContentValidator = new FileContentValidator();
        musicBandCollection = fileContentValidator.validateFileContent();
        lastInitTime = LocalDateTime.now();
    }

    public void saveCollection() {
        sortCollection(musicBandCollection);
        fileManager.writeCollection(musicBandCollection);
        lastSaveTime = LocalDateTime.now();
    }

    public Integer generateNextID() {
        return (musicBandCollection.isEmpty()) ? 1 : musicBandCollection.stream().max(Comparator.comparing(MusicBand::getId)).get().getId() + 1;
    }

    @Override
    public String toString() {
        if (musicBandCollection.isEmpty()) {
            return "Empty collection.";
        }
        StringBuilder info = new StringBuilder();
        for (MusicBand musicBand : musicBandCollection) {
            info.append(musicBand.toString()).append("\n-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-\n");
        }
        return info.toString();
    }
}
