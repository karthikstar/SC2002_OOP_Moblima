package entities.movie;

/**
 * Enumerated type for better readability and easier referencing to attribute
 */
public enum MovieStatus implements Comparable<MovieStatus> {
    NOW_SHOWING("Now showing"),
    PREVIEW("Preview"),
    COMING_SOON("Coming soon"),
    END_OF_SHOWING("End of showing");

    private final String status;

    private MovieStatus(String status){
        this.status = status;
    }

    @Override
    public String toString(){
        return status;
    }
}
