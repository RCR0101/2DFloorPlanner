package services;

import canvas.Canvas;
import models.Room;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class DoRedo {

    public static void doredo(Canvas canvas) throws IOException, ClassNotFoundException {
        if (canvas.changeLog  < DoUndo.maxChangeLog) {
            canvas.rooms.clear();
            for(Object room : (ArrayList) canvas.allRooms.get(++canvas.changeLog)){
                canvas.rooms.add(Room.getCopy((Room) room));
            }
            canvas.repaint();
            canvas.fixture = null ;
        }
    }
}
