package services;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import models.Room;


public class LoadFile {
    ArrayList<Room> roomList;

    public ArrayList<Room> getFile() throws IOException, ClassNotFoundException{
        NewFile nFile = new NewFile();
        FileInputStream fileInputStream = new FileInputStream(nFile.newSave());
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        @SuppressWarnings("unchecked")
        ArrayList<Room> roomList = (ArrayList<Room>) objectInputStream.readObject();
        objectInputStream.close();
        return roomList;
    }
}
