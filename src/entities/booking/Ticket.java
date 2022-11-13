package entities.booking;

import entities.cinema.Cinema;

import java.io.Serializable;

/**
 * Class that represents the ticket object for each showtime.
 */
public class Ticket implements Serializable {
    /**
     * Ticket Type based on age and day of showtime.
     */
    private TicketType Type;
    /**
     * Price of the Ticket.
     */
    private double Price;
    /**
     * ID of the seat represented by the ticket.
     */
    private String seatID;
    /**
     * Cinema object of the ticket being booked for.
     */
    private Cinema cineplex;
    /**
     * Cinema Number for the Ticket for the movie showtime viewing.
     */
    private int cinemaHall;

    /**
     * Constructor for the Ticket object.
     * @param Type Type of Ticket
     */
    public Ticket(TicketType Type) {
        this.Type = Type;
    }

    /**
     * Getter for the Ticket Type.
     * @return Ticket Type
     */
    public TicketType getType() {
        return Type;
    }
    /**
     * Setter for the Ticket Type.
     * @param type Ticket Type to be set
     */
    public void setType(TicketType type) {
        Type = type;
    }

    /**
     * Getter for the price of the ticket.
     * @return Ticket Price
     */
    public double getPrice() {
        return Price;
    }
    /**
     * Setter for the price of the ticket.
     * @param price Ticket Price to be set
     */
    public void setPrice(double price) {
        Price = price;
    }

    /**
     * Getter for the ID of the seat represented by the ticket.
     * @return Seat ID
     */
    public String getSeatID() {
        return seatID;
    }
    /**
     * Setter for the ID of the seat represented by the ticket.
     * @param seatID Seat ID to be set
     */
    public void setSeatID(String seatID) {
        this.seatID = seatID;
    }

    /**
     * Getter for the cinema object where ticket is for.
     * @return Cinema Object
     */
    public Cinema getCineplex() {
        return cineplex;
    }
    /**
     * Setter for the cinema object where ticket is for.
     * @param cineplex Cinema Object to be set
     */
    public void setCineplex(Cinema cineplex) {
        this.cineplex = cineplex;
    }

    /**
     * Getter for the cinema/hall number for which ticket showtime is for viewing.
     * @return Cinema/Hall Number
     */
    public int getCinemaHall() {
        return cinemaHall;
    }
    /**
     * Setter for the cinema/hall number for which ticket showtime is for viewing.
     * @param cinemaHall Cinema/Hall Number to be set
     */
    public void setCinemaHall(int cinemaHall) {
        this.cinemaHall = cinemaHall;
    }
}