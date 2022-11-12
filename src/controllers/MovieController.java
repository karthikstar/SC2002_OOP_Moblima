// MAYBE CHANGE GETUSERINT THINGS LATER!
package controllers;

import boundaries.movie.*;
import entities.movie.*;
import utils.DataSerializer;
import utils.FilePathFinder;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MovieController {
    private Map<Integer,Movie> movies;
    private MovieController() {
        this.movies = new HashMap<Integer, Movie>();
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

        if (userType.equals("Staff")) {
            do {
                MovieByStatusUI.printStaffMenu();

                choice = InputController.getUserInt(0,5);

                switch (choice) {
                    case 1:
                        ArrayList<Movie> allShowingMovies = new ArrayList<Movie>();
                        for (Map.Entry<Integer, Movie> indivMovie : movies.entrySet()) {
                            if (indivMovie.getValue().getShowStatus().equals(MovieStatus.valueOf("COMING_SOON"))  ||
                                    indivMovie.getValue().getShowStatus().equals(MovieStatus.valueOf("PREVIEW"))  ||
                                    indivMovie.getValue().getShowStatus().equals(MovieStatus.valueOf("NOW_SHOWING")) ||
                                    indivMovie.getValue().getShowStatus().equals(MovieStatus.valueOf("END_OF_SHOWING")))  {
                                allShowingMovies.add(indivMovie.getValue());
                            }
                        }
                        this.selectMovie(allShowingMovies, userType);
                        break;
                    case 2:
                        ArrayList<Movie> comingSoon = new ArrayList<Movie>();
                        for (Map.Entry<Integer, Movie> indivMovie : movies.entrySet()) {
                            if (indivMovie.getValue().getShowStatus().equals(MovieStatus.valueOf("COMING_SOON"))) {
                                comingSoon.add(indivMovie.getValue());
                            }
                        }
                        this.selectMovie(comingSoon, userType);
                        break;
                    case 3:
                        ArrayList<Movie> advanceSales = new ArrayList<Movie>();
                        for (Map.Entry<Integer, Movie> indivMovie : movies.entrySet()) {
                            if (indivMovie.getValue().getShowStatus().equals(MovieStatus.valueOf("PREVIEW"))) {
                                advanceSales.add(indivMovie.getValue());
                            }
                        }
                        this.selectMovie(advanceSales, userType);
                        break;
                    case 4:
                        ArrayList<Movie> nowShowing = new ArrayList<Movie>();
                        for (Map.Entry<Integer, Movie> indivMovie : movies.entrySet()) {
                            if (indivMovie.getValue().getShowStatus().equals(MovieStatus.valueOf("NOW_SHOWING"))) {
                                nowShowing.add(indivMovie.getValue());
                            }
                        }
                        this.selectMovie(nowShowing, userType);
                        break;
                    case 5:
                        ArrayList<Movie> endShowing = new ArrayList<Movie>();
                        for (Map.Entry<Integer, Movie> indivMovie : movies.entrySet()) {
                            if (indivMovie.getValue().getShowStatus().equals(MovieStatus.valueOf("END_OF_SHOWING"))) {
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
        else if (userType.equals("Customer")) {
            do {
                MovieByStatusUI.printCustomerMenu();

                choice = InputController.getUserInt(0,5);

                switch (choice) {
                    case 1:
                        ArrayList<Movie> allShowingMovies = new ArrayList<Movie>();
                        for (Map.Entry<Integer, Movie> indivMovie : movies.entrySet()) {
                            if (indivMovie.getValue().getShowStatus().equals(MovieStatus.valueOf("COMING_SOON")) ||
                                    indivMovie.getValue().getShowStatus().equals(MovieStatus.valueOf("PREVIEW")) ||
                                    indivMovie.getValue().getShowStatus().equals(MovieStatus.valueOf("NOW_SHOWING"))) {
                                allShowingMovies.add(indivMovie.getValue());
                            }
                        }
                        if (allShowingMovies.size() == 0) {
                            System.out.println("No Movies that are currently showing!");
                            break;
                        }
                        this.selectMovie(allShowingMovies, userType);
                        break;
                    case 2:
                        ArrayList<Movie> comingSoon = new ArrayList<Movie>();
                        for (Map.Entry<Integer, Movie> indivMovie : movies.entrySet()) {
                            if (indivMovie.getValue().getShowStatus().equals(MovieStatus.valueOf("COMING_SOON"))) {
                                comingSoon.add(indivMovie.getValue());
                            }
                        }
                        if (comingSoon.size() == 0) {
                            System.out.println("No Movies that are classified Coming Soon!");
                            break;
                        }
                        this.selectMovie(comingSoon, userType);
                        break;
                    case 3:
                        ArrayList<Movie> advanceSales = new ArrayList<Movie>();
                        for (Map.Entry<Integer, Movie> indivMovie : movies.entrySet()) {
                            if (indivMovie.getValue().getShowStatus().equals(MovieStatus.valueOf("PREVIEW"))) {
                                advanceSales.add(indivMovie.getValue());
                            }
                        }
                        if (advanceSales.size() == 0) {
                            System.out.println("No Movies that are on preview!");
                            break;
                        }
                        this.selectMovie(advanceSales, userType);
                        break;
                    case 4:
                        ArrayList<Movie> nowShowing = new ArrayList<Movie>();
                        for (Map.Entry<Integer, Movie> indivMovie : movies.entrySet()) {
                            if (indivMovie.getValue().getShowStatus().equals(MovieStatus.valueOf("NOW_SHOWING"))) {
                                nowShowing.add(indivMovie.getValue());
                            }
                        }
                        if (nowShowing.size() == 0) {
                            System.out.println("No Movies that are now showing!");
                            break;
                        }
                        this.selectMovie(nowShowing, userType);
                        break;
                    case 5:
                        this.searchMovies(userType);
                        break;
                    case 0:
                        System.out.println("Back to Movie Main Menu...");
                        break;
                }
            } while (choice != 0);
        }

    }

        private void selectMovie(ArrayList<Movie> list, String userType) {
            int choice, choice2;
            if (list.size() == 0) {
                System.out.println("No movies found! Going back to previous menu..");
                return;
            }
            do {
                for(int i = 0 ; i < list.size() ; i++) {
                    System.out.println((i+1) + ") " + list.get(i).getTitle() + " [" + list.get(i).getShowStatus().toString()+ "]");
                }

                do {
                    System.out.println("Please select a choice for the movie to view information for (Enter 0 to exit).");
                    choice = InputController.getUserInt();
                    choice -= 1;
                    if (choice < -1 || choice >= list.size()) System.out.println("Invalid choice. Please choose a number between 0 and " + list.size());
                } while (choice < -1 || choice >= list.size());

                if (choice == -1) return;

                System.out.println(list.get(choice).toString());

                // PART TO DIRECT INTO MOVIE SPECIFIC STUFF!!! ALWAYS DO WHILE LOOP THE BRANCHESSSS!!!!
                indivMovieOptions(list.get(choice), userType);
                System.out.println("\nEnter 0 to return to Movie Menu \n" +
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
                            ReviewController.getInstance().printReviews(movie.getReviews());
                            break;
                        case 5:
                            ReviewController.getInstance().deleteReview(movie.getReviews());
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
                            ShowtimeController.getInstance().getMovieShowtimes(movie.getId(),userType);
                            break;
                        case 2:
                            ReviewController.getInstance().printReviews(movie.getReviews());
                            break;
                        case 3:
                            ReviewController.getInstance().addReview(movie.getId());
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
        int numGenres = InputController.getUserInt(1,MovieGenre.values().length);
        for (int i=0;i<numGenres;i++) {
            System.out.printf("Enter choice number of genre %d: ", i+1);
            int choice = InputController.getUserInt(1,MovieGenre.values().length)-1;
            boolean restart = false;
            for (int z = 0; z < tempGenreList.size(); z++) {
                if (MovieGenre.values()[choice].equals(tempGenreList.get(z))) {
                    System.out.println("Genre already added! Select another genre.");
                    i--;
                    restart = true;
                    break;
                }
            }
            if (restart) continue;
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
                boolean restart = false;
                String castName = InputController.getUserString();
                for (int z = 0; z < tempCastList.size(); z++) {
                    if (castName.equals(tempCastList.get(z))) {
                        System.out.println("Cast member already added! Enter another cast member.");
                        i--;
                        restart = true;
                        break;
                    }
                }
                if (restart) continue;
                tempCastList.add(castName);
        }
        newMovie.setCast(tempCastList);

        System.out.println("List of Movie Types: ");
        for(int i=0;i<MovieType.values().length;i++)System.out.println(i+1 +". " +MovieType.values()[i].toString());
        System.out.println("Enter number of movie types to be added: ");
        int numTypes = InputController.getUserInt(1,MovieType.values().length);
        for (int i=0;i<numTypes;i++) {
            System.out.printf("Enter choice number of movie type %d: ", i+1);
            int choice = InputController.getUserInt(1,MovieType.values().length)-1;
            boolean restart = false;
            for (int z = 0; z < tempTypeList.size(); z++) {
                if (MovieType.values()[choice].equals(tempTypeList.get(z))) {
                    System.out.println("Type already added! Select another type.");
                    i--;
                    restart = true;
                    break;
                }
            }
            if (restart) continue;
            System.out.println("You picked "+MovieType.values()[choice].toString() + ".");
            tempTypeList.add(MovieType.values()[choice]);
        }
        newMovie.setType(tempTypeList);

        //SETTING UP OF SHOWTIMES???


        int choice;

        do {
            System.out.println(newMovie);
            System.out.println();
            MovieAddUI.printMenu();

            choice = InputController.getUserInt(0,2);

            switch (choice) {
                case 1:
                    this.save(newMovie);
                    this.movies.put(newMovie.getId(), newMovie);

                    System.out.println("Movie created! Back to Movie Main Menu......");
                    choice = 0;
                    break;
                case 2:
                    this.editMovies(newMovie);
                    break;
                case 0:
                    System.out.println("Back to Movie Main Menu......");
                    break;
            }
        } while (choice != 0);
    }

    private void editMovies (Movie movieEditable){
        int choice;
        do {
            System.out.println("Current movie details:\n" + movieEditable.toString());
            MovieAddUI.printMenu();
            choice = InputController.getUserInt(0,11);
            switch(choice) {
                case 1:
                    System.out.println("Enter new title: ");
                    movieEditable.setTitle(InputController.getUserString());
                    break;
                case 2:
                    System.out.println("Choose a new movie rating: ");
                    for(int i=0;i<MovieRating.values().length;i++){
                        System.out.println(i+1 + ". " +MovieRating.values()[i].toString());
                    }
                    int choice2 = InputController.getUserInt(1,MovieRating.values().length)-1;
                    System.out.println("You picked "+MovieRating.values()[choice2].toString() + ".");
                    movieEditable.setRating(MovieRating.values()[choice2]);
                    break;
                case 3:
                    ArrayList<MovieGenre> tempGenreList= new ArrayList<MovieGenre>();
                    System.out.println("Genre List: ");
                    for(int i=0;i<MovieGenre.values().length;i++)System.out.println(i+1 +". " +MovieGenre.values()[i].toString());
                    System.out.println("Enter number of genres: ");
                    int numGenres = InputController.getUserInt();
                    for (int i=0;i<numGenres;i++) {
                        System.out.printf("Enter choice number of genre %d: ", i+1);
                        choice = InputController.getUserInt()-1;
                        System.out.println("You picked "+MovieGenre.values()[choice].toString() + ".");
                        tempGenreList.add(MovieGenre.values()[choice]);
                    }
                    movieEditable.setGenres(tempGenreList);
                    break;
                case 4:
                    System.out.println("Enter new duration (in minutes): ");
                    movieEditable.setDuration(InputController.getUserInt());
                    break;
                case 5:
                    System.out.println("Enter new opening date: ");
                    String newOpeningDate = InputController.getUserString();
                    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate date = LocalDate.parse(newOpeningDate, dateFormat);
                    movieEditable.setMovieOpeningDate(date);
                    break;
                case 6:
                    System.out.println("Enter new end date: ");
                    String newEndDate = InputController.getUserString();
                    dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    date = LocalDate.parse(newEndDate, dateFormat);
                    movieEditable.setMovieEndDate(date);
                    break;
                case 7:
                    System.out.println("Enter revised language: ");
                    movieEditable.setLanguage(InputController.getUserString());
                    break;
                case 8:
                    System.out.println("Enter new synopsis: ");
                    movieEditable.setSynopsis(InputController.getUserString());
                    break;
                case 9:
                    System.out.println("Enter revised director: ");
                    movieEditable.setDirector(InputController.getUserString());
                    break;
                case 10:
                    System.out.println("Enter new number of tickets sold: ");
                    movieEditable.setTicketsSold(InputController.getUserLong());
                    break;
                case 11:
                    System.out.println("Enter new number of cast members: ");
                    int castSize = InputController.getUserInt();
                    ArrayList<String> newCastList = new ArrayList<String>();
                    for (int i = 0; i < castSize; i++) {
                        System.out.println("Enter cast member name: ");
                        newCastList.add(InputController.getUserString());
                    }
                    movieEditable.setCast(newCastList);
                    break;
                case 12:
                    ArrayList<MovieType> tempTypeList= new ArrayList<MovieType>();
                    System.out.println("Type List: ");
                    for(int i=0;i<MovieType.values().length;i++)System.out.println(i+1 +". " +MovieType.values()[i].toString());
                    System.out.println("Enter number of movie types: ");
                    int numTypes = InputController.getUserInt();
                    for (int i=0;i<numTypes;i++) {
                        System.out.printf("Enter choice number of type %d: ", i+1);
                        choice = InputController.getUserInt()-1;
                        System.out.println("You picked "+MovieType.values()[choice].toString() + ".");
                        tempTypeList.add(MovieType.values()[choice]);
                    }
                    movieEditable.setType(tempTypeList);
                    break;
                case 0:
                    System.out.println("Edits Completed.");
                    break;
            }
        } while (choice != 0);
        save(movieEditable);
    }

    // Only removes movie from viewing by customer.
    private void removeMovie(Movie movie) {
        LocalDate today = LocalDate.now();
        movie.setMovieEndDate(today.minusDays(1));
        save(movie);
    }

    public void viewTop5(String userType) {
        int choice, choice2;

        do {
            if (userType.equals("Customer")) {
                ViewTop5UI.printCustomerMenu();
            } else if (userType.equals("Staff")) {
                ViewTop5UI.printStaffMenu();
            }

            choice = InputController.getUserInt(0,2);

            ArrayList<Movie> top5Movies = new ArrayList<Movie>();

            switch (choice) {
                case 1:
                    for(Map.Entry<Integer, Movie> entry : movies.entrySet()){
                        if(entry.getValue().getShowStatus().equals(MovieStatus.valueOf("PREVIEW"))|| entry.getValue().getShowStatus().equals(MovieStatus.valueOf("NOW_SHOWING"))){
                            top5Movies.add(entry.getValue());
                        }
                    }

                    top5Movies.sort(Comparator.comparingLong(Movie::getTicketsSold).reversed());

                    if(top5Movies.size()==0){
                        System.out.println("No Movies Found.");
                        break;

                    } else {
                        for (int i=0;i<Math.min(5, top5Movies.size());i++) {
                            System.out.println(i+1 +". "+ top5Movies.get(i).getTitle() +" (Number of Tickets Sold:  "+ top5Movies.get(i).getTicketsSold()+")");
                        }
                    }
                    break;
                case 2:
                    for(Map.Entry<Integer, Movie> entry : movies.entrySet()){
                        if(entry.getValue().getShowStatus().equals(MovieStatus.valueOf("PREVIEW"))|| entry.getValue().getShowStatus().equals(MovieStatus.valueOf("NOW_SHOWING"))){
                            top5Movies.add(entry.getValue());
                        }
                    }

                    // Gets rids of those movies with <= 1 review
                    for(int i=top5Movies.size()-1;i>=0;i--){
                        if(top5Movies.get(i).getReviews().size() <= 1){
                            top5Movies.remove(i);
                        }
                    }
                    top5Movies.sort(Comparator.comparingDouble(Movie::getOverallStarsDouble).reversed());

                    if(top5Movies.size()==0){
                        System.out.println("No Movies Found.");
                        break;
                    } else {
                        for (int i=0;i<Math.min(5, top5Movies.size());i++) {
                            System.out.println(i+1 +". "+top5Movies.get(i).getTitle() +" (Review Score:  "+ top5Movies.get(i).getOverallStars()+")");
                        }
                    }
                    break;
                case 0:
                    System.out.println("Back to Previous Menu ...");
                    break;
            }

            if (userType.equals("Customer")) {
                do {
                    System.out.println("Choose a movie (Press 0 to exit): ");
                    int numberOfChoices = top5Movies.size();
                    choice2 = InputController.getUserInt(0,numberOfChoices)-1;

                    if (choice2 == -1) break;
                    System.out.println(top5Movies.get(choice2).toString());
                    indivMovieOptions(top5Movies.get(choice2),userType);
                    break;
                } while (choice2 != -1);
                System.out.println("Back to Top 5 Movie Listing...");
            }
        } while (choice != 0);
    }

    private boolean load() {
        File[] listOfFiles = new File(FilePathFinder.findRootPath() + "/src/data/movies").listFiles();

        if(listOfFiles != null){
            System.out.println("Loading Movies...");
            for(int i=0;i<listOfFiles.length;i++){
                String path = listOfFiles[i].getPath(); // Returns full path incl file name and type
                Movie newMovie = (Movie) DataSerializer.ObjectDeserializer(path);
                this.movies.put(newMovie.getId(), newMovie);
            }
            return true;
        }
        System.out.println("Movie database is empty!");
        return false;
    }

    private void save(Movie movie) {
        String path = FilePathFinder.findRootPath() + "/src/data/movies/movie_"+movie.getId()+".dat";
        DataSerializer.ObjectSerializer(path,movie);
        System.out.println("Movies updated!");
    }

    public Movie getMoviebyID(int movieID){
        return movies.get(movieID);
    }

    public ArrayList<MovieType> getMovieTypesbyID(int movieID){
        return movies.get(movieID).getType();
    }

    public void increaseTicketsSold(int movieID, long ticketsSold){
        movies.get(movieID).setTicketsSold(movies.get(movieID).getTicketsSold() + ticketsSold);
        save(getMoviebyID(movieID));
    }

    void addReview(int movieID, MovieReview review) {
        Movie movie = movies.get(movieID);
        movie.addMovieReview(review);
        save(movie);
    }


    void removeReview(int movieID, int reviewID) {
        Movie movie = movies.get(movieID);
        movie.removeMovieReview(reviewID);
        save(movie);
    }

    void updateShowtimes(int movieID, int showtimeID) {
        Movie movie = movies.get(movieID);
        movie.addShowtimeID(showtimeID);
        save(movie);
    }

}
