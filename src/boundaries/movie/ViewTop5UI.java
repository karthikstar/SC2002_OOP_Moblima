package boundaries.movie;

public class ViewTop5UI {
    
    public static void printStaffMenu() {
        System.out.println(	"-------------------- View Top 5 Movies by chosen category (STAFF) ---------------------\n" +
                " 1. By Number of Tickets Sold                                       \n" +
                " 2. By Overall Customer Ratings                           \n" +
                " 0. Back to StaffApp                                      \n"+
                "------------------------------------------------------------");
        System.out.println("Enter choice: ");
    }

    public static void printCustomerMenu() {
        System.out.println(	"-------------------- View Top 5 Movies by chosen category (CUSTOMER) ---------------------\n" +
                " 1. By Number of Tickets Sold                                       \n" +
                " 2. By Overall Customer Ratings                           \n" +
                " 0. Back to CustomerApp                                   \n"+
                "------------------------------------------------------------");
        System.out.println("Enter choice: ");
    }
}
