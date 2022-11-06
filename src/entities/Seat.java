package entities;

public class Seat {
    private int seatID;
    private boolean assigned = false;

    public Seat (int id) {
        seatID = id;
    }

    public Seat (int id, boolean assigned) {
        seatID = id;
        this.assigned = assigned;
    }

    public int getSeatID() {
        return seatID;
    }

    public void setSeatID(int seatID) {
        this.seatID = seatID;
    }

    public boolean isAssigned() {
        return assigned;
    }

    public void assign() {
        assigned = true;
    }

    public void unAssign() {
        assigned = false;
    }
}
