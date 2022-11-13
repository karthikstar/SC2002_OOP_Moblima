package boundaries.movie;

/**
 * MovieAdd UI is a class that contains static methods to print ui to the staff when they try to add a movie
 */
public class MovieAddUI extends MovieUI{
    /**
     * Prints a menu for addition of movie by staff
     */
    public static void printMenu() {
        System.out.println(
                generateTitleWithLines("ADD MOVIE") +
                " 1. Submit movie                                      \n" +
                " 2. Edit movie                                        \n" +
                " 0. Discard movie, back to Movie Menu                 \n"+
                generateFullLine());
        promptUserChoice();
    }
}
