package controllers;

import boundaries.movie.MovieMasterMenu;
import boundaries.movie.ViewMovieMenu;
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
            MovieMasterMenu.getInstance().masterMenuStaff();

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
            ViewMovieMenu.getInstance().viewMovies(userType);

            choice = InputController.getUserInt();

            switch (choice) {
                case 1:
                    ArrayList<Movie> allShowingMovies = new ArrayList<Movie>;
                    for(Map.Entry<String,Movie> indivMovie : movies.entrySet()){
                        if(indivMovie.getValue().getShowStatus().equals("COMING_SOON")||
                                indivMovie.getValue().getShowStatus().equals("ADVANCE_SALES")||
                                indivMovie.getValue().getShowStatus().equals("NOW_SHOWING")){
                            allShowingMovies.add(indivMovie.getValue());
                        }
                    }
                    this.selectMovie(allShowingMovies,userType);
                    break;
                case 2:
                    List<Movie> comingSoon = new ArrayList<Movie>();
                    for(Map.indivMovie<String,Movie> indivMovie : movies.indivMovieSet()){
                        if(indivMovie.getValue().getShowingStatus().equalsString("COMING_SOON")){
                            comingSoon.add(indivMovie.getValue());
                        }
                    }
                    this.selectMovie(comingSoon,appType);
                    break;
                case 3:
                    List<Movie> preview = new ArrayList<Movie>();
                    for(Map.indivMovie<String,Movie> indivMovie : movies.indivMovieSet()){
                        if(indivMovie.getValue().getShowingStatus().equalsString("PREVIEW")){
                            preview.add(indivMovie.getValue());
                        }
                    }
                    this.selectMovie(preview,appType);
                    break;
                case 4:
                    List<Movie> nowShowing = new ArrayList<Movie>();
                    for(Map.indivMovie<String,Movie> indivMovie : movies.indivMovieSet()){
                        if(indivMovie.getValue().getShowingStatus().equalsString("NOW_SHOWING")){
                            nowShowing.add(indivMovie.getValue());
                        }
                    }
                    this.selectMovie(nowShowing,appType);
                    break;
                case 5:
                    List<Movie> endShowing = new ArrayList<Movie>();
                    for(Map.indivMovie<String,Movie> indivMovie : movies.indivMovieSet()){
                        if(indivMovie.getValue().getShowingStatus().equalsString("END_OF_SHOWING")){
                            endShowing.add(indivMovie.getValue());
                        }
                    }
                    this.selectMovie(endShowing,appType);
                    break;
                case 0:
                    System.out.println("Back to StaffApp...");
                    break;
        } while (choice != 0);
    }

}
