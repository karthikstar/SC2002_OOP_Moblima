package controllers;

import boundaries.transaction.TransactionMainMenuUI;
import entities.booking.PaymentMethod;
import entities.booking.Ticket;
import entities.booking.TicketType;
import entities.booking.Transaction;
import utils.DataSerializer;
import utils.FilePathFinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that manages the functionalities requires in that of a transaction.
 */
public class TransactionController {
    /**
     * Singleton Constructor
     */
    private static TransactionController single_instance = null;
    /**
     * Singleton Constructor
     */
    public static TransactionController getInstance()
    {
        if (single_instance == null) {
            single_instance = new TransactionController();
        }
        return single_instance;
    }

    /**
     * Constructor for the Controller class.
     */
    public TransactionController() {
        this.transaction = new Transaction();
    }

    /**
     * Transaction object being handled.
     */
    private Transaction transaction;
    /**
     * Name of the Customer making the booking.
     */
    private String bookerName;
    /**
     * Phone Number of the Customer making the booking.
     */
    private String bookerMobileNo;
    /**
     * Email of the Customer making the booking.
     */
    private String bookerEmail;
    /**
     * Payment method being used in the transaction.
     */
    private PaymentMethod paymentMethod;
    /**
     * Controls loops using a boolean variable.
     */
    public Boolean exit = false;

    /**
     * Method that begins the transaction process. Allows them to enter their information and payment method for the transaction.
     * @param ticketList List of all ticket objects being purchased in the transaction
     * @param ticketPrices A HashMap containing all the prices of each individual ticket type
     * @param ticketCount A HashMap containing the number of tickets being bought for each Type of Ticket
     */
    public void startTransaction(ArrayList<Ticket> ticketList, HashMap<TicketType, Double> ticketPrices, HashMap<TicketType, Integer> ticketCount) {

        exit = false;
        int choice;
        String input;

        updateTotalPrice(ticketPrices, ticketCount);
        transaction.setTicketList(ticketList);

        displayPrices(ticketPrices, ticketCount);

        while (!exit) {
            TransactionMainMenuUI.printMenu();
            choice = InputController.getUserInt(0,1);

            switch (choice) {
                case 0:
                    exit = true;
                    reset();
                    break;
                case 1:
                    Boolean check = false;
                    while(!check) {
                        System.out.println("Please enter your name: ");
                        input = InputController.getUserString();
                        if (validateName(input)){
                            bookerName = input;
                            check = true;
                        }
                    }

                    System.out.println("Please enter your email: ");
                    String userEmail = InputController.getUserEmail();
                    this.bookerEmail = userEmail;

                    System.out.println("Please enter your mobile phone number (no country code): ");
                    String userMobileNumber = InputController.getUserMobileNumber();
                    this.bookerMobileNo = userMobileNumber;

                    for(int i = 0; i< PaymentMethod.values().length; i++)System.out.println(i+1 +". " +PaymentMethod.values()[i].toString());
                    System.out.println("\nPlease select your payment method: ");
                    int input2 = InputController.getUserInt(1,4) - 1;
                    System.out.println("You picked "+ PaymentMethod.values()[input2].toString() + ".");
                    this.paymentMethod = PaymentMethod.values()[input2];

                    // call finaliseBooking() in BookingController to finalise booking, and inject updated info into other controllers
                    BookingController.getInstance().finaliseBooking();
                    break;
            }
        }
    }


    /**
     * Displays pricing information for each ticket type and total price in a receipt format.
     * @param ticketPrices Map<TicketType, Double> The prices of the tickets that are being bought for each ticket type.
     * @param ticketCount Map<TicketType, Integer> The number of tickets that are being bought for each ticket type.
     */
    public void displayPrices(Map<TicketType, Double> ticketPrices, Map<TicketType, Integer> ticketCount) {

        // Print out selected ticket prices inclusive of GST and total amount
        System.out.println("Please check your booking details below:");
        System.out.printf("%-40s%-20s%-20s%-20s\n", "Item", "Unit Price", "Quantity", "Net Price");
        for (Map.Entry<TicketType, Integer> item : ticketCount.entrySet()) {
            // Item and quantity
            System.out.printf("%-40sS$ %-20.2fx%-16d", item.getKey().toString() + " TICKET", ticketPrices.get(item.getKey()), item.getValue());

            // Net price (item price multiplied by amount of items)
            System.out.printf("S$ %-20.2f\n", ticketPrices.get(item.getKey()) * item.getValue());
        }

        // Booking fee
        System.out.printf("%-40sS$ %-20.2fx%-16dS$ %-20.2f\n", "BOOKING FEE", 1.5, 1, 1.5);

        // Net total
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.printf("%-80sS$ %-20.2f\n", "NET TOTAL (INCL. GST)", transaction.getTotalPrice());
        System.out.println("-----------------------------------------------------------------------------------------");
    }

    /**
     * Set the respective attributes for transaction, booking & customer details and save the transaction into the database.
     */
    public void confirmTransaction() {
        transaction.setTransactionID();
        transaction.setMethod(paymentMethod);
        BookingController.getInstance().getBooking().setTransactionId(transaction.getTransactionID());
        CustomerController.getInstance().updateCustomer(bookerName, bookerEmail, bookerMobileNo);

        String savePath = FilePathFinder.findRootPath() + "/src/data/transactions/transaction_" + transaction.getTransactionID() + ".dat";
        DataSerializer.ObjectSerializer(savePath, transaction);
    }

    /**
     * Method that checks if the entered name for the customer is valid.
     * @param name Name of Customer inputted as a string.
     * @return Validity of the Name input as a boolean value.
     */
    private Boolean validateName(String name) {
        String checkName = name.replaceAll("[^a-zA-Z0-9-_'\"]","");
        char[] chars = checkName.toCharArray();

        if (name.length() > 70) {
            System.out.println("Sorry, we are not able to store such a long name. Please input a shorter name.");
            return false;
        }

        for (char c : chars) {
            if(!Character.isLetter(c)) {
                System.out.println("Your name should be purely alphabetic. Please try again.");
                return false;
            }
        }
        return true;
    }

    /**
     * Method that updates the total price of the transaction by adding the price of each ticket.
     * @param ticketPrices HashMap that stores the price of each ticket by its type.
     * @param ticketCount HashMap that stores the number of tickets bought for each ticket type.
     */
    private void updateTotalPrice(Map<TicketType, Double> ticketPrices, Map<TicketType, Integer> ticketCount) {
        double totalPrice = 0;

        for (Map.Entry<TicketType, Integer> item : ticketCount.entrySet()) {
            totalPrice += ticketPrices.get(item.getKey()) * item.getValue();
        }

        totalPrice += 1.5;
        transaction.setTotalPrice(totalPrice);
    }

    /**
     * Resets the Current Transaction Manager so that it is ready for another transaction.
     */
    public void reset() {
        transaction = null;
        TransactionController.getInstance();
        bookerName = null;
        bookerMobileNo = null;
        bookerEmail = null;
        exit = true;
        single_instance = null;
    }

}
