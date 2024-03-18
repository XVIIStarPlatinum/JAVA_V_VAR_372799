package ru.itmo.se.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Coordinates {

    private float x;

    private float y;

    public float getX() {
        try {
            return x;
        } catch (NullPointerException e) {
            return Float.MIN_VALUE;
        }
    }

    @Override
    public String toString() {
        return "X = " + x + "; " + "Y = " + y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Coordinates coordinates) {
            return x == coordinates.getX() && y == coordinates.getY();
        }
        return false;
    }
}
