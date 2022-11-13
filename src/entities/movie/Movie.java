package entities.movie;

import utils.FilePathFinder;

import java.io.File;
import java.util.ArrayList;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

/**
 * Movie is a class that represents a Movie object
 */
public class Movie implements Serializable {
    /**
     * Counter to keep track of the ids generated for the movie
     */
    private static int idCounter = getNumberOfExistingMovies();

    //Attributes
    /**
     * Id of movie
     */
    private int id;
    /**
     * Title Of Movie
     */
    private String title;
    /**
     * Rating for movie
     */
    private MovieRating rating;
    /**
     * List of genres for the movie
     */
    private ArrayList<MovieGenre> genres;
    /**
     * Duration of the movie
     */
    private int duration;
    /**
     * Opening Date of the movie
     */
    private LocalDate movieOpeningDate;
    /**
     * Ending Date of the movie
     */
    private LocalDate movieEndDate;
    /**
     * Language of the movie
     */
    private String language;
    /**
     * Synopsis of the movie
     */
    private String synopsis;
    /**
     * Director of the movie
     */
    private String director;
    /**
     * Number of tickets sold for the movie
     */
    private long ticketsSold = 0;
    /**
     * List of cast for the movie
     */
    private ArrayList<String> cast;
    /**
     * List of movie reviews for the movie
     */
    private ArrayList<MovieReview> reviews;
    /**
     * List of movie types (e.g 2D, ATMOS) for the movie
     */
    private ArrayList<MovieType> type;
    /**
     * List of showtime IDs for the movie
     */
    private ArrayList<Integer> showtimeIDs;

    //Constructors

    /**
     * Constructor of the Movie which does not require any arguments to instantiate a Movie Object
     */
    public Movie(){
        this.genres = new ArrayList<MovieGenre>();
        this.cast = new ArrayList<String>();
        this.type = new ArrayList<MovieType>();
        this.reviews = new ArrayList<MovieReview>();
        this.showtimeIDs = new ArrayList<Integer>();
        this.id = idCounter;
        idCounter++;
    }

    /**
     * Constructor of the movie which takes in several parameters to instantiate a Movie Object
     * @param id the ID of the movie
     * @param title the title of the movie
     * @param rating the rating of the movie
     * @param type the type of the movie
     * @param synopsis the synopsis of the movie
     * @param duration the duration of the movie
     * @param movieOpeningDate the opening date for the movie
     * @param movieEndDate the ending date for the movie
     * @param language the language that the movie is displayed in
     * @param director the director for the movie
     * @param genres the list of genres for the movie
     * @param ticketsSold the number of tickets sold for the movie
     * @param cast the list of cast who acted in the movie
     */
    public Movie(
            int id, String title, MovieRating rating, ArrayList<MovieType> type, String synopsis, int duration, LocalDate movieOpeningDate, LocalDate movieEndDate, String language, String director, ArrayList<MovieGenre> genres, int ticketsSold,  ArrayList<String> cast
    ){
        this.id = idCounter;
        idCounter++;
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

    private static int getNumberOfExistingMovies() {
        String path = FilePathFinder.findRootPath() + "/src/data/movies";
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
     * Retrieves the show status for the movie
     * @return a MovieStatus of enum type
     */
    public MovieStatus getShowStatus() {
        LocalDate today = LocalDate.now();
        if (today.isAfter(movieEndDate))
            return MovieStatus.END_OF_SHOWING;
        else {
            double daysBetween = Duration.between(today.atStartOfDay(), movieOpeningDate.atStartOfDay()).toDays();
            if (daysBetween > 30) {
                return MovieStatus.COMING_SOON;
            } else if (daysBetween <= 30 && daysBetween > 0) {
                return MovieStatus.PREVIEW;
            } else {
                return MovieStatus.NOW_SHOWING;
            }
        }
    }

    /**
     * Calculates the overall movie rating stars for a movie by taking into account stars for all the reviews for the particular movie
     * @return a String representing the overall rating stars for the movie
     */
    public String getOverallStars(){
        double sum = 0;
        if(reviews.size()>1){
            for(MovieReview review : reviews){
                sum += review.getNumOfStars();
            }
            DecimalFormat df = new DecimalFormat("#.0");
            return df.format(sum/reviews.size());
        }
        else {
            return "N/A";
        }
    }

    /**
     * Calculates the overall movie rating stars for a movie by taking into account stars for all the reviews for the particular movie.
     * This is to be passed into Comparator function in MovieController for sorting based on movie rating stars.
     * @return a Double representing the overall rating stars for the movie
     */
    public Double getOverallStarsDouble() {
        double sum = 0;
        for(MovieReview review : reviews){
            sum += review.getNumOfStars();
        }
        return sum/reviews.size();
    }

    /**
     * Generates a String consisting of all the movie details in a neat format.
     * @return a String consisting of all the movie details
     */
    @Override
    public String toString(){
        String overallCast = "";
        for (int i=0; i<getCast().size(); i++)
            overallCast = overallCast.concat(getCast().get(i) + ", ");
        overallCast = overallCast.substring(0, overallCast.length()-2);


        String allGenres = "";
        for(int i = 0; i<getGenres().size();i++){
            allGenres += getGenres().get(i) + ", ";
        }

        String allTypes = "";
        for (int i=0; i<getType().size(); i++)
            allTypes = allTypes.concat(getType().get(i) + ", ");
        allTypes = allTypes.substring(0, allTypes.length()-2);

        String allShowTimeId = "";
        for (int i=0; i<getShowtimeIDs().size(); i++)
            allShowTimeId = allShowTimeId.concat(getShowtimeIDs().get(i) + ", ");

        if(allShowTimeId.equals(""))
            allShowTimeId = "N/A";
        else {
            allShowTimeId = allShowTimeId.substring(0, allShowTimeId.length()-2);
        }

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
                + "Overall review stars: " + getOverallStars() + "\n";
        return details + "\n";
    }

    // Getters & Setters

    /**
     * Get ID of the Movie
     * @return an ID of int type
     */
    public int getId() {
        return id;
    }

    /**
     * Set ID for the movie
     * @param id an integer, referring to the id for the movie
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get Title of the Movie
     * @return a Title of String type
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set Title for the movie
     * @param title a String, representing the title of the movie
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the movie rating for the movie
     * @return a `MovieRating` enum, representing the rating of the movie
     */
    public MovieRating getRating() {
        return rating;
    }

    /**
     * Set the movie rating for the movie
     * @param rating a `MovieRating` enum, representing the rating of the movie
     */
    public void setRating(MovieRating rating) {
        this.rating = rating;
    }

    /**
     * Returns a list of Movie Genres for the movie
     * @return an ArrayList of `MovieGenre` enumm
     */
    public ArrayList<MovieGenre> getGenres() {
        return genres;
    }

    /**
     * Set a list of genres for the movie
     * @param genres an ArrayList of `MovieGenre` enum
     */
    public void setGenres(ArrayList<MovieGenre> genres) {
        this.genres = genres;
    }

    /**
     * Retrieve duration of the movie
     * @return an integer, representing the duration of the movie
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Set duration for the movie
     * @param duration an integer, representing the duration of the movie
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Retrieve opening date for the movie
     * @return a LocalDate object, representing the opening date for the movie
     */
    public LocalDate getMovieOpeningDate() {
        return movieOpeningDate;
    }

    /**
     * Sets an opening date for the movie
     * @param movieOpeningDate a LocalDate object, representing the opening date of the movie
     */
    public void setMovieOpeningDate(LocalDate movieOpeningDate) {
        this.movieOpeningDate = movieOpeningDate;
    }

    /**
     * Retrieves the ending date for the movie
     * @return a LocalDate object, representing the ending date for the movie
     */
    public LocalDate getMovieEndDate() {
        return movieEndDate;
    }

    /**
     * Set ending date for the movie
     * @param movieEndDate a LocalDate object, representing the ending date for the movie
     */
    public void setMovieEndDate(LocalDate movieEndDate) {
        this.movieEndDate = movieEndDate;
    }

    /**
     * Retrieve the language that the movie will be showed in
     * @return a String, representing the language of the movie
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Set language for the movie to be showed in
     * @param language a String, representing the language of the movie
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Retrieve the synopsis for the movie
     * @return a String, representing the synopsis for the movie
     */
    public String getSynopsis() {
        return synopsis;
    }

    /**
     * Sets the synopsis for the movie
     * @param synopsis a String, representing the synopsis for the movie
     */
    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    /**
     * Retrieve the director for the movie
     * @return a String, representing the director of the movie
     */
    public String getDirector() {
        return director;
    }

    /**
     * Set director for the movie
     * @param director a String, representing the director of the movie
     */
    public void setDirector(String director) {
        this.director = director;
    }

    /**
     * Retrieve the number of tickets sold for the movie
     * @return a long, representing the number of tickets sold for the movie
     */
    public long getTicketsSold() {
        return ticketsSold;
    }

    /**
     * Set the number of tickets sold for the movie
     * @param ticketsSold a long representing the number of tickets sold for the movie
     */
    public void setTicketsSold(long ticketsSold) {
        this.ticketsSold = ticketsSold;
    }

    /**
     * Retrieves a list of cast of the movie
     * @return an ArrayList of String, representing the cast for the movie
     */
    public ArrayList<String> getCast() {
        return cast;
    }

    /**
     * Set a list of cast for the movie
     * @param cast an ArrayList of String, representing the cast for the movie
     */
    public void setCast(ArrayList<String> cast) {
        this.cast = cast;
    }

    /**
     * Retrieve a list of reviews for the movie
     * @return an ArrayList of `MovieReview` objects, representing the reviews for the movie
     */
    public ArrayList<MovieReview> getReviews() {
        return reviews;
    }

    /**
     * Set a list of reviews for the movie
     * @param reviews an ArrayList of `MovieReview` objects, representing the reviews for the movie
     */
    public void setReviews(ArrayList<MovieReview> reviews) {
        this.reviews = reviews;
    }

    /**
     * Retrieves a list of movie types (e.g 2D, 3D, BLOCKBUSTER, ATMOS) that represent a movie
     * @return an ArrayList of `MovieType` enum, representing the types for the movie
     */
    public ArrayList<MovieType> getType() {
        return type;
    }

    /**
     * Set a list of movie types for the movie
     * @param type an ArrayList of `MovieType` enum, representing the types for the movie
     */
    public void setType(ArrayList<MovieType> type) {
        this.type = type;
    }

    /**
     * Retrieve a list of showtimeIDs for the movie
     * @return an ArrayList of integers, representing the showtimeIDs for the movie
     */
    public ArrayList<Integer> getShowtimeIDs() {
        return showtimeIDs;
    }

    /**
     * Set a list of available showtimeIDs for the movie
     * @param showtimeIDs an ArrayList of integers, representing the showtimeIDs for the movie
     */
    public void setShowtimeIDs(ArrayList<Integer> showtimeIDs) {
        this.showtimeIDs = showtimeIDs;
    }

    /**
     * Retrieves the movie opening date for the movie
     * @return a String, representing the opening date for the movie
     */
    public String getMovieOpeningDateString() {
        return movieOpeningDate.format(DateTimeFormatter.ofPattern("EEEE, dd/MM/yyyy"));
    }

    /**
     * Retrieves the movie ending date for the movie
     * @return a String, representing the ending date for the movie
     */
    public String getMovieEndDateString() {
        return movieEndDate.format(DateTimeFormatter.ofPattern("EEEE, dd/MM/yyyy"));
    }


    /**
     * Add a review to the list of movie reviews stored in this Movie object
     * @param review a `MovieReview` object, representing a review for the movie
     */
    public void addMovieReview(MovieReview review) {
        reviews.add(review);
    }

    /**
     * Remove a review from the list of movie reviews stored in this Movie object based on the id of the review
     * @param reviewID an integer, representing the id of the review to be deleted
     */
    public void removeMovieReview(int reviewID) {
        for (int i=0;i<getReviews().size(); i++)
            if (getReviews().get(i).equals(reviewID)) {
                reviews.remove(i);
            }
    }

    /**
     * Add a showtime ID into the list of the showtimeIDs stored in this Movie Object
     * @param showtimeID an integer, representing the id of a showtime
     */
    public void addShowtimeID(int showtimeID) {
        showtimeIDs.add(showtimeID);
    }
}