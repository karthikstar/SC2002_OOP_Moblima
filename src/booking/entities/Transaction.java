// WHERE TO IMPLEMENT THE CUSTOMER DETAILS?
package booking.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction implements Serializable {
    private String transactionID;
    private double totalPrice;
    private PaymentMethod method;

    public Transaction() {
        this.transactionID = "";
        this.totalPrice = 0;
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

    public PaymentMethod getMethod() {
        return method;
    }

    public void setMethod(PaymentMethod method) {
        this.method = method;
    }
}
