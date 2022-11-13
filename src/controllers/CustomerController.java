package controllers;

import entities.accounts.CustomerAccount;
import entities.booking.Booking;
import utils.DataSerializer;
import utils.FilePathFinder;

import javax.xml.crypto.Data;
import java.io.File;
import java.util.HashMap;

/**
 * CustomerController is a class which will handle customer-related issues such as checking their booking history
 */
public class CustomerController {

    // has all the mobile no.s matching to cust.id
    /**
     * A HashMap that stores all the mobile numbers matching to a customer id
     */
    private HashMap<String, Integer> idMobileHash;

    /**
     * A HashMap that stores all the emails matching to a customer id
     */
    private HashMap<String, Integer> idEmailHash; // Key: email, Value: id

    /**
     * A HashMap that stores all the customer ids matching to a customer object
     */
    private HashMap<Integer, CustomerAccount> idCustomerObjHash;

    /**
     * The current customerAccount that the CustomerController is working with
     */
    private CustomerAccount customerAccount;

    /**
     * Gets the current customerAccount object the CustomerController is working with
     * @return
     */
    public CustomerAccount getCustomerAccountObj() {
        return customerAccount;
    }

    /**
     * Sets the current customerAccount object for the CustomerController to work with
     * @param customerAccountObj
     */
    public void setCustomerAccountObj(CustomerAccount customerAccountObj) {
        this.customerAccount = customerAccountObj;
    }

    /**
     * single_instance ensures that only one instance of CustomerController can be created
     */
    private static CustomerController single_instance = null;

    /**
     * Instantiates the CustomerController singleton, and will create a new instance of CustomerController if one doesn't exist.
     * @return an instance of CustomerController
     */
    public static CustomerController getInstance() {
        if(single_instance == null) {
            single_instance = new CustomerController();
        }
        return single_instance;
    }

    /**
     * Constructor of CustomerController
     */
    private CustomerController() {
        this.idEmailHash = new HashMap<String, Integer>();
        this.idMobileHash = new HashMap<String, Integer>();

        this.idCustomerObjHash = new HashMap<Integer, CustomerAccount>();
        this.loadCustomersFromFile();
    }

    /**
     * Retrieves a CustomerAccount object from a email
     * @param email a String representing the email of the customer
     * @return a CustomerAccount object referring to the customer who has this email
     */
    private CustomerAccount getCustomerFromEmail(String email) {
        if(idCustomerObjHash.containsKey(idEmailHash.get(email))){
            return idCustomerObjHash.get(idEmailHash.get(email));
        } else {
            return null;
        }
    }

    /**
     * Retrieves a CustomerAccount object from a mobile number
     * @param mobileNo a String representing the mobile number of the customer
     * @return a CustomerAccount object referring to the customer who has this mobile number
     */
    private CustomerAccount getCustomerFromMobile(String mobileNo) {
        if(idCustomerObjHash.containsKey(idMobileHash.get(mobileNo))){
            return idCustomerObjHash.get(idMobileHash.get(mobileNo));
        } else {
            return null;
        }
    }

    /**
     * Prints the booking history of a customer
     * @param customer a CustomerAccount object referring to the customer, whose booking history is being printed here
     */
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


    /**
     * Update a customer's information and will be called during makeBooking() in BookingController
     * @param customerName is a String representing the customer's name
     * @param email is a String representing the customer's email
     * @param mobileNo is a String representing the customer's mobile number
     */
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

    /**
     * Stores the booking id into the current customer object we are working with
     * @param bookingID is an integer, representing a booking id to be put into this customer object
     */
    public void storeBooking(int bookingID) {
        // add bookingID to the customerAccount object
        getCustomerAccountObj().addBookingID(bookingID);

        // store this updated customerAccountObject, as a value to its corresponding CustomerId key in hash table
        idCustomerObjHash.put(getCustomerAccountObj().getCustomerID(), getCustomerAccountObj());
        saveCustomerFile();
        reset();
    }

    /**
     * Resets the customerController's attributes
     */
    public void reset() {
        setCustomerAccountObj(null);
        single_instance = null;
    }

    /**
     * Display the past bookings of a customer, based on their email
     */
    public void displayPastBookingViaEmail() {
        System.out.println("Please enter your email address: ");
        String userEmail = InputController.getUserEmail();
        printBookingHistory(getCustomerFromEmail(userEmail));
    }

    /**
     * Display the past bookings of a customer, based on their mobile number
     */
    public void displayPastBookingViaMobile() {
        System.out.println("Please enter your mobile no: ");
        String userMobileNumber = InputController.getUserMobileNumber();
        printBookingHistory(getCustomerFromMobile(userMobileNumber));
    }


    /**
     * Load all the files present in the specified folder and uses the information in them to update the relevant hashmaps of a CustomerAccount object.
     */
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


    /**
     * Load booking files based on their booking id
     * @param bookingID an integer, representing a booking id
     * @return a Booking object, which was stored in the files
     */
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

    /**
     * Save customer data into a file
     */
    private void saveCustomerFile() {
        String filePath = FilePathFinder.findRootPath() + "/src/data/customers/customer_" + getCustomerAccountObj().getCustomerID() + ".dat";
        DataSerializer.ObjectSerializer(filePath, getCustomerAccountObj());

    }


}
