package services;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import models.Room;
public class SaveChange {

        ArrayList<Room> roomList;
        int changeLog ;
        public SaveChange(ArrayList<Room> roomList , int changeLog){
            this.roomList = roomList;
            this.changeLog = changeLog;
        }

        public void saveFile() throws IOException {
            FileOutputStream fileOutputStream = new FileOutputStream(System.getProperty("user.dir") + File.separator + "Changes" + File.separator + "saveChangeFile"+changeLog+".txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(roomList);
            objectOutputStream.close();
        }


}
