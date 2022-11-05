package boundaries;
import java.util.Scanner;

//Main App
public class MainApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
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
            System.out.println("Select Option: ");

            while (!sc.hasNextInt()) {
                System.out.println("Please enter a valid option - 1 for Customer, 2 for Staff, 0 to exit");
                sc.next();
            }
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Lets bring you to the customer page..");
                    CustomerUI.getInstance().displayCustomerUI();
                    break;
                case 2:
                    System.out.println("Lets bring you to the staff page..");
                    break;
                case 0:
                    System.out.println("Goodbye");
                    break;
                default:
                    System.out.println("Pls enter a valid choice between 0 and 2");
            }
        } while (choice != 0);
    }
}
