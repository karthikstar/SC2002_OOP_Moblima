package entities.movie;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Review of a Movie in the system
 */
//@SuppressWarnings("serial")
public class MovieReview implements Serializable {
    private static int idCounter = 1;
    private int reviewId;
    private String username;
    private int numOfStars;
    private String comment;
    private int movieId;
    private LocalDateTime dateTime;

    public MovieReview() {
        this.dateTime = LocalDateTime.now();
    }
    public MovieReview(int Id, String username, int numOfStars, String Comment, int movieId){
        this.reviewId = Id;
        this.username = username;
        this.numOfStars = numOfStars;
        this.comment = Comment;
        this.movieId = movieId;
        this.dateTime = LocalDateTime.now();
    }

    public static int requestId() {
        int request = idCounter;
        idCounter++;
        return request;
    }

    public int getReviewId() {
        return reviewId;
    }

    /**
     * Get username (email) of reviewer of this Review
     * @return String   Reviewer's username for this Review
     */
    public String getUsername() {
        return this.username;
    }


    /** Get number of stars given by the reviewer of this Review
     * @return double   Reviewer's number of stars given for this Review
     */
    public int getNumOfStars(){
        return this.numOfStars;
    }


    /** Get additional comment given by the reviewer of this Review
     * @return String   Reviewer's additional comment for this Review
     */
    public String getComment(){
        return this.comment;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    /**
     * Change the number of stars given by the reviewer of this Review
     * @param numOfStars    Reviewer's new number of stars for this Review
     */
    public void setNumOfStars(int numOfStars){
        this.numOfStars = numOfStars;
    }


    /**
     * Change the additional comment given by the reviewew of this Review
     * @param Comment Reviewer's new additional comment for this Review
     */
    public void setAdditionalComment(String Comment){
        this.comment = Comment;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * String to return when this Movie_Goer is being called
     * @return String
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
