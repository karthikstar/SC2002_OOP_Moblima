package boundaries.movie;

/**
 * MovieUI is a class that contains static methods for printing of basic UI such as lines and prompts for user choice. This class will be extended by other subclasses for ease of use of these printing functionalities.
 */
public class MovieUI {

    /**
     * Prints a full line
     * @return
     */
    public static String generateFullLine() {
        return "------------------------------------------------------------";
    }

    /**
     * Prints a title paddded with lines on both sides
     * @param string is a String to be padded with lines on both sides
     * @return a String, which is a title padded with lines on both sides
     */
    public static String generateTitleWithLines(String string) {
        return "------------------- " + string + " -------------------\n";
    }

    /**
     * Prints a user prompt message to enter choice
     */
    public static void promptUserChoice() {
        System.out.println("Enter Choice: ");
    }
}
