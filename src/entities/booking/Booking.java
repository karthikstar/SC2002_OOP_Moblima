package entities.booking;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class Booking implements Serializable {

    private int bookingID;
    private LocalDateTime dateTime;
    private ArrayList<Ticket> ticketsBought;
    private String cineplexName;
    private String cinemaID;
    private String movieTitle;
    private String transactionId;

    private int movieID;

    private static int bookingCount = 0;

    public void printBookingDetails() {
        System.out.println("-----------------------------------------------------------------------------");

        System.out.printf("Booking ID: %d\n", getBookingID());
        DateTimeFormatter format= DateTimeFormatter.ofPattern("dd MMM yyyy, hh.mma");
        System.out.printf("Booking Time: %s\n", getDateTime().format(format));
        System.out.printf("Movie: %s\n", getMovieTitle());
        System.out.printf("Cineplex: %s\n", getCineplexName());
        System.out.printf("Cinema ID / Hall No: %s\n", getCinemaID());
        System.out.printf("Transaction ID: %s\n", getTransactionId());
        System.out.printf("Seats: ");
        for (int i = 0; i < getTicketsBought().size(); i++) {
            System.out.printf("%s ", getTicketsBought().get(i).getSeatID());
        }

        System.out.println
        ("\n-----------------------------------------------------------------------------");
    }


    //Constructor
    public Booking(Integer bookingID, LocalDateTime dateTime, ArrayList<Ticket> ticketsBought, String cineplexName, String cinemaID, String movieTitle, int movieID, String transactionId) {
        this.bookingID = bookingID;
        this.dateTime = dateTime;
        this.ticketsBought = ticketsBought;
        this.cineplexName = cineplexName;
        this.cinemaID = cinemaID;
        this.movieTitle = movieTitle;
        this.movieID = movieID;
        this.transactionId = transactionId;
        bookingCount++;
    }

    public Booking(){
        bookingCount++;
    }


    public static int getBookingCount() {
        return bookingCount;
    }

    public static void setBookingCount(int bookingCount) {
        Booking.bookingCount = bookingCount;
    }

    //Getters & Setters
    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(Integer bookingID) {
        this.bookingID = bookingID;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public ArrayList<Ticket> getTicketsBought() {
        return ticketsBought;
    }

    public void setTicketsBought(ArrayList<Ticket> ticketsBought) {
        this.ticketsBought = ticketsBought;
    }

    public String getCineplexName() {
        return cineplexName;
    }

    public void setCineplexName(String cineplexName) {
        this.cineplexName = cineplexName;
    }

    public String getCinemaID() {
        return cinemaID;
    }

    public void setCinemaID(String cinemaID) {
        this.cinemaID = cinemaID;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public int getMovieID() {
        return movieID;
    }
}
