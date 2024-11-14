package com.example.services;


import java.awt.*;
import java.io.File;
import java.util.ArrayList;

import com.example.canvas.Canvas;
import com.example.models.Room;

import javax.swing.*;

public class SaveChange {

        public static boolean isIntersection(Canvas canvas){
            if(canvas == null) return false;
            if(canvas.currentRoom == null) return false;

            for(Object otherRoom : canvas.rooms){

                if(canvas.currentRoom.intersects((Room)otherRoom) && canvas.currentRoom != otherRoom){

                    System.out.println("Returning true");
                    return true;
                }
            }
            return false;
        }
        public static void saveChanges(Canvas canvas) {
            if(!isIntersection(canvas)) {

                ++canvas.changeLog;
                ArrayList<Room> clone = new ArrayList<>();
                for (Object room : canvas.rooms) {
                    clone.add(Room.getCopy((Room) room));
                }
                if (canvas.changeLog < canvas.allRooms.size()) {
                    canvas.allRooms.set(canvas.changeLog, clone);
                    System.out.println();
                } else {
                    canvas.allRooms.add(clone);
                }
                canvas.fixture = null;
                canvas.currentRoom = null;
                canvas.customRoom = false;
                canvas.defaultRoom = false;
                canvas.room1 = null ;
                canvas.room2 = null ;
            }
            else
            {
                String str = System.getProperty("user.dir") + File.separator  +"assets" + File.separator + "images" + File.separator + "logo.jpg";
                ImageIcon logo = new ImageIcon(str);
                Image resizedImage = logo.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
                ImageIcon resizedIcon = new ImageIcon(resizedImage);
                int response = JOptionPane.showConfirmDialog(
                        null,
                        "Overlap Detected",
                        "Error",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        resizedIcon

                );

            }

        }

}
