package services;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import models.Room;


public class RetrieveFile {
    ArrayList<Room> roomList;

    public ArrayList<Room> getFile() throws IOException, ClassNotFoundException{
        FileInputStream fileInputStream = new FileInputStream("/Users/adalmia/Documents/projects/java_dev/2DFloorPlanner/saves/saveFileTest.txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        @SuppressWarnings("unchecked")
        ArrayList<Room> roomList = (ArrayList<Room>) objectInputStream.readObject();
        objectInputStream.close();
        return roomList;
    }
}
