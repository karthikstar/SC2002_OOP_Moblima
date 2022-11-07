package boundaries.movie;

public class MovieMasterMenu {
    private static MovieMasterMenu single_instance = null;

    public static MovieMasterMenu getInstance() {
        if (single_instance == null)
            single_instance = new MovieMasterMenu();
        return single_instance;
    }

    public void masterMenuStaff() {
            System.out.println("------------------- MOVIE MASTER CONTROL (STAFF) ------------------\n" +
                    " 1. View/ Edit Movies 						    		\n" +
                    " 2. Add Movies		                                 	\n" +
                    " 3. Search Movies (By Title)	                        \n" +
                    " 0. Back to StaffApp......                             \n"+
                    "----------------------------------------------------------");

            System.out.println("Enter choice: ");
    }

}
