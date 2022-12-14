package boundaries;

import entities.cinema.Cinema;
import entities.cinema.Cineplex;
import entities.cinema.Showtime;
import entities.movie.MovieType;

import java.lang.reflect.Array;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * ShowtimeUI is a class that stores all the functionalities that manages the showtimes side of the application.
 */
public class ShowtimeUI {
    /**
     * Prints all the showtimes available.
     * @param showtimeList List of Showtime Objects
     */
    public static void printShowtimes(ArrayList<Showtime> showtimeList) {
        System.out.println("Here are the available showtimes for the movie you're looking for:");

        if(showtimeList.size() == 0) {
            System.out.println("Unfortunately, no showtimes are available for this movie.");
        } else {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy, hh.mma");
            for(int i = 0; i < showtimeList.size(); i++) {
                System.out.printf((i+1) + ") Cineplex Name: %s, CinemaID | Hall No: %s\n", showtimeList.get(i).getCinema().getCineplexName(), showtimeList.get(i).getCinema().getCinemaID().split("_")[1]);
                System.out.printf("\t Movie Type: %s\n" , showtimeList.get(i).getMovieType());
                System.out.println("\t Date & Time: " + showtimeList.get(i).getDateTime().format(dateTimeFormatter));
                System.out.println("\t Seating Availability: " + showtimeList.get(i).getStatus());
                System.out.println();
            }
        }
    }

    /**
     * Prints the showtime details of all showtimes.
     * @param showtimeList List of Showtime Objects
     */
    public static void printShowtimesChoice(ArrayList<Showtime> showtimeList) {
        for(int i = 0; i < showtimeList.size(); i++) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy, hh.mma");
            System.out.printf((i+1) + ".");
            System.out.printf("\tCineplex Name: %s, CinemaID / Hall No: %s\n", showtimeList.get(i).getCinema().getCineplexName(), showtimeList.get(i).getCinema().getCinemaID().split("_")[1]);
            System.out.printf("\t Movie Type: %s\n" , showtimeList.get(i).getMovieType());
            System.out.println("\t Date & Time: " + showtimeList.get(i).getDateTime().format(dateTimeFormatter));
            System.out.println();
        }
    }

    /**
     * Prints Customer Showtime functionalities UI.
     */
    public static void custShowtimeMenu() {
        System.out.println("What would you like to do now?");
        System.out.printf(
                "1. View Specific Showtime\n" +
                "0. Back to Individual Movie Options\n"
                );
        System.out.println("Please select one of the above choices between 0 to 1: ");
    }
    /**
     * Prints Customer Selected Showtime functionalities UI.
     */
    public static void custChosenShowtimeMenu() {
        System.out.printf(
                "What would you like to do with this selected showtime?\n" +
                "1. View all details of this showtime\n"+
                "2. Book this showtime\n" +
                "0. Back to list of showtimes\n"
        );
        System.out.println("Please select one of the above choices between 0 to 2: ");
    }
    /**
     * Prints Staff Showtime functionalities UI.
     */
    public static void staffShowtimeMenu() {
        System.out.println("Showtime Portal - Staff");
        System.out.printf(
                "1. View / Edit / Delete a Showtime\n" +
                "2. Create New Showtime\n" +
                "0. Back to List of Movie Options\n"
                );
        System.out.println("Please select one of the above choices between 0 to 2: ");
    }

    /**
     * Prints Staff Showtime functionalities UI if there is no showtime.
     */
    public static void staffShowtimeMenuNoShowtime() {
        System.out.println("Showtime Manager - Staff");
        System.out.printf(
                        "1. Create New Showtime\n" +
                        "0. Back to List of Showtimes\n"
        );
        System.out.println("Please select one of the above choices between 0 to 1: ");
    }
    /**
     * Prints Staff Showtime functionalities UI for each chosen showtime for editing/removing/viewing.
     */
    public static void staffShowtimeOperationsMenu() {
        System.out.printf(
                "[STAFF] What would you like to do with the selected showtime?\n" +
                "1. View all details of showtime\n" +
                "2. Update showtime\n" +
                "3. Delete showtime\n" +
                "0. Back to list of showtimes\n"
        );
        System.out.println("Please select one of the above choices between 0 to 3: ");
    }
    /**
     * Prints Staff Editing Showtimes functionalities.
     */
    public static void staffUpdateMenu() {
        System.out.printf(
                "Welcome to the Update Menu for Showtime!\n" +
                "Here are the following attributes you can update for a showtime\n" +
                "1. Showtime Date and Time\n" +
                "2. Movie ID\n" +
                "3. Cinema\n" +
                "4. Cinema Availability\n" +
                "5. Movie Type\n" +
                "0. Back to selected showtime menu\n"
        );
    }

    /**
     * Prints showtime details from the given showtime.
     * @param chosenShowtime Showtime object
     */
    public static void printShowtimeDetails(Showtime chosenShowtime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy, hh.mma");
        System.out.println("\nHere are the details of your chosen showtime:");
        System.out.println("Cineplex Name: " + chosenShowtime.getCinema().getCineplexName());
        System.out.println("CinemaID / Hall No: " + chosenShowtime.getCinema().getCinemaID());
        System.out.println("Cinema Type: " + chosenShowtime.getCinema().getCinemaType());
        System.out.println("Movie Type: " + chosenShowtime.getMovieType());
        System.out.println("Date/Time: "+ chosenShowtime.getDateTime().format(dateTimeFormatter));
        System.out.println("Seat Availability: " + chosenShowtime.getStatus());
        System.out.println("Total No. Of Seats: " + chosenShowtime.getCinema().getTotalNOfSeats());
        System.out.println("Occupied No. Of Seats: \n" + chosenShowtime.getOccupiedNoOfSeats());
        System.out.println("Cinema Seat Layout: ");
        chosenShowtime.getCinema().printCinemaSeatLayout();
    }

    /**
     * Prints list of cineplexes available in the company.
     * @param listOfCineplexes List of Cineplexes objects
     */
    public static void printListOfCineplexes(ArrayList<Cineplex> listOfCineplexes) {
        System.out.println("List of Cineplexes:");
        if (listOfCineplexes.size() == 0) {
            System.out.println("No Cineplexes in database!");
            return;
        }
        for(int i = 0; i < listOfCineplexes.size(); i++) {
            System.out.println(i + 1 + ". " + listOfCineplexes.get(i).getCineplexName());
        }
    }

    /**
     * Prints List of Cinemas available by code.
     * @param listOfCinemas List of Cinema Objects
     */
    public static void printListOfCinemas(ArrayList<Cinema> listOfCinemas) {
        System.out.println("List of Cinemas:");
        for(int i = 0; i < listOfCinemas.size(); i++) {
            System.out.println(i + 1 + ". " + listOfCinemas.get(i).getCineplexCode() + "_" + (i+1));
        }
    }

    /**
     * Prints list of movie types available to choose from.
     * @param listOfMovieTypes List of MovieType Object
     */
    public static void printListOfMovieTypes(ArrayList<MovieType> listOfMovieTypes) {
        for(int i = 0; i < listOfMovieTypes.size(); i++) {
            System.out.println(i + 1 + ". " + listOfMovieTypes.get(i).toString());
        }
    }


}
