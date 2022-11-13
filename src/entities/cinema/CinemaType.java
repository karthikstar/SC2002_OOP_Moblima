package entities.cinema;

import entities.booking.PriceChanger;
/**
 * Enum that lists the different cinema types.
 */
public enum CinemaType implements PriceChanger {
    /**
     * Standard Cinema.
     */
    STANDARD("STANDARD"),
    /**
     * Platinum Cinema.
     */
    PLATINUM("PLATINUM");
    /**
     * Stores the string of the CinemaType enum.
     */
    private final String name;

    /**
     * Method that sets the string above as the respective cinema type string.
     * @param name
     */
    private CinemaType(String name){
        this.name = name;
    }

    /**
     * Returns the String of the Cinema Type.
     * @return String of Cinema Type
     */

    @Override
    public String toString() {
        return name;
    }
}
