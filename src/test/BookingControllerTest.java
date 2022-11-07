package test;

import controllers.BookingController;
import entities.booking.Booking;
import entities.cinema.Cineplex;

import java.io.IOException;

public class BookingControllerTest {
    public static void main(String[] args) throws IOException {
        Cineplex cineplex1 = new Cineplex("AMK");
        System.out.println("Booking Controller");
        BookingController bookingController = BookingController.getInstance();
//        bookingController.setCinemaSeatingLayout(cineplex1.getCinemaList().get(0).getCinemaSeatLayout());
        bookingController.displaySeatingPlan(cineplex1.getCinemaList().get(0).getCinemaSeatLayout());

    }
}
