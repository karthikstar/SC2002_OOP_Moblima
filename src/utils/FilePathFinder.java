package utils;

import java.io.File;
import java.io.IOException;

public class FilePathFinder {
    public static String findRootPath() {
        File directory = new File("./");
        String filePath = null;

        try {
            filePath = directory.getCanonicalPath();
        } catch (IOException e) {
            throw new RuntimeException("Unable to find filepath!");
        }

        return filePath;
    }
}
