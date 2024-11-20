package com.example.menubar;

import com.example.canvas.CanvasState;
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
        if (canvas.changeLog > 0) {
            // Move back in the changelog
            CanvasState previousState = (CanvasState) canvas.allStates.get(--canvas.changeLog);

            // Restore rooms and furniture
            canvas.rooms.clear();
            canvas.rooms.addAll(previousState.getRooms());

            canvas.furnitureItems.clear();
            canvas.furnitureItems.addAll(previousState.getFurnitureItems());

            canvas.repaint();
        }
    }
}