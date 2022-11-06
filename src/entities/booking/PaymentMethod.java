package entities.booking;

public enum PaymentMethod {
    masterCard("MasterCard"),
    visa("Visa"),
    grabPay("GrabPay"),
    ocbcPayAnyone("OCBC PayAnyone");

    private String method;

    private PaymentMethod(String method) {this.method = method;}

    @Override
    public String toString() {
        return method;
    }
}
