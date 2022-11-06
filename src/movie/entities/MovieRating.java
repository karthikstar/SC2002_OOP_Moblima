package movie.entities;

public enum MovieRating {
    G ("G"),
    PG ("PG"),
    PG13 ("PG13"),
    NC16 ("NC16"),
    M18 ("M18"),
    R21 ("R21");

    private final String names;

    private MovieRating(String names) { this.names = names; }

//    /**
//     * For string comparison.
//     * @param otherName String to be compared to.
//     * @return boolean on whether the String value of MovieRating is equals to otherName.
//     */
//    public boolean equalsString(String otherName) {
//        // (otherName == null) check is not needed because name.equals(null) returns false
//        return names.equals(otherName);
//    }

    public String toString() {
        return this.names;
    }
}
