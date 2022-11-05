package boundaries;
import controllers.InputController;

public class CustomerUI {
    // Implementing Singleton design pattenr to ensure CustomerUI only has one instance

    private static CustomerUI single_custUI = null;

    private CustomerUI(){

    }

    public static CustomerUI getInstance() {
        if(single_custUI == null){
            single_custUI = new CustomerUI();
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
                + "1. View All Movies\n"
                + "2. Book Movie Ticket\n"
                + "3. View Booking History\n"
                + "0. Exit To Main Interface\n"
                + "---------------------------\n"
        );

        System.out.println("Please enter your choice:");
        do {
            userChoice = InputController.getUserInt();
            switch (userChoice){
                case 0:
                    System.out.println("Bringing you back to MOBLIMA Main Interface..");
                case 1:
                    System.out.println("lets go to all movies");
                    break;
                case 2:
                    System.out.println("lets go to ticketing manager");
                    break;
                case 3:
                    System.out.println("lets view booking history");
                    break;
                default:
                    System.out.println("You have entered an invalid choice. Please choose an option between 0 to 3.");
                    break;
            }
        } while (userChoice != 0);
    }
}
