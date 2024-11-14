package com.example.canvas;

import javax.swing.*;
import com.example.models.Room;
import com.example.services.FileManager;
import com.example.models.Furniture;
import com.example.services.SaveChange ;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;

public class Canvas<T> extends JComponent {
    public boolean customRoom = false;
    public ArrayList<Room> rooms = new ArrayList<>(100);
    public ArrayList<Furniture> furs = new ArrayList<>(100);
    public ArrayList<ArrayList<Room>> allRooms = new ArrayList<>(100);
    public Room currentRoom = null;
    public Furniture currentFurniture = null;
    public T fixture = null;
    public boolean defaultRoom = false;
    public boolean furniture = false;
    private int gridSize = 50;
    private boolean roomsLoaded = false;
    public int changeLog = 0;
    public Room roomContainingFurniture = null;
    private int clickX = -1, clickY = -1;
    public double iniX =  0;
    public double iniY =  0;
    public boolean relativeAlignment = false ;
    public Room room1 = null, room2= null;
    public Canvas(int gridSize) {
        this.gridSize = gridSize;

        addMouseMotionListener(new Drag());
        addMouseListener(new Drop());

        // Ensure the rooms list is initialized even if no file is loaded
        if (rooms == null) {
            rooms = new ArrayList<>();
        }
    }

    public void loadRoomsFromFile() {
        FileManager fileManager = new FileManager();
        try {
            String filePath = fileManager.openFileChooser();
            if (filePath != null) {
                rooms = fileManager.loadFile(filePath);
                ArrayList<Room> clone = new ArrayList<>();
                for (Room room : rooms) {
                    clone.add(Room.getCopy(room));
                }
                allRooms.add(clone);

                // Update state
                roomsLoaded = true;
                fileManager.resetUnsavedChanges(); // Reset unsaved changes after loading
                repaint(); // Repaint the component to reflect the loaded state
            } else {
                // If no file is selected, initialize rooms to an empty list
                System.out.println("No file selected. Initializing an empty rooms list.");
                rooms = new ArrayList<>();
                roomsLoaded = false;
            }
        } catch (ClassNotFoundException | IOException e) {
            System.err.println("Failed to load rooms from the file.");
            e.printStackTrace();
            rooms = new ArrayList<>(); // Fallback to an empty list in case of error
            roomsLoaded = false;
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

        if (!rooms.isEmpty()) {
            for (Room rect : rooms) {
                g.setColor(rect.color);
                Graphics2D g2d = (Graphics2D) g;
                g2d.fill(new Rectangle2D.Double(rect.x, rect.y, rect.width, rect.height));
            }
        }
        if(!furs.isEmpty()){
            for(Furniture rect : furs){
                g.setColor(rect.color);
                Graphics2D g2d = (Graphics2D) g;
                g2d.fill(new Rectangle2D.Double(rect.x, rect.y, rect.width, rect.height));
            }
        }
    }

    public void setSelectedObject(T fixture) {
        this.fixture = fixture;
    }
    //to implement
    public int getHeight(T fixture) { return 160;}
    public int getWidth(T fixture) { return 160;}
    public Color getColor(T fixture) {
        switch (fixture.toString()) {
            case "bedroom":
                return new Color(255, 0, 0, 90);
            case "bathroom":
                return new Color(0, 255, 0, 90);
            case "living":
                return new Color(0, 0, 255, 90);
            case "kit":
                return new Color(255, 255, 0, 90);
            default:
                return new Color(0, 255, 255, 90);
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
        @Override
        public void mousePressed(MouseEvent e) {
            if (!customRoom && !defaultRoom ) {
                System.out.println("140");
                currentRoom = find(e.getPoint());
                if (currentRoom != null) {
                    iniX = currentRoom.x;
                    iniY = currentRoom.y;

                }
            }
        }

        public void mouseClicked(MouseEvent e) {
            if(relativeAlignment){
                if(room1 == null){
                    room1 = find(e.getPoint());
                }else{
                    room2 = find(e.getPoint());
                    iniX = room2.x;
                    iniY = room2.y;
                }

            }
            else if(true){
            /*
            if(e.getClickCount() > 1 && currentRoom == null) {

                if(find(e.getPoint()) == null) {
                    System.out.println("Room not found");
                } else {
                    currentRoom = find(e.getPoint());
                }
            }
             */
            if(customRoom && currentRoom != null && SwingUtilities.isLeftMouseButton(e)) {
                System.out.println("You clicked on a custom room");
                SaveChange.saveChanges(Canvas.this);
                repaint();
            }

            if(SwingUtilities.isLeftMouseButton(e) && currentRoom == null ) {
                if(fixture == null) {
                    currentRoom = new Room(snapToGrid(e.getX()), snapToGrid(e.getY()), 0, 0,
                            new Color(25, 54, 68, 64));
                } else {
                    currentRoom = new Room(snapToGrid(e.getX()), snapToGrid(e.getY()), 160, 160, getColor(fixture));
                }
                rooms.add(currentRoom);
                repaint();
            }

            if(SwingUtilities.isLeftMouseButton(e) && currentRoom != null && e.getClickCount() == 1 && defaultRoom) {
                System.out.println("You clicked on a non-custom room");
                SaveChange.saveChanges(Canvas.this);
                repaint();
            }
        }

           }
           public void mouseReleased(MouseEvent e) {
            if ( !customRoom && !defaultRoom &&!relativeAlignment){

                currentRoom.x = snapToGrid(e.getX());
                currentRoom.y = snapToGrid(e.getY());
                System.out.println("Line 199");
                SaveChange.saveChanges(Canvas.this);
                if(currentRoom != null){
                    System.out.println("Line 215");
                    currentRoom.x = iniX ;
                    currentRoom.y = iniY;
                    com.example.services.SaveChange.saveChanges(Canvas.this);
                    repaint() ;
                }
                repaint();
            }
           }
    }

    private class Drag extends MouseMotionAdapter {
        public void mouseDragged(MouseEvent e){
            if(currentRoom != null && !customRoom && !relativeAlignment) {
                currentRoom.x = snapToGrid(e.getX());
                currentRoom.y = snapToGrid(e.getY());
                System.out.println(iniX + " " + iniY);
                repaint();
            }
        }

        public void mouseMoved(MouseEvent e) {

            if(currentRoom != null && !customRoom && !relativeAlignment) {
                currentRoom.x = snapToGrid(e.getX());
                currentRoom.y = snapToGrid(e.getY());
            }
            if(currentRoom != null && customRoom) {
                double w = snapToGrid(e.getX()) - currentRoom.x;
                double h = snapToGrid(e.getY()) - currentRoom.y;
                if(w >= 0) currentRoom.width = w;
                if(h >= 0) currentRoom.height = h;
                if(w < 0){
                    currentRoom.x = snapToGrid(e.getX());
                    currentRoom.width = -w;
                }
                if(h < 0){
                    currentRoom.y = snapToGrid(e.getY());
                    currentRoom.height = -h;
                }
            }
            repaint();
        }
    }

    public void resetCanvas() {
        FileManager fileManager = new FileManager(); // Create an instance of FileManager

        // Check for unsaved changes before resetting
        if (!rooms.isEmpty() && FileManager.hasUnsavedChanges()) {
            int option = JOptionPane.showConfirmDialog(
                    this,
                    "You have unsaved changes. Do you want to save them before creating a new canvas?",
                    "Unsaved Changes",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION) {
                // User canceled the operation; do not reset
                return;
            } else if (option == JOptionPane.YES_OPTION) {
                // User chose to save changes
                try {
                    fileManager.saveFile(rooms);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.err.println("Failed to save changes.");
                }
            }
            // If user chose NO, proceed to reset the canvas
        }

        // Proceed to reset the canvas
        rooms.clear();
        clickX = -1;
        clickY = -1;
        FileManager.resetUnsavedChanges(); // Reset unsaved changes after clearing
        repaint();
    }
}