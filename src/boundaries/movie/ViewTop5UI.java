package boundaries.movie;

/**
 * ViewTop5UI is a class that contains static methods to print top 5 movies menu for customer and staff
 */
public class ViewTop5UI extends MovieUI{
    /**
     * Prints Top 5 movies menu for staff
     */
    public static void printStaffMenu() {
        System.out.println(
                generateTitleWithLines("View Top 5 Movies by chosen category (STAFF)") +
                " 1. By Number of Tickets Sold                                       \n" +
                " 2. By Overall Customer Ratings                           \n" +
                " 0. Back to StaffApp                                      \n"+
                generateFullLine()
        );
        promptUserChoice();
    }

    /**
     * Prints Top 5 movies menu for customer
     */
    public static void printCustomerMenu() {
        System.out.println(
                generateTitleWithLines("View Top 5 Movies by chosen category (CUSTOMER)") +
                " 1. By Number of Tickets Sold                                       \n" +
                " 2. By Overall Customer Ratings                           \n" +
                " 0. Back to CustomerApp                                   \n"+
                generateFullLine()
        );
        promptUserChoice();
    }
}
