package com.example.services;

import com.example.canvas.Canvas;
import com.example.models.Room;

import java.io.IOException;
import java.util.ArrayList;

public class DoRedo {

    public static void doRedo(Canvas canvas) throws IOException, ClassNotFoundException {
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
