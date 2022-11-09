package entities.accounts;

import java.io.Serializable;
import java.util.ArrayList;

public class CustomerAccount implements Serializable {

    private String customerName = null;

    private int customerID;

    private String mobileNo;

    private String email;

    private ArrayList<Integer> bookingHistory = new ArrayList<>();

    private static Integer customerAccountCount = 0;

    public CustomerAccount(String customerName, String email, String mobileNo) {
        this.email = email;
        this.mobileNo = mobileNo;
        this.customerName = customerName;
        customerAccountCount++;
    }

    public void addBookingID(Integer id){
        bookingHistory.add(id);
    }

    public ArrayList<Integer> getBookingHistory() {
        return bookingHistory;
    }

    public int getCustomerID() {
        return customerID;
    }

    public String getEmail() {
        return email;
    }

    public static Integer getCustomerAccountCount() {
        return customerAccountCount;
    }

    public static void setCustomerAccountCount(Integer customerAccountCount) {
        CustomerAccount.customerAccountCount = customerAccountCount;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public void setBookingHistory(ArrayList<Integer> bookingHistory) {
        this.bookingHistory = bookingHistory;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

}
