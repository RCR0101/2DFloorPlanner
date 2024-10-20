package com.example.frames;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

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

    static class ClosingWindowListener extends WindowAdapter {
        private OuterFrame outerFrame;

        public ClosingWindowListener(OuterFrame outerFrame) {
            this.outerFrame = outerFrame;
        }

        @Override
        public void windowClosing(WindowEvent e) {
            String str = System.getProperty("user.dir") + File.separator + "assets" + File.separator + "images" + File.separator + "logo.jpg";
            ImageIcon logo = new ImageIcon(str);
            Image resizedImage = logo.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
            ImageIcon resizedIcon = new ImageIcon(resizedImage);
            int response = JOptionPane.showConfirmDialog(
                    null,
                    "Do you want to save unsaved changes?",
                    "Confirm Exit",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    resizedIcon);

            if (response == JOptionPane.YES_OPTION) {
                System.out.println("Saving changes...");
                try {
                    outerFrame.fileManager.saveFile(outerFrame.canvas.rooms);
                    System.exit(0);
                } catch (IOException e1) {
                    System.err.println("There was an IOException: " + e1);
                    e1.printStackTrace();
                }
            } else if (response == JOptionPane.NO_OPTION) {
                System.exit(0);
            }
        }
    }
}