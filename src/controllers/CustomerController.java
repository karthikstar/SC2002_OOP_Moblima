package controllers;

import entities.accounts.CustomerAccount;
import entities.booking.Booking;
import utils.DataSerializer;
import utils.FilePathFinder;

import javax.xml.crypto.Data;
import java.io.File;
import java.util.HashMap;

public class CustomerController {

    // has all the mobile no.s matching to cust.id
    private HashMap<String, Integer> idMobileHash;

    // has all the emails matching to cust id
    private HashMap<String, Integer> idEmailHash; // Key: email, Value: id

    // has all the customer ids matching to the cust object
    private HashMap<Integer, CustomerAccount> idCustomerObjHash;

    private CustomerAccount customerAccount;

    // gets the current customerAccount object
    public CustomerAccount getCustomerAccountObj() {
        return customerAccount;
    }

    public void setCustomerAccountObj(CustomerAccount customerAccountObj) {
        this.customerAccount = customerAccountObj;
    }

    private static CustomerController single_instance = null;

    public static CustomerController getInstance() {
        if(single_instance == null) {
            single_instance = new CustomerController();
        }
        return single_instance;
    }

    // Constructor
    public CustomerController() {
        this.idEmailHash = new HashMap<String, Integer>();
        this.idMobileHash = new HashMap<String, Integer>();

        this.idCustomerObjHash = new HashMap<Integer, CustomerAccount>();
        this.loadCustomersFromFile();
    }

    private CustomerAccount getCustomerFromEmail(String email) {
        if(idCustomerObjHash.containsKey(idEmailHash.get(email))){
            return idCustomerObjHash.get(idEmailHash.get(email));
        } else {
            return null;
        }
    }

    private CustomerAccount getCustomerFromMobile(String mobileNo) {
        if(idCustomerObjHash.containsKey(idMobileHash.get(mobileNo))){
            return idCustomerObjHash.get(idMobileHash.get(mobileNo));
        } else {
            return null;
        }
    }

    private void printBookingHistory(CustomerAccount customer) {
        Booking bookingToPrint;

        if(customer == null) {
            System.out.println("Sorry, no records were found");
        } else {
            System.out.println("Here are your past bookings: ");

            for(int i = 0; i < customer.getBookingHistory().size(); i++) {
                // retrieve the booking ids belonging to customer, and load them from the stored files.
                bookingToPrint = loadBookingFromFile(customer.getBookingHistory().get(i));

                if(bookingToPrint != null) {
                    bookingToPrint.printBookingDetails();
                }

            }
        }
    }


    // this function will be called during makeBooking() in BookingController

    // Update a customer's information
    public void updateCustomer(String customerName, String email, String mobileNo) {
        CustomerAccount currentCustomer;

        // retrieve customerid based on given mobile no, and check if the idCustomerObj hashtable has the customer id
        if(idCustomerObjHash.containsKey(idMobileHash.get(mobileNo))) {
            currentCustomer = getCustomerFromMobile(mobileNo);
        }
        // else, create new customer,
        else {
            currentCustomer = new CustomerAccount(customerName,email, mobileNo);
            currentCustomer.setCustomerID(CustomerAccount.getCustomerAccountCount()); // no need to + 1, as when we instantiate, count increases by 1 hence we can use that for id


            idEmailHash.put(email, currentCustomer.getCustomerID());
            idMobileHash.put(mobileNo, currentCustomer.getCustomerID());
        }
        setCustomerAccountObj(currentCustomer);
    }


    // Store this bookingID into current customer obj. - called by BookingController
    public void storeBooking(int bookingID) {
        // add bookingID to the customerAccount object
        getCustomerAccountObj().addBookingID(bookingID);

        // store this updated customerAccountObject, as a value to its corresponding CustomerId key in hash table
        idCustomerObjHash.put(getCustomerAccountObj().getCustomerID(), getCustomerAccountObj());
        saveCustomerFile();
        reset();
    }

    // resets the customerContoller's attributes
    public void reset() {
        setCustomerAccountObj(null);
        single_instance = null;
    }

    // display past bookings via email
    public void displayPastBookingViaEmail() {
        System.out.println("Please enter your email address: ");
        String userEmail = InputController.getUserEmail();
        printBookingHistory(getCustomerFromEmail(userEmail));
    }

    // display past bookings via mobile number
    public void displayPastBookingViaMobile() {
        System.out.println("Please enter your mobile no: ");
        String userMobileNumber = InputController.getUserMobileNumber();
        printBookingHistory(getCustomerFromMobile(userMobileNumber));
    }


    // Load all customer data from file
    private void loadCustomersFromFile() {
        String folder = FilePathFinder.findRootPath() + "/src/data/customers";
        File[] files = null;

        try {
            File directory = new File(folder);
            files = directory.listFiles();
        } catch(Exception e) {
            System.out.println("Exception occurred!");
        }

        if(files != null) {
            for(int i = 0; i < files.length; i++) {
                if (files[i].getName().equalsIgnoreCase(".gitkeep")) continue;
                String filePath = files[i].getPath();
                CustomerAccount newCustomer = (CustomerAccount) DataSerializer.ObjectDeserializer(filePath);

                idEmailHash.put(newCustomer.getEmail(), newCustomer.getCustomerID());
                idMobileHash.put(newCustomer.getMobileNo(), newCustomer.getCustomerID());
                idCustomerObjHash.put(newCustomer.getCustomerID(), newCustomer);
            }
        }
    }

    // load booking from stored files, based on bookingID.
    private Booking loadBookingFromFile(Integer bookingID) {
        Booking booking = null;

        String folder = FilePathFinder.findRootPath() + "/src/data/bookings";
        File[] files;
        try {
            File directory = new File(folder);
            files = directory.listFiles();
        } catch(Exception e) {
            return null;
        }

        if(files != null) {
            for(int i = 0; i < files.length; i++) {
                String filePath = files[i].getPath();
                if(filePath.contains(String.valueOf(bookingID))) {
                    booking = (Booking) DataSerializer.ObjectDeserializer(filePath);
                }
            }
            return booking;
        }
        // if no match, return null
        return null;
    }


    // save customer into a file
    private void saveCustomerFile() {
        String filePath = FilePathFinder.findRootPath() + "/src/data/customers/customer_" + getCustomerAccountObj().getCustomerID() + ".dat";
        DataSerializer.ObjectSerializer(filePath, getCustomerAccountObj());

    }


}
