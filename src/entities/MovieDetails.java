package entities;

import java.util.ArrayList;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

public class MovieDetails implements Serializable {
    private int id;
    private String title;
    private double duration;

    private LocalDate movieOpeningDate;
    private LocalDate movieEndDate;

    private String language;
    private String synopsis;
    private String director;
    private ArrayList<String> cast;

    private String rating;
    private ArrayList<MovieReview> reviews;
    private MovieType type;

    public MovieDetails(
            int id, String title, MovieType type, String synopsis, String rating, double duration, LocalDate movieOpeningDate, LocalDate movieEndDate, String language, String director, ArrayList<String> cast
    ){
        this.id = id;
        this.title = title;
        this.type = type;
        this.synopsis = synopsis;
        this.rating = rating;
        this.duration = duration;
        this.movieOpeningDate = movieOpeningDate;
        this.movieEndDate = movieEndDate;
        this.language = language;
        this.director = director;
        this.cast = cast;
        this.reviews = new ArrayList<MovieReview>();
    }

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

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public LocalDate getMovieOpeningDate() {
        return movieOpeningDate;
    }

    public String getMovieOpeningDateString() {
        return movieOpeningDate.format(DateTimeFormatter.ofPattern("EEEE, dd/MM/yyyy"));
    }

    public void setMovieOpeningDate(LocalDate movieOpeningDate) {
        this.movieOpeningDate = movieOpeningDate;
    }

    public LocalDate getMovieEndDate() {
        return movieEndDate;
    }

    public String getMovieEndDateString() {
        return movieEndDate.format(DateTimeFormatter.ofPattern("EEEE, dd/MM/yyyy"));
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

    public ArrayList<String> getCast() {
        return cast;
    }

    public void setCast(ArrayList<String> cast) {
        this.cast = cast;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public ArrayList<MovieReview> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<MovieReview> reviews) {
        this.reviews = reviews;
    }

    public MovieType getType() {
        return type;
    }

    public void setType(MovieType type) {
        this.type = type;
    }

    /**
     * Get the screening status of the Movie
     * Screening status derived from finding the difference between the start/end date and today's date
     * If today's date is after the end date of the movie, it will display "END OF SHOWING"
     * Else, if days difference between today and Movie's start date is more than 30, it is "COMING SOON"
     * Else, if days difference between today and Movie's start date is less than 30, it is "ADVANCE SALES"
     * Else, if today is after Movie's start date and before Movie's end date, it is "NOW SHOWING"
     * @return MovieStatus  Screening status of the movie
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
                return MovieStatus.ADVANCE_SALES;
            } else {
                return MovieStatus.NOW_SHOWING;
            }
        }
    }

    public String getOverallRating(){
        double sum = 0;
        if(reviews.size()>1){
            for(MovieReview review : reviews){
                sum += review.getNumOfStars();
            }
            DecimalFormat df = new DecimalFormat("#.##");
            return df.format(sum/reviews.size());
        }
        else {
            return "N/A";
        }
    }

    public String toString(){
        String overallCast = "";
        for (int i=0; i<getCast().size(); i++)
            overallCast = overallCast.concat(getCast().get(i) + ",");
        overallCast = overallCast.substring(0, overallCast.length()-1);

        String reviews = "";
        for(int i = 0; i<getReviews().size();i++){
            reviews += getReviews().get(i) + "\n\n";
        }
        if(reviews.equals(""))
            reviews = "N/A";

        String details = "";
        details += "ID: " + getId() + "\n"
                + "Title: " + getTitle() + "\n"
                + "Type: " + getType() + "\n"
                + "Status: " + getShowStatus().asString() + "\n"
                + "Synopsis: " + getSynopsis() + "\n"
                + "Rating: " + getRating() + "\n"
                + "Duration: " + String.valueOf(getDuration()) + " hour(s)\n"
                + "Release date: " +  getMovieOpeningDateString() + "\n"
                + "End date: " +  getMovieEndDateString() + "\n"
                + "Director: " + getDirector() + "\n"
                + "Cast: " + overallCast + "\n"
                + "Overall review rating: " + getOverallRating() + "\n"
                + "Reviews: \n\n" + reviews;
        return details + "\n";
    }


}