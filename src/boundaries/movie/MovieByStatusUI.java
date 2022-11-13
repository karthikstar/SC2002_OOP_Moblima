package boundaries.movie;

/**
 * MovieByStatus UI is a class that contains static methods to print output to users when they try to view movies (for customer) and view/edit movies (for staff)
 */
public class MovieByStatusUI extends MovieUI {
    /**
     * Prints staff menu for viewing and editing of movies
     */
    public static void printStaffMenu() {
            System.out.println(
                    generateTitleWithLines("VIEW/ EDIT MOVIES (STAFF)") +
                    " 1. List all movies	                                \n" +
                    " 2. Coming Soon 						       			\n" +
                    " 3. Preview		                                    \n" +
                    " 4. Now Showing	                                    \n" +
                    " 5. End of Showing                                     \n" +
                    " 0. Back to Movies Master Control......                     \n" +
                    generateFullLine()
            );
            promptUserChoice();
    }

    /**
     * Prints customer menu for viewing movies
     */
    public static void printCustomerMenu() {
        System.out.println(
                generateTitleWithLines("VIEW MOVIES (CUSTOMER)") +
                " 1. List all movies	                                \n" +
                " 2. Coming Soon 						       			\n" +
                " 3. Preview		                                    \n" +
                " 4. Now Showing	                                    \n" +
                " 5. Search Movies (By Title)                                    \n" +
                " 0. Back to Customer Movie Menu...                     \n" +
                generateFullLine()
        );
        promptUserChoice();
    }

}