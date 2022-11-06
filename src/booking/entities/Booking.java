package booking.entities;

import java.util.ArrayList;

public class Booking {

    private String bookingID;
    private ArrayList<Ticket> ticketsBought;


    this.ticketsBought = new ArrayList<Ticket>();

    public ArrayList<Ticket> getTicketsBought() {
        return ticketsBought;
    }

    public void setTicketsBought(ArrayList<Ticket> ticketsBought) {
        this.ticketsBought = ticketsBought;
    }
}
