package boundaries.movie;

public class MovieUI {

    public static String generateFullLine() {
        return "------------------------------------------------------------";
    }

    public static String generateHalfLine() {
        return "-------------------";
    }

    public static String generateTitleWithLines(String string) {
        return "------------------- " + string + " -------------------\n";
    }
    public static void promptUserChoice() {
        System.out.println("Enter Choice: ");
    }
}
