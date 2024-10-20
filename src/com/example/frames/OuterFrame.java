package com.example.frames;

import java.awt.*;
import javax.swing.*;

import com.example.panels.*;
import com.example.services.FileManager;
import com.example.canvas.Canvas;
import com.example.menubar.*;

@SuppressWarnings("rawtypes")
public class OuterFrame extends JFrame {
    static public Canvas canvas;
    private FileManager fileManager;
    private CustomMenuBarLogic customMenuLogic;
    private CommandPanelLogic commandPanelLogic;

    public OuterFrame() {
        this.fileManager = new FileManager();
        this.customMenuLogic = new CustomMenuBarLogic(this, this.fileManager);
        canvas = new Canvas(20);
        this.commandPanelLogic = new CommandPanelLogic(canvas);
        CommandPanelUI cmdPanel = new CommandPanelUI(this.commandPanelLogic);
        CustomMenuBarUI menuBar = new CustomMenuBarUI(this, this.customMenuLogic);

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