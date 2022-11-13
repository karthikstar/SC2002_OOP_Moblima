package boundaries.movie;

/**
 * MovieEditUI is a class that contains static methods to print ui to the staff when they try to edit a movie
 */
public class MovieEditUI extends MovieUI {
    /**
     * Prints a menu for staff to see when they want to edit the movie attributes
     */
    public static void printMenu() {
        System.out.println(
                generateTitleWithLines("MOVIE EDITING MENU") +
                " 1. Edit Title      	                                  \n" +
                " 2. Edit Rating                      	        		  \n" +
                " 3. Edit Genre	                                     \n" +
                " 4. Edit Duration                                           \n" +
                " 5. Edit Release Date                                       \n" +
                " 6. Edit End Date                                         \n" +
                " 7. Edit Languages                                        \n" +
                " 8. Edit Synopsis                                       \n" +
                " 9. Edit Director                             \n" +
                " 10. Edit Number of Tickets Sold                             \n" +
                " 11. Edit Cast                                 \n" +
                " 12. Edit Movie Types                                 \n" +
                " 0. Finish Editing Movie                                \n"+
                generateFullLine()
             );
        promptUserChoice();
    }
}
