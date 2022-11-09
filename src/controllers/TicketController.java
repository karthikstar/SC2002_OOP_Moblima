package controllers;

import entities.booking.Ticket;
import entities.booking.TicketType;
import entities.cinema.Showtime;
import entities.movie.Movie;

import java.time.format.TextStyle;
import java.util.*;

public class TicketController {
    private static TicketController single_instance = null;
    public static TicketController getInstance()
    {
        if (single_instance == null) {
            single_instance = new TicketController();
        }
        return single_instance;
    }

    private Scanner sc = new Scanner(System.in);

    // Attributes
    private ArrayList<Ticket> selectedTickets = new ArrayList<Ticket>();

    private HashMap<TicketType, Integer> ticketCount = new HashMap<TicketType, Integer>();

    /**
     * This holds the various prices of the ticket types. These prices are the modifed prices
     * after implementing the price modifiers for every aspect of the booking in question
     */
    private HashMap<TicketType, Double> ticketPrices = new HashMap<TicketType, Double>();
    
    public Boolean exit = false;


    public void startTicketSelection(Showtime showtime, ArrayList<String> selectedSeats) {

        // Get new ticket prices based on showtime
        updateTicketPrices(showtime);

        exit = false;
        int maxTickets = selectedSeats.size(); // Total number of tickets available for selection
        int ticketChoices = TicketType.values().length; // Number of ticket choices available
        int ticketsLeft = maxTickets; // Tracks number of tickets left for selection
        int choice;

        while (!exit) {

            ticketsLeft = maxTickets - selectedTickets.size();
            
            System.out.println("---------------------------- TICKET SELECTION FOR SEAT -----------------------------");
            System.out.printf("You have selected tickets for %d seat(s). %d ticket(s) remaining to select for:\n", selectedSeats.size(), ticketsLeft);

            for (int i = 1; i <= ticketChoices; i++) {
                System.out.printf("%d. %s\n", i, TicketType.values()[i-1].toString());
            }
            System.out.printf("%d. Clear selected tickets\n", ticketChoices+1);
            System.out.printf("%d. Proceed to payment\n", ticketChoices+2);
            System.out.println("0. Back to seat selection menu!");
            System.out.println("-----------------------------------------------------------------------");

            System.out.println("Please enter a choice:");

            choice = InputController.getUserInt(0,ticketChoices+2);

            if (choice == 0) {
                reset();
            }

            else if (choice == ticketChoices+1) {
                clearTicketSelection();
                System.out.println("Ticket selections cleared.");
            }

            else if (choice == ticketChoices+2) {

                if (ticketsLeft == 0) {
                    for (int i = 0; i < selectedTickets.size(); i++) {
                        selectedTickets.get(i).setSeatID(selectedSeats.get(i));
                    }
                    exit = true;
                    TransactionController.getInstance().startTransaction(selectedTickets, ticketPrices, ticketCount);
                }
                else {
                    System.out.printf("Not all tickets have been selected! %d tickets remaining to select for.\n", ticketsLeft);
                }
            }
            else if (choice >= 1 && choice <= ticketChoices) {
                System.out.printf("How many %s tickets would you like to purchase? (Max %d):\n", TicketType.values()[choice-1].toString(), ticketsLeft);

                int count = InputController.getUserInt(1,ticketsLeft);

                addTicketSelection(TicketType.values()[choice-1], count);
            }
        }
    }


    public void confirmTicketSelection() {
        BookingController.getInstance().getBooking().setTicketsBought(selectedTickets);
        int movieID = BookingController.getInstance().getShowtime().getMovieId();
        MovieController.getInstance().increaseTicketsSold(movieID, selectedTickets.size());
    }

    private void addTicketSelection(TicketType ticketType, int count) {
        for (int i = 0; i < count; i++) {
            Ticket newTicket = new Ticket(ticketType);

            newTicket.setPrice(ticketPrices.get(ticketType));

            selectedTickets.add(newTicket);
        }
        ticketCount.put(ticketType, count);
    }

    private void updateTicketPrices(Showtime showtime) {
        HashMap<TicketType, Double> prices = ticketPrices;
        PriceController priceList = PriceController.getInstance();

        for (TicketType type : TicketType.values()) {
            // Get base price based on ticket type
            prices.put(type, PriceController.getInstance().getPrice("BASE"));

            // Update price based on ticket type
            prices.put(type, prices.get(type) + priceList.getPrice(type.toString()));

            // Update price based on date (see if it's a holiday)
            prices.put(type, prices.get(type) + priceList.getPrice(showtime.getDateTime().toLocalDate()));

            // Update price based on day of week
            String day = showtime.getDateTime().getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.getDefault());
            prices.put(type, prices.get(type) + priceList.getPrice(day.toUpperCase()));

            // Update price based on movie format
            prices.put(type, prices.get(type) + priceList.getPrice(showtime.getMovieType().toString()));

            // Update price based on cinema type
            prices.put(type, prices.get(type) + priceList.getPrice(showtime.getCinema().getCinemaType().toString()));
        }
    }

    private void clearTicketSelection() {
        selectedTickets.clear();
        ticketCount.clear();
    }

    public void reset() {
        selectedTickets.clear();
        ticketCount.clear();
        ticketPrices.clear();
        exit = true;
    }
}
