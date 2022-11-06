package entities.movie;

/**
 * Enumerated type for better readability and easier referencing to attribute
 */
public enum MovieType {
    TWO_D("2D"),
    THREE_D("3D"),
    ATMOS("ATMOS"),
    BLOCKBUSTER ("BLOCKBUSTER");

    private final String type;

    private MovieType(String type) {
        this.type = type;
    }

    public String getType(){
        return type;
    }
}
