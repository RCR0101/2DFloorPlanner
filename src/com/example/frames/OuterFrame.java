package com.example.frames;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.example.panels.CommandPanel;
import com.example.services.SaveFile;
import com.example.canvas.Canvas;
import com.example.menubar.CustomMenuBar;

@SuppressWarnings("rawtypes")
public class OuterFrame implements WindowListener {
    public Canvas<?> canvas;

    public static void main(String[] args) {
        OuterFrame outerFrame = new OuterFrame();
        outerFrame.canvas = new Canvas(40);
        CommandPanel cmdPanel = new CommandPanel(outerFrame.canvas);
        CustomMenuBar menuBar = new CustomMenuBar(outerFrame.canvas.getRoomList(), outerFrame.canvas);
        JFrame frame = new JFrame("2D Floor Planner");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(outerFrame.canvas, BorderLayout.CENTER);
        frame.add(cmdPanel.createCommandPanel(), BorderLayout.EAST);
        frame.add(menuBar.createMenuBar(), BorderLayout.NORTH);
        frame.addWindowListener(outerFrame);
        frame.setVisible(true);
    }

    @Override
    public void windowClosing(WindowEvent e) {
        ImageIcon logo = new ImageIcon(
                "/Users/adalmia/Documents/projects/java_dev/2DFloorPlanner/assets/images/logo.jpg");
        Image resizedImage = logo.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        if (SaveFile.hasUnsavedChanges() && !canvas.getRoomList().isEmpty()) {
            int resp = getResponse(resizedIcon);
            if(resp!=-1)
                System.exit(0);
        } else {
            System.exit(0);
        }
    }

    private int getResponse(ImageIcon resizedIcon) {
        int response = JOptionPane.showConfirmDialog(
                null,
                "Do you want to save unsaved changes?",
                "Confirm Exit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                resizedIcon);

        if (response == JOptionPane.YES_OPTION) {
            SaveFile sFile = new SaveFile(canvas.getRoomList());
            try {
                sFile.saveFile();
            } catch (IOException e1) {
                System.err.println("There was an IOException: " + e1);
            }
        } else if (response == JOptionPane.NO_OPTION) {
            System.exit(0);
        }
        return response;
    }

    @Override
    public void windowOpened(WindowEvent e) {}

    @Override
    public void windowClosed(WindowEvent e) {}

    @Override
    public void windowIconified(WindowEvent e) {}

    @Override
    public void windowDeiconified(WindowEvent e) {}

    @Override
    public void windowActivated(WindowEvent e) {}

    @Override
    public void windowDeactivated(WindowEvent e) {}
}
