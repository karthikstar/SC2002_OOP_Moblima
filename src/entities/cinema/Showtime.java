package entities.cinema;

import java.io.Serializable;
import java.time.LocalDateTime;
import entities.movie.MovieType;


public class Showtime implements Serializable {

    private static int showtimeCounter = 0;
    private int showTimeId;
    private LocalDateTime dateTime;
    private String cineplexName;

    private int cinemaID;
    private int movieId;
    private MovieType movieType;
    private Cinema cinema;
    private CinemaAvailability status;


    // To be ran everytime tickets are bought somehow?
    public void updateAvailability(){
        double percentageFilled = cinema.getOccupiedNoOfSeats()/cinema.getTotalNOfSeats();

        if (percentageFilled < 0.5){
            status = CinemaAvailability.OPEN_FOR_SALES;
        }
        else if (percentageFilled < 1) {
            status = CinemaAvailability.SELLING_FAST;
        }
        else status = CinemaAvailability.FULL_CAPACITY;
    }




    //Constructors
    public Showtime(int showTimeId, LocalDateTime dateTime, String cineplexName, int cinemaID, int movieId, MovieType movieType, Cinema cinema, CinemaAvailability status) {
        this.showTimeId = showTimeId;
        this.dateTime = dateTime;
        this.cineplexName = cineplexName;
        this.cinemaID = cinemaID;
        this.movieId = movieId;
        this.movieType = movieType;
        this.cinema = cinema;
        this.status = status;
        showtimeCounter++;
    }

    //Getters & Setters
    public int getShowTimeId() {
        return showTimeId;
    }

    public void setShowTimeId(int showTimeId) {
        this.showTimeId = showTimeId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getCineplexName() {
        return cineplexName;
    }

    public void setCineplexName(String cineplexName) {
        this.cineplexName = cineplexName;
    }

    public int getCinemaID() {
        return cinemaID;
    }

    public void setCinemaID(int cinemaNumber) {
        this.cinemaID = cinemaNumber;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public MovieType getMovieType() {
        return movieType;
    }

    public void setMovieType(MovieType movieType) {
        this.movieType = movieType;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public CinemaAvailability getStatus() {
        return status;
    }

    public void setStatus(CinemaAvailability status) {
        this.status = status;
    }

    public static int getShowtimeCounter() {
        return showtimeCounter;
    }
}
