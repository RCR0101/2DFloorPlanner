package com.example.models;

import java.awt.Color;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Room implements Serializable {
    @Serial
    private static final long serialVersionUID = 6697947686724816858L;

    public int x;
    public int y;
    public int width;
    public int height;
    public Color color;

    public Room(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return x == room.x &&
               y == room.y &&
               width == room.width &&
               height == room.height &&
               Objects.equals(color, room.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, width, height, color);
    }
}
