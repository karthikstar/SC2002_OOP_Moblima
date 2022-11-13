package boundaries;
import controllers.CustomerController;
import controllers.InputController;
import controllers.MovieController;
import controllers.TicketController;
import entities.accounts.CustomerAccount;
import entities.booking.Booking;

/**
 * Customer Application Class.
 */
public class CustomerApp {
    /**
     * Singleton Constructor
     */
    private static CustomerApp single_custUI = null;

    /**
     * Constructor for CustomerApp.
     */
    private CustomerApp(){

    }
    /**
     * Singleton Constructor
     */
    public static CustomerApp getInstance() {
        if(single_custUI == null){
            single_custUI = new CustomerApp();
        }
        return single_custUI;
    }

    /**
     * Prints Customer Portal UI and allows them to choose which functionality to access.
     */
    public void displayCustomerUI() {
        int userChoice = -1;
        do {
            System.out.printf(
                    "---------------------------\n"
                            + "Welcome To the Customer Portal! What would you like to do today?\n"
                            + "1. View Movies\n"
                            + "2. View Top 5 Movies\n"
                            + "3. View Booking History\n"
                            + "0. Exit To Main Interface\n"
                            + "---------------------------\n"
            );
            System.out.println("Please select one of the above choices between 0 to 3:");
            userChoice = InputController.getUserInt(0,3);
            switch (userChoice){
                case 0:
                    System.out.println("Bringing you back to Main APP ..");
                    break;
                case 1:
                    MovieController.getInstance().viewMovies("Customer");
                    break;
                case 2:
                    MovieController.getInstance().viewTop5("Customer");
                    break;
                case 3:
                    // display booking history
                    int bookingHistoryChoice;
                    do {
                        BookingUI.printCustomerBookingHistoryMenu();
                        bookingHistoryChoice = InputController.getUserInt(0,2);

                        switch (bookingHistoryChoice) {
                            case 1: // by email
                                CustomerController.getInstance().displayPastBookingViaEmail();
                                break;
                            case 2: // by mobile no.
                                CustomerController.getInstance().displayPastBookingViaMobile();
                                break;
                            case 0:
                                System.out.println("Back to Customer interface..");
                                break;
                            default:
                                System.out.println("You have entered an invalid choice. Please choose an option between 0 to 2.");
                                break;
                        }
                    } while(bookingHistoryChoice != 0);
                    break;
                default:
                    System.out.println("You have entered an invalid choice. Please choose an option between 0 to 3.");
                    break;
            }
        } while (userChoice != 0);
    }
}
