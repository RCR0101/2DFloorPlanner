package canvas;

import javax.swing.JComponent;

import models.Room;
import services.RetrieveFile;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Color;

public class Canvas<T> extends JComponent {
    public ArrayList<Room> rooms = new ArrayList<>(100);
    T fixture = null;
    private int clickX = -1, clickY = -1;
    private int gridSize = 50;
    private boolean roomsLoaded = false;

    public Canvas(int gridSize) {
        this.gridSize = gridSize;

        loadRoomsFromFile();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (fixture != null) {
                    clickX = snapToGrid(e.getX());
                    clickY = snapToGrid(e.getY());
                    repaint();
                }
            }
        });
    }

    private void loadRoomsFromFile() {
        try {
            rooms = new RetrieveFile().getFile();
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
                g.fillRect(rect.x, rect.y, rect.width, rect.height);
            }
        }

        if (clickX != -1 && clickY != -1 && fixture != null) {
            Room newRoom = new Room(clickX, clickY, 160, 160, getColor(fixture));
            rooms.add(newRoom); // Add new room to the list
            g.setColor(newRoom.color);
            g.fillRect(newRoom.x, newRoom.y, newRoom.width, newRoom.height);
            g.setColor(Color.BLACK);
            g.drawString(fixture.toString(), clickX, clickY - 10);
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

    public ArrayList<Room> getRoomList() {
        return rooms;
    }
}
