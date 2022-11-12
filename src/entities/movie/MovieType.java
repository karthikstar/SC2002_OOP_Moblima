package entities.movie;

import entities.booking.PriceChanger;

/**
 * Enumerated type for better readability and easier referencing to attribute
 */
public enum MovieType implements PriceChanger {
    TWO_D("2D"),
    THREE_D("3D"),
    ATMOS("ATMOS"),
    BLOCKBUSTER ("BLOCKBUSTER"); //Only applicable to 2D!

    private final String type;

    private MovieType(String type) {
        this.type = type;
    }

    public String getType(){
        return type;
    }

    public String toString(){
        return type;
    }
}
