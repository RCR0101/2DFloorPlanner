package com.example.services;

import com.example.canvas.Canvas;
import com.example.models.Room;

import java.io.*;
import java.util.ArrayList;

import static java.lang.Math.max;

public class DoUndo {
    static int maxChangeLog = 0 ;
    public static void doUndo(Canvas canvas) throws IOException, ClassNotFoundException {
        maxChangeLog = max(maxChangeLog,canvas.changeLog);
        if (canvas.changeLog > 0) {

            //canvas.rooms = (ArrayList) ((ArrayList) canvas.allRooms.get(--canvas.changeLog)).clone();
            canvas.rooms.clear();
            for(Object room : (ArrayList) canvas.allRooms.get(--canvas.changeLog)){
                canvas.rooms.add(Room.getCopy((Room) room));
            }
            System.out.println("Going back to : " + canvas.changeLog);
            canvas.fixture = null ;
            canvas.repaint();
        }
    }
}
