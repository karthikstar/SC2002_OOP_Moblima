package entities.movie;

import utils.FilePathFinder;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * MovieReview is a class that represents a review for a movie, and specifies what parameters a movie review has
 */
public class MovieReview implements Serializable {
    /**
     * Integer to keep count of the number of existing reviews
     */
    private static int idCounter = getNumberOfExistingReviews();
    /**
     * An integer which represents the id of the movie review
     */
    private int reviewId;
    /**
     * A String that represents the username of the person who made the movie review
     */
    private String username;
    /**
     * An integer which represents the number of rating stars the person is allocating for the movie, in this review
     */
    private int numOfStars;
    /**
     * A string which represents the comments for the movie review
     */
    private String comment;
    /**
     * An integer which represents the id of the movie
     */
    private int movieId;
    /**
     * A LocalDateTime object to capture the date and time of the movie review
     */
    private LocalDateTime dateTime;

    /**
     * Constructor that instantiates a MovieReview object without taking in any parameters
     */
    public MovieReview() {
        this.dateTime = LocalDateTime.now();
        this.setReviewId(idCounter);
        idCounter++;
    }

    /**
     * Constructor that instantiates a MovieReview object by taking in several parameters
     * @param Id an integer, representing the id of the review
     * @param username a String, representing the username of the person who wrote the review
     * @param numOfStars an integer, representing the number of rating stars for the review
     * @param Comment a String, representing the comments given for the review
     * @param movieId an integer, representing the id of the movie
     */
    public MovieReview(int Id, String username, int numOfStars, String Comment, int movieId){
        this.reviewId = Id;
        this.username = username;
        this.numOfStars = numOfStars;
        this.comment = Comment;
        this.movieId = movieId;
        this.dateTime = LocalDateTime.now();
    }

    /**
     * To generate id to be used when instantiatng a new MovieReview object
     * @return an integer, representing the id to be used for the new MovieReview object
     */
    public static int requestId() {
        int request = idCounter;
        idCounter++;
        return request;
    }

    /**
     * To retrieve the number of existing reviews which already exist in our data folder for reviews
     * @return an integer, representing the number of reviews which already exist in the data folder for reviews
     */
    private static int getNumberOfExistingReviews() {
        String path = FilePathFinder.findRootPath() + "/src/data/reviews";
        try {
            File directory = new File(path);
            File[] files = directory.listFiles();
        } catch(Exception e) {
            return 0;
        }

        File directory = new File(path);
        File[] files = directory.listFiles();
        if(files.length != 0) {
            return files.length;
        }
        return 0;
    }

    /**
     * Retrieves the id for the MovieReview object
     * @return an integer, representing the id for the MovieReview
     */
    public int getReviewId() {
        return reviewId;
    }

    /**
     * Retrieve the username of reviewer of this MovieReview
     * @return a String, representing the username of the reviewer who made this review
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Set username for this MovieReview instance
     * @param username a String representing the usernmae to be set for this instance
     */
    public void setUsername(String username) {
        this.username = username;
    }


    /***
     * Retrieves the number of movie rating stars that was set for this MovieReview
     * @return an integer, representing the number of movie rating stars set for this MovieReview
     */
    public int getNumOfStars(){
        return this.numOfStars;
    }


    /** Retrieves comment set for this MovieReview instance by the reviewer
     * @return a String, representing the comments provided by reviewer for this MovieReview instance
     */
    public String getComment(){
        return this.comment;
    }

    /**
     * Set an id for the movieReview instance
     * @param reviewId an integer, representing the id to be set for the MovieReview
     */
    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    /**
     * Set the number of movie rating stars for this MovieReview instance
     * @param numOfStars an integer, representing the number of movie rating stars to be set for this MovieReview instance
     */
    public void setNumOfStars(int numOfStars){
        this.numOfStars = numOfStars;
    }


    /**
     * Set comments for this MovieReview, provided by the reviewer
     * @param Comment a String, representing the comments provided by the reviewer for this MovieReview instance
     */
    public void setComment(String Comment){
        this.comment = Comment;
    }

    /**
     * Retrieves the id of the movie, for which this movie review is made
     * @return an integer, representing the id of the movie being reviewed
     */
    public int getMovieId() {
        return movieId;
    }

    /**
     * Set movieID for this MovieReview instance
     * @param movieId an integer, representing the id of the movie for which this review is made for
     */
    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    /**
     * Retrieves a DateTime object which represents the date and time that this movie review was made
     * @return a DateTime object which represents the date and time of creation of this movie review
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Sets a DateTime object for this MovieReview, to represent the date and time for this movie review
     * @param dateTime a DateTime object, which represents the date and time to be set for this instance
     */
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * String to be returned when this instance is called
     * @return a String, which represents the details of this MovieReview instance
     */
    @Override
    public String toString(){
        String details = "";
        details += "        Review ID: " + getReviewId() + "\n"
                +  "        Username: " + getUsername() + "\n"
                +  "        Date & Time: " + getDateTime().format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh.mma")) + "\n"
                +  "        Number of stars: " + String.valueOf(getNumOfStars()) + "/5\n"
                +  "        Comment: " + getComment();
        return details;
    }
}
