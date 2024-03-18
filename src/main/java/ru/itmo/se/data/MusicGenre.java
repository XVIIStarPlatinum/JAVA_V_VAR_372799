package ru.itmo.se.data;

/**
 *
 */
public enum MusicGenre {

    PSYCHEDELIC_ROCK,

    PSYCHEDELIC_CLOUD_RAP,

    SOUL,

    POP,

    BRIT_POP,

    NULL;

    public static String nameList() {
        StringBuilder nameList = new StringBuilder();
        for (MusicGenre musicGenre : values()) {
            if (musicGenre == NULL) break;
            nameList.append(musicGenre.name()).append(",\n");
        }
        nameList = new StringBuilder(nameList.substring(0, nameList.length() - 2));
        nameList.append("\n======================");
        return nameList.toString();
    }
}
