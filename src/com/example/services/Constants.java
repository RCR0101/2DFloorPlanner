package services;

import java.nio.file.Paths;

public class Constants {
    static final String SAVE_PATH = String.valueOf(Paths.get(Paths.get(System.getProperty("user.dir")).toString(), "saves", "saveFileTest.txt"));
}
