package services;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

public class NewFile {
    String SAVE_PATH;

    public String newSave() {
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int response  = fileChooser.showOpenDialog(null);
        if(response == JFileChooser.APPROVE_OPTION){
            SAVE_PATH = fileChooser.getSelectedFile().getAbsolutePath();
        }
        return SAVE_PATH;
    }
}
