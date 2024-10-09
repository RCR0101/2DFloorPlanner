package canvas;
import javax.swing.*;
import models.Room;
import services.RetrieveFile;
import services.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Canvas<T> extends JComponent {
    public boolean customRoom = false;
    public ArrayList<Room> rooms = new ArrayList<>(100);
    public ArrayList<ArrayList<Room>> allRooms = new ArrayList<>(100);
    public Room currentRoom = null;
    public T fixture = null;
    public boolean defaultRoom = false;
    private int gridSize = 50;
    private boolean roomsLoaded = false;
    public int changeLog = 0;

    public Canvas(int gridSize) {
        this.gridSize = gridSize;

        loadRoomsFromFile();
        addMouseMotionListener(new Drag());
        addMouseListener(new Drop());

    }

    private void loadRoomsFromFile() {
        try {
            rooms = new RetrieveFile().getFile();
            ArrayList<Room> clone = new ArrayList<>();
            for(Room room : rooms) {
                clone.add(Room.getCopy(room));
            }
            allRooms.add(clone);
            roomsLoaded = true;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    public int snapToGrid(int value) {
        return (value / gridSize) * gridSize;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.GRAY);

        for (int i = 0; i < getWidth(); i += gridSize) {
            g.drawLine(i, 0, i, getHeight());
        }
        for (int j = 0; j < getHeight(); j += gridSize) {
            g.drawLine(0, j, getWidth(), j);
        }

        if (roomsLoaded) {
            for (Room rect : rooms) {
                g.setColor(rect.color);
                Graphics2D g2d = (Graphics2D) g;
                g2d.fill(new Rectangle2D.Double(rect.x, rect.y, rect.width, rect.height));
            }
        }

    }

    public void setSelectedObject(T fixture) {
        this.fixture = fixture;
    }

    public Color getColor(T fixture) {
        switch (fixture.toString()) {
            case "bedroom":
                return new Color(255, 0, 0, 64);
            case "bathroom":
                return new Color(0, 255, 0, 64);
            case "living":
                return new Color(0, 0, 255, 64);
            case "kit":
                return new Color(255, 255, 0, 128);
            default:
                return new Color(0, 255, 255, 64);
        }
    }

    public Room find(Point2D point){
        for(Room room : rooms){
            System.out.println("I am trying");
            if(room.contains(point)) return room ;
        }
        return null;
    }
    public ArrayList<Room> getRoomList() {
        return rooms;
    }
    private class Drop extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            if(e.getClickCount() > 1 && currentRoom == null) {
                if(find(e.getPoint()) == null) {
                    System.out.println("Room not found");
                }else{
                    currentRoom = find(e.getPoint());
                }
            }

            if(customRoom && currentRoom != null && SwingUtilities.isLeftMouseButton(e)) {
                System.out.println("You clicked on a custom room");
                SaveChange.saveChanges(Canvas.this);
                repaint();
            }


             if(SwingUtilities.isLeftMouseButton(e) && currentRoom == null) {
                if(fixture == null) { currentRoom = new Room(snapToGrid(e.getX()),snapToGrid(e.getY()),0,0,
                        new Color(25, 54, 68, 64));
                }
                    else {
                    currentRoom = new Room(snapToGrid(e.getX()), snapToGrid(e.getY()), 160, 160, getColor(fixture));
                }
                rooms.add(currentRoom);

            }

             if(SwingUtilities.isLeftMouseButton(e) && currentRoom != null && e.getClickCount() == 1 && !customRoom) {
                System.out.println("You clicked on a non-custom room");
                SaveChange.saveChanges(Canvas.this);
                repaint();
            }

        }
    }

    private class Drag extends MouseMotionAdapter {
        public void mouseMoved(MouseEvent e) {
            if(currentRoom != null && !customRoom) {
                currentRoom.x = snapToGrid(e.getX());
                currentRoom.y = snapToGrid(e.getY());

            }
            if(currentRoom != null && customRoom) {
                System.out.println("L190");
                double w =   snapToGrid(e.getX()) - currentRoom.x;
                double h = snapToGrid(e.getY()) - currentRoom.y;
                if(w >= 0) currentRoom.width =  w;
                if(h >= 0) currentRoom.height = h;
                //needs work
                if(w < 0){
                    currentRoom.x = snapToGrid(e.getX());
                    currentRoom.width = -w ;
                }
                if(h < 0){
                    currentRoom.y = snapToGrid(e.getY());
                    currentRoom.height = -h ;
                }


            }
            if(currentRoom == null){
                //System.out.println("current room is null");
            }
            repaint();
        }
    }



}
