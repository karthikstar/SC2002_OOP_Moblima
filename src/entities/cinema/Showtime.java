package entities.cinema;

import java.io.File;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;

import entities.movie.MovieType;
import utils.FilePathFinder;

/**
 * Class that represents the individual showtime object, which has its own seating plans and other specific attributes such as movie & movie type shown.
 */
public class Showtime implements Serializable {
    /**
     * Generates an ID integer number based on the number of existing showtime stored in our database.
     */
    private static int idCounter = getNumberOfExistingShowtimes();
    /**
     * ID for the showtime instance.
     */
    private int showTimeId;
    /**
     * Date and Time for the showtime.
     */
    private LocalDateTime dateTime;
    /**
     * ID for the movie being screened.
     */
    private int movieId;
    /**
     * Movie Type being shown, whether it is 2D, 3D or in Dolby Atmos etc
     */
    private MovieType movieType;
    /**
     * Cinema object being stored.
     */
    private Cinema cinema;
    /**
     * Availability of the cinema based on the 3 strings stored in the enum "CinemaAvailability" that gives information on the availability of seats.
     */
    private CinemaAvailability status;
    /**
     * Number of occupied seats in the cinema.
     */
    private int occupiedNoOfSeats = 0;
    /**
     * List of strings containing the cinema seating layout to be displayed on the user console.
     */
    private ArrayList<String> cinemaSeatLayout;

    /**
     * Method that updates the status attribute of the cinemas availability based on the proportion of the number of seats being booked already.
     */
    // To be ran everytime tickets are bought somehow?
    public void updateAvailability(){
        double percentageFilled = (double) getOccupiedNoOfSeats()/cinema.getTotalNOfSeats();

        if (percentageFilled < 0.5){
            status = CinemaAvailability.OPEN_FOR_SALES;
        }
        else if (percentageFilled < 1) {
            status = CinemaAvailability.SELLING_FAST;
        }
        else status = CinemaAvailability.FULL_CAPACITY;
    }

    /**
     * Constructor for the Showtime Object.
     */
    public Showtime() {
        this.showTimeId = idCounter;
        idCounter++;
//        this.cinemaSeatLayout = cinema.getCinemaSeatLayout();
    }
    /**
     * Constructor for the Showtime Object.
     */
    public Showtime(int showTimeId, LocalDateTime dateTime, int movieId, MovieType movieType, Cinema cinema, CinemaAvailability status) {
        this.showTimeId = showTimeId;
        this.dateTime = dateTime;
        this.movieId = movieId;
        this.movieType = movieType;
        this.cinema = cinema;
        this.status = status;
    }

    /**
     * Getter for the number of existing showtimes stored in the database.
     * @return
     */
    private static int getNumberOfExistingShowtimes() {
        String path = FilePathFinder.findRootPath() + "/src/data/showtimes";
        try {
            File directory = new File(path);
            File[] files = directory.listFiles();
        } catch(Exception e) {
            return 0;
        }
        File directory = new File(path);
        File[] files = directory.listFiles();
        if (files == null) return 0;
        if(files.length != 0) {
            return files.length;
        }
        return 0;
    }

    /**
     * Getter for the ID of the showtime.
     * @return Showtime ID
     */
    public int getShowTimeId() {
        return showTimeId;
    }

    /**
     * Setter for the showtime ID.
     * @param showTimeId Showtime ID
     */
    public void setShowTimeId(int showTimeId) {
        this.showTimeId = showTimeId;
    }

    /**
     * Getter for the date and time of the showtime.
     * @return Date & Time of showtime
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Setter for the date & time of the showitme.
     * @param dateTime Date & Time of showtime
     */
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Getter for the movie ID being screened.
     * @return ID of the movie
     */
    public int getMovieId() {
        return movieId;
    }
    /**
     * Setter for the movie ID being screened.
     * @param movieId ID of the movie
     */
    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    /**
     * Getter for the Movie Type being screened (2D, 3D etc.)
     * @return Movie Type
     */
    public MovieType getMovieType() {
        return movieType;
    }
    /**
     * Setter for the Movie Type being screened (2D, 3D etc.)
     * @param movieType Movie Type
     */
    public void setMovieType(MovieType movieType) {
        this.movieType = movieType;
    }

    /**
     * Getter for the cinema object that showtime is at.
     * @return Cinema object
     */
    public Cinema getCinema() {
        return cinema;
    }
    /**
     * Setter for the cinema object that showtime is at.
     * @param cinema Cinema object
     */
    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    /**
     * Getter for the Seating Availability status.
     * @return Seating Availability status
     */
    public CinemaAvailability getStatus() {
        return status;
    }
    /**
     * Setter for the Seating Availability status.
     * @param status Seating Availability status
     */
    public void setStatus(CinemaAvailability status) {
        this.status = status;
    }

    /**
     * Getter for the ID to be assigned to a new showtime object.
     * @return ID generated
     */
    public int getIdCounter() {
        return idCounter;
    }
    /**
     * Setter for the ID to be assigned to a new showtime object.
     * @param idCounter ID fixed
     */
    public static void setIdCounter(int idCounter) {
        Showtime.idCounter = idCounter;
    }

    /**
     * Getter for the number of occupied seats in the showtime.
     * @return Occupied Number of Seats
     */
    public int getOccupiedNoOfSeats() {
        return occupiedNoOfSeats;
    }
    /**
     * Setter for the number of occupied seats in the showtime.
     * @param occupiedNoOfSeats Occupied Number of Seats
     */
    public void setOccupiedNoOfSeats(int occupiedNoOfSeats) {
        this.occupiedNoOfSeats = occupiedNoOfSeats;
    }

    /**
     * Method that increased number of occupied seats of the shwowtime based on the input number of seats taken in a booking.
     * @param bookedSeats Number of seats bought by the customer
     */
    public void increaseOccupiedNoOfSeats(int bookedSeats) {
        this.occupiedNoOfSeats += bookedSeats;
    }

    /**
     * Method that checks if the Showtime is on a weekend, and set ticket prices accordingly.
     * @return True/False value on whether it is a weekend
     */
    public boolean isWeekend(){
        if(dateTime.getDayOfWeek() == DayOfWeek.SATURDAY || dateTime.getDayOfWeek() == DayOfWeek.SUNDAY){
            return true;
        }
        else if(dateTime.getDayOfWeek() == DayOfWeek.FRIDAY && dateTime.getHour()>18){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Setter for the List of Strings which represents the showtime's seating layout
     * @param cinemaSeatLayout Showtime's Current Seating Plan
     */
    public void setCinemaSeatLayout(ArrayList<String> cinemaSeatLayout) {
        this.cinemaSeatLayout = cinemaSeatLayout;
    }

    /**
     * Getter for the List of Strings which represents the showtime's seating layout
     * @return Showtime's Seating Layout
     */
    public ArrayList<String> getCinemaSeatLayout() {
        return cinemaSeatLayout;
    }
}
