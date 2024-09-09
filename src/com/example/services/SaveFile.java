package services;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import models.Room;


public class SaveFile {
    ArrayList<Room> roomList;
    public SaveFile(ArrayList<Room> roomList){
        this.roomList = roomList;
    }

    public void saveFile() throws IOException{
        FileOutputStream fileOutputStream = new FileOutputStream("/Users/adalmia/Documents/projects/java_dev/2DFloorPlanner/saves/saveFileTest.txt");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(roomList);
        objectOutputStream.close();
    }
}
