package entities.cinema;

import utils.FilePathFinder;

import java.io.*;
import java.util.ArrayList;

public class Cinema implements Serializable {

    private String cineplexCode;
    private String cineplexName;
    private String cinemaID;
    private CinemaType cinemaType;

    private int totalNOfSeats;
    private ArrayList<String> cinemaSeatLayout;

//    private ArrayList<Session> sessions;

    public Cinema(String cinemaID) {
        this.cinemaID = cinemaID;
        this.cineplexCode = cinemaID.split("_")[0];
        this.cinemaSeatLayout = new ArrayList<String>();

        this.loadSeatingPlanFromFile(cinemaID);
    }
    public String getCineplexName() {
        return cineplexName;
    }

    public String getCineplexCode() {
        return cineplexCode;
    }

    public String getCinemaID() {
        return cinemaID;
    }

    public CinemaType getCinemaType() {
        return cinemaType;
    }

    public int getTotalNOfSeats() {
        return totalNOfSeats;
    }
    public ArrayList<String> getCinemaSeatLayout() {
        return cinemaSeatLayout;
    }

    public void setCineplexCode(String cineplexCode) {
        this.cineplexCode = cineplexCode;
    }
    public void setCineplexName(String cineplexName) {
        this.cineplexName = cineplexName;
    }

    public void setCinemaID(String cinemaID) {
        this.cinemaID = cinemaID;
    }

    public void setCinemaType(CinemaType cinemaType) {
        this.cinemaType = cinemaType;
    }

    public void setTotalNOfSeats(int totalNOfSeats) {
        this.totalNOfSeats = totalNOfSeats;
    }

    public void setCinemaSeatLayout(ArrayList<String> cinemaSeatLayout) {
        this.cinemaSeatLayout = cinemaSeatLayout;
    }

    public void printCinemaSeatLayout() {
        for(int i = 0; i < this.getCinemaSeatLayout().size() - 1; i++) { // minus one
            System.out.println(this.getCinemaSeatLayout().get(i));
        }
    }
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
