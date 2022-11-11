package entities.movie;

import java.util.ArrayList;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

public class Movie implements Serializable {
    private static int idCounter = 1;

    //Attributes
    private int id;
    private String title;
    private MovieRating rating;
    private ArrayList<MovieGenre> genres;
    private int duration;
    private LocalDate movieOpeningDate;
    private LocalDate movieEndDate;
    private String language;
    private String synopsis;
    private String director;
    private long ticketsSold = 0;
    private ArrayList<String> cast;
    private ArrayList<MovieReview> reviews;
    private ArrayList<MovieType> type;
    private ArrayList<Integer> showtimeIDs;

    //Constructors
    public Movie(){
        this.genres = new ArrayList<MovieGenre>();
        this.cast = new ArrayList<String>();
        this.type = new ArrayList<MovieType>();
        this.reviews = new ArrayList<MovieReview>();
        this.showtimeIDs = new ArrayList<Integer>();
        this.id = idCounter;
        idCounter++;
    }
    public Movie(
            int id, String title, MovieRating rating, ArrayList<MovieType> type, String synopsis, int duration, LocalDate movieOpeningDate, LocalDate movieEndDate, String language, String director, ArrayList<MovieGenre> genres, int ticketsSold,  ArrayList<String> cast
    ){
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.type = type;
        this.synopsis = synopsis;
        this.duration = duration;
        this.movieOpeningDate = movieOpeningDate;
        this.movieEndDate = movieEndDate;
        this.language = language;
        this.director = director;
        this.ticketsSold = ticketsSold;
        this.cast = cast;
        this.genres = genres;
        this.reviews = new ArrayList<MovieReview>();
        this.showtimeIDs = new ArrayList<Integer>();
    }

    public MovieStatus getShowStatus() {
        LocalDate today = LocalDate.now();
        if (today.isAfter(movieEndDate))
            return MovieStatus.END_OF_SHOWING;
        else {
            double daysBetween = Duration.between(today.atStartOfDay(), movieOpeningDate.atStartOfDay()).toDays();
            if (daysBetween > 30) {
                return MovieStatus.COMING_SOON;
            } else if (daysBetween <= 30 && daysBetween > 0) {
                return MovieStatus.ADVANCE_SALES;
            } else {
                return MovieStatus.NOW_SHOWING;
            }
        }
    }

    public String getOverallStars(){
        double sum = 0;
        if(reviews.size()>1){
            for(MovieReview review : reviews){
                sum += review.getNumOfStars();
            }
            DecimalFormat df = new DecimalFormat("#.#");
            return df.format(sum/reviews.size());
        }
        else {
            return "N/A";
        }
    }

    // Used as a comparator function for sorting. Assumes there is >1 review already (checked in Controller).
    public Double getOverallStarsDouble() {
        double sum = 0;
        for(MovieReview review : reviews){
            sum += review.getNumOfStars();
        }
        return sum/reviews.size();
    }

    @Override
    public String toString(){
        String overallCast = "";
        for (int i=0; i<getCast().size(); i++)
            overallCast = overallCast.concat(getCast().get(i) + ", ");
        overallCast = overallCast.substring(0, overallCast.length()-2);

        String reviews = "";
        for(int i = 0; i<getReviews().size();i++){
            reviews += getReviews().get(i) + "\n\n";
        }
        if(reviews.equals(""))
            reviews = "N/A";

        String allGenres = "";
        for(int i = 0; i<getGenres().size();i++){
            allGenres += getGenres().get(i) + "\n\n";
        }

        String allTypes = "";
        for (int i=0; i<getType().size(); i++)
            allTypes = allTypes.concat(getType().get(i) + ", ");
        allTypes = allTypes.substring(0, allTypes.length()-2);

        String allShowTimeId = "";
        for (int i=0; i<getShowtimeIDs().size(); i++)
            allShowTimeId = allShowTimeId.concat(getShowtimeIDs().get(i) + ", ");
        allShowTimeId = allShowTimeId.substring(0, allShowTimeId.length()-2);
        if(allShowTimeId.equals(""))
            allShowTimeId = "N/A";

        String details = "";
        details += "ID: " + getId() + "\n"
                + "Title: " + getTitle() + "\n"
                + "Rating: " + getRating() + "\n"
                + "Genres:" + allGenres + "\n"
                + "Type:" + allTypes + "\n"
                + "Language:" + getLanguage() + "\n"
                + "Status: " + getShowStatus().toString() + "\n"
                + "Synopsis: " + getSynopsis() + "\n"
                + "Duration: " + String.valueOf(getDuration()) + " minutes\n"
                + "Opening date: " +  getMovieOpeningDateString() + "\n"
                + "End date: " +  getMovieEndDateString() + "\n"
                + "Director: " + getDirector() + "\n"
                + "Tickets Sold: " + getTicketsSold() + "\n"
                + "Cast: " + overallCast + "\n"
                + "Showtime IDs: " + allShowTimeId + "\n"
                + "Overall review stars: " + getOverallStars() + "\n"
                + "Reviews: \n\n" + reviews;
        return details + "\n";
    }

    // Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MovieRating getRating() {
        return rating;
    }

    public void setRating(MovieRating rating) {
        this.rating = rating;
    }

    public ArrayList<MovieGenre> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<MovieGenre> genres) {
        this.genres = genres;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public LocalDate getMovieOpeningDate() {
        return movieOpeningDate;
    }

    public void setMovieOpeningDate(LocalDate movieOpeningDate) {
        this.movieOpeningDate = movieOpeningDate;
    }

    public LocalDate getMovieEndDate() {
        return movieEndDate;
    }

    public void setMovieEndDate(LocalDate movieEndDate) {
        this.movieEndDate = movieEndDate;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public long getTicketsSold() {
        return ticketsSold;
    }

    public void setTicketsSold(long ticketsSold) {
        this.ticketsSold = ticketsSold;
    }

    public ArrayList<String> getCast() {
        return cast;
    }

    public void setCast(ArrayList<String> cast) {
        this.cast = cast;
    }

    public ArrayList<MovieReview> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<MovieReview> reviews) {
        this.reviews = reviews;
    }

    public ArrayList<MovieType> getType() {
        return type;
    }

    public void setType(ArrayList<MovieType> type) {
        this.type = type;
    }

    public ArrayList<Integer> getShowtimeIDs() {
        return showtimeIDs;
    }

    public void setShowtimeIDs(ArrayList<Integer> showtimeIDs) {
        this.showtimeIDs = showtimeIDs;
    }

    public String getMovieOpeningDateString() {
        return movieOpeningDate.format(DateTimeFormatter.ofPattern("EEEE, dd/MM/yyyy"));
    }

    public String getMovieEndDateString() {
        return movieEndDate.format(DateTimeFormatter.ofPattern("EEEE, dd/MM/yyyy"));
    }


    // Adding to the ArrayLists
    public void addMovieReview(MovieReview review) {
        reviews.add(review);
    }

    public void removeMovieReview(int reviewID) {
        for (int i=0;i<getReviews().size(); i++)
            if (getReviews().get(i).equals(reviewID)) {
                reviews.remove(i);
            }
    }

    public void addShowtimeID(int showtimeID) {
        showtimeIDs.add(showtimeID);
    }
}