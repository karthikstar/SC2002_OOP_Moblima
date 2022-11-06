package movie.entities;

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


    private final String names;

    /**
     * Constructor for Genre enum, taking in the string value of the enum and setting it as an attribute.
     * @param name of the enum.
     */
    private MovieGenre(String name) { this.names = name; }

//    /**
//     * For string comparison.
//     * @param otherName String to be compared to.
//     * @return boolean on whether the String value of Genre is equals to otherName.
//     */
//    public boolean equalsString(String otherName) {
//        (otherName == null) check is not needed because name.equals(null) returns false
//        return names.equals(otherName);
//    }

    @Override
    public String toString() {
        return this.names;
    }
}
