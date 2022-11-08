package controllers;

import boundaries.ShowtimeUI;
import entities.cinema.CinemaAvailability;
import entities.cinema.Showtime;
import entities.movie.Movie;
import entities.movie.MovieStatus;
import utils.DataSerializer;
import utils.FilePathFinder;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShowtimeController {
// need finish this class
// do TicketController
// then transactionController
// then makeBooking under BookingController

    private HashMap <String, Showtime> showtimes = new HashMap<>();

    private static ShowtimeController single_instance = null;

    public static ShowtimeController getInstance() {
        if(single_instance == null) {
            single_instance = new ShowtimeController();
        }
        return single_instance;
    }

    private ShowtimeController() {
        HashMap<String, Showtime> showTimeHashMap = this.loadData();

        if(showTimeHashMap != null) {
            this.showtimes = showTimeHashMap;
        }
    }

    void getMovieShowtimes(String movieID, String userType) {
        int userChoice;
        // Get showtime Ids for a particular movie
        ArrayList<String> showtimeIdsForMovie = MovieController.getInstance().getMoviebyID(movieID).getShowTimeIds();
        ArrayList<Showtime> showtimeList = new ArrayList<Showtime>();

        for(int i = 0; i < showtimeIdsForMovie.size(); i++) {
            //get showtime object for the particular showtime ID
            Showtime showtime = this.showtimes.get(showtimeIdsForMovie.get(i));
            // add this showtime object into the showTimeList
            showtimeList.add(showtime);
        }

        do {
            // Print available showtimes for the movie
            ShowtimeUI.printShowtimes(showtimeList);

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
                        System.out.println("Enter the no. of which showtime you would like to view/update/delete: ");
                        int showtimeChoice = InputController.getUserInt(1, showtimeIdsForMovie.size()) - 1;
                        String chosenShowtimeID = showtimeIdsForMovie.get(showtimeChoice);
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

                if(movie.getShowStatus().equals(MovieStatus.COMING_SOON)) {
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
                        System.out.println("Bringing you back to MovieController.."); // probs change naming
                        break;
                    case 1:
                        String showtimeID = showtimeIdsForMovie.get(userChoice);
                        this.chosenShowtimeCust(showtimeID);
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

    private void chosenShowtimeCust(String chosenShowtimeID) {
        int userChoice;

        String movieID = this.showtimes.get(chosenShowtimeID).getMovieId();
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


    private void staffShowtimeOperations(String showtimeID){
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
                    this.updateShowtimeDetails();
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

    private Showtime retrieveShowtime(String showtimeID) {
        return this.showtimes.get(showtimeID);
    }

    private void viewShowtimeDetails(String chosenShowtimeID) {
        DateTimeFormatter dateTimeFormatter= DateTimeFormatter.ofPattern("dd MMM yyyy, hh.mma");
        // retrieve the showtime obj, corresponding to the chosenShowtimeID
        Showtime chosenShowtime = this.showtimes.get(chosenShowtimeID);
        ShowtimeUI.printShowtimeDetails(chosenShowtime);
    }

    private void deleteShowtime(String showtimeID) throws IOException {
        Showtime showtimeToDelete = this.showtimes.get(showtimeID);
        if(showtimeToDelete == null) {
            System.out.println("This showtime does not exist.");
        } else {
            showtimeToDelete.setStatus(CinemaAvailability.FULL_CAPACITY);
            System.out.println("Showtime has been marked as full capacity and will no longer be viewed by customers.");
            this.saveShowtime(showtimeToDelete, showtimeID);
        }
    }

 

    void saveShowtime(Showtime showtimeObj, String showtimeID) throws IOException {
        String filePath = FilePathFinder.findRootPath() + "/src/data/showtimes/showtime_" + showtimeID + ".dat";
        DataSerializer.ObjectSerializer(filePath, showtimeObj);
    }

    private HashMap<String, Showtime> loadData() throws IOException {
        HashMap<String, Showtime> storedShowtimes = new HashMap<>();

        String filePath = FilePathFinder.findRootPath() + "/src/data/showtimes";
        File folder = new File(filePath);

        File[] fileList = folder.listFiles();
        // Deserialize each file in the list , convert them to Showtime Objects, and add to the Hashmap
        if(fileList != null) {
            for(int i = 0; i < fileList.length; i++) {
                String path = fileList[i].getPath();

                Showtime newShowtimeObj = (Showtime) DataSerializer.ObjectDeserializer(path);
                String regex = "\\.(?=[^\\.]+$)";
                String regex2 = "_";
                String showtimeID = fileList[i].getName().split(regex)[0].split(regex2)[1];
                storedShowtimes.put(showtimeID, newShowtimeObj);
            }
        }
        return storedShowtimes;
    }
}
