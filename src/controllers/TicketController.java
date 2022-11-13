package controllers;

import entities.booking.Ticket;
import entities.booking.TicketType;
import entities.cinema.CinemaType;
import entities.cinema.Showtime;
import entities.movie.Movie;

import java.time.format.TextStyle;
import java.util.*;

/**
 * Class that manages the functionalities required in that of a ticket.
 */
public class TicketController {
    /**
     * Singleton Constructor
     */
    private static TicketController single_instance = null;
    /**
     * Singleton Constructor
     */
    public static TicketController getInstance()
    {
        if (single_instance == null) {
            single_instance = new TicketController();
        }
        return single_instance;
    }

    /**
     * List of Ticket Objects being managed.
     */
    private ArrayList<Ticket> selectedTickets = new ArrayList<Ticket>();
    /**
     * HashMap that stores the number of tickets bought for each ticket type.
     */
    private HashMap<TicketType, Integer> ticketCount = new HashMap<TicketType, Integer>();

    /**
     * This holds the various prices of the ticket types. These prices are the modifed prices
     * after implementing the price modifiers for every aspect of the booking.
     */
    private HashMap<TicketType, Double> ticketPrices = new HashMap<TicketType, Double>();
    /**
     * Controls loops using a boolean variable.
     */
    public Boolean exit = false;

    /**
     * Method that begins the process of selecting the tickets to be purchased.
     * @param showtime Showtime object being purchased.
     * @param selectedSeats List of Strings representing the seat numbers being bought for.
     */
    public void startTicketSelection(Showtime showtime, ArrayList<String> selectedSeats) {

        // Get new ticket prices based on showtime
        updateTicketPrices(showtime);

        exit = false;
        int maxTickets = selectedSeats.size(); // Total number of tickets available for selection
        int ticketChoices = TicketType.values().length-2; // Number of ticket choices available
        int ticketsLeft = maxTickets; // Tracks number of tickets left for selection
        int choice;

        System.out.println("\n---------------PRICE LIST---------------");
        PriceController.getInstance().printAllPriceChangers();

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

            if(ticketsLeft == 0) {
                choice = InputController.getUserInt(0,ticketChoices+2);
                while (choice >= 1 && choice <= 3) {
                    System.out.println("There are no tickets left available for selection, please proceed to payment or add/clear seats!");
                    System.out.println("Please enter a choice: ");
                    choice = InputController.getUserInt();
                }
            } else {
                choice = InputController.getUserInt(0,ticketChoices+2);
            }

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

    /**
     * Method that sets the needed attributes such as selected tickets, movie ID and number of tickets sold in the movie to be updated.
     */
    public void confirmTicketSelection() {
        BookingController.getInstance().getBooking().setTicketsBought(selectedTickets);
        int movieID = BookingController.getInstance().getShowtime().getMovieId();
        MovieController.getInstance().increaseTicketsSold(movieID, selectedTickets.size());
    }

    /**
     * Method that sets the ticket prices based on the selected ticket, then adds them to the ticket selection list including the number of each ticket.
     * @param ticketType Ticket Type being bought for.
     * @param count Number of tickets bought for the ticket types in question.
     */
    private void addTicketSelection(TicketType ticketType, int count) {
        for (int i = 0; i < count; i++) {
            Ticket newTicket = new Ticket(ticketType);

            newTicket.setPrice(ticketPrices.get(ticketType));

            selectedTickets.add(newTicket);
        }
        ticketCount.put(ticketType, count);
    }

    /**
     * Updates the ticketPrices HashMap based on the various PriceChangers of the current showtime.
     * @param showtime ShowTime object tickets are being bought for.
     */
    private void updateTicketPrices(Showtime showtime) {
        HashMap<TicketType, Double> prices = ticketPrices;
        PriceController priceList = PriceController.getInstance();

        for (TicketType type : TicketType.values()) {
            // Get base price based on ticket type
            prices.put(type, PriceController.getInstance().getPrice((CinemaType.STANDARD)));

            // Update price based on ticket type
            prices.put(type, prices.get(type) + priceList.getPrice(type));

            // Update price based on date (see if it's a holiday)
            Boolean isHoliday = HolidayController.getInstance().isHoliday(showtime.getDateTime().toLocalDate());
            if (isHoliday){
                prices.put(type, prices.get(type) + priceList.getPrice(TicketType.HOLIDAY));
            }

            // Update price based on day of week
            if (showtime.isWeekend()){
                prices.put(type, prices.get(type) + priceList.getPrice(TicketType.WEEKEND));
            }

            // Update price based on movie type
            prices.put(type, prices.get(type) + priceList.getPrice(showtime.getMovieType()));

            // Update price based on cinema type
            prices.put(type, prices.get(type) + priceList.getPrice(showtime.getCinema().getCinemaType()));
        }
    }

    /**
     * Resets the selected tickets HashMap & and ticket count HashMap.
     */
    private void clearTicketSelection() {
        selectedTickets.clear();
        ticketCount.clear();
    }

    /**
     * Resets the Ticket Controller object so that it is now ready to manage another set of tickets in another showtime transaction.
     */
    public void reset() {
        selectedTickets.clear();
        ticketCount.clear();
        ticketPrices.clear();
        exit = true;
        single_instance = null;
    }
}
