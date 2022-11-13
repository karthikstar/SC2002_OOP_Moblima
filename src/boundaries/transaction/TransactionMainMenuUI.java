package boundaries.transaction;

/**
 * Stores the UI for transaction menus.
 */
public class TransactionMainMenuUI {
    /**
     * Prints the Booking Confirmation menu to proceed to payment.
     */
    public static void printMenu() {
        System.out.println("---------------- BOOKING CONFIRMATION (PAYMENT PORTAL) ----------------\n"+
                " 1. Enter payment details                           \n"+
                " 0. Back to ticket selection                        \n"+
                "------------------------------------------------------");
        System.out.println("Please enter a choice:");
    }
}
