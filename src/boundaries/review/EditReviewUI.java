package boundaries.review;

/**
 * Stores the Review Options UI.
 */
public class EditReviewUI {
    /**
     * Prints the Review Editing Menu.
     */
    public static void printMenu() {
        System.out.println(	"------------------------ EDIT REVIEW OPTIONS --------------------\n" +
                " 1. Edit Review Comment   						    	\n" +
                " 2. Edit Number of Stars	   						    	 	 \n" +
                " 3. Edit Name   						    	 	 \n" +
                " 0. Editing Complete!				            \n"+
                "---------------------------------------------------------");

        System.out.println("Please select one of the above choices between 0 to 3: ");
    }
}
