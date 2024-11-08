package com.example.frames;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;

import com.example.services.Util;

public class ClosingWindowListener extends WindowAdapter {
    private OuterFrame outerFrame;

    public ClosingWindowListener(OuterFrame outerFrame) {
        this.outerFrame = outerFrame;
    }

    @Override
    public void windowClosing(WindowEvent e) {
        String str = Util.getAbsolutePath("assets/images/logo.png");
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
                outerFrame.getFileManager().saveFile(OuterFrame.canvas.rooms);
                System.exit(0);
            } catch (IOException e1) {
                System.err.println("There was an IOException: " + e1);
            }
        } else if (response == JOptionPane.NO_OPTION) {
            System.exit(0);
        }
    }
}