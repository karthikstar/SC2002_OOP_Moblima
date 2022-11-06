package utils;

import java.io.File;
import java.io.IOException;

public class FilePathFinder {
    public static String findRootPath() throws IOException {
        File directory = new File("./");
        String filePath = directory.getCanonicalPath();

        if (filePath == null) {
            throw new IOException("Unable to find filepath!");
        } else {
            return filePath;
        }
    }
}
