package canvas;

import javax.swing.*;

import frames.OuterFrame;
import models.Room;
import services.RetrieveFile;
import services.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.*;
import java.util.ArrayList;

public class Canvas<T> extends JComponent {
    public ArrayList<Room> rooms = new ArrayList<>(100);
    public ArrayList<ArrayList<Room>> allRooms = new ArrayList<>(100);
    public Room currentRoom = null;
    public T fixture = null;
    private int clickX = -1, clickY = -1;
    private int gridSize = 50;
    private boolean roomsLoaded = false;
    private boolean isRightClick = false;
    public int changeLog = 0;
    public Canvas(int gridSize) {
        this.gridSize = gridSize;

        loadRoomsFromFile();
        addMouseMotionListener(new MouseMotionHandler());
        addMouseListener(new MouseHandler());
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (fixture != null) {
                    clickX = snapToGrid(e.getX());
                    clickY = snapToGrid(e.getY());
                    isRightClick = SwingUtilities.isRightMouseButton(e) ;
                    repaint();
                }
            }
        });
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
        } catch (FileNotFoundException e) {
            allRooms.add(new ArrayList<Room>());
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
        if (clickX != -1 && clickY != -1 && fixture != null && !isRightClick) {
            Room newRoom = new Room(clickX, clickY, 160, 160, getColor(fixture));
            rooms.add(newRoom); // Add new room to the list

            g.setColor(newRoom.color);
            Graphics2D g2d = (Graphics2D) g;
            g2d.fill(new Rectangle2D.Double(newRoom.x, newRoom.y, newRoom.width, newRoom.height));
            g.setColor(Color.BLACK);
            g.drawString(fixture.toString(), clickX, clickY - 10);




            clickX = -1 ;
            clickY = -1 ;
            fixture = null ;

            ++changeLog;
            ArrayList<Room> clone= new ArrayList<>();
            for(Room room : rooms){
                clone.add(Room.getCopy(room));
            }
            if(changeLog < allRooms.size()) {

                allRooms.set(changeLog, clone);
                System.out.println("Line 101 " + changeLog);
                System.out.println();
            }
            else {
                System.out.println("Line 103 " + changeLog);
                allRooms.add(clone);
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
    private class MouseHandler extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            if(e.getClickCount() > 1 && currentRoom == null) {
                if(find(e.getPoint()) == null) {
                    System.out.println("Room not found");
                }else{
                    currentRoom = find(e.getPoint());
                }
            }
            if(SwingUtilities.isRightMouseButton(e)) {
                currentRoom.x = snapToGrid(e.getX());
                currentRoom.y = snapToGrid(e.getY()) ;
                ++changeLog;
                ArrayList<Room> clone= new ArrayList<>();
                for(Room room : rooms){
                    clone.add(Room.getCopy(room));
                }
                if(changeLog < allRooms.size()) {

                    allRooms.set(changeLog, clone);
                    System.out.println("Line 101 " + changeLog);
                    System.out.println();
                }
                else {
                    System.out.println("Line 103 " + changeLog);
                    allRooms.add(clone);
                }
                currentRoom = null;
            }



        }
    }
    private class MouseMotionHandler extends MouseMotionAdapter {
        public void mouseMoved(MouseEvent e) {
            if(currentRoom != null){
                currentRoom.x = snapToGrid(e.getX());
                currentRoom.y = snapToGrid(e.getY());
                repaint();
            }
            if(currentRoom == null){
                repaint();
            }
        }
    }

}
