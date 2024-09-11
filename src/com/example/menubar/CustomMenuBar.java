package menubar;

import java.awt.Component;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import canvas.Canvas;
import models.Room;
import services.NewFile;
import services.SaveFile;

public class CustomMenuBar {
    ArrayList<Room> rooms;
    Canvas<?> canvas;
    String filePath;
    public CustomMenuBar(ArrayList<Room> rooms, Canvas<?> canvas) {
        this.rooms = rooms;
        this.canvas = canvas;
    }

    public Component createMenuBar() {
        SaveFile sFile = new SaveFile(rooms);
        NewFile nFile = new NewFile();
        JMenuBar mb = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Edit");
        JMenuItem newItem = new JMenuItem("New");
        newItem.addActionListener(e -> {
            try{
            filePath = nFile.createTempFile();
            canvas.resetCanvas();
            }catch(IOException eIO){
                System.err.println("There was an IOException.");
            }
        });
        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(e -> {
            try {
                sFile.saveFile();
            } catch (IOException eIO) {
                System.err.println("There was an IOException.");
            }
        });
        JMenuItem openItem = new JMenuItem("Open");
        openItem.addActionListener(e -> canvas.loadRoomsFromFile());
        JMenuItem undo = new JMenuItem("Undo");
        undo.addActionListener(e -> {
        });
        JMenuItem redo = new JMenuItem("Redo");
        redo.addActionListener(e -> {
        });
        file.add(newItem);
        file.add(saveItem);
        file.add(openItem);
        edit.add(undo);
        edit.add(redo);
        mb.add(file);
        mb.add(edit);
        return mb;
    }
}
