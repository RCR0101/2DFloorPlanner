package services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import models.Room;

public class LoadFile {
    ArrayList<Room> roomList;

    public ArrayList<Room> getFile(String filePath) throws IOException, ClassNotFoundException {
        File file = new File(filePath);

        if (file.length() == 0) {
            return new ArrayList<>();
        }

        FileInputStream fileInputStream = new FileInputStream(filePath);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        @SuppressWarnings("unchecked")
        ArrayList<Room> roomList = (ArrayList<Room>) objectInputStream.readObject();
        objectInputStream.close();
        return roomList;
    }

    public ArrayList<Room> loadDifferentFile() throws IOException, ClassNotFoundException {
        NewFile nFile = new NewFile();
        String filePath = nFile.loadSave();
        return getFile(filePath);
    }
}
