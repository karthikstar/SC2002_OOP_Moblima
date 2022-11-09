package entities.booking;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class Booking implements Serializable {

    private String bookingID;
    private LocalDateTime dateTime;
    private ArrayList<Ticket> ticketsBought;
    private String cineplexName;
    private int cinemaNumber;
    private String movieTitle;
    private String transactionId;

    public void printBookingDetails() {
        System.out.println("-----------------------------------------------------------------------------");

        System.out.printf("Booking ID: %s\n", getBookingID());
        DateTimeFormatter format= DateTimeFormatter.ofPattern("dd MMM yyyy, hh.mma");
        System.out.printf("Booking Time: %s\n", getDateTime().format(format));
        System.out.printf("Movie: %s\n", getMovieTitle());
        System.out.printf("Cineplex: %s\n", getCineplexName());
        System.out.printf("Hall: %d\n", getcinemaNumber());
        System.out.printf("Transaction ID: %s\n\n", getTransactionId());
        System.out.printf("Seats: ");
        for (int i = 0; i < getTicketsBought().size(); i++) {
            System.out.printf("%s ", getTicketsBought().get(i).getSeatID());
        }

        System.out.println
        ("-----------------------------------------------------------------------------");
    }


    //Constructor
    public Booking(String bookingID, LocalDateTime dateTime, ArrayList<Ticket> ticketsBought, String cineplexName, int cinemaNumber, String movieTitle, String transactionId) {
        this.bookingID = bookingID;
        this.dateTime = dateTime;
        this.ticketsBought = ticketsBought;
        this.cineplexName = cineplexName;
        this.cinemaNumber = cinemaNumber;
        this.movieTitle = movieTitle;
        this.transactionId = transactionId;
    }


    //Getters & Setters
    public String getBookingID() {
        return bookingID;
    }

    public void setBookingID(String bookingID) {
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

    public int getcinemaNumber() {
        return cinemaNumber;
    }

    public void setcinemaNumber(int cinemaNumber) {
        this.cinemaNumber = cinemaNumber;
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
}
