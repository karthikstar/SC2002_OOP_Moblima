package entities.booking;

/**
 * Enum that stores the various payment methods available to customers on booking.
 */
public enum PaymentMethod {
    masterCard("MasterCard"),
    visa("Visa"),
    grabPay("GrabPay"),
    ocbcPayAnyone("OCBC PayAnyone");
    /**
     * Payment Method.
     */
    private String method;

    /**
     * Method that sets the method attribute to be the given payment method string.
     * @param method Payment Method as a string
     */
    private PaymentMethod(String method) {this.method = method;}

    /**
     * Returns the payment method in the string format provided in enum declaration.
     * @return Payment Method in String Format
     */
    @Override
    public String toString() {
        return method;
    }
}
