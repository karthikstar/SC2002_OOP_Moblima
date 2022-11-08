// MAYBE CHANGE GETUSERINT THINGS LATER!
package controllers;

import boundaries.movie.MovieMainMenuUI;
import boundaries.movie.MovieByStatusUI;
import boundaries.movie.MovieOptionsUI;
import entities.movie.Movie;
import entities.movie.MovieGenre;
import entities.movie.MovieRating;
import entities.movie.MovieReview;
import entities.movie.MovieStatus;
import entities.movie.MovieType;

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

            choice = InputController.getUserInt(0,5);

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
            if (userType.equals("Staff")) {
                int choice;
                do {
                    MovieOptionsUI.printStaffMenu();
                    choice = InputController.getUserInt(0,5);
                    switch (choice) {
                        case 1:
                            ShowtimeController.getInstance().getMovieShowtimes(movie.getId(), userType);
                            break;
                        case 2:
                            this.editMovies(movie);
                            break;
                        case 3:
                            this.removeMovie(movie);
                            break;
                        case 4:
                            ReviewManager.getInstance().printReviews(movie.getReviews());
                            break;
                        case 5:
                            ReviewManager.getInstance().deleteReview(movie.getReviews());
                            break;
                        case 0:
                            System.out.println("Back to Movie Listings......");
                            break;
//                    default:
//                        System.out.println("Please enter a number between 0-5");
                    }
                } while (choice != 0);
            }
            else if (userType.equals("Customer") && movie.getShowStatus().equals(MovieStatus.COMING_SOON)) {
                System.out.println("No movie options for movies that are COMING SOON!");
            }
            else {
                int choice;
                do {
                    MovieOptionsUI.printCustomerMenu();
                    choice = InputController.getUserInt(0, 3);
                    switch (choice) {
                        case 1:
                            ShowtimeManager.getInstance().getMovieShowtimes(movie.getId(),userType);
                            break;
                        case 2:
                            ReviewManager.getInstance().printReviews(movie.getReviews());
                            break;
                        case 3:
                            ReviewManager.getInstance().addReview(movie.getId());
                            break;
                        case 0:
                            System.out.println("Back to Movie Listings......");
                            break;
//                        default:
//                            System.out.println("Please enter a number between 0-3");
//                            break;
                    }
                } while(choice != 0);
            }
        }

    private void searchMovies(String userType){
        System.out.println("Please enter your search: ");
        String search = InputController.getUserString().toLowerCase();

        ArrayList<Movie> results = new ArrayList<>();

        for (Movie movie : movies.values()) {
            if (movie.getTitle().toLowerCase().contains(search)) {
                results.add(movie);
            }
        }
        if(results.size() == 0){
            System.out.println("No such movie found!");
            return;
        }
        selectMovie(results,userType);
    }

    private void addMovies() {
        Movie newMovie = new Movie();
        ArrayList<MovieGenre> tempGenreList = new ArrayList<MovieGenre>();
        ArrayList<MovieType> tempTypeList = new ArrayList<MovieType>();
        ArrayList<String> tempCastList = new ArrayList<String>();

        System.out.println("Enter movie title: ");
        String title = InputController.getUserString();
        newMovie.setTitle(title);

        System.out.println("Pick movie rating: ");
        for(int i=0;i<MovieRating.values().length;i++){System.out.println(i+1 + ". " +MovieRating.values()[i].toString());}
        int movieRatingChoice = InputController.getUserInt(1, MovieRating.values().length)-1;
        System.out.println("You picked "+MovieRating.values()[movieRatingChoice].toString() + ".");
        newMovie.setRating(MovieRating.values()[movieRatingChoice]);

        System.out.println("List of Genres:");
        for(int i=0;i<MovieGenre.values().length;i++)System.out.println(i+1 +". " +MovieGenre.values()[i].toString());
        System.out.println("Enter number of genres: ");
        int numGenres = InputController.getUserInt();
        for (int i=0;i<numGenres;i++) {
            System.out.printf("Enter choice number of genre %d: ", i+1);
            int choice = InputController.getUserInt()-1;
            System.out.println("You picked "+MovieGenre.values()[choice].toString() + ".");
            tempGenreList.add(MovieGenre.values()[choice]);
        }
        newMovie.setGenres(tempGenreList);

        System.out.println("Enter movie duration (in minutes): ");
        newMovie.setDuration(InputController.getUserInt());

        System.out.println("Enter opening date (format DD/MM/YYYY): ");
        newMovie.setMovieOpeningDate(InputController.getDate());

        System.out.println("Enter end date (format DD/MM/YYYY): ");
        newMovie.setMovieEndDate(InputController.getDate());

        //CONSIDER MAKING ENUM OF LANGUAGES?
        System.out.println("Enter language: ");
        newMovie.setLanguage(InputController.getUserString());

        System.out.println("Enter synopsis: ");
        newMovie.setSynopsis(InputController.getUserString());

        System.out.println("Enter director: ");
        newMovie.setDirector(InputController.getUserString());

        System.out.println("Enter number of cast members: ");
        int numCast = InputController.getUserInt();
        for (int i=0;i<numCast;i++) {
            System.out.printf("Enter name of cast member number %d: ", i+1);
            tempCastList.add(InputController.getUserString());
        }
        newMovie.setCast(tempCastList);

        System.out.println("List of Movie Types: ");
        for(int i=0;i<MovieType.values().length;i++)System.out.println(i+1 +". " +MovieType.values()[i].toString());
        System.out.println("Enter number of movie types to be added: ");
        int numTypes = InputController.getUserInt();
        for (int i=0;i<numTypes;i++) {
            System.out.printf("Enter choice number of movie type %d: ", i+1);
            int choice = InputController.getUserInt()-1;
            System.out.println("You picked "+MovieType.values()[choice].toString() + ".");
            tempTypeList.add(MovieType.values()[choice]);
        }
        newMovie.setType(tempTypeList);

        //SETTING UP OF SHOWTIMES???


        int choice;

        do {
            System.out.println(newMovie.toString());
            System.out.println();
            System.out.println(	"========================= ADD MOVIE ====================\n" +
                    " 1. Submit movie                                      \n" +
                    " 2. Edit movie                                        \n" +
                    " 0. Discard movie, back to Movie Menu                 \n"+
                    "========================================================");
            System.out.println("Enter choice: ");

            while (!sc.hasNextInt()) {
                System.out.println("Invalid input type. Please enter an integer value between 0-2.");
                sc.next(); // Remove newline character
            }

            choice1 = sc.nextInt();

            switch (choice1) {
                case 1:
                    String movieID = IDHelper.getLatestID("movie");
                    newMovie.setMovieID(movieID);
                    this.save(newMovie);
                    this.movies.put(newMovie.getMovieID(), newMovie);

                    System.out.println("Movie created! Back to Movie Menu......");
                    choice1 = 0;
                    break;
                case 2:
                    this.editMovies(newMovie);
                    break;
                case 0:
                    System.out.println("Movie discarded. Back to Movie Menu......");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 0-2");
                    break;
            }

        } while (choice != 0);
    }




}
