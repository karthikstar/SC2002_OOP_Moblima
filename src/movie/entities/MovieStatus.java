package movie.entities;

/**
 * Enumerated type for better readability and easier referencing to attribute
 */
public enum MovieStatus{
    COMING_SOON("Coming soon"),
    ADVANCE_SALES("Advance Sales"),
    NOW_SHOWING("Now showing"),
    END_OF_SHOWING("End of showing");

    private final String status;

    private MovieStatus(String status){
        this.status = status;
    }

    public String asString(){
        return status;
    }
}
