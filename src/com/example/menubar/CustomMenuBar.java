package menubar;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.*;

import services.* ;
import frames.*;
public class CustomMenuBar {
    OuterFrame frame;
    public Component createMenuBar(OuterFrame frame){

        this.frame = frame ;
        JMenuBar mb = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Edit");
        JMenuItem newItem = new JMenuItem("New");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem openItem = new JMenuItem("Open");

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
    static boolean undoClicked = false;
    UndoActionListener(OuterFrame frame){
        this.frame = frame ;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        undoClicked = true;
        try {
            DoUndo.doundo(frame.canvas);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

}
class RedoActionListener extends AbstractAction {
    OuterFrame frame ;
    RedoActionListener(OuterFrame frame){
        this.frame = frame ;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try{

            DoRedo.doredo(frame.canvas);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
}
