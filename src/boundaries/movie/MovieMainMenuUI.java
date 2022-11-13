package boundaries.movie;

/**
 * MovieMainMenuUI is a class that contains static methods to print movie main menu for staff
 */
public class MovieMainMenuUI extends MovieUI {
    /**
     * Print movie menu for staff
     */
    public static void printStaffMenu() {
            System.out.println(
                    generateTitleWithLines("MOVIE MAIN MENU (STAFF)") +
                    " 1. View/ Edit Movies 						    		\n" +
                    " 2. Add Movies		                                 	\n" +
                    " 3. Search Movies (By Title)	                        \n" +
                    " 0. Back to StaffApp......                             \n"+
                    generateFullLine()
                    );

            promptUserChoice();
    }

}
