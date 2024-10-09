package services;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import models.Room;


public class RetrieveFile {
    ArrayList<Room> roomList;

    public ArrayList<Room> getFile() throws IOException, ClassNotFoundException{
        FileInputStream fileInputStream = new FileInputStream(Constants.SAVE_PATH);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        ArrayList<Room> roomList = (ArrayList<Room>) objectInputStream.readObject();
        objectInputStream.close();
        return roomList;
    }
}
