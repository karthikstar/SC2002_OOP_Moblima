package entities.movie;

/**
 * MovieRating is an enum, which represents the rating of a movie (e.g G, NC16, R21)
 */
public enum MovieRating {
    G ("G"),
    PG ("PG"),
    PG13 ("PG13"),
    NC16 ("NC16"),
    M18 ("M18"),
    R21 ("R21");

    /**
     * String representing the name of the movie rating
     */
    private final String names;

    /**
     * Constructor for the `MovieRating` enum
     * @param names a String representing the name of the MovieRating, and will be set as an attribute
     */
    private MovieRating(String names) { this.names = names; }


    /**
     * For Conversion to String format
     * @return a String representing the name of the movie rating
     */
    public String toString() {
        return this.names;
    }
}
