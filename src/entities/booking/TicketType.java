package entities.booking;

/**
 * Enum that stores the various ticket types.
 */
public enum TicketType implements PriceChanger{
    ADULT("ADULT"),
    SENIOR("SENIOR"),
    STUDENT("STUDENT"),
    HOLIDAY("HOLIDAY"),
    WEEKEND("WEEKEND");
    /**
     * Ticket Type as a string.
     */
    private String type;

    /**
     * Sets the type attribute to be its corresponding string ticket type.
     * @param type Ticket Type
     */
    private TicketType(String type) {
        this.type = type;
    }

    /**
     * Return the string of the Ticket Type.
     * @return String of the Ticket Type
     */
    @Override
    public String toString() {
        return type;
    }


}
