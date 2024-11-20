package com.example.services;

import com.example.models.Room;
import com.example.models.Furniture;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private static String savePath = null;
    private static boolean isTempFile = false;
    private static boolean isUnsavedChanges = true;

    // Retrieve the file from the given path or default savePath
    public Object[] loadFile(String filePath) throws IOException, ClassNotFoundException {
        File file = new File(filePath != null ? filePath : savePath);

        if (!file.exists() || file.length() == 0) {
            return new Object[]{new ArrayList<>(), new ArrayList<>()}; // Return empty lists
        }

        try (FileInputStream fileInputStream = new FileInputStream(file);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

            @SuppressWarnings("unchecked")
            ArrayList<Room> roomList = (ArrayList<Room>) objectInputStream.readObject();
            @SuppressWarnings("unchecked")
            List<Furniture> furnitureList = (List<Furniture>) objectInputStream.readObject();

            return new Object[]{roomList, furnitureList};

        } catch (StreamCorruptedException e) {
            throw new IOException("The file format is not a valid serialized object.", e);
        }
    }

    // Save the file to the current savePath or prompt for a new path
    public void saveFile(ArrayList<Room> roomList, List<Furniture> furnitureList) throws IOException {
        if (isTempFile || !isSavePathSet()) {
            savePath = promptSavePath();
        }

        if (savePath != null) {
            try (FileOutputStream fileOutputStream = new FileOutputStream(savePath);
                 ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
                objectOutputStream.writeObject(roomList);
                objectOutputStream.writeObject(furnitureList);
            }

            isUnsavedChanges = false;
        }
    }

    // Create a temporary file and set savePath
    public String createTempFile() throws IOException {
        File tempFile = File.createTempFile("temp_", ".tmp");
        tempFile.deleteOnExit();
        savePath = tempFile.getAbsolutePath();
        isTempFile = true;
        return savePath;
    }

    // Prompt the user to choose a new save location
    public String promptSavePath() {
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        fileChooser.setDialogTitle("Save File");
        fileChooser.setApproveButtonText("Save");

        int response = fileChooser.showSaveDialog(null);
        if (response == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String chosenPath = fileToSave.getAbsolutePath();
            if (!chosenPath.endsWith(".txt")) {
                chosenPath += ".txt";
            }

            if (isTempFile) {
                copyTempFileToNewLocation(chosenPath);
            } else {
                savePath = chosenPath;
            }

            isTempFile = false;
        }
        return savePath;
    }

    // Copy the temporary file to the specified new location
    private void copyTempFileToNewLocation(String newLocation) {
        try {
            File tempFile = new File(savePath);
            File newFile = new File(newLocation);
            Files.copy(tempFile.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            tempFile.delete();
            savePath = newLocation;
        } catch (IOException e) {
            System.out.println("Error copying temp file to new location");
        }
    }

    // Open file chooser to select a file to load
    public String openFileChooser() {
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        fileChooser.setDialogTitle("Open File");

        int response = fileChooser.showOpenDialog(null);
        if (response == JFileChooser.APPROVE_OPTION) {
            savePath = fileChooser.getSelectedFile().getAbsolutePath();
            isTempFile = false;
        }
        return savePath;
    }

    // Check if the current file is saved
    public boolean isFileSaved(ArrayList<Room> currentRooms, List<Furniture> currentFurniture) throws IOException, ClassNotFoundException {
        if (isSavePathSet()) {
            Object[] savedData = loadFile(savePath);
            @SuppressWarnings("unchecked")
            ArrayList<Room> savedRooms = (ArrayList<Room>) savedData[0];
            @SuppressWarnings("unchecked")
            List<Furniture> savedFurniture = (List<Furniture>) savedData[1];
            boolean isSaved = savedRooms.equals(currentRooms) && savedFurniture.equals(currentFurniture);
            isUnsavedChanges = !isSaved;
            return isSaved;
        }
        return false;
    }

    // Utility methods to manage the save state
    public static boolean hasUnsavedChanges() {
        return isUnsavedChanges;
    }

    public static void markUnsavedChanges() {
        isUnsavedChanges = true;
    }

    public static void resetUnsavedChanges() {
        isUnsavedChanges = false;
    }

    public boolean isSavePathSet() {
        return savePath != null;
    }

    public boolean isTempFile() {
        return isTempFile;
    }

    public String getSavePath() {
        return savePath;
    }
}