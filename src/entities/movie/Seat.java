package entities.movie;

public class Seat {
    private String seatID;
    private boolean assigned = false;

    public Seat (String id) {
        seatID = id;
    }

    public Seat (String id, boolean assigned) {
        seatID = id;
        this.assigned = assigned;
    }

    public String getSeatID() {
        return seatID;
    }

    public void setSeatID(String seatID) {
        this.seatID = seatID;
    }

    public boolean isAssigned() {
        return assigned;
    }

    public void assign() {
        assigned = true;
    }

    public void unassign() {
        assigned = false;
    }
}
