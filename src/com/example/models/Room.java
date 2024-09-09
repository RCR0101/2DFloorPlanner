package models;

import java.awt.Color;
import java.io.Serializable;

public class Room implements Serializable {
    public int x;
    public int y;
    public int width;
    public int height;
    public Color color;
    public Room(int x, int y, int width, int height, Color color){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }
}
