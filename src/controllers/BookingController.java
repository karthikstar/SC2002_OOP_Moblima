package controllers;

import boundaries.BookingUI;
import entities.booking.Booking;
import entities.cinema.ShowTime;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class BookingController {
    private ArrayList<String> cinemaSeatingLayout;

    private ArrayList<String> selectedSeats = new ArrayList<>();

    // check ofor seat column no, and its index position in the string, store these 2 values as a key value pair in hashmap
    private HashMap<Integer, Integer> storeColumnNoAndIndex = new HashMap<>();

    private HashMap<Character, ArrayList<Integer>> checkRow = new HashMap<>();

    private Booking booking = null; // refers to current booking

    private ShowTime showTime = null; // refers to current selected showtime


    public static BookingController single_instance = null;

    public static BookingController getInstance()
    {
        if(single_instance == null) {
            single_instance = new BookingController();
        }
        return single_instance;
    }


    private BookingController(){
//        resetSelf()

    }

// impt
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
                    // store the Seat's column no and its index position in the string
                    getStoreColumnNoAndIndex().put(Integer.valueOf(seatColumnNo), colIndex);

                }
                colIndex++;
            }

            if(foundSeatColumnRow){
                break;
            }
        }

        boolean exitBooking = false;
        while(!exitBooking) {
            BookingUI.printBookingMenu();
            int minBookingMenuChoice = 0;
            int maxBookingMenuChoice = 3;
            int choice = InputController.getUserInt(minBookingMenuChoice,maxBookingMenuChoice);

            switch (choice) {
                case 0:
                    exitBooking = true;
                    break;
                case 1:
//                    selectSeat();
                    break;
                case 2:
//                    unselectSeat();
                    break;
                case 3: //Ticket Selection - requires at least 1 seat to be selected. if no seats selected, dont allow ticket selection
//                    ticketmanager
                    break;

                default:
                    System.out.println("Invalid Input! Please enter a valid integer between 0 to 3.");
                    break;

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

    private ArrayList<String> cloneCinemaSeatingLayout(ArrayList<String> cinemaSeatingLayout) {
        ArrayList<String> cinemaSeatingLayoutClone = new ArrayList<>();
        for(int i = 0; i < cinemaSeatingLayout.size(); i++) {
            cinemaSeatingLayoutClone.add(cinemaSeatingLayout.get(i));
            i++;
        }
        return cinemaSeatingLayoutClone;
    }

    public HashMap<Integer, Integer> getStoreColumnNoAndIndex() {
        return storeColumnNoAndIndex;
    }
}
