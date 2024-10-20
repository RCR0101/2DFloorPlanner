package com.example.frames;

import java.awt.*;
import javax.swing.*;

import com.example.panels.CommandPanel;
import com.example.services.FileManager;
import com.example.canvas.Canvas;
import com.example.menubar.CustomMenuBar;

@SuppressWarnings("rawtypes")
public class OuterFrame extends JFrame {
    static public Canvas canvas;
    private FileManager fileManager;

    public OuterFrame() {
        this.fileManager = new FileManager(); // Initialize the FileManager
        canvas = new Canvas(20);
        CommandPanel cmdPanel = new CommandPanel(canvas);
        CustomMenuBar menuBar = new CustomMenuBar(this, this.fileManager);

        setTitle("2D Floor Planner");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());
        add(canvas, BorderLayout.CENTER);
        add(cmdPanel.createCommandPanel(), BorderLayout.EAST);
        add(menuBar.createMenuBar(), BorderLayout.NORTH);
        addWindowListener(new ClosingWindowListener(this));
    }

    public FileManager getFileManager() {
        return fileManager;
    }
}