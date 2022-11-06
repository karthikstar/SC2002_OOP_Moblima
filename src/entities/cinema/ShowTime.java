package entities.cinema;

import java.io.Serializable;
import java.time.LocalDateTime;
import entities.movie.MovieType;


public class ShowTime implements Serializable {
    private String showTimeId;
    private LocalDateTime dateTime;
    private String cineplexName;
    private int cinemaNumber;
    private String movieId;
    private MovieType type;
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
    public ShowTime(String showTimeId, LocalDateTime dateTime, String cineplexName, int cinemaNumber, String movieId, MovieType type, Cinema cinema, CinemaAvailability status) {
        this.showTimeId = showTimeId;
        this.dateTime = dateTime;
        this.cineplexName = cineplexName;
        this.cinemaNumber = cinemaNumber;
        this.movieId = movieId;
        this.type = type;
        this.cinema = cinema;
        this.status = status;
    }

    //Getters & Setters
    public String getShowTimeId() {
        return showTimeId;
    }

    public void setShowTimeId(String showTimeId) {
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

    public int getCinemaNumber() {
        return cinemaNumber;
    }

    public void setCinemaNumber(int cinemaNumber) {
        this.cinemaNumber = cinemaNumber;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public MovieType getType() {
        return type;
    }

    public void setType(MovieType type) {
        this.type = type;
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
}
