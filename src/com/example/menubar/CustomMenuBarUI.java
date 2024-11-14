package com.example.menubar;

import javax.swing.*;
import java.awt.*;

import com.example.frames.OuterFrame;
import com.example.services.Util;

public class CustomMenuBarUI {
    private OuterFrame frame;
    private CustomMenuBarLogic menuBarLogic;

    public CustomMenuBarUI(OuterFrame frame, CustomMenuBarLogic menuBarLogic) {
        this.frame = frame;
        this.menuBarLogic = menuBarLogic;
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
        menuBarLogic.setupNewAction(newItem);
        menuBarLogic.setupSaveAction(saveItem);
        menuBarLogic.setupOpenAction(openItem);

        // Setup undo/redo actions
        UndoActionListener undoActionListener = new UndoActionListener(frame);
        RedoActionListener redoActionListener = new RedoActionListener(frame);
        undo.addActionListener(undoActionListener);
        redo.addActionListener(redoActionListener);
        menuBarLogic.setupUndoRedoShortcuts(mb, undoActionListener, redoActionListener);

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
}