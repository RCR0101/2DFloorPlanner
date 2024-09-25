package menubar;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

import canvas.Canvas;
import models.Room;
import services.* ;
import frames.*;

import static java.lang.Math.max;

public class CustomMenuBar {
    public static int maxChangeLog = 0 ;
    OuterFrame frame;
    public Component createMenuBar(OuterFrame frame){

        this.frame = frame ;
        JMenuBar mb = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Edit");
        JMenuItem newItem = new JMenuItem("New");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem openItem = new JMenuItem("Open");

        //for undo/redo using keys :
        UndoActionListener undoActionListener = new UndoActionListener(frame);
        RedoActionListener redoActionListener = new RedoActionListener(frame);
        InputMap inputMap  = mb.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = mb.getActionMap();
        inputMap.put(KeyStroke.getKeyStroke("ctrl Z"),"undo");
        inputMap.put(KeyStroke.getKeyStroke("ctrl Y"),"redo");
        actionMap.put("undo",undoActionListener);
        actionMap.put("redo",redoActionListener);
        undoActionListener.putValue(Action.NAME, "Undo");
        redoActionListener.putValue(Action.NAME, "Redo");
        JMenuItem undo = new JMenuItem(undoActionListener);
        JMenuItem redo = new JMenuItem(redoActionListener);


        file.add(newItem);
        file.add(saveItem);
        file.add(openItem);
        edit.add(undo);
        edit.add(redo);
        mb.add(file);
        mb.add(edit);
        return mb;
    }

}

class UndoActionListener extends AbstractAction {
    OuterFrame frame ;
    Canvas canvas ;
    UndoActionListener(OuterFrame frame){
        this.frame = frame ;
        this.canvas = frame.canvas ;
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        CustomMenuBar.maxChangeLog = max(CustomMenuBar.maxChangeLog,canvas.changeLog);
        if (canvas.changeLog > 0) {

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
class RedoActionListener extends AbstractAction {
    OuterFrame frame ;
    Canvas canvas ;
    RedoActionListener(OuterFrame frame){
        this.frame = frame ;
        this.canvas = frame.canvas ;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (canvas.changeLog  < CustomMenuBar.maxChangeLog) {
            canvas.rooms.clear();
            for(Object room : (ArrayList) canvas.allRooms.get(++canvas.changeLog)){
                canvas.rooms.add(Room.getCopy((Room) room));
            }
            canvas.repaint();
            canvas.fixture = null ;
        }
    }
}
