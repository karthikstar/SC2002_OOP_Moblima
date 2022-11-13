package entities.cinema;

import utils.FilePathFinder;

import java.io.*;
import java.util.ArrayList;

/**
 * Cinema is a class that represents a cinema object (or cinema hall).
 */
public class Cinema implements Serializable {
    /**
     * Cineplex code in format "AAA".
     */
    private String cineplexCode;
    /**
     * Cineplex name.
     */
    private String cineplexName;
    /**
     * Cinema Number.
     */
    private String cinemaID;
    /**
     * Whether Cinema Type is standard or a platinum cinema.
     */
    private CinemaType cinemaType;
    /**
     * Total number of seatings in the cinema.
     */
    private int totalNOfSeats;
    /**
     * List of Strings which represents the seating layout of the cinema.
     */
    private ArrayList<String> cinemaSeatLayout;


    /**
     * Cinema constructor.
     * @param cinemaID string of the cinemaID | example: "AAA_1"
     */
    public Cinema(String cinemaID) {
        this.cinemaID = cinemaID;
        this.cineplexCode = cinemaID.split("_")[0];
        this.cinemaSeatLayout = new ArrayList<String>();

        this.loadSeatingPlanFromFile(cinemaID);
    }

    /**
     * Getter for the name of the cineplex.
     * @return name of the cineplex
     */
    public String getCineplexName() {
        return cineplexName;
    }

    /**
     * Getter for the cineplex code.
     * @return Cineplex Code
     */
    public String getCineplexCode() {
        return cineplexCode;
    }
    /**
     * Getter for the cinema ID.
     * @return Cineplex ID
     */
    public String getCinemaID() {
        return cinemaID;
    }
    /**
     * Getter for the cinema type.
     * @return Cineplex Type
     */
    public CinemaType getCinemaType() {
        return cinemaType;
    }
    /**
     * Getter for the total number of seats.
     * @return Total number of seats
     */
    public int getTotalNOfSeats() {
        return totalNOfSeats;
    }
    /**
     * Getter for the cinema seats layout represents by the list of strings.
     * @return Cinema Seating Layout
     */
    public ArrayList<String> getCinemaSeatLayout() {
        return cinemaSeatLayout;
    }

    /**
     * Setter for the cineplex code.
     * @param cineplexCode
     */
    public void setCineplexCode(String cineplexCode) {
        this.cineplexCode = cineplexCode;
    }

    /**
     * Setter for the cineplex name.
     * @param cineplexName
     */
    public void setCineplexName(String cineplexName) {
        this.cineplexName = cineplexName;
    }

    /**
     * Setter for the cinema ID.
     * @param cinemaID
     */
    public void setCinemaID(String cinemaID) {
        this.cinemaID = cinemaID;
    }

    /**
     * Setter for the cinema type.
     * @param cinemaType
     */
    public void setCinemaType(CinemaType cinemaType) {
        this.cinemaType = cinemaType;
    }

    /**
     * Setter for the total number of seats.
     * @param totalNOfSeats
     */
    public void setTotalNOfSeats(int totalNOfSeats) {
        this.totalNOfSeats = totalNOfSeats;
    }

    /**
     * Setter for the cinema seat layout.
     * @param cinemaSeatLayout
     */
    public void setCinemaSeatLayout(ArrayList<String> cinemaSeatLayout) {
        this.cinemaSeatLayout = cinemaSeatLayout;
    }

    /**
     * Method that prints the cinema seating layout onto the user display.
     */
    public void printCinemaSeatLayout() {
        for(int i = 0; i < this.getCinemaSeatLayout().size() - 1; i++) { // minus one
            System.out.println(this.getCinemaSeatLayout().get(i));
        }
    }

    /**
     * Loads the seating plan of each cinema from each cinema.txt file.
     * @param cinemaID Cinema ID in format "AAA_1"
     */
    private void loadSeatingPlanFromFile(String cinemaID) {
        String filePath = FilePathFinder.findRootPath();
        filePath = filePath + "/src/data/cinemas/" + cinemaID +".txt";


        FileReader frStream = null;
        try {
            frStream = new FileReader( filePath );
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Seating Plan files not found!");
        }
        BufferedReader brStream = new BufferedReader( frStream );
        String inputLine;

        ArrayList<String> seatLayout = new ArrayList<String>();

        try {
            int lineCount = 0;

            do {
                inputLine = brStream.readLine();
                if (inputLine == null) {
                    break;
                }
                switch (lineCount) {
                    case 0:
                        //System.out.println("Cineplex Name: " + inputLine);
                        this.setCineplexName(inputLine);
                        break;
                    case 1:
                        //System.out.println("Cinema ID: " + inputLine);
                        this.setCinemaID(inputLine);
                        break;
                    case 2:
                        //System.out.println("Cinema Type: " + inputLine);
                        this.setCinemaType(CinemaType.valueOf(inputLine));
                        break;
                    case 3:
                        this.setTotalNOfSeats(Integer.parseInt(inputLine));
                        //System.out.println("Total No Of Seats: " + inputLine);
                        break;
                    default:
                        seatLayout.add(inputLine);
                        break;
                }
                lineCount++;
            } while (inputLine != null);

            //System.out.println("\n\nNew Cinema Showtime Generated!");

            brStream.close();

            this.setCinemaSeatLayout(seatLayout);
//            this.printCinemaSeatLayout();

        } catch (IOException e) {
            System.out.println("IO error: " + e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }
    }
}
