package boundaries.movie;

public class MovieByStatusUI extends MovieUI {

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