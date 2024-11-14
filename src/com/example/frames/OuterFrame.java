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
import com.formdev.flatlaf.FlatDarkLaf;

@SuppressWarnings("rawtypes")
public class OuterFrame {
    static public Canvas canvas;
    private FileManager fileManager;

    public static void main(String[] args) {
        FlatDarkLaf.setup();
        OuterFrame outerFrame = new OuterFrame();
        outerFrame.fileManager = new FileManager(); // Initialize the FileManager
        canvas = new Canvas(20);
        CommandPanel cmdPanel = new CommandPanel(canvas);
        CustomMenuBar menuBar = new CustomMenuBar(outerFrame, outerFrame.fileManager);
        JFrame frame = new JFrame("2D Floor Planner");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(outerFrame.canvas, BorderLayout.CENTER);
        frame.add(cmdPanel.createCommandPanel(), BorderLayout.EAST);
        frame.add(menuBar.createMenuBar(), BorderLayout.NORTH);
        frame.addWindowListener(new ClosingWindowListener(outerFrame));
        frame.setVisible(true);
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

    public FileManager getFileManager() {
        return fileManager;
    }
}