package com.example.services;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Paths;

public class Util {
    /// Gets the absolute path to the project files
    ///
    /// @param relativePath This is the path to the project file, for example: assets/icons/undo.png. Make sure to use forward slash, and not to use it in the beginning
    public static String getAbsolutePath(String relativePath) {
        return String.valueOf(Paths.get(Paths.get(System.getProperty("user.dir")).toString(), relativePath));
    }

    public static ImageIcon resizeImage(ImageIcon image) {
        Image newImage = image.getImage();
        newImage = newImage.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        return new ImageIcon(newImage);
    }
}