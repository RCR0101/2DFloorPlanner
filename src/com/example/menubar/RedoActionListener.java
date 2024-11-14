package com.example.menubar;

import com.example.frames.OuterFrame;
import com.example.canvas.Canvas;
import com.example.models.Room;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class RedoActionListener extends AbstractAction {
    private OuterFrame frame;
    private Canvas canvas;

    RedoActionListener(OuterFrame frame) {
        this.frame = frame;
        this.canvas = frame.canvas;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (canvas.changeLog < CustomMenuBar.maxChangeLog) {
            canvas.rooms.clear();
            for (Object room : (ArrayList) canvas.allRooms.get(++canvas.changeLog)) {
                canvas.rooms.add(Room.getCopy((Room) room));
            }
            System.out.println("Redo: moved forward to changeLog " + canvas.changeLog);
            canvas.fixture = null;
            canvas.repaint();
        }
    }
}