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

public class TransactionController {
    private static TransactionController single_instance = null;
    public static TransactionController getInstance()
    {
        if (single_instance == null) {
            single_instance = new TransactionController();
        }
        return single_instance;
    }

    private Transaction transaction = new Transaction();
    private String bookerName;
    private String bookerMobileNo;
    private String bookerEmail;
    private PaymentMethod paymentMethod;
    public Boolean exit = false;


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
     * Displays pricing information and total price
     * @param ticketPrices Map<TicketType, Double> The prices of the tickets that are being bought
     * @param ticketCount Map<TicketType, Integer> The number of tickets that are being bought
     */
    public void displayPrices(Map<TicketType, Double> ticketPrices, Map<TicketType, Integer> ticketCount) {

        // Print out selected ticket prices inclusive of GST and total amount
        System.out.println("Please check your booking details below:");
        System.out.printf("%-40s%-20s%-20s%-20s\n", "Item", "Unit Price", "Quantity", "Net Price");
        for (Map.Entry<TicketType, Integer> item : ticketCount.entrySet()) {
            // Item and quantity
            System.out.printf("%-40s%-20.2fx%-19d", item.getKey().toString() + " TICKET", ticketPrices.get(item.getKey()), item.getValue());

            // Net price (item price multiplied by amount of items)
            System.out.printf("%-20.2f\n", ticketPrices.get(item.getKey()) * item.getValue());
        }

        // Booking fee
        System.out.printf("%-40s%-20.2fx%-19d%-20.2f\n", "BOOKING FEE", 1.5, 1, 1.5);

        // Net total
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.printf("%-80s%-20.2f\n", "NET TOTAL (INCL. GST)", transaction.getTotalPrice());
        System.out.println("-----------------------------------------------------------------------------------------");
    }


    public void confirmTransaction() {
        transaction.setTransactionID();
        transaction.setMethod(paymentMethod);
        BookingController.getInstance().getBooking().setTransactionId(transaction.getTransactionID());
        CustomerController.getInstance().updateCustomer(bookerName, bookerEmail, bookerMobileNo);

        String savePath = FilePathFinder.findRootPath() + "/src/data/transactions/transaction_" + transaction.getTransactionID() + ".dat";
        DataSerializer.ObjectSerializer(savePath, transaction);
    }

    // Private methods
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

    private void updateTotalPrice(Map<TicketType, Double> ticketPrices, Map<TicketType, Integer> ticketCount) {
        double totalPrice = 0;

        for (Map.Entry<TicketType, Integer> item : ticketCount.entrySet()) {
            totalPrice += ticketPrices.get(item.getKey()) * item.getValue();
        }

        totalPrice += 1.5;
        transaction.setTotalPrice(totalPrice);
    }

    public void reset() {
        transaction = null;
        bookerName = null;
        bookerMobileNo = null;
        bookerEmail = null;
        exit = true;
    }

}
