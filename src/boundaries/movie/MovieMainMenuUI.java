package boundaries.movie;

public class MovieMainMenuUI extends MovieUI {
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
