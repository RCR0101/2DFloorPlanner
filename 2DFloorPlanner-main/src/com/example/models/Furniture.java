package models;

import java.awt.*;

public class Furniture {
    public int x , y  , height , width  ;
    public Color color;
    public Furniture(int x, int y, int width, int height, Color color ) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = Color.GREEN;
    }

}
