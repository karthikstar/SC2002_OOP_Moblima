package boundaries.movie;

public class MovieByStatusUI {

    public static void printStaffMenu() {
            System.out.println("------------------- VIEW/ EDIT MOVIES (STAFF) ------------------\n" +
                    " 1. List all movies	                                \n" +
                    " 2. Coming Soon 						       			\n" +
                    " 3. Preview		                                    \n" +
                    " 4. Now Showing	                                    \n" +
                    " 5. End of Showing                                     \n" +
                    " 0. Back to Movies Master Control......                     \n" +
                    "---------------------------------------------------------");

            System.out.println("Enter choice: ");
    }

    public static void printCustomerMenu() {
        System.out.println("------------------- VIEW MOVIES (CUSTOMER) ------------------\n" +
                " 1. List all movies	                                \n" +
                " 2. Coming Soon 						       			\n" +
                " 3. Preview		                                    \n" +
                " 4. Now Showing	                                    \n" +
                " 5. Search Movies (By Title)                                    \n" +
                " 0. Back to Customer Movie Menu...                     \n" +
                "---------------------------------------------------------");

        System.out.println("Enter choice: ");
    }

}