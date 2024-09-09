package menubar;

import java.awt.Component;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class CustomMenuBar {
    public Component createMenuBar(){
        JMenuBar mb = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Edit");
        JMenuItem newItem = new JMenuItem("New");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem undo = new JMenuItem("Undo");
        JMenuItem redo = new JMenuItem("Redo");
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
