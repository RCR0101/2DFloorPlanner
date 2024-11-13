package com.example.menubar;

import com.example.canvas.CanvasState;
import com.example.frames.OuterFrame;
import com.example.canvas.Canvas;
import com.example.models.Room;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class RedoActionListener extends AbstractAction {
    private OuterFrame frame;
    private Canvas canvas;

    public RedoActionListener(OuterFrame frame) {
        this.frame = frame;
        this.canvas = OuterFrame.canvas;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (canvas.changeLog < Math.min(CustomMenuBarLogic.maxChangeLog, canvas.allStates.size())) {
            CanvasState newState = (CanvasState) canvas.allStates.get(++canvas.changeLog);

            // Restore rooms and furniture
            canvas.rooms.clear();
            canvas.rooms.addAll(newState.getRooms());

            canvas.furnitureItems.clear();
            canvas.furnitureItems.addAll(newState.getFurnitureItems());

            canvas.repaint();
            System.out.println("Redo: moved forward to changeLog " + canvas.changeLog);
        }
    }
}