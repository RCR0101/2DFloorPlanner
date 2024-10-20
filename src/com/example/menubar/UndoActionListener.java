package com.example.menubar;

import com.example.frames.OuterFrame;
import com.example.canvas.Canvas;
import com.example.models.Room;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import static java.lang.Math.max;

public class UndoActionListener extends AbstractAction {
    private OuterFrame frame;
    private Canvas canvas;

    public UndoActionListener(OuterFrame frame) {
        this.frame = frame;
        this.canvas = OuterFrame.canvas;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CustomMenuBarLogic.maxChangeLog = max(CustomMenuBarLogic.maxChangeLog, canvas.changeLog);
        if (canvas.changeLog > 0) {
            canvas.rooms.clear();
            for (Object room : (ArrayList) canvas.allRooms.get(--canvas.changeLog)) {
                canvas.rooms.add(Room.getCopy((Room) room));
            }
            System.out.println("Undo: reverted to changeLog " + canvas.changeLog);
            canvas.fixture = null;
            canvas.repaint();
        }
    }
}