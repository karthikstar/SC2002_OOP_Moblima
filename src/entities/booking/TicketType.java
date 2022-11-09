package entities.booking;

public enum TicketType implements PriceChanger{
    ADULT("ADULT"),
    SENIOR("SENIOR"),
    STUDENT("STUDENT"),
    HOLIDAY("HOLIDAY"),
    WEEKEND("WEEKEND");

    private String type;

    private TicketType(String type) {
        this.type = type;
    }
    @Override
    public String toString() {
        return type;
    }


}
