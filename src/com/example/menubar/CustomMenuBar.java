package menubar;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import canvas.Canvas;
import models.Room;

import services.SaveFile;

public class CustomMenuBar {
    ArrayList<Room> rooms;
    Canvas<?> canvas;
    public CustomMenuBar(ArrayList<Room> rooms, Canvas<?> canvas) {
        this.rooms = rooms;
        this.canvas = canvas;
    }

    public Component createMenuBar() {
        SaveFile sFile = new SaveFile(rooms);
        JMenuBar mb = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Edit");
        JMenuItem newItem = new JMenuItem("New");
        newItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    sFile.saveFile();
                } catch (IOException eIO) {
                    eIO.printStackTrace();
                }
            }
        });
        JMenuItem openItem = new JMenuItem("Open");
        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.loadRoomsFromFile();
            }
        });
        JMenuItem undo = new JMenuItem("Undo");
        undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        JMenuItem redo = new JMenuItem("Redo");
        redo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
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
