package entities.booking;

public class Ticket {
    private TicketType Type;
    private double Price;
    private String seatID;

    // REMEMBER TO AMEND THESE
    //private Cinema cineplex;
    private int cinemaHall;

    public Ticket(TicketType Type) {
        this.Type = Type;
    }


    // Getters & Setters
    public TicketType getType() {
        return Type;
    }

    public void setType(TicketType type) {
        Type = type;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public String getSeatID() {
        return seatID;
    }

    public void setSeatID(String seatID) {
        this.seatID = seatID;
    }

//    public Cinema getCineplex() {
//        return cineplex;
//    }
//
//    public void setCineplex(Cinema cineplex) {
//        this.cineplex = cineplex;
//    }

    public int getCinemaHall() {
        return cinemaHall;
    }

    public void setCinemaHall(int cinemaHall) {
        this.cinemaHall = cinemaHall;
    }
}