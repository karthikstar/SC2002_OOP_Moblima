package entities.movie;

/**
 * MovieStatus is an enum, which represents the status of a Movie, such as whether it is coming soon, or showing currently
 */
public enum MovieStatus implements Comparable<MovieStatus> {
    NOW_SHOWING("Now showing"),
    PREVIEW("Preview"),
    COMING_SOON("Coming soon"),
    END_OF_SHOWING("End of showing");

    /**
     * A String representing the status of the movie
     */
    private final String status;

    /**
     * Constructor for MovieStatus
     * @param status
     */
    private MovieStatus(String status){
        this.status = status;
    }

    /**
     * String to be returned when this instance is called
     * @return a String, representing the status of a movie
     */
    @Override
    public String toString(){
        return status;
    }
}
