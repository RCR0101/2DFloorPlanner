package com.example.canvas;

import javax.swing.*;
import com.example.models.Room;
import com.example.services.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;

public class Canvas<T> extends JComponent {
    public boolean customRoom = false;
    public ArrayList<Room> rooms = new ArrayList<>(100);
    public ArrayList<ArrayList<Room>> allRooms = new ArrayList<>(100);
    public Room currentRoom = null;
    public T fixture = null;
    public boolean defaultRoom = false;
    private final int gridSize;
    public int changeLog = 0;

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
        boolean roomsLoaded = false;
        try {
            String filePath = fileManager.openFileChooser();
            if (filePath != null) {
                rooms = fileManager.loadFile(filePath);
                ArrayList<Room> clone = new ArrayList<>();
                for (Room room : rooms) {
                    clone.add(Room.getCopy(room));
                }
                allRooms.add(clone);

                roomsLoaded = true;
                FileManager.resetUnsavedChanges();
                repaint();
            } else {
                // If no file is selected, initialize rooms to an empty list
                System.out.println("No file selected. Initializing an empty rooms list.");
                rooms = new ArrayList<>();
                roomsLoaded = false;
            }
        } catch (ClassNotFoundException | IOException e) {
            System.err.println("Failed to load rooms from the file.");
            System.out.println("Error: " + e.getMessage());
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

        // Enable anti-aliasing for smoother graphics
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the grid with a subtle color
        g2d.setColor(new Color(200, 200, 200));  // Lighter gray for the grid
        for (int i = 0; i < getWidth(); i += gridSize) {
            g2d.drawLine(i, 0, i, getHeight());
        }
        for (int j = 0; j < getHeight(); j += gridSize) {
            g2d.drawLine(0, j, getWidth(), j);
        }

        // Draw rooms with borders
        if (!rooms.isEmpty()) {
            for (Room rect : rooms) {
                // Fill the room with its color
                g2d.setColor(rect.color);
                g2d.fill(new Rectangle2D.Double(rect.x, rect.y, rect.width, rect.height));

                // Draw the border around the room
                g2d.setColor(Color.black.darker());
                g2d.setStroke(new BasicStroke(2));  // Thicker border
                g2d.draw(new Rectangle2D.Double(rect.x, rect.y, rect.width, rect.height));
            }
        }
    }

    public void setSelectedObject(T fixture) {
        this.fixture = fixture;
    }

    public Color getColor(T fixture) {
        return switch (fixture.toString()) {
            case "bedroom" -> new Color(255, 0, 0, 90);
            case "bathroom" -> new Color(0, 255, 0, 90);
            case "living" -> new Color(0, 0, 255, 90);
            case "kit" -> new Color(255, 255, 0, 90);
            default -> new Color(0, 255, 255, 90);
        };
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
                } else {
                    currentRoom = find(e.getPoint());
                }
            }

            if(customRoom && currentRoom != null && SwingUtilities.isLeftMouseButton(e)) {
                System.out.println("You clicked on a custom room");
                SaveChange.saveChanges(Canvas.this);
                repaint();
            }

            if(SwingUtilities.isLeftMouseButton(e) && currentRoom == null) {
                if(fixture == null) {
                    currentRoom = new Room(snapToGrid(e.getX()), snapToGrid(e.getY()), 0, 0,
                            new Color(25, 54, 68, 64));
                } else {
                    currentRoom = new Room(snapToGrid(e.getX()), snapToGrid(e.getY()), 160, 160, getColor(fixture));
                }
                rooms.add(currentRoom);
                repaint();
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
                    System.err.println("Error: " + e.getMessage());
                    System.err.println("Failed to save changes.");
                }
            }
            // If user chose NO, proceed to reset the canvas
        }

        rooms.clear();
        int clickX = -1;
        int clickY = -1;
        FileManager.resetUnsavedChanges(); // Reset unsaved changes after clearing
        repaint();
    }
}