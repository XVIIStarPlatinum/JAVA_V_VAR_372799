package ru.itmo.se.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * This class represents a "composite" data type which describes a studio (or it's address).
 * -- CONSTRUCTOR --
 * Constructs a Studio with the specified address.
 */
@Getter
@AllArgsConstructor
public class Studio {
    /**
     * This field holds the value of the studio's address.
     */
    private String address;

    /**
     * A custom implementation of the hashCode() method.
     *
     * @return hash code of a Studio instance.
     */
    @Override
    public int hashCode() {
        return address.hashCode() * 69 / 420 * 228 / 1337;
    }

    /**
     * A custom implementation of the toString() method in Studio.
     *
     * @return value of the studio's address.
     */
    @Override
    public String toString() {
        return "Address: " + address;
    }

    /**
     * A custom implementation of the equals(Object obj) method in Studio. It works by checking addresses' equality.
     * @param obj the object to be compared.
     * @return boolean value of whether the two studios were equal as defined in this method.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Studio studio) {
            return address.equals(studio.address);
        }
        return false;
    }
}
