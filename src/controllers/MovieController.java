package controllers;

import boundaries.movie.MovieMainMenuUI;
import boundaries.movie.MovieByStatusUI;
import entities.movie.Movie;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

public class MovieController {

    Scanner sc = new Scanner(System.in);
    private Map<String,Movie> movies;
    private void MovieManager() {
        this.movies= new HashMap<String,Movie>();
        this.load();
    }

    private static MovieController single_instance = null;

    public static MovieController getInstance() {
        if (single_instance == null)
            single_instance = new MovieController();
        return single_instance;
    }

    public void movieMenuStaff() {
        int choice;

        do {
            MovieMainMenuUI.printStaffMenu();

            choice = InputController.getUserInt();

            switch (choice) {
                case 1:
                    this.viewMovies("Staff");
                    break;
                case 2:
                    this.addMovies();
                    break;
                case 3:
                    this.searchMovies("Staff");
                    break;
                case 0:
                    System.out.println("Back to Staff Movie Master Menu......");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 0-3.");
                    break;
            }
        } while (choice != 0);
    }

    public void viewMovies(String userType) {
        int choice;

        do {
            MovieByStatusUI.printStaffMenu();

            choice = InputController.getUserInt();

            switch (choice) {
                case 1:
                    ArrayList<Movie> allShowingMovies = new ArrayList<Movie>();
                    for (Map.Entry<String, Movie> indivMovie : movies.entrySet()) {
                        if (indivMovie.getValue().getShowStatus().equals("COMING_SOON") ||
                                indivMovie.getValue().getShowStatus().equals("ADVANCE_SALES") ||
                                indivMovie.getValue().getShowStatus().equals("NOW_SHOWING")) {
                            allShowingMovies.add(indivMovie.getValue());
                        }
                    }
                    this.selectMovie(allShowingMovies, userType);
                    break;
                case 2:
                    ArrayList<Movie> comingSoon = new ArrayList<Movie>();
                    for (Map.Entry<String, Movie> indivMovie : movies.entrySet()) {
                        if (indivMovie.getValue().getShowStatus().equals("COMING_SOON")) {
                            comingSoon.add(indivMovie.getValue());
                        }
                    }
                    this.selectMovie(comingSoon, userType);
                    break;
                case 3:
                    ArrayList<Movie> advanceSales = new ArrayList<Movie>();
                    for (Map.Entry<String, Movie> indivMovie : movies.entrySet()) {
                        if (indivMovie.getValue().getShowStatus().equals("ADVANCE_SALES")) {
                            advanceSales.add(indivMovie.getValue());
                        }
                    }
                    this.selectMovie(advanceSales, userType);
                    break;
                case 4:
                    ArrayList<Movie> nowShowing = new ArrayList<Movie>();
                    for (Map.Entry<String, Movie> indivMovie : movies.entrySet()) {
                        if (indivMovie.getValue().getShowStatus().equals("NOW_SHOWING")) {
                            nowShowing.add(indivMovie.getValue());
                        }
                    }
                    this.selectMovie(nowShowing, userType);
                    break;
                case 5:
                    ArrayList<Movie> endShowing = new ArrayList<Movie>();
                    for (Map.Entry<String, Movie> indivMovie : movies.entrySet()) {
                        if (indivMovie.getValue().getShowStatus().equals("END_OF_SHOWING")) {
                            endShowing.add(indivMovie.getValue());
                        }
                    }
                    this.selectMovie(endShowing, userType);
                    break;
                case 0:
                    System.out.println("Back to Movie Main Menu...");
                    break;
            }
        } while (choice != 0);
    }

        private void selectMovie(ArrayList<Movie> list, String userType) {
            int choice, choice2;
            do {
                for(int i = 0 ; i < list.size() ; i++) {
                    System.out.println((i+1) + ") " + list.get(i).getTitle() + " [" + list.get(i).getShowStatus().toString()+ "]");
                }

                int choice = -1;
                do {
                    System.out.println("Please select the movie to view information for (Enter 0 to exit).");
                    System.out.printf("Enter choice: ");
                    choice = InputController.getUserInt();
                    choice -= 1;
                    if (choice < -1 || choice >= list.size()) System.out.println("Invalid choice. Please choose a number between 0 and " + list.size());
                } while (choice < -1 || choice >= list.size());

                if (choice == -1) return;

                System.out.println(list.get(choice).toString());

                // PART TO DIRECT INTO MOVIE SPECIFIC STUFF!!! ALWAYS DO WHILE LOOP THE BRANCHESSSS!!!!
                indivMovieOptions(list.get(choice), userType);
                System.out.println("Enter 0 to return to Movie Menu \n" +
                        "Enter any other number (no decimal) to return to list of movies:");
                choice2 = InputController.getUserInt();
            } while (choice2 != 0);
        }

        private void indivMovieOptions (Movie movie, String userType) {

        }
}
