package com.example.menubar;

import javax.swing.*;
import java.io.IOException;
import com.example.frames.OuterFrame;
import com.example.services.FileManager;

public class CustomMenuBarLogic {
    private OuterFrame frame;
    private FileManager fileManager;

    public static int maxChangeLog = 0;

    public CustomMenuBarLogic(OuterFrame frame, FileManager fileManager) {
        this.frame = frame;
        this.fileManager = fileManager;
    }

    public void setupNewAction(JMenuItem newItem) {
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

    public void setupSaveAction(JMenuItem saveItem) {
        saveItem.addActionListener(_ -> {
            try {
                fileManager.saveFile(OuterFrame.canvas.rooms);
                System.out.println("File saved successfully.");
            } catch (IOException eIO) {
                System.err.println("There was an IOException while saving the file.");
            }
        });
    }

    public void setupOpenAction(JMenuItem openItem) {
        openItem.addActionListener(_ -> {
            try {
                OuterFrame.canvas.loadRoomsFromFile();
                System.out.println("File opened successfully.");
            } catch (Exception ex) {
                System.err.println("Failed to open file: " + ex.getMessage());
            }
        });
    }

    public void setupUndoRedoShortcuts(JMenuBar mb, UndoActionListener undoActionListener, RedoActionListener redoActionListener) {
        InputMap inputMap = mb.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = mb.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("ctrl Z"), "undo");
        inputMap.put(KeyStroke.getKeyStroke("ctrl Y"), "redo");
        actionMap.put("undo", undoActionListener);
        actionMap.put("redo", redoActionListener);
    }
}