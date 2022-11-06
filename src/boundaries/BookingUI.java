package boundaries;

import controllers.BookingController;

public class BookingUI {
    public BookingUI(){}
    public static BookingUI single_instance = null;

    public static BookingUI getInstance()
    {
        if(single_instance == null) {
            single_instance = new BookingUI();
        }
        return single_instance;
    }

    public static void printBookingMenu() {
        System.out.printf(
            "-------- SEAT BOOKING --------\n" +
            "1. Select a Seat\n" +
            "2. Unselect a seat\n" +
            "3. Confirm Seats and Proceed To Ticketing Portal\n" +
            "0. Exit" +
            "------------------------------\n"
        );
        System.out.println("Please select an option between 0 to 3");
    }
}
