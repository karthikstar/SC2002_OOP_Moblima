package booking.entities;

public enum paymentMethod {
    masterCard("MasterCard"),
    visa("Visa"),
    grabPay("GrabPay"),
    ocbcPayAnyone("OCBC PayAnyone");

    private String method;

    private paymentMethod(String method) {this.method = method;}

    @Override
    public String toString() {
        return method;
    }
}
