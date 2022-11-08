package boundaries;

import entities.cinema.Showtime;

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
                System.out.printf("%d. Cineplex Name: %s, CinemaID / Hall No: %d\n", showtimeList.get(i).getCineplexName(), showtimeList.get(i).getCinemaID());
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

    public static void printShowtimeDetails(Showtime chosenShowtime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy, hh.mma");
        System.out.println("Here are the details of your chosen showtime:");
        System.out.println("Cineplex Name: " + chosenShowtime.getCineplexName());
        System.out.println("CinemaID / Hall No: " + chosenShowtime.getCinemaID());
        System.out.println("Cinema Type: " + chosenShowtime.getCinema().getCinemaType());
        System.out.println("Movie Type: " + chosenShowtime.getMovieType());
        System.out.println("Date/Time: "+ chosenShowtime.getDateTime().format(dateTimeFormatter));
        System.out.println("Seat Availability: " + chosenShowtime.getStatus());
        System.out.println("Total No. Of Seats " + chosenShowtime.getCinema().getTotalNOfSeats());
        System.out.println("Occupied No. Of Seats " + chosenShowtime.getCinema().getOccupiedNoOfSeats());
        System.out.println("Cinema Seat Layout: ");
        chosenShowtime.getCinema().printCinemaSeatLayout();
    }
}
