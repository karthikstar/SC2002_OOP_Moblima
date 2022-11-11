package entities.cinema;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;

import entities.movie.MovieType;


public class Showtime implements Serializable {

    private static int idCounter = 1;
    private int showTimeId;
    private LocalDateTime dateTime;
    private String cineplexName;

    private String cineplexCode;

    private String cinemaID;
    private int movieId;
    private MovieType movieType;
    private Cinema cinema;
    private CinemaAvailability status;

    private ArrayList<String> cinemaSeatLayout;

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


    public Showtime() {
        this.showTimeId = idCounter;
        idCounter++;
    }

    //Constructors
    public Showtime(int showTimeId, LocalDateTime dateTime, String cineplexName, String cineplexCode, String cinemaID, int movieId, MovieType movieType, Cinema cinema, CinemaAvailability status) {
        this.showTimeId = showTimeId;
        this.dateTime = dateTime;
        this.cineplexName = cineplexName;
        this.cineplexCode = cineplexCode;
        this.cinemaID = cinemaID;
        this.movieId = movieId;
        this.movieType = movieType;
        this.cinema = cinema;
        this.status = status;
    }

    //Getters & Setters
    public int getShowTimeId() {
        return showTimeId;
    }

    public String getCineplexCode() {return cineplexCode;}

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

    public String getCinemaID() {
        return cinemaID;
    }

    public void setCinemaID(String cinemaID) {
        this.cinemaID = cinemaID;
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

    public static int getIdCounter() {
        return idCounter;
    }

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

    public void setCinemaSeatLayout(ArrayList<String> cinemaSeatLayout) {
        this.cinemaSeatLayout = cinemaSeatLayout;
    }

    public ArrayList<String> getCinemaSeatLayout() {
        return cinemaSeatLayout;
    }
}
