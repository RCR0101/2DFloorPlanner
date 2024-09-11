package services;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class NewFile {
    private static String SAVE_PATH = null;
    private static boolean isTempFile = false;

    public String createTempFile() throws IOException {
        File tempFile = File.createTempFile("2dfloorscanner_temp_", ".tmp");
        tempFile.deleteOnExit();
        SAVE_PATH = tempFile.getAbsolutePath();
        isTempFile = true;
        return SAVE_PATH;
    }

    public String newSave() {
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        fileChooser.setDialogTitle("Save File");
        fileChooser.setApproveButtonText("Save");

        int response = fileChooser.showSaveDialog(null);
        if (response == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String savePath = fileToSave.getAbsolutePath();

            if (!savePath.endsWith(".txt")) {
                savePath += ".txt";
            }

            try {
                if (isTempFile && SAVE_PATH != null) {
                    File tempFile = new File(SAVE_PATH);
                    File newFile = new File(savePath);

                    Files.copy(tempFile.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                    tempFile.delete();

                    SAVE_PATH = savePath;
                    isTempFile = false;
                } else {
                    SAVE_PATH = savePath;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return SAVE_PATH;
    }

    public String loadSave() {
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        fileChooser.setDialogTitle("Open File");

        int response = fileChooser.showOpenDialog(null);
        if (response == JFileChooser.APPROVE_OPTION) {
            SAVE_PATH = fileChooser.getSelectedFile().getAbsolutePath();
            isTempFile = false;
        }
        return SAVE_PATH;
    }

    public String getSavePath() {
        return SAVE_PATH;
    }

    public boolean isSavePathSet() {
        return SAVE_PATH != null;
    }

    public boolean isTempFile() {
        return isTempFile;
    }
}
