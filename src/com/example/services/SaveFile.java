package services;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import models.Room;

public class SaveFile {
    ArrayList<Room> roomList;
    private static boolean isUnsavedChanges = true;

    public SaveFile(ArrayList<Room> roomList) {
        this.roomList = roomList;
    }

    public void saveFile() throws IOException {
        NewFile nFile = new NewFile();
        String savePath = nFile.getSavePath();

        if (nFile.isTempFile() || !nFile.isSavePathSet()) {
            savePath = nFile.newSave();
        }

        if (savePath != null) {
            FileOutputStream fileOutputStream = new FileOutputStream(savePath);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(roomList);
            objectOutputStream.close();

            isUnsavedChanges = false;
        }
    }

    public boolean isFileSaved() throws ClassNotFoundException, IOException {
        NewFile nFile = new NewFile();
        LoadFile lFile = new LoadFile();

        if (nFile.isSavePathSet()) {
            ArrayList<Room> roomSaved = lFile.getFile(nFile.getSavePath());
            boolean isSaved = roomSaved.equals(roomList);

            isUnsavedChanges = !isSaved;

            return isSaved;
        }
        return false;
    }

    public static boolean hasUnsavedChanges() {
        return isUnsavedChanges;
    }

    public static void markUnsavedChanges() {
        isUnsavedChanges = true;
    }
}
