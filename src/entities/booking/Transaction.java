package entities.booking;

import controllers.BookingController;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Class that represents the transaction object being made for each booking.
 */
public class Transaction implements Serializable {
    /**
     * ID for the transaction.
     */
    private String transactionID;
    /**
     * Total Price being paid in the transaction.
     */
    private double totalPrice;
    /**
     * Payment Method being used in the transaction.
     */
    private PaymentMethod method;
    /**
     * List of ticket objects being purchased in the transaction.
     */
    private ArrayList<Ticket> ticketList;

    /**
     * Constructor for the Transaction object.
     */
    public Transaction() {
        this.transactionID = "";
        this.totalPrice = 0;
        this.method = null;
        this.ticketList = new ArrayList<Ticket>();
    }

    /**
     * Getter for the ID of the transaction.
     * @return Transaction ID
     */
    public String getTransactionID() {
        return transactionID;
    }

    /**
     * Setter which constructs the Transaction ID as a string given by cinema_code ("AAA_1") - Date & Time of transaction as a string.
     */
    public void setTransactionID() {
        LocalDateTime dateTime= LocalDateTime.now() ;
        DateTimeFormatter newDateTime= DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        String dateTimeStr = dateTime.format(newDateTime);
        String newID = BookingController.getInstance().getShowtime().getCinema().getCinemaID() + "-" + dateTimeStr;
        this.transactionID = newID;
    }

    /**
     * Getter for the total price being paid for in the transaction.
     * @return Total Price Paid in the transaction
     */
    public double getTotalPrice() {
        return totalPrice;
    }
    /**
     * Setter for the total price being paid for in the transaction.
     * @param totalPrice Total Price Paid to be set
     */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * Getter for the Payment Method being used in the transaction.
     * @return Payment Method used in Transaction
     */
    public PaymentMethod getMethod() {
        return method;
    }
    /**
     * Setter for the Payment Method being used in the transaction.
     * @param method Payment Method to be set
     */
    public void setMethod(PaymentMethod method) {
        this.method = method;
    }

    /**
     * Getter for the list of Ticket objects being purchased in the transaction.
     * @return List of Ticket objects being bought
     */
    public ArrayList<Ticket> getTicketList() {
        return ticketList;
    }
    /**
     * Setter for the list of Ticket objects being purchased in the transaction.
     * @param ticketList List of Ticket objects being bought to be set
     */
    public void setTicketList(ArrayList<Ticket> ticketList) {
        this.ticketList = ticketList;
    }
}
