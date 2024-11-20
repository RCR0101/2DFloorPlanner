package com.example.models;

import java.io.Serial;
import java.io.Serializable;

public class Opening implements Serializable {
    @Serial
    private static final long serialVersionUID = 7894561230987654321L;
    public enum Type { DOOR, WINDOW }
    public enum Side { TOP, BOTTOM, LEFT, RIGHT }

    public Type type;
    public Side side;
    public double position; // Distance along the side from the starting point
    public double length;   // Length of the opening

    public Opening(Type type, Side side, double position, double length) {
        this.type = type;
        this.side = side;
        this.position = position;
        this.length = length;
    }
}