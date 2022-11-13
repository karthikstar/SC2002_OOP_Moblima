package boundaries.movie;

public class ViewTop5UI extends MovieUI{
    
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
