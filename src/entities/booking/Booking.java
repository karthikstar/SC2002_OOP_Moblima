package entities.booking;

import utils.DataSerializer;
import utils.FilePathFinder;

import java.io.File;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.time.LocalDateTime;

/**
 * Class that stores the booking object when a customer makes a booking of a showtime.
 */
public class Booking implements Serializable {
    /**
     * ID for the booking.
     */
    private int bookingID;
    /**
     * Date and Time of the booking.
     */
    private LocalDateTime dateTime;
    /**
     * List of Ticket objects purchased in the booking.
     */
    private ArrayList<Ticket> ticketsBought;
    /**
     * Name of the cineplex booking is made at.
     */
    private String cineplexName;
    /**
     * ID of the cinema at which booking is done in format "AAA_1".
     */
    private String cinemaID;
    /**
     * Title of movie being booked for.
     */
    private String movieTitle;
    /**
     * ID of the transaction being made in the booking.
     */
    private String transactionId;
    /**
     * ID of the movie being booked.
     */
    private int movieID;
    /**
     * Number of existing bookings stored in the database.
     */
    private static int bookingCount = getNumberOfExistingBookings() - 1;

    /**
     * Method that prints all the information and attributes in the booking object onto the user console.
     */
    public void printBookingDetails() {
        System.out.println("-----------------------------------------------------------------------------");

        System.out.printf("Booking ID: %d\n", getBookingID());
        DateTimeFormatter format= DateTimeFormatter.ofPattern("dd MMM yyyy, hh.mma");
        System.out.printf("Booking Time: %s\n", getDateTime().format(format));
        System.out.printf("Movie: %s\n", getMovieTitle());
        System.out.printf("Cineplex: %s\n", getCineplexName());
        System.out.printf("Cinema ID / Hall No: %s\n", getCinemaID().split("_")[1]);
        System.out.printf("Transaction ID: %s\n", getTransactionId());
        System.out.printf("Seats: ");
        for (int i = 0; i < getTicketsBought().size(); i++) {
            System.out.printf("%s ", getTicketsBought().get(i).getSeatID());
        }

        System.out.println
        ("\n-----------------------------------------------------------------------------");
    }

    /**
     * Constructor for the booking object.
     */
    public Booking(){
        bookingCount++;
    }

    /**
     * Gets the number of existing booking objects stored in the database.
     * @return Number of existing bookings stored in the database
     */
    private static int getNumberOfExistingBookings() {
        String path = FilePathFinder.findRootPath() + "/src/data/bookings";
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
     * Getter for the number of bookings stored.
     * @return Number of Bookings stored
     */
    public static int getBookingCount() {
        return bookingCount;
    }
    /**
     * Setter for the number of bookings stored.
     * @param bookingCount Number of Bookings to be set
     */
    public static void setBookingCount(int bookingCount) {
        Booking.bookingCount = bookingCount;
    }

    /**
     * Getter for the Booking ID.
     * @return Booking ID
     */
    public int getBookingID() {
        return bookingID;
    }

    /**
     * Setter for the Booking ID.
     * @param bookingID Booking ID to be set
     */
    public void setBookingID(Integer bookingID) {
        this.bookingID = bookingID;
    }

    /**
     * Getter for the date and time of the booking.
     * @return Date and Time of booking
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }
    /**
     * Setter for the date and time of the booking.
     * @param dateTime Date and Time of booking to be set
     */
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Getter for the number of tickets purchased in the booking.
     * @return Number of Tickets Bought
     */
    public ArrayList<Ticket> getTicketsBought() {
        return ticketsBought;
    }
    /**
     * Setter for the number of tickets purchased in the booking.
     * @param ticketsBought Number of Tickets Bought to be set
     */
    public void setTicketsBought(ArrayList<Ticket> ticketsBought) {
        this.ticketsBought = ticketsBought;
    }

    /**
     * Getter for the name of the cineplex to be booked.
     * @return Cineplex Name
     */
    public String getCineplexName() {
        return cineplexName;
    }
    /**
     * Setter for the name of the cineplex to be booked.
     * @param cineplexName Cineplex Name to be set
     */
    public void setCineplexName(String cineplexName) {
        this.cineplexName = cineplexName;
    }

    /**
     * Getter for the ID of the cinema being booked.
     * @return Cinema ID
     */
    public String getCinemaID() {
        return cinemaID;
    }
    /**
     * Setter for the ID of the cinema being booked.
     * @param cinemaID Cinema ID to be set
     */
    public void setCinemaID(String cinemaID) {
        this.cinemaID = cinemaID;
    }

    /**
     * Getter for the title of the movie being booked for.
     * @return Movie Title of Booking
     */
    public String getMovieTitle() {
        return movieTitle;
    }
    /**
     * Setter for the title of the movie being booked for.
     * @param movieTitle Move Title of Booking to be set
     */
    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    /**
     * Getter for the ID of the transaction being made in the booking.
     * @return Transaction ID
     */
    public String getTransactionId() {
        return transactionId;
    }
    /**
     * Setter for the ID of the transaction being made in the booking.
     * @param transactionId  Transaction ID to be set
     */
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * Setter for the ID of the movie being booked.
     * @param movieID Movie ID of booking to be set
     */
    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }
    /**
     * Getter for the ID of the movie being booked.
     * @return Movie ID to be set
     */
    public int getMovieID() {
        return movieID;
    }


}
