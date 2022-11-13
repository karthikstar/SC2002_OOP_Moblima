package boundaries;
import controllers.InputController;

import java.util.Scanner;

/**
 * Main Application Access.
 */
public class MainApp {
    public static void main(String[] args) {
        int choice;

        String logo = "\n" +
                "                  _     _ _                 \n" +
                "                 | |   | (_)                \n" +
                "  _ __ ___   ___ | |__ | |_ _ __ ___   __ _ \n" +
                " | '_ ` _ \\ / _ \\| '_ \\| | | '_ ` _ \\ / _` |\n" +
                " | | | | | | (_) | |_) | | | | | | | | (_| |\n" +
                " |_| |_| |_|\\___/|_.__/|_|_|_| |_| |_|\\__,_|\n" +
                "                                            \n";

        do {
            System.out.printf(
                    "WELCOME TO..\n" +
                    logo +

                    "1. Customer\n" +
                    "2. Staff\n" +
                    "0. Exit\n"
            );
            System.out.println("Please select one of the above choices between 0 to 2: ");

            int minChoice = 0;
            int maxChoice = 2;

            choice = InputController.getUserInt(minChoice, maxChoice);

            switch (choice) {
                case 0:
                    System.out.println("Thank you for using our app! Goodbye");
                    break;
                case 1:
                    CustomerApp.getInstance().displayCustomerUI();
                    break;
                case 2:
                    StaffApp.getInstance().displayStaffUI();
                    break;
                default:
                    System.out.println("Please enter a valid choice between 0 to 2");
                    break;
            }
        } while (choice != 0);
    }
}
