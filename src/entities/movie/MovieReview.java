package entities.movie;

import java.io.Serializable;

/**
 * Represents a Review of a Movie in the system
 */
//@SuppressWarnings("serial")
public class MovieReview implements Serializable {
    private int reviewId;
    /**
     * this Review's username (email)
     */
    private String username;

    /**
     * this Review's number of stars
     */
    private double numOfStars;

    /**
     * this Review's additional comment (optional)
     */
    private String comment;


    /**
     * Creates a Review with the given attributes
     * Additional comment is optional
     * @param username              this Review's username (email)
     * @param numOfStars            this Review's number of stars
     * @param Comment     this Review's additional comment (optional)
     */
    public MovieReview(int Id, String username, double numOfStars, String Comment){
        this.reviewId = Id;
        this.username = username;
        this.numOfStars = numOfStars;
        this.comment = Comment;
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
    public double getNumOfStars(){
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
    public void setNumOfStars(double numOfStars){
        this.numOfStars = numOfStars;
    }


    /**
     * Change the additional comment given by the reviewew of this Review
     * @param Comment Reviewer's new additional comment for this Review
     */
    public void setAdditionalComment(String Comment){
        this.comment = Comment;
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
                +  "        Number of stars: " + String.valueOf(getNumOfStars()) + "\n"
                +  "        Comment: " + getComment();
        return details;
    }
}
