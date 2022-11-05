package entities;

import java.io.Serializable;

/**
 * Represents a Review of a Movie in the system
 */
//@SuppressWarnings("serial")
public class MovieReview implements Serializable {

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
    private String Comment;


    /**
     * Creates a Review with the given attributes
     * Additional comment is optional
     * @param username              this Review's username (email)
     * @param numOfStars            this Review's number of stars
     * @param Comment     this Review's additional comment (optional)
     */
    public MovieReview(String username, double numOfStars, String Comment){
        this.username = username;
        this.numOfStars = numOfStars;
        this.Comment = Comment;
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
        return this.Comment;
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
        this.Comment = Comment;
    }


    /**
     * String to return when this Movie_Goer is being called
     * @return String
     */

    public String asString(){
        String details = "";
        details += "        Username: " + getUsername() + "\n"
                +  "        Number of stars: " + String.valueOf(getNumOfStars()) + "\n"
                +  "        Comment: " + getComment();
        return details;
    }
}
