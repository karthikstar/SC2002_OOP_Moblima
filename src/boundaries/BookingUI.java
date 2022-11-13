package boundaries;

import controllers.BookingController;

/**
 * Stores the UI for booking instances to be printed.
 */
public class BookingUI {
    /**
     * Constructor for BookingUI.
     */
    public BookingUI(){}

    /**
     * Singleton Constructor
     */
    public static BookingUI single_instance = null;

    /**
     * Instantiates the BookingUI singleton and creates an instance if one does not already exist
     * @return an instance of BookingUI
     */
    public static BookingUI getInstance()
    {
        if(single_instance == null) {
            single_instance = new BookingUI();
        }
        return single_instance;
    }

    /**
     * Prints the booking menu options.
     */
    public static void printBookingMenu() {
        System.out.printf(
            "-------- SEAT BOOKING --------\n" +
            "1. Select a Seat\n" +
            "2. Unselect a seat\n" +
            "3. Confirm Seats and Proceed To Ticketing Portal\n" +
            "0. Exit\n" +
            "------------------------------\n"
        );
        System.out.println("Please select one of the above choices between 0 to 3:");
    }
    /**
     * Prints the customer checking booking history menu.
     */
    public static void printCustomerBookingHistoryMenu() {
        System.out.println("How do you want to check your booking history?");
        System.out.printf(
                "1. Check Booking History via Email Address\n" +
                "2. Check Booking History via Mobile Number\n" +
                "0. Back to Customer Interface\n"
        );
        System.out.println("Please select one of the above choices between 0 to 2:");
    }
}
