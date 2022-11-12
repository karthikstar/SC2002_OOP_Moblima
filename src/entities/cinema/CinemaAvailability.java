package entities.cinema;

import java.io.Serializable;

public enum CinemaAvailability implements Serializable {
    FULL_CAPACITY("FULL CAPACITY"),
    SELLING_FAST("SELLING FAST"),
    OPEN_FOR_SALES("OPEN FOR SALES");

    private String availability;

    private CinemaAvailability (String availability) {
        this.availability = availability;
    }

    @Override
    public String toString() {
        return this.availability;
    }

}
