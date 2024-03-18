package ru.itmo.se.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public class Studio {

    private String address;

    @Override
    public String toString() {
        return "Address: " + address;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Studio studio) {
            return address.equals(studio.address);
        }
        return false;
    }
}
