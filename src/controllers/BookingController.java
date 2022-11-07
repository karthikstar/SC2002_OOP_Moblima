package controllers;

import boundaries.BookingUI;
import entities.booking.Booking;
import entities.cinema.CinemaAvailability;
import entities.cinema.ShowTime;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class BookingController {
    private ArrayList<String> cinemaSeatingLayout;

    private ArrayList<String> selectedSeats = new ArrayList<>();

    // check for seat column no, and its index position in the string, store these 2 values as a key value pair in hashmap
    private HashMap<Integer, Integer> storeColumnNoAndIndex = new HashMap<>();

    private HashMap<Character, ArrayList<Integer>> storeRowAndColChoices = new HashMap<>();

    private Booking booking = null; // refers to current booking

    private ShowTime showTime = null; // refers to current selected showtime

    public Boolean exitBooking = false;
    public static BookingController single_instance = null;

    // ensure there's only one instance of BookingController
    public static BookingController getInstance()
    {
        if(single_instance == null) {
            single_instance = new BookingController();
        }
        return single_instance;
    }


    private BookingController(){
        resetData();
    }

    // Reset data of this BookingController instance so that data doesn't persist, in the event that user clicks back
    public void resetData() {
        setBooking(null);
        setShowTime(null);
        setCinemaSeatingLayout(null);
        getSelectedSeats().clear();
        getStoreColumnNoAndIndex().clear();
        getStoreRowAndColChoices().clear();
        exitBooking = true;
    }

    // Initialise Seat Selection Process
    public void initSeatSelection(ShowTime showtime) {
        setShowTime(showtime);

        // set CinemaSeatingLayout to a cloned copy so that we don't change the originial seating layout until confirmation
        ArrayList<String> SeatingLayoutClone = cloneCinemaSeatingLayout(showtime.getCinema().getCinemaSeatLayout());
        setCinemaSeatingLayout(SeatingLayoutClone);


        // Looking for row with seat column numbers.
        for(int row = 0; row < getCinemaSeatingLayout().size(); row++) {

            String rowString = getCinemaSeatingLayout().get(row);

            if(rowString.length() == 0 || rowString == null) {
                continue;
            }

            int colIndex = 0;
            Boolean foundSeatColumnRow = false;

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

            // Showtime not implemented yet. AFTER THAT UNCOMMENT THIS OUT
//            if(showtime.getCinemaStatus() == CinemaAvailability.FULL_CAPACITY) {
//                System.out.println("Unfortunately, this showtime has reached full capacity! Please try another showtime.");
//                exitBooking = true;
//                resetData();
//                return;
//            }

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
//                        TicketManager
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
            System.out.println("Seat " + seatChoice + " has been succesfully added!");
            updateCinemaSeatingLayout("addSeat", seatChoice);

        }

        // if not valid, isValidSeatSelection() will print an error message.
    }

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
            getStoreRowAndColChoices().get(selectedRow).remove(selectedCol);

            // If row now has no col choices stored in it, delete this row
            if(getStoreRowAndColChoices().get(selectedRow).size() <= 0) {
                getStoreRowAndColChoices().remove(selectedRow);
            }

            // Update Cinema Seating Layout
            updateCinemaSeatingLayout("deleteSeat", seatChoice);
            System.out.println("You have succesfully removed Seat " + seatChoice + "!");
        }
        // if not valid seat removal, isValidSeatRemoval() will print a error message
    }

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
                        System.out.println("Invalid seat selection! This seat has either been selected by you previously or is already occupied.");
                        return false;
                    }

                }

            }

        };
        System.out.println("Invalid seat selection! Please enter a valid seat ID (e.g C3). ");
        return false;
    }


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

    public ShowTime getShowtime() {
        return showTime;
    }

    public void setShowTime(ShowTime showTime) {
        this.showTime = showTime;
    }

    public ArrayList<String> getCinemaSeatingLayout() {
        return cinemaSeatingLayout;
    }

    public void setCinemaSeatingLayout(ArrayList<String> cinemaSeatingLayout) {
        this.cinemaSeatingLayout = cinemaSeatingLayout;
    }

    public ArrayList<String> getSelectedSeats() {
        return selectedSeats;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Booking getBooking() {
        return booking;
    }

    private ArrayList<String> cloneCinemaSeatingLayout(ArrayList<String> cinemaSeatingLayout) {
        ArrayList<String> cinemaSeatingLayoutClone = new ArrayList<>();
        for(int i = 0; i < cinemaSeatingLayout.size(); i++) {
            cinemaSeatingLayoutClone.add(cinemaSeatingLayout.get(i));
            i++;
        }
        return cinemaSeatingLayoutClone;
    }

    public void displaySeatingPlan(ArrayList<String> cinemaSeatingLayout) {
        String rowString;
        for(int row = 0; row < cinemaSeatingLayout.size(); row++ ) {
            rowString = cinemaSeatingLayout.get(row);
            if(rowString.length() == 0 || rowString == null) {
                System.out.println();
                continue;
            } else {
                System.out.println(rowString);
            }
        }
        System.out.println("\nLegend: |_| : Available Seat, |X|: Unavailable Seat, |S| : Your Selected Seat");
    }
    public HashMap<Integer, Integer> getStoreColumnNoAndIndex() {
        return storeColumnNoAndIndex;
    }

    public HashMap<Character, ArrayList<Integer>> getStoreRowAndColChoices() {
        return storeRowAndColChoices;
    }
}