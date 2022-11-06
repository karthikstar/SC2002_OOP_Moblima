package entities.booking;

public enum TicketType {
    ADULT,
    SENIOR,
    CHILDREN,
    STUDENT;

    private String type;

    @Override
    public String toString() {
        return type;
    }


}
