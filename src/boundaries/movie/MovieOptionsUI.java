package boundaries.movie;

public class MovieOptionsUI {

    public static void printStaffMenu() {
        System.out.println(	"---------------------- INDIVIDUAL MOVIE OPTIONS (STAFF) ---------------------\n" +
                " 1. Display/Edit Showtimes                              \n" +
                " 2. Edit Movie 						       		      \n" +
                " 3. Remove Movie		                                  \n" +
                " 4. View Reviews	                                      \n" +
                " 5. Delete Reviews	                                  \n" +
                " 0. Back to Movie Listings			                  \n"+
                "----------------------------------------------------------");

        System.out.println("Enter choice: ");
    }

    public static void printCustomerMenu() {
        System.out.println(	"---------------------- INDIVIDUAL MOVIE OPTIONS (CUSTOMER) ---------------------\n" +
                " 1. Display Showtimes                                   \n" +
                " 2. View Reviews                                        \n" +
                " 3. Leave Review                                        \n" +
                " 0. Back to Movie Listings                              \n"+
                "----------------------------------------------------------");

        System.out.println("Enter your choice: ");
    }
}
