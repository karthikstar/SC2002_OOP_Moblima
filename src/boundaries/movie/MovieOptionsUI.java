package boundaries.movie;

/**
 * MovieOptionsUI is a class that contains static methods to print individual movie options for customer and staff
 */
public class MovieOptionsUI extends MovieUI {

    public static void printStaffMenu() {
        /**
         * Prints individual movie options for staff
         */
        System.out.println(
                generateTitleWithLines("INDIVIDUAL MOVIE OPTIONS (STAFF)") +
                " 1. Display/Edit Showtimes                              \n" +
                " 2. Edit Movie 						       		      \n" +
                " 3. Remove Movie		                                  \n" +
                " 4. View Reviews	                                      \n" +
                " 5. Delete Reviews	                                  \n" +
                " 0. Back to Movie Listings			                  \n"+
                generateFullLine()
            );
        promptUserChoice();
    }

    /**
     * Prints individual movie options for customer
     */
    public static void printCustomerMenu() {
        System.out.println(
                generateTitleWithLines("INDIVIDUAL MOVIE OPTIONS (CUSTOMER)") +
                " 1. Display Showtimes                                   \n" +
                " 2. View Reviews                                        \n" +
                " 3. Leave Review                                        \n" +
                " 0. Back to Movie Listings                              \n"+
                generateFullLine()
            );
        promptUserChoice();
    }
}
