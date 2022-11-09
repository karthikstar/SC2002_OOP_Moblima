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

    public static void printCustomerBookingHistoryMenu() {
        System.out.println("How do you want to check your booking history?");
        System.out.printf(
                "1. Check Booking History via Email Address\n" +
                "2. Check Booking History via Mobile Number\n" +
                "0. Back to Customer Interface\n"
        );
        System.out.println("Please select an option between 0 to 2");
    }
}
