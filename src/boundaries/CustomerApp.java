package boundaries;
import controllers.CustomerController;
import controllers.InputController;
import controllers.MovieController;
import controllers.TicketController;
import entities.accounts.CustomerAccount;
import entities.booking.Booking;

public class CustomerApp {
    // Implementing Singleton design pattenr to ensure CustomerUI only has one instance

    private static CustomerApp single_custUI = null;

    private CustomerApp(){

    }


    public static CustomerApp getInstance() {
        if(single_custUI == null){
            single_custUI = new CustomerApp();
        }
        return single_custUI;
    }

    /**
     * 1. Search / List Movie
     * 2. View movie details - incl. reviews and ratings
     * 3. Check Seat availability and selection of seats
     * 4. Book and Purchase Of Ticket
     * 5. View Booking History
     * 6. List the Top 5 Ranking by ticket sales OR by overall reviewers' ratings
     * 7. Enter their own review/rating of the movie
     */
    public void displayCustomerUI() {
        int userChoice = -1;
        System.out.printf(
                "---------------------------\n"
                + "Welcome To the Customer Interface!\n"
                + "1. View Movies\n"
                + "2. View Top 5 Movies\n"
                + "3. View Booking History\n"
                + "0. Exit To Main Interface\n"
                + "---------------------------\n"
        );

        System.out.println("Please enter your choice:");
        do {
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
