package entities.cinema;

import java.io.Serializable;

/**
 * Enum that lists the different cinema availability statuses.
 */
public enum CinemaAvailability implements Serializable {
    /**
     * Cinema that is completely full.
     */
    FULL_CAPACITY("FULL CAPACITY"),
    /**
     * Cinema that have more than half the seats filled.
     */
    SELLING_FAST("SELLING FAST"),
    /**
     * Cinema that have less than half the seats filled.
     */
    OPEN_FOR_SALES("OPEN FOR SALES");
    /**
     * Stores the string of the CinemaAvailability.
     */
    private String availability;

    /**
     * Method that sets the availability of the cinema attribute above.
     * @param availability
     */
    private CinemaAvailability (String availability) {
        this.availability = availability;
    }

    /**
     * Returns the string of the enum of CinemaAvailability.
     * @return String of the enum
     */
    @Override
    public String toString() {
        return this.availability;
    }

}
