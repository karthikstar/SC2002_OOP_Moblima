// WHERE TO IMPLEMENT THE CUSTOMER DETAILS????????????????????????????????????????????????????????????????????????????????
package entities.booking;

import controllers.BookingController;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Transaction implements Serializable {
    private String transactionID;
    private double totalPrice;
    private PaymentMethod method;
    private ArrayList<Ticket> ticketList;

    public Transaction() {
        this.transactionID = "";
        this.totalPrice = 0;
        this.method = null;
        this.ticketList = new ArrayList<Ticket>();
    }

    //Getters & Setters
    public String getTransactionID() {
        return transactionID;
    }

    // Come back to this!
    public void setTransactionID() {
        LocalDateTime dateTime= LocalDateTime.now() ;
        DateTimeFormatter newDateTime= DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        String dateTimeStr = dateTime.format(newDateTime);
        String newID = BookingController.getInstance().getShowtime().getCinema().getCinemaID() + dateTimeStr;
        this.transactionID = newID;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public PaymentMethod getMethod() {
        return method;
    }

    public void setMethod(PaymentMethod method) {
        this.method = method;
    }

    public ArrayList<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(ArrayList<Ticket> ticketList) {
        this.ticketList = ticketList;
    }
}
