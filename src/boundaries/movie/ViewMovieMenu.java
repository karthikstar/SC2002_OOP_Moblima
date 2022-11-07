package boundaries.movie;

public class ViewMovieMenu {
    private static ViewMovieMenu single_instance = null;

    public static ViewMovieMenu getInstance() {
        if (single_instance == null)
            single_instance = new ViewMovieMenu();
        return single_instance;
    }

    public void viewMovies(String userType) {
        if (userType.equals("Staff")) {
            System.out.println("------------------- VIEW/ EDIT MOVIES (STAFF) ------------------\n" +
                    " 1. List all movies	                                \n" +
                    " 2. Coming Soon 						       			\n" +
                    " 3. Preview		                                    \n" +
                    " 4. Now Showing	                                    \n" +
                    " 5. End of Showing                                     \n" +
                    " 0. Back to Movies Master Control......                     \n" +
                    "---------------------------------------------------------");

            System.out.println("Enter choice: ");
        }
        else {

        }
    }
}
