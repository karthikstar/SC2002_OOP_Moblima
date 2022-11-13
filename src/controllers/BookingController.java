package controllers;

import boundaries.BookingUI;
import boundaries.CustomerApp;
import boundaries.MainApp;
import entities.booking.Booking;
import entities.booking.Ticket;
import entities.cinema.CinemaAvailability;
import entities.cinema.Showtime;
import utils.DataSerializer;
import utils.FilePathFinder;

import javax.xml.crypto.Data;
import java.awt.print.Book;
import java.io.File;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * BookingController is a class which will handle all the booking related issues. It will interact with other controllers to coordinate the entire booking process.
 */
public class BookingController {
    /**
     * An ArrayList of Strings representing the current seating plan of the cinema, including selected seats
     */
    private ArrayList<String> cinemaSeatingLayout;

    /**
     * An ArrayList of Strings representing the current selected seats
     */
    private ArrayList<String> selectedSeats = new ArrayList<>();

    // check for seat column no, and its index position in the string, store these 2 values as a key value pair in hashmap
    /**
     * A hashmap that stores the seat column number and its index position in the string as key value pairs
     */
    private HashMap<Integer, Integer> storeColumnNoAndIndex = new HashMap<>();

    /**
     * A Hashmap that Stores the row and respective column choices
     */
    private HashMap<Character, ArrayList<Integer>> storeRowAndColChoices = new HashMap<>();

    /**
     * This is the current booking that is being worked om
     */
    private Booking booking = null;

    /**
     * This is the current showtime that this booking is being done for
     */
    private Showtime showTime = null;

    /**
     * This is to enable exiting out of the booking window
     */
    public Boolean exitBooking = false;

    /**
     * Tracks whether BookingController has been instantiated previously
     */
    public static BookingController single_instance = null;

    /**
     * Instantiates the BookingController singleton. Creates a new instance if no previous instance is found.
     * @return an instance of BookingController
     */
    public static BookingController getInstance()
    {
        if(single_instance == null) {
            single_instance = new BookingController();
        }
        return single_instance;
    }

    /**
     * Constructor of BookingController, which also calls reset to clear its memory to prepare for a new booking
     */
    private BookingController(){
        resetData();
    }

    /**
     * Resets data of this BookingController instance so that data doesn't persist, in the event that user clicks back
     */
    public void resetData() {
        setBooking(null);
        setShowTime(null);
        setCinemaSeatingLayout(null);
        getSelectedSeats().clear();
        getStoreColumnNoAndIndex().clear();
        getStoreRowAndColChoices().clear();
        exitBooking = true;
        single_instance = null;
    }

    /**
     * Starts the booking process by intiialising the seat selection procedure.It shows the available seats in the cinema for the particular showtime, for selection by customers.
     * @param showtime a Showtime object which represents the particular showtime for which we are intiialising this seat selection procedure for
     */
    public void initSeatSelection(Showtime showtime) {
        setShowTime(showtime);

        // set CinemaSeatingLayout to a cloned copy so that we don't change the originial seating layout until confirmation
        ArrayList<String> SeatingLayoutClone = cloneCinemaSeatingLayout(showtime.getCinema().getCinemaSeatLayout());

        setCinemaSeatingLayout(SeatingLayoutClone);

        // Looking for row with seat column numbers.
        for(int row = 0; row < getCinemaSeatingLayout().size(); row++) {

            System.out.println(row);
            String rowString = getCinemaSeatingLayout().get(row);

            if(rowString.length() == 0) {
                continue;
            }

            int colIndex = 0;
            boolean foundSeatColumnRow = false;

            while(colIndex < rowString.length()){
                char c = rowString.charAt(colIndex);

                if(Character.isDigit(c)) {

                    foundSeatColumnRow = true;
                    String seatColumnNo = Character.toString(c);

                    // if next char is a digit as well, means it is a double digit seatColumnNo
                    if(colIndex + 1 < rowString.length() && Character.isDigit(rowString.charAt(colIndex + 1))) {
                        seatColumnNo = seatColumnNo + Character.toString(rowString.charAt(colIndex + 1));
                        colIndex++;
                    }
                    // store the Seat's column no and its index position in the string, inside the hashmap.
                    getStoreColumnNoAndIndex().put(Integer.valueOf(seatColumnNo), colIndex);

                }
                colIndex++;
            }
            // once we have found the row string containing the seat col numbers, break
            if(foundSeatColumnRow){
                break;
            }
        }

        // Display Booking UI until user exits
        exitBooking = false;
        while(!exitBooking) {
            // Check if showtime has reached full capacity
            if(showtime.getStatus() == CinemaAvailability.FULL_CAPACITY) {
                System.out.println("Unfortunately, this showtime has reached full capacity! Please try another showtime.");
                exitBooking = true;
                resetData();
                return;
            }

            // If showtime still has seats, dispaly cinema's seating plan.
            displaySeatingPlan(getCinemaSeatingLayout());

            BookingUI.printBookingMenu();
            int minBookingMenuChoice = 0;
            int maxBookingMenuChoice = 3;
            int choice = InputController.getUserInt(minBookingMenuChoice,maxBookingMenuChoice);

            switch (choice) {
                case 0:
                    exitBooking = true;
                    resetData();
                    break;
                case 1:
                    addSeat();
                    break;
                case 2:
                    removeSelectedSeat();
                    break;
                case 3: //Ticket Selection - requires at least 1 seat to be selected. if no seats selected, dont allow ticket selection
                    if(getSelectedSeats().size() > 0){
                        TicketController.getInstance().startTicketSelection(getShowtime(),getSelectedSeats());
                    } else {
                        System.out.println("You have not added any seats! Please select at least one seat before entering into our Ticketing Portal");
                    }
                    break;
                default:
                    System.out.println("Invalid Input! Please enter a valid integer between 0 to 3.");
                    break;

            }
        }

    }

    /**
     * Finalises the booking procedure after payment is made, by injecting this confirmation data into other controllers
     */
    public void finaliseBooking() {
        // confirm all the selected seats
        for(int i = 0; i < getSelectedSeats().size(); i++){
            updateCinemaSeatingLayout("confirmSeat", getSelectedSeats().get(i));
        }

        // Update the current showtime with the new cinema seating plan
        getShowtime().getCinema().setCinemaSeatLayout(getCinemaSeatingLayout());
        getShowtime().updateAvailability();

        // Increase number of occupied seats & tickets sold
        getShowtime().increaseOccupiedNoOfSeats(getSelectedSeats().size());

        // Save the new showtime status
        ShowtimeController.getInstance().saveShowtime(getShowtime(), getShowtime().getShowTimeId());

        // Create a new booking and fill it up with confirmed info
        setBooking(new Booking());

        //confirm tickets and transaction
        TicketController.getInstance().confirmTicketSelection();
        TransactionController.getInstance().confirmTransaction();

        // Update Booking's Date and time
        getBooking().setDateTime(getShowtime().getDateTime());

        // Update booking with movie id, cineplexName and cinema id
        getBooking().setMovieID(showTime.getMovieId());
        getBooking().setMovieTitle(MovieController.getInstance().getMoviebyID(getBooking().getMovieID()).getTitle());
        getBooking().setCineplexName(showTime.getCinema().getCineplexName());
        getBooking().setCinemaID(showTime.getCinema().getCinemaID());

        getBooking().setBookingID(Booking.getBookingCount()); // no need to +1 as we initialised new booking, which increments count by 1.
        CustomerController.getInstance().storeBooking(booking.getBookingID());

        System.out.println("Your booking has been confirmed! Here are the details:");
        getBooking().printBookingDetails();

        System.out.println("Please print a copy of it for your reference");
        System.out.println("Returning back to main screen..");

        // store booking in serialized file
        String filePath = FilePathFinder.findRootPath() +"/src/data/bookings/booking_" + getBooking().getBookingID() + ".dat";
        DataSerializer.ObjectSerializer(filePath, getBooking());

        // reset BookingController instance, as well as ticketcontroller, and transactioncontroller.
        TicketController.getInstance().reset();
        TransactionController.getInstance().reset();
        TransactionController.getInstance();
        resetData();
    }

    /**
     * Enables addition of seats by customer, and has validation checks in place to ensure this addition of seat is valid.
     */
    private void addSeat() {
        System.out.println("Please select a seat to be added (e.g A1)");
        String seatChoice = InputController.getUserString().toUpperCase();

        if(isValidSeatSelection(seatChoice)) {
            char selectedRow = seatChoice.charAt(0);
            int selectedCol = Integer.valueOf(seatChoice.substring(1));

            // add seat choice to selectedSeats
            getSelectedSeats().add(seatChoice);

            // if no existing seats selected for a row, create row in getStoreRowNoAndIndex
            if(!getStoreRowAndColChoices().containsKey(selectedRow)) {
                getStoreRowAndColChoices().put(selectedRow, new ArrayList<Integer>());
            }

            // add in the selected col for the row in hashmap
            getStoreRowAndColChoices().get(selectedRow).add(selectedCol);;

            // Update Cinema Layout
            System.out.println("Seat " + seatChoice + " has been successfully added!");
            updateCinemaSeatingLayout("addSeat", seatChoice);

        }
        // if not valid, isValidSeatSelection() will print an error message.
    }

    /**
     * Enables removal of selected seats by customer
     */
    private void removeSelectedSeat() {
        System.out.println("Please select a seat to be removed from your cart (e.g C3)");
        String seatChoice = InputController.getUserString().toUpperCase();

        // Check if its a valid seat to be removed.
        if(isValidSeatRemoval(seatChoice)) {
            char selectedRow = seatChoice.charAt(0);
            int selectedCol = Integer.valueOf(seatChoice.substring(1));

            // Remove from selectedSeats
            getSelectedSeats().remove(seatChoice);

            // remove this seat from storeRowAndColChoices

            // First find the index of the selectedCol, in the ArrayList returned by getStoreRowAndColChoices().get(selectedRow)
            int indexOfSeat = 0;
            for(indexOfSeat = 0; indexOfSeat< getStoreRowAndColChoices().get(selectedRow).size(); indexOfSeat++) {
                if(getStoreRowAndColChoices().get(selectedRow).get(indexOfSeat) == selectedCol) {
                    break;
                }
            }

            // we can then remove this selectedCol from the ArrayList returned by getStoreRowAndColChoices().get(selectedRow), based on indexOfSeat
            getStoreRowAndColChoices().get(selectedRow).remove(indexOfSeat);

            // If row now has no col choices stored in it, delete this row
            if(getStoreRowAndColChoices().get(selectedRow).size() <= 0) {
                getStoreRowAndColChoices().remove(selectedRow);
            }

            // Update Cinema Seating Layout
            updateCinemaSeatingLayout("deleteSeat", seatChoice);
            System.out.println("You have successfully removed Seat " + seatChoice + "!");
        }
        // if not valid seat removal, isValidSeatRemoval() will print a error message
    }

    /**
     * Checks whether removal of seat prompted by customer is valid
     * @param seatChoice a String representing the target seat that the customer wants to remove from his choices
     * @return a boolean, representing if this removal of seat is valid
     */
    private boolean isValidSeatRemoval(String seatChoice) {
        String seatChoiceRegex = "[A-Z]\\d{1,2}";
        // Check 1: Check if formatting of SeatChoice is valid
        if(seatChoice.matches(seatChoiceRegex)) {
            // Check 2: Check current selected seats to see if it contains this seat to be removed
            if(getSelectedSeats().contains(seatChoice)){
                char rowChoice = seatChoice.charAt(0);
                int colChoice = Integer.valueOf(seatChoice.substring(1));

                ArrayList<Integer> colChoicesForRow = getStoreRowAndColChoices().get(rowChoice);

                // Check 3 - Check If Seat is In Between 2 Seats
                // If seat to be removed is in between 2 seats, then prevent this removal (based on check 3)
                if(colChoicesForRow.contains(colChoice - 1) && colChoicesForRow.contains(colChoice + 1)){
                    System.out.println("Seat " + seatChoice + " cannot be removed as it has 2 adjacent seats selected by you on the same row.");
                    System.out.println("You are not allowed to leave spaces between selected seats on the same row!");
                    return false;
                } else {
                    // seat is not in between 2 seats - allow removal (based on check 3)
                    return true;
                }
            } // Seat to be removed is not part of the current selected seats (based on check 2)
            else {
                System.out.println("Seat " + seatChoice + " is not one of your selected seats at the moment! Please try again.");
                return false;
            }
        }
        // Formatting of seat choice is wrong (based on check 1)
        System.out.println("Invalid seat selection! Please enter a valid seat ID to be removed (e.g C3). ");
        return false;
    }

    /**
     * Checks whether selection of seat by customer is valid.
     * Ensures that this seat is a valid seat that is part of the cinema, not already occupied, and enforces that a customer cannot leave spaces between their seat choices on the same row.
     * @param seatChoice a String representing the choice of seat selected by the customer
     * @return a boolean, representing whether this is a valid seat selection
     */
    private boolean isValidSeatSelection(String seatChoice) {
        String seatChoiceRegex = "[A-Z]\\d{1,2}";
        if(seatChoice.matches(seatChoiceRegex)) {
            char rowChoice = seatChoice.charAt(0);
            int colChoice = Integer.valueOf(seatChoice.substring(1));

            // if column choice doesnt exist in store, not valid
            if(!getStoreColumnNoAndIndex().containsKey(colChoice)) {
                return false;
            }

            String rowString;
            for(int row = 0; row < getCinemaSeatingLayout().size(); row++) {

                rowString = getCinemaSeatingLayout().get(row);

                if(rowString.length() == 0 || rowString == null) {
                    continue;
                }
                // Iterate through each row until we find the correct row according to user choice

                // Check #1 - check whether rowString's first char matches row choice
                if(rowString.charAt(0) == rowChoice) {
                    int colIndex = getStoreColumnNoAndIndex().get(colChoice);

                    // Check #2 - if colIndex is beyond the rowString's length
                    if(colIndex >= rowString.length()) {
                        return false;
                    }

                    // Check #3- Check if selected seat is occupied
                    if(rowString.charAt(colIndex) == '_') { // not occupied
                        // if user has alr selected a seat in the row, enforce that they can only select seat adjacent to it
                        // If user has selected seat in row 1, then if he selects in row 2, is fine.
                        // Check #4 - Check if Row has been booked before by the user
                        if(getStoreRowAndColChoices().containsKey(rowChoice)) {
                            ArrayList<Integer> rowArray = storeRowAndColChoices.get(rowChoice);

                            // Check #5 - Check if adjacent to previous selection
                            for(int i = 0; i < rowArray.size(); i++) {
                                // traverse this array of colChoices which represent existing selected seats in the row
                                if(Math.abs(rowArray.get(i) - colChoice) == 1) { // checks if there are any choices in rowArray right next to colChoice
                                    return true;
                                }

                            }
                            System.out.println("Invalid seat selection! You are not allowed to leave any spaces between selected seats for the same row.");
                            return false;

                        } else {
                            // if row has not been booked before by the user, then is valid selection
                            return true;
                        }

                    } else {
                        // Seat has already been occupied
                        System.out.println("Invalid seat selection! This seat has either been selected by you previously/ already occupied / not available for selection.");
                        return false;
                    }

                }

            }

        };
        System.out.println("Invalid seat selection! Please enter a valid seat ID (e.g C3). ");
        return false;
    }

    /**
     * Updates cinema seating plan, when there is either a seat selected, removed or confirmed by a customer
     * @param event a String representing whether it is a selection, removal or confirmation of seat
     * @param seatID a String representing the seat to which we are modifying based on the event
     */
    private void updateCinemaSeatingLayout(String event, String seatID) {
        char targetRow = seatID.charAt(0);
        int targetCol = Integer.valueOf(seatID.substring(1));

        String seatUpdatedString;

        switch(event) {
            case "addSeat":
                seatUpdatedString = "O";
                break;
            case "confirmSeat":
                seatUpdatedString = "X";
                break;
            case "deleteSeat":
                seatUpdatedString = "_";
                break;
            default:
                System.out.println("Invalid event! Event can only be addition, deletion or confirmation of a seat.");
                return;

        }

        // find the exact position in the cinemaSeatingLayout, to modify it with the updated String for the target String
        for(int row = 0; row < getCinemaSeatingLayout().size(); row++) {
            String rowString = getCinemaSeatingLayout().get(row);

            if(rowString.length() == 0 || rowString == null) {
                continue;
            }

            if(rowString.charAt(0) == targetRow) {
                int targetIndex = getStoreColumnNoAndIndex().get(targetCol);

                String updatedRowString = rowString.substring(0, targetIndex) + seatUpdatedString + rowString.substring(targetIndex + 1);

                // Update row in cinemaSeatingLayout, with updated row string
                getCinemaSeatingLayout().set(row, updatedRowString);
                return;
            }
        }
    }

    /**
     * Retrieves the current showtime being booked
     * @return a Showtime Object representing the current showtime being booked
     */
    public Showtime getShowtime() {
        return showTime;
    }

    /**
     * Sets a showtime object to perform booking operation on
     * @param showTime a Showtime Object that we want to perform a booking operation on
     */
    public void setShowTime(Showtime showTime) {
        this.showTime = showTime;
    }

    /**
     * Retrieves the current seating plan of the cinema
     * @return an ArrayList of String representing the seating plan
     */
    public ArrayList<String> getCinemaSeatingLayout() {
        return cinemaSeatingLayout;
    }

    /**
     * Sets the seating plan for this BookingController to work with to perform the booking procedure
     * @param cinemaSeatingLayout an ArrayList of Strings representing the seating plan for BookingController to work with
     */
    public void setCinemaSeatingLayout(ArrayList<String> cinemaSeatingLayout) {
        this.cinemaSeatingLayout = cinemaSeatingLayout;
    }

    /**
     * Retrieves the current selected seats
     * @return a ArrayList of Strings representing the current selected seats
     */
    public ArrayList<String> getSelectedSeats() {
        return selectedSeats;
    }

    /**
     * Sets the current booking that the BookingController is working with
     * @param booking a Booking object that the BookingController is working with
     */
    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    /**
     * Retrieve the Booking object that we are working with
     * @return a Booking object that we are working with
     */
    public Booking getBooking() {
        return booking;
    }

    /**
     * Creates a copy of the cinema seating plan for the particular movie's showtime
     * @param cinemaSeatingLayout is an ArrayList of Strings representing the seating plan of the cinema being booked
     * @return an ArrayList of Strings, representing a cloned copy of the cinema seating plan that we will be working with
     */
    private ArrayList<String> cloneCinemaSeatingLayout(ArrayList<String> cinemaSeatingLayout) {
        ArrayList<String> seatingLayoutCopy = new ArrayList<>();

        for(int i = 0; i < cinemaSeatingLayout.size();i++) {
            seatingLayoutCopy.add(cinemaSeatingLayout.get(i));
        }
        return seatingLayoutCopy;
    }

    /**
     * Prints out the seating plan for the customer to see
     * @param cinemaSeatingLayout an ArrayList of Strings representing the seating plan, to be printed
     */
    public void displaySeatingPlan(ArrayList<String> cinemaSeatingLayout) {
        String rowString;
        for(int row = 0; row < cinemaSeatingLayout.size() - 1; row++ ) {
            rowString = cinemaSeatingLayout.get(row);
            if(rowString.length() == 0) {
                System.out.println();
                continue;
            } else {
                System.out.println(rowString);
            }
        }
        System.out.println("\nLegend: |_| : Available Seat, |X|: Unavailable Seat, |O| : Your Selected Seat(s)");
    }

    /**
     * Retrieve the stored pairs of seat column number and their corresponding index position in the String
     * @return a HashMap which has key value pairs of seat column number and its corresponding index position
     */
    public HashMap<Integer, Integer> getStoreColumnNoAndIndex() {
        return storeColumnNoAndIndex;
    }

    /**
     * Retrieves the stored key value pairs of the row and respective column choices made by customer
     * @return a Hashmap which has  key value pairs of the row and respective column choices made by customer
     */
    public HashMap<Character, ArrayList<Integer>> getStoreRowAndColChoices() {
        return storeRowAndColChoices;
    }
}
