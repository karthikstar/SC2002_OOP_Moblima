package boundaries;

import entities.cinema.Cinema;
import entities.cinema.Cineplex;
import entities.cinema.Showtime;
import entities.movie.MovieType;

import java.lang.reflect.Array;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ShowtimeUI {
    public static void printShowtimes(ArrayList<Showtime> showtimeList) {
        System.out.println("Here are the available showtimes for the movie you're looking for:");

        if(showtimeList.size() <= 0) {
            System.out.println("Unfortunately, no showtimes are available for this movie.");
        } else {
            for(int i = 0; i < showtimeList.size(); i++) {
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy, hh.mma");
                System.out.printf("Cineplex Name: %s, CinemaID / Hall No: %s\n", showtimeList.get(i).getCinema().getCineplexName(), showtimeList.get(i).getCinema().getCinemaID().split("_")[1]);
                System.out.printf("\t Movie Type: %s\n" , showtimeList.get(i).getMovieType());
                System.out.println("\t Date & Time: " + showtimeList.get(i).getDateTime().format(dateTimeFormatter));
                System.out.println();
            }
        }
    }

    public static void custShowtimeMenu() {
        System.out.println("Showtime Portal - Customers");
        System.out.printf(
                "1. View Specific Showtime\n" +
                "0. Back to List of Showtimes\n"
                );
        System.out.println("Please select one of the above choices: ");
    }

    public static void custChosenShowtimeMenu() {
        System.out.printf(
                "1. View all details of showtime\n"+
                "2. Book this showtime\n" +
                "0. Back to list of showtimes\n"
        );
        System.out.println("Please select one of the above choices: ");
    }
    public static void staffShowtimeMenu() {
        System.out.println("Showtime Portal - Staff");
        System.out.printf(
                "1. View / Edit / Delete a Showtime\n" +
                "2. Create New Showtime\n" +
                "0. Back to List of Showtimes\n"
                );
        System.out.println("Please select one of the above choices: ");
    }

    // in the event that there is no showtime for the particular movie
    public static void staffShowtimeMenuNoShowtime() {
        System.out.println("Showtime Manager - Staff");
        System.out.printf(
                        "1. Create New Showtime\n" +
                        "0. Back to List of Showtimes\n"
        );
        System.out.println("Please select one of the above choices: ");
    }

    public static void staffShowtimeOperationsMenu() {
        System.out.printf(
                "[STAFF] What would you like to do with the selected showtime?\n" +
                "1. View all details of showtime\n" +
                "2. Update showtime\n" +
                "3. Delete showtime\n" +
                "0. Back to list of showtimes\n"
        );
        System.out.println("Please select one of the above choices: ");
    }

    public static void staffUpdateMenu() {
        System.out.printf(
                "Welcome to the Update Menu for Showtime!\n" +
                "Here are the following attributes you can update for a showtime\n" +
                "1. Showtime Date and Time\n" +
                "2. Movie ID\n" +
                "3. Cinema\n" +
                "4. Cineplex Name\n" +
                "5. Cinema Availability\n" +
                "6. Movie Type\n" +
                "0. Back to selected showtime menu\n"
        );
    }

    public static void printShowtimeDetails(Showtime chosenShowtime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy, hh.mma");
        System.out.println("Here are the details of your chosen showtime:");
        System.out.println("Cineplex Name: " + chosenShowtime.getCinema().getCineplexName());
        System.out.println("CinemaID / Hall No: " + chosenShowtime.getCinema().getCinemaID());
        System.out.println("Cinema Type: " + chosenShowtime.getCinema().getCinemaType());
        System.out.println("Movie Type: " + chosenShowtime.getMovieType());
        System.out.println("Date/Time: "+ chosenShowtime.getDateTime().format(dateTimeFormatter));
        System.out.println("Seat Availability: " + chosenShowtime.getStatus());
        System.out.println("Total No. Of Seats " + chosenShowtime.getCinema().getTotalNOfSeats());
        System.out.println("Occupied No. Of Seats " + chosenShowtime.getOccupiedNoOfSeats());
        System.out.println("Cinema Seat Layout: ");
        chosenShowtime.getCinema().printCinemaSeatLayout();
    }

    public static void printListOfCineplexes(ArrayList<Cineplex> listOfCineplexes) {
        System.out.println("List of Cineplexes:");
        for(int i = 0; i < listOfCineplexes.size(); i++) {
            System.out.println(i + 1 + ". " + listOfCineplexes.get(i).getCineplexName());
        }
    }
    public static void printListOfCinemas(ArrayList<Cinema> listOfCinemas) {
        System.out.println("List of Cinemas:");
        for(int i = 0; i < listOfCinemas.size(); i++) {
            System.out.println(i + 1 + ". " + listOfCinemas.get(i).getCineplexName());
        }
    }
    public static void printListOfMovieTypes(ArrayList<MovieType> listOfMovieTypes) {
        System.out.println("List of Cinemas:");
        for(int i = 0; i < listOfMovieTypes.size(); i++) {
            System.out.println(i + 1 + ". " + listOfMovieTypes.get(i).toString());
        }
    }


}
