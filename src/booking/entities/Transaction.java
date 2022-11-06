package booking.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction implements Serializable {
    private String transactionID;
    private double totalPrice;
    private ArrayList<Ticket> ticketsBought;
    private paymentMethod method;

    public Transaction() {
        this.transactionID = "";
        this.totalPrice = 0;
        this.ticketsBought = new ArrayList<Ticket>();
        this.method = null;
    }

    //Getters & Setters
    public String getTransactionID() {
        return transactionID;
    }

    // Come back to this!
    public void setTransactionID(String transactionID) {
        LocalDateTime dateTime= LocalDateTime.now() ;
        DateTimeFormatter newDateTime= DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        String dateTimeStr = dateTime.format(newDateTime);
        //String newID = BookingManager.getInstance().getShowtime().getCinema().getCinemaID() + dateTimeStr;
        //this.transactionID = newID;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ArrayList<Ticket> getTicketsBought() {
        return ticketsBought;
    }

    public void setTicketsBought(ArrayList<Ticket> ticketsBought) {
        this.ticketsBought = ticketsBought;
    }

    public paymentMethod getMethod() {
        return method;
    }

    public void setMethod(paymentMethod method) {
        this.method = method;
    }
}
