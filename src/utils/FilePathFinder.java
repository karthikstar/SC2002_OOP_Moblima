package utils;

import java.io.File;
import java.io.IOException;

/**
 * Class that provides the utility to find the projects root path from each user's computer.
 */
public class FilePathFinder {
    /**
     * Method that returns the string of the project root path.
     * @return Project root path as a string
     */
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
