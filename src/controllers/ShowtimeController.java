package controllers;

import boundaries.ShowtimeUI;
import entities.cinema.Cinema;
import entities.cinema.CinemaAvailability;
import entities.cinema.Cineplex;
import entities.cinema.Showtime;
import entities.movie.Movie;
import entities.movie.MovieStatus;
import entities.movie.MovieType;
import utils.DataSerializer;
import utils.FilePathFinder;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * Class that manages the functionalities required in that of showtimes.
 */
public class ShowtimeController {
    /**
     * HashMap that stores the showtime IDs and its respective showtime objects.
     */
    private HashMap<Integer, Showtime> showtimes = new HashMap<>();
    /**
     * Singleton Constructor
     */
    private static ShowtimeController single_instance = null;
    /**
     * Singleton Constructor
     */
    public static ShowtimeController getInstance() {
        if(single_instance == null) {
            single_instance = new ShowtimeController();
        }
        return single_instance;
    }

    /**
     * Constructor for showtime Controller objects, showtimes are loaded from the database.
     */
    private ShowtimeController() {
        // load data from .dat files
        HashMap<Integer, Showtime> showTimeHashMap = this.loadData();

        if(showTimeHashMap != null) {
            this.showtimes = showTimeHashMap;
        }
    }

    /**
     * Prints the list of showtimes for a specific provided movie ID, called from the MovieController.
     * @param movieID ID of movie
     * @param userType "Customer" or "Staff"
     */
    void getMovieShowtimes(int movieID, String userType) {
        int userChoice;
        // Get showtime Ids for a particular movie
        ArrayList<Integer> showtimeIdsForMovie = MovieController.getInstance().getMoviebyID(movieID).getShowtimeIDs();
        ArrayList<Showtime> showtimeList = new ArrayList<Showtime>();

        for(int i = 0; i < showtimeIdsForMovie.size(); i++) {
            //get showtime object for the particular showtime ID
            Showtime showtime = this.showtimes.get(showtimeIdsForMovie.get(i));
            // add this showtime object into the showTimeList
            showtimeList.add(showtime);
        }

        do {
            // Print available showtimes for the movie
            ShowtimeUI.printShowtimes(showtimeList); // it will also handle the case whr there are 0 showtimes

            if(userType.equalsIgnoreCase("Staff") && showtimeList.size() != 0) {
                ShowtimeUI.staffShowtimeMenu();
                int minChoice = 0;
                int maxChoice = 2;
                userChoice = InputController.getUserInt(minChoice, maxChoice);

                switch (userChoice) {
                    case 0:
                        System.out.println("Back to list of Showtimes");
                        break;
                    case 1:
                        System.out.println("Enter the showtime number you would like to view/update/delete: ");
                        int showtimeChoice = InputController.getUserInt(1, showtimeIdsForMovie.size()) - 1;
                        int chosenShowtimeID = showtimeIdsForMovie.get(showtimeChoice);
                        this.staffShowtimeOperations(chosenShowtimeID); // goes to staffShowtimeOperations based on the specific showtimeID selected
                        break;
                    case 2: // creating a new showtime
                        Showtime newShowtime = this.createShowtime(movieID);
                        showtimeList.add(newShowtime);
                        break;
                    default:
                        System.out.println("Invalid Input! Please enter a valid integer between 0 to 2.");
                        break;
                }

            } // in the event that its a staff, and there are no showtimes for the movie
            else if (userType.equalsIgnoreCase("Staff") && showtimeList.size() == 0) {
                ShowtimeUI.staffShowtimeMenuNoShowtime();
                int minChoice = 0;
                int maxChoice = 1;
                userChoice = InputController.getUserInt(minChoice, maxChoice);
                switch (userChoice) {
                    case 0:
                        System.out.println("Back to list of Showtimes");
                        break;
                    case 1: // creating a new showtime
                        Showtime newShowtime = this.createShowtime(movieID);
                        showtimeList.add(newShowtime);
                        break;
                    default:
                        System.out.println("Invalid Input! Please enter a valid integer between 0 to 1.");
                        break;
                }
            } else if (userType.equalsIgnoreCase("Customer") && showtimeList.size() != 0) {
                Movie movie = MovieController.getInstance().getMoviebyID(movieID);

                if(movie.getShowStatus().equals(MovieStatus.COMING_SOON)) { // not sure how necessary this is..
                    System.out.println("Sorry, this movie is coming soon, hence you can't book this showtime yet. Bringing you back to the list of showtimes..");
                    userChoice = 0;
                } else {
                    ShowtimeUI.custShowtimeMenu();
                    int minChoice = 0;
                    int maxChoice = 1;

                    userChoice = InputController.getUserInt(minChoice, maxChoice);
                }
                switch (userChoice) {
                    case 0:
                        System.out.println("Bringing you back to MovieController.."); // probs change naming??
                        break;
                    case 1:
                        System.out.printf("Please select one of the choices (%d to %d) to view, else enter 0 to return back.\n", 1, showtimeList.size());
                        ShowtimeUI.printShowtimesChoice(showtimeList);
                        System.out.printf("Enter choice: ");
                        int showtimeChoice = InputController.getUserInt(0, showtimeList.size());
                        if(showtimeChoice != 0) {
                            int showtimeID = showtimeIdsForMovie.get(showtimeChoice - 1);
                            this.chosenShowtimeCust(showtimeID);
                        }
                        break;
                    default:
                        System.out.println("Invalid Input! Please enter a valid integer between 0 to 1.");
                        break;
                }

            } else {
                userChoice = 0;
            }
        } while(userChoice != 0);

    }

    /**
     * Individual Showtime options available such as viewing details and booking the showtime.
     * @param chosenShowtimeID ID of showtime chosen.
     */
    private void chosenShowtimeCust(int chosenShowtimeID) {
        int userChoice;

        int movieID = this.showtimes.get(chosenShowtimeID).getMovieId();
        Movie movie = MovieController.getInstance().getMoviebyID(movieID);

        if(movie.getShowStatus().equals(MovieStatus.COMING_SOON)){}
        else {
            do {
                ShowtimeUI.custChosenShowtimeMenu();
                int minChoice = 0;
                int maxChoice = 2;
                userChoice = InputController.getUserInt(minChoice, maxChoice);

                switch (userChoice) {
                    case 0:
                        System.out.println("Bringing you back to the list of showtimes");
                        break;
                    case 1:
                        this.viewShowtimeDetails(chosenShowtimeID);
                        break;
                    case 2:
                        Showtime chosenShowtime = this.retrieveShowtime(chosenShowtimeID);
                        BookingController.getInstance().initSeatSelection(chosenShowtime);
                        break;
                    default:
                        System.out.println("Invalid Input! Please enter a valid integer between 0 to 2.");
                        break;
                }
            } while (userChoice != 0);
        }
    }

    /**
     * Functionalities given to staff to manage the showtimes such as creating/editing/deleting of showtimes
     * @param showtimeID ID of showtime to be managed by staff.
     */
    private void staffShowtimeOperations(int showtimeID) {
        int userChoice;
        do {
            ShowtimeUI.staffShowtimeOperationsMenu();
            int minChoice = 0;
            int maxChoice = 3;
            userChoice = InputController.getUserInt(minChoice, maxChoice);

            switch (userChoice) {
                case 0:
                    System.out.println("Bringing you back to list of showtimes");
                    break;
                case 1:
                    this.viewShowtimeDetails(showtimeID);
                    break;
                case 2:
                    this.updateShowtimeDetails(showtimeID);
//                     userChoice = 0; - use this if want to quit this operations menu after each update
//                    System.out.println("Bringing you back to list of showtimes");
                    break;
                case 3:
                    this.deleteShowtime(showtimeID);
//                    userChoice = 0;
//                    System.out.println("Bringing you back to list of showtimes");
                    break;
                default:
                    System.out.println();
                    System.out.println("Invalid Input! Please enter a valid integer between 0 to 3.");
                    break;
            }
        } while(userChoice != 0);

    }

    /**
     * Returns a showtime based on its ID.
     * @param showtimeID ID of showtime
     * @return Showtime object
     */
    private Showtime retrieveShowtime(int showtimeID) {
        return this.showtimes.get(showtimeID);
    }

    /**
     * Shows the details of each showtime.
     * @param chosenShowtimeID
     */
    private void viewShowtimeDetails(int chosenShowtimeID) {
        DateTimeFormatter dateTimeFormatter= DateTimeFormatter.ofPattern("dd MMM yyyy, hh.mma");
        // retrieve the showtime obj, corresponding to the chosenShowtimeID
        Showtime chosenShowtime = this.showtimes.get(chosenShowtimeID);
        ShowtimeUI.printShowtimeDetails(chosenShowtime);
    }

    /**
     * Removes the showtime from viewing by marking it as "FULL_CAPACITY".
     * @param showtimeID ID of showtime
     */
    private void deleteShowtime(int showtimeID) {
        Showtime showtimeToDelete = this.showtimes.get(showtimeID);
        if(showtimeToDelete == null) {
            System.out.println("This showtime does not exist.");
        } else {
            showtimeToDelete.setStatus(CinemaAvailability.FULL_CAPACITY);
            System.out.println("Showtime has been marked as full capacity and will no longer be viewed by customers.");
            this.saveShowtime(showtimeToDelete, showtimeID);
        }
    }

    /**
     * Creation of a new showtime for a given movie. Allows showtime details to be entered by the staff.
     * @param movieID ID of movie for showtime to be added
     * @return Showtime object created
     */
    private Showtime createShowtime(int movieID) {
        Showtime newShowTime = new Showtime();
        int showtimeID = newShowTime.getIdCounter();
        String cinemaID;
        CinemaAvailability cinemaAvailability;
        MovieType movieType;

        System.out.println("Enter a date and time for showtime, in the format dd/MM/yyyy HH:mm.");
        LocalDateTime localDateTime = InputController.getDateTimeFromUser();
        newShowTime.setDateTime(localDateTime);

        ArrayList<Cineplex> ListOfCineplex = CompanyController.getInstance().getCompany().getCineplexList();

        // print list of cineplexes available
        ShowtimeUI.printListOfCineplexes(ListOfCineplex);
        int minChoice = 1;
        int maxChoice = ListOfCineplex.size();
        System.out.println("Choose a cineplex from the given list.");
        int userChoice = InputController.getUserInt(minChoice, maxChoice) - 1;

        // Selection of Cinema
        ArrayList<Cinema> ListOfCinemas = ListOfCineplex.get(userChoice).getCinemaList();
        ShowtimeUI.printListOfCinemas(ListOfCinemas);

        System.out.println("Choose a cinema from the given list.");
        int minChoiceCinema = 1;
        int maxChoiceCinema = ListOfCinemas.size();
        int userChoiceCinema = InputController.getUserInt(minChoiceCinema, maxChoiceCinema) - 1;

        cinemaID = ListOfCinemas.get(userChoiceCinema).getCinemaID();


        Cinema newCinema = CompanyController.getInstance().generateNewCinema(cinemaID);
        newShowTime.setCinema(newCinema);

        newShowTime.updateAvailability();

        // pick movie format
        System.out.println("\nAvailable Movie Types: ");
        ArrayList<MovieType> ListOfMovieTypes = MovieController.getInstance().getMovieTypesbyID(movieID);

        ShowtimeUI.printListOfMovieTypes(ListOfMovieTypes);

        System.out.println("Please pick a movie type by specifying its corresponding number");
        int minChoiceMovieType = 1;
        int maxChoiceMovieType = ListOfMovieTypes.size();

        int userChoiceMovieType = InputController.getUserInt(minChoiceMovieType, maxChoiceMovieType) - 1;
        movieType = ListOfMovieTypes.get(userChoiceMovieType);
        newShowTime.setMovieType(movieType);

        newShowTime.setMovieId(movieID);
        newShowTime.setCinemaSeatLayout(newShowTime.getCinema().getCinemaSeatLayout());

        MovieController.getInstance().updateShowtimes(movieID, showtimeID);
        this.showtimes.put(showtimeID, newShowTime);
        this.saveShowtime(newShowTime, showtimeID);
        System.out.println("A new showtime has been successfully created.");
        System.out.println("Redirecting you back to Showtime Portal - Staff..");
        return newShowTime;
    }

    /**
     * Allows staff to edited the showtime information from its ID.
     * @param showtimeID ID of showtime
     */
    private void updateShowtimeDetails(int showtimeID) {
        int userChoice;

        Showtime selectedShowtime = this.showtimes.get(showtimeID);
        if(selectedShowtime == null ) {
            System.out.println("Showtime doesn't exist!");
        } else {
            do {
                ShowtimeUI.staffUpdateMenu();
                System.out.println("Please select one of the choices above between 0 to 5:");
                int minChoice = 0;
                int maxChoice = 5;
                userChoice = InputController.getUserInt(minChoice, maxChoice);

                switch(userChoice) {
                    case 1:
                        System.out.println("Please enter a new date and time for Showtime in the format (dd/MM/yyyy HH:mm)");
                        LocalDateTime newDateTime = InputController.getDateTimeFromUser();
                        selectedShowtime.setDateTime(newDateTime);
                        break;
                    case 2:
                        System.out.println("Please enter a new Movie ID: ");
                        int newMovieID = InputController.getUserInt();
                        selectedShowtime.setMovieId(newMovieID);
                        break;
                    case 3:
                        // String
                        System.out.println("Please enter a new Cinema ID (XXX_X): ");
                        String newCinemaID = InputController.getUserString();
                        Cinema newCinema = CompanyController.getInstance().generateNewCinema(newCinemaID);
                        selectedShowtime.setCinema(newCinema);
                        break;
                    case 4:
                        System.out.println("Please enter new cinema availability status: ");
                        String newCinemaAvailability = InputController.getUserString();
                        while(!checkIfValidCinemaAvailability(newCinemaAvailability)){
                            newCinemaAvailability = InputController.getUserString();
                        }
                        selectedShowtime.setStatus(CinemaAvailability.valueOf(newCinemaAvailability));

                        break;
                    case 5:
                        System.out.println("Please enter a new movie type: ");
                        String newMovieType = InputController.getUserString();
                        while(!checkIfValidMovieType(newMovieType)) {
                            newMovieType = InputController.getUserString();
                        }
                        selectedShowtime.setMovieType(MovieType.valueOf(newMovieType));
                        break;
                    default:
                        System.out.println("Invalid Input! Please enter a valid integer between 0 to 5.");
                        break;
                }
            } while(userChoice != 0);
            this.showtimes.put(showtimeID, selectedShowtime);
            this.saveShowtime(selectedShowtime, showtimeID);
        }
    }

    /**
     * Checks if Cinema Availability entered is valid.
     * @param cinemaAvailability String entered for cinema availability to be stored.
     * @return True if its valid / False if its invalid
     */
    private boolean checkIfValidCinemaAvailability(String cinemaAvailability) {
        try {
            CinemaAvailability cinemaAvailabilityEnum = CinemaAvailability.valueOf(cinemaAvailability);
            return true;
        } catch(IllegalArgumentException e) {
            System.out.println("Invalid cinema status! Please try again");
            return false;
        }
    }
    /**
     * Checks if Movie Type entered is valid.
     * @param movieType String entered for movie type to be stored.
     * @return True if its valid / False if its invalid
     */
    private boolean checkIfValidMovieType(String movieType) {
        try {
            MovieType movieTypeEnum = MovieType.valueOf(movieType);
            return true;
        } catch(IllegalArgumentException e) {
            System.out.println("Invalid movie type! Please try again");
            return false;
        }
    }

    /**
     * Saves showtime in the database.
     * @param showtimeObj Showtime object
     * @param showtimeID ID of showtime
     */
    void saveShowtime(Showtime showtimeObj, int showtimeID) {
        String filePath = FilePathFinder.findRootPath() + "/src/data/showtimes/showtime_" + showtimeID + ".dat";
        DataSerializer.ObjectSerializer(filePath, showtimeObj);
    }

    /**
     * Loads the showtimes from the database.
     * @return HashMap of showtime IDs to retrieve the showtime objects.
     */
    private HashMap<Integer, Showtime> loadData() {
        HashMap<Integer, Showtime> storedShowtimes = new HashMap<>();

        String filePath = FilePathFinder.findRootPath() + "/src/data/showtimes";
        File folder = new File(filePath);

        File[] fileList = folder.listFiles();
        // Deserialize each file in the list , convert them to Showtime Objects, and add to the Hashmap
        if(fileList != null) {
            for(int i = 0; i < fileList.length; i++) {
                if (fileList[i].getName().equalsIgnoreCase(".gitkeep")) continue;
                String path = fileList[i].getPath();

                Showtime newShowtimeObj = (Showtime) DataSerializer.ObjectDeserializer(path);
                String regex = "\\.(?=[^\\.]+$)";
                String regex2 = "_";
                String showtimeID = fileList[i].getName().split(regex)[0].split(regex2)[1];
                storedShowtimes.put(Integer.parseInt(showtimeID), newShowtimeObj);
            }
        }
        return storedShowtimes;
    }
}
