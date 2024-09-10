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
        FileOutputStream fileOutputStream = new FileOutputStream(Constants.SAVE_PATH);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(roomList);
        System.out.println("saves i think");
        objectOutputStream.close();
    }

    public boolean isFileSaved() throws ClassNotFoundException, IOException{
        ArrayList<Room> roomSaved = new LoadFile().getFile();
        System.out.println(roomList);
        System.out.println(roomSaved);
        return roomSaved.containsAll(roomList) && roomList.containsAll(roomSaved);
    }
}
