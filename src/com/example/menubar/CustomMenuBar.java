package com.example.menubar;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.*;
import java.util.ArrayList;
import com.example.canvas.Canvas;
import com.example.models.Room;
import com.example.services.FileManager;
import com.example.frames.OuterFrame;
import com.example.services.Util;

import static java.lang.Math.max;

public class CustomMenuBar {
    public static int maxChangeLog = 0;
    private OuterFrame frame;
    private FileManager fileManager;

    public CustomMenuBar(OuterFrame frame, FileManager fileManager) {
        this.frame = frame;
        this.fileManager = fileManager;
    }

    public Component createMenuBar() {
        JMenuBar mb = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Edit");

        // Load icons
        ImageIcon newIcon = Util.resizeImage(new ImageIcon(Util.getAbsolutePath("assets/icons/new.png")));
        ImageIcon openIcon = Util.resizeImage(new ImageIcon(Util.getAbsolutePath("assets/icons/open.png")));
        ImageIcon saveIcon = Util.resizeImage(new ImageIcon(Util.getAbsolutePath("assets/icons/save.png")));
        ImageIcon redoIcon = Util.resizeImage(new ImageIcon(Util.getAbsolutePath("assets/icons/redo.png")));
        ImageIcon undoIcon = Util.resizeImage(new ImageIcon(Util.getAbsolutePath("assets/icons/undo.png")));

        // Create menu items with icons
        JMenuItem newItem = new JMenuItem("New", newIcon);
        JMenuItem saveItem = new JMenuItem("Save", saveIcon);
        JMenuItem openItem = new JMenuItem("Open", openIcon);
        JMenuItem undo = new JMenuItem("Undo", undoIcon);
        JMenuItem redo = new JMenuItem("Redo", redoIcon);

        // Setup file menu actions
        setupNewAction(newItem);
        setupSaveAction(saveItem);
        setupOpenAction(openItem);

        // Setup undo/redo actions
        UndoActionListener undoActionListener = new UndoActionListener(frame);
        RedoActionListener redoActionListener = new RedoActionListener(frame);
        undo.addActionListener(undoActionListener);
        redo.addActionListener(redoActionListener);
        setupUndoRedoShortcuts(mb, undoActionListener, redoActionListener);

        // Add items to file menu
        file.add(newItem);
        file.add(saveItem);
        file.add(openItem);
        // Add items to edit menu
        edit.add(undo);
        edit.add(redo);

        // Add menus to the menu bar
        mb.add(file);
        mb.add(edit);

        return mb;
    }

    private void setupNewAction(JMenuItem newItem) {
        newItem.addActionListener(_ -> {
            try {
                String tempFilePath = fileManager.createTempFile();
                OuterFrame.canvas.resetCanvas();
                System.out.println("New file created at: " + tempFilePath);
            } catch (IOException eIO) {
                System.err.println("There was an IOException while creating a new file.");
            }
        });
    }

    private void setupSaveAction(JMenuItem saveItem) {
        saveItem.addActionListener(_ -> {
            try {
                fileManager.saveFile(OuterFrame.canvas.rooms);
                System.out.println("File saved successfully.");
            } catch (IOException eIO) {
                System.err.println("There was an IOException while saving the file.");
            }
        });
    }

    private void setupOpenAction(JMenuItem openItem) {
        openItem.addActionListener(_ -> {
            try {
                OuterFrame.canvas.loadRoomsFromFile();
                System.out.println("File opened successfully.");
            } catch (Exception ex) {
                System.err.println("Failed to open file: " + ex.getMessage());
            }
        });
    }

    private void setupUndoRedoShortcuts(JMenuBar mb, UndoActionListener undoActionListener, RedoActionListener redoActionListener) {
        InputMap inputMap = mb.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = mb.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("ctrl Z"), "undo");
        inputMap.put(KeyStroke.getKeyStroke("ctrl Y"), "redo");
        actionMap.put("undo", undoActionListener);
        actionMap.put("redo", redoActionListener);
    }
}

class UndoActionListener extends AbstractAction {
    private OuterFrame frame;
    private Canvas canvas;

    UndoActionListener(OuterFrame frame) {
        this.frame = frame;
        this.canvas = OuterFrame.canvas;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CustomMenuBar.maxChangeLog = max(CustomMenuBar.maxChangeLog, canvas.changeLog);
        if (canvas.changeLog > 0) {
            canvas.rooms.clear();
            for (Object room : (ArrayList) canvas.allRooms.get(--canvas.changeLog)) {
                canvas.rooms.add(Room.getCopy((Room) room));
            }
            System.out.println("Undo: reverted to changeLog " + canvas.changeLog);
            canvas.fixture = null;
            canvas.repaint();
        }
    }
}

class RedoActionListener extends AbstractAction {
    private OuterFrame frame;
    private Canvas canvas;

    RedoActionListener(OuterFrame frame) {
        this.frame = frame;
        this.canvas = OuterFrame.canvas;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (canvas.changeLog < CustomMenuBar.maxChangeLog) {
            canvas.rooms.clear();
            for (Object room : (ArrayList) canvas.allRooms.get(++canvas.changeLog)) {
                canvas.rooms.add(Room.getCopy((Room) room));
            }
            System.out.println("Redo: moved forward to changeLog " + canvas.changeLog);
            canvas.fixture = null;
            canvas.repaint();
        }
    }
}