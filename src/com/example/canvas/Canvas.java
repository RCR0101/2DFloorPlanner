package com.example.canvas;

import javax.swing.*;

import com.example.models.Opening;
import com.example.models.Room;
import com.example.services.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
            for (Room room : rooms) {
                // Fill the room with its color
                g2d.setColor(room.color);
                g2d.fill(new Rectangle2D.Double(room.x, room.y, room.width, room.height));
                drawRoomBorderWithOpenings(g2d, room);
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
            Point2D point = e.getPoint();
            if(SwingUtilities.isLeftMouseButton(e) && fixture != null && (fixture.toString().equals("door") || fixture.toString().equals("window"))) {
                Room room = find(point);
                if (room != null) {
                    Room.SidePosition sidePosition = room.getSideAtPoint(point);
                    if (sidePosition != null) {
                        // Determine the opening type
                        Opening.Type openingType = fixture.toString().equals("door") ? Opening.Type.DOOR : Opening.Type.WINDOW;
                        double openingLength = 50.0;
                        double snappedPosition = snapToGrid((int) sidePosition.position);

                        // Add the opening to the room
                        Opening opening = new Opening(openingType, sidePosition.side, snappedPosition, openingLength);
                        room.addOpening(opening);

                        // Repaint to reflect changes
                        repaint();
                    }
                }
            }
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

            if(SwingUtilities.isLeftMouseButton(e) && currentRoom == null && !(fixture.toString().equals("door") || fixture.toString().equals("window"))) {
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

    private void drawRoomBorderWithOpenings(Graphics2D g2d, Room room) {
        for (Opening.Side side : Opening.Side.values()) {
            drawSideWithOpenings(g2d, room, side);
        }
    }

    private void drawSideWithOpenings(Graphics2D g2d, Room room, Opening.Side side) {
        double x1, y1, x2, y2;
        double sideLength;

        // Determine side coordinates and length
        switch (side) {
            case TOP:
                x1 = room.x;
                y1 = room.y;
                x2 = room.x + room.width;
                y2 = room.y;
                sideLength = room.width;
                break;
            case BOTTOM:
                x1 = room.x;
                y1 = room.y + room.height;
                x2 = room.x + room.width;
                y2 = room.y + room.height;
                sideLength = room.width;
                break;
            case LEFT:
                x1 = room.x;
                y1 = room.y;
                x2 = room.x;
                y2 = room.y + room.height;
                sideLength = room.height;
                break;
            case RIGHT:
                x1 = room.x + room.width;
                y1 = room.y;
                x2 = room.x + room.width;
                y2 = room.y + room.height;
                sideLength = room.height;
                break;
            default:
                return;
        }
        List<Opening> sideOpenings = room.openings.stream()
                .filter(o -> o.side == side)
                .sorted(Comparator.comparingDouble(o -> o.position))
                .toList();

        // Draw the side with adjustments for openings
        double currentPosition = 0.0;
        for (Opening opening : sideOpenings) {
            if (currentPosition < opening.position) {
                drawLineSegment(g2d, room, side, currentPosition, opening.position, "SOLID");
            }
            if (opening.type == Opening.Type.WINDOW) {
                drawLineSegment(g2d, room, side, opening.position, opening.position + opening.length, "DOTTED");
            }
            currentPosition = opening.position + opening.length;
        }
        if (currentPosition < sideLength) {
            drawLineSegment(g2d, room, side, currentPosition, sideLength, "SOLID");
        }
    }

    private void drawLineSegment(Graphics2D g2d, Room room, Opening.Side side, double startPos, double endPos, String style) {
        double xStart = 0, yStart = 0, xEnd = 0, yEnd = 0;
        switch (side) {
            case TOP:
                xStart = room.x + startPos;
                yStart = room.y;
                xEnd = room.x + endPos;
                yEnd = room.y;
                break;
            case BOTTOM:
                xStart = room.x + startPos;
                yStart = room.y + room.height;
                xEnd = room.x + endPos;
                yEnd = room.y + room.height;
                break;
            case LEFT:
                xStart = room.x;
                yStart = room.y + startPos;
                xEnd = room.x;
                yEnd = room.y + endPos;
                break;
            case RIGHT:
                xStart = room.x + room.width;
                yStart = room.y + startPos;
                xEnd = room.x + room.width;
                yEnd = room.y + endPos;
                break;
        }
        if ("SOLID".equals(style)) {
            g2d.setStroke(new BasicStroke(2));
        } else if ("DOTTED".equals(style)) {
            float[] dashPattern = {5, 5};
            g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
                    10, dashPattern, 0));
        }

        // Draw the line segment
        g2d.drawLine((int) xStart, (int) yStart, (int) xEnd, (int) yEnd);
    }
}