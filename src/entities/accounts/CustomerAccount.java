package entities.accounts;

import utils.FilePathFinder;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * CustomerAccount represents an account for a Customer, capturing details such as email, mobile number so that bookings can be associated to and be retrieved by them
 */
public class CustomerAccount implements Serializable {

    /**
     * String representing the name of customer
     */
    private String customerName = null;

    /**
     * An integer representing the id of the customer
     */
    private int customerID;

    /**
     * A String representing the mobile number of the customer, who is represented by this CustomerAccount instance
     */
    private String mobileNo;

    /**
     * A String representing the email of the customer, who is represented by this CustomerAccount instance
     */
    private String email;

    /**
     * An ArrayList of integers that captures the bookingIDs associated with a customer
     */
    private ArrayList<Integer> bookingHistory = new ArrayList<>();

    /**
     * An integer to keep track of number of CustomerAccount instances created
     */
    private static int customerAccountCount = getNumberOfExistingCustomers()- 1;

    /**
     * Constructor for CustomerAccount, which is instantiated using several customer details for the customer whom this account is being created for
     * @param customerName a String representing the name of the customer
     * @param email a String representing the email of the customer
     * @param mobileNo a String representing the mobile number of the customer
     */
    public CustomerAccount(String customerName, String email, String mobileNo) {
        this.email = email;
        this.mobileNo = mobileNo;
        this.customerName = customerName;
        customerAccountCount++;
    }

    /**
     * Retrieves the number of existing customers in our data storage
     * @return an integer, representing the number of existing customers
     */
    private static int getNumberOfExistingCustomers() {
        String path = FilePathFinder.findRootPath() + "/src/data/customers";
        try {
            File directory = new File(path);
            File[] files = directory.listFiles();
        } catch(Exception e) {
            return 0;
        }

        File directory = new File(path);
        File[] files = directory.listFiles();
        if(files.length != 0) {
            return files.length;
        }
        return 0;
    }

    /**
     * Add booking id to the bookingHistory ArrayList, which captures the booking ids associated with a customer
     * @param id an integer, representing the id of a Booking instance
     */
    public void addBookingID(Integer id){
        bookingHistory.add(id);
    }

    /**
     * Retrieves the history of bookings for a particular Customer
     * @return an ArrayList of integers, representing the booking IDs associated with the customer
     */
    public ArrayList<Integer> getBookingHistory() {
        return bookingHistory;
    }

    /**
     * Gets the CustomerId for this instance
     * @return an integer, representing the CustomerID for this instance
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Gets the email of Customer associated with this CustomerAccount instance
     * @return a String representing the email of the customer
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the count of instances of CustomerAccount
     * @return an integer representing the number of instances instantiated for the CustomerAccount class
     */
    public static Integer getCustomerAccountCount() {
        return customerAccountCount;
    }

    /**
     * Gets the mobile number of the Customer associated with this CustomerAccount instance
     * @return a String representing the name of the customer
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Gets the mobile number of the Customer associated with this CustomerAccount instance
     * @return a String representing the mobile number of Customer
     */
    public String getMobileNo() {
        return mobileNo;
    }

    /**
     * Sets the id of the customer associated with this CustomerAccount instance
     * @param customerID an integer representing the id of the customer
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * Sets the booking history of customer associated with this CustomerAccount instance
     * @param bookingHistory an ArrayList of integers, which are the Booking ids associated with the customer
     */
    public void setBookingHistory(ArrayList<Integer> bookingHistory) {
        this.bookingHistory = bookingHistory;
    }

    /**
     * Sets the email of a customer associated with this CustomerAccount instance
     * @param email a String rerpesenting the email of the customer
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the mobile number of customer associated with this CustomerAccount instance
     * @param mobileNo a String representing the mobile number of the customer
     */
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    /**
     * Sets the name of customer for this CustomerAccount instance
     * @param customerName a String representing the name of the customer
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

}
