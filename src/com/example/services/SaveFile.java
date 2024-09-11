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
        NewFile nFile = new NewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(nFile.newSave());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(roomList);
        objectOutputStream.close();
    }

    public boolean isFileSaved() throws ClassNotFoundException, IOException{
        ArrayList<Room> roomSaved = new LoadFile().getFile();
        return roomSaved.equals(roomList);
    }
}
