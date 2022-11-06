package entities;

public enum CinemaType {
    STANDARD("STANDARD"),
    PLATINUM("PLATINUM");
    private final String name;
    private CinemaType(String name){
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
