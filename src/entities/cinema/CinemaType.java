package entities.cinema;

import entities.booking.PriceChanger;

public enum CinemaType implements PriceChanger {
    STANDARD("STANDARD"),
    PLATINUM("PLATINUM");
    private final String name;
    private CinemaType(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
