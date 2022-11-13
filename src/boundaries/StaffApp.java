package boundaries;

import controllers.InputController;
import controllers.MovieController;
import controllers.StaffController;
import controllers.SystemSettingsController;
import entities.movie.Movie;

/**
 * Class that manages the staff side of the application.
 */
public class StaffApp {
    /**
     * Singleton Constructor
     */
    private static StaffApp single_instance = null;

    /**
     * Object Constructor
     */
    private StaffApp(){

    }
    /**
     * Singleton Constructor
     */
    public static StaffApp getInstance() {
        if(single_instance == null) {
            single_instance = new StaffApp();
        }
        return single_instance;
    }

    /**
     * Displays the Staff UI for the Staff Portal and allows them to login or exit.
     */
    public void displayStaffUI() {
        boolean isLoggedin = false;
        boolean quit = false;

        int userChoice;

        do {
            System.out.printf(
                    "---------------------------\n"
                    + "Welcome To the Staff Portal!\n"
                    + "1. Login\n"
                    + "0. Exit To Main Portal\n"
                    + "---------------------------\n"
            );
            System.out.println("Please enter a choice between 0 to 1:");
            int minChoice = 0;
            int maxChoice = 1;
            userChoice = InputController.getUserInt(minChoice, maxChoice);

            switch(userChoice){
                case 0:
                    System.out.println("Bringing you back to Main Interface");
                    quit = true;
                    break;
                case 1:
                    System.out.println("Enter Your Username: ");
                    String username = InputController.getUserString();
                    System.out.println("Enter Your Password: ");
                    String password = InputController.getUserString();

                    boolean isValidLogin = StaffController.getInstance().login(username, password);
                    if(isValidLogin){
                        isLoggedin = true;
                        this.displayLoggedInUI();
                        quit = true;
                    } else {
                        System.out.println("Incorrect Username / Password, Please try again!");
                    }
                    break;
                default:
                    System.out.println("You have entered an invalid choice. Please choose an option between 0 to 1.");
                    quit = true;
                    break;
            }
        } while(!quit && !isLoggedin);
    }

    /**
     * Displays the UI for the staff after login and allow them to select which functionality they would like to access.
     */
    public void displayLoggedInUI() {
        int userChoice;

        do {
            System.out.printf(
                    "You have successfully logged in! Here are the list of things that you can do:\n" +
                    "1. View Top 5 Movies\n" +
                    "2. Configure System Settings (Holiday Dates & Prices)\n" +
                    "3. Movie Options Menu (ShowTimes can be edited per movie) \n" +
                    "0. Exit from Staff Portal\n"
            );

            System.out.println("Please enter your choice between 0 to 3: ");
            int minChoice = 0;
            int maxChoice = 3;
            userChoice = InputController.getUserInt(minChoice, maxChoice);
            switch (userChoice) {
                case 0:
                    System.out.println("You have been logged out from the Staff Portal, Good bye!");
                    break;
                case 1:
                    MovieController.getInstance().viewTop5("Staff");
                    break;
                case 2:
                    SystemSettingsController.getInstance().main(); // change accordingly.
                    break;
                case 3:
                    MovieController.getInstance().movieMenuStaff();
                    break;
                default:
                    System.out.println("You have entered an invalid choice. Please choose an option between 0 to 3.");
                    break;
            }
        } while(userChoice != 0);
    }

}
