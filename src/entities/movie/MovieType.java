package entities.movie;

import entities.booking.PriceChanger;

/**
 * MovieType is an enum, representing the type of the movie, such as whether it is 2D or 3D
 */
public enum MovieType implements PriceChanger {
    TWO_D("2D"),
    THREE_D("3D"),
    ATMOS("ATMOS"),
    BLOCKBUSTER ("BLOCKBUSTER"); //Only applicable to 2D!

    /**
     * String representing the type of movie
     */
    private final String type;

    /**
     * Constructor of MovieType
     * @param type a String representing the type of movie
     */
    private MovieType(String type) {
        this.type = type;
    }

    /**
     * Retrieves the type of movie
     * @return a String, representing the type of movie
     */
    public String getType(){
        return type;
    }

    /**
     * String to be returned when MovieType is called
     * @return a String, representing the type of movie
     */
    public String toString(){
        return type;
    }
}
