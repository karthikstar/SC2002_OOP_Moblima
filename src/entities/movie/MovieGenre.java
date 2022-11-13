package entities.movie;

/**
 * MovieGenre is a enum data type that represents various genres of a movie. These can be set by staff as an attribute of movies. This is visible to customers as well.
 */
public enum MovieGenre {
    ACTION ("ACTION"),
    ANIMATION ("ANIMATION"),
    ADVENTURE ("ADVENTURE"),
    COMEDY ("COMEDY"),
    CRIME ("CRIME"),
    DRAMA ("DRAMA"),
    FANTASY ("FANTASY"),
    HISTORICAL ("HISTORICAL"),
    HORROR ("HORROR"),
    MYSTERY ("MYSTERY"),
    ROMANCE ("ROMANCE"),
    SCIFI ("SCIFI"),
    SUPERHERO ("SUPERHERO"),
    THRILLER ("THRILLER"),
    WESTERN ("WESTERN");

    /**
     * String representing the name of the movie genre
     */
    private final String names;

    /**
     * Constructor for MovieGenre
     * @param name a String, representing the name of the MovieGenre
     */
    private MovieGenre(String name) { this.names = name; }

    /**
     * For Conversion to String format
     * @return a String, representing the String value of the genre
     */
    @Override
    public String toString() {
        return this.names;
    }
}
