package com.example.models;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.Objects;

public class Room implements Serializable {
    public double x;
    public double y;
    public double width;
    public double height;
    public Color color;
    public Room(int x, int y, int width, int height, Color color){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }
    public Room(double x, double y, double width, double height, Color color){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }
    public Room(){}
    public static  Room getCopy(Room room){
        return new Room(room.x,room.y,room.width,room.height,room.color);
    }
    public Color getColor(){
        return color;
    }
    public boolean contains(Point2D p){

        return p.getX() > x && p.getX() < x + width && p.getY() > y && p.getY() < y + height;
    }
    public boolean intersects(Room room){
        Rectangle2D rect = new Rectangle2D.Double(x,y,width,height);
        Rectangle2D rect2 = new Rectangle2D.Double(room.x,room.y,room.width,room.height);
        return rect.intersects(rect2);
    }
    @Override
    public boolean equals(Object o){
        if(getClass() != o.getClass()){
            return false ;
        }
        if(o == null) return false ;
        Room room = (Room) o;
        return room.x == x && room.y == y && room.width == width && room.height == height && room.color == color;
    }
    @Override
    public int hashCode(){
            return Objects.hash(x, y,width,height,color);
    }
}
