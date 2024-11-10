package com.example.main;

import com.example.frames.OuterFrame;
import com.formdev.flatlaf.intellijthemes.FlatSolarizedLightIJTheme;

public class Main {
    public static void main(String[] args) {
        FlatSolarizedLightIJTheme.setup();
        javax.swing.SwingUtilities.invokeLater(() -> {
            OuterFrame outerFrame = new OuterFrame();
            outerFrame.setVisible(true);
        });
    }
}