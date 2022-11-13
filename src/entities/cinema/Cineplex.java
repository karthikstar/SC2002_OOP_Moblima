package entities.cinema;

import utils.FilePathFinder;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Class that represents the cineplex object.
 */
public class Cineplex implements Serializable {
    /**
     * Cineplex Code in format "AAA".
     */
    private String cineplexCode;
    /**
     * Cineplex Name.
     */
    private String cineplexName;
    /**
     * List of cinema objects.
     */
    private ArrayList<Cinema> cinemaList;
    /**
     * Number of cinemas in the cineplex.
     */
    private int noOfCinemas;

    /**
     * Constructor for the Cineplex object.
     * @param cineplexCode Code for cineplex in format "AAA"
     * @throws IOException
     */
    public Cineplex(String cineplexCode) throws IOException {
        this.cinemaList = new ArrayList<Cinema>();
        this.setCineplexCode(cineplexCode);
        this.loadCineplexFile(cineplexCode);
    }

    /**
     * Getter for the Cineplex Code.
     * @return Cineplex Code
     */
    public String getCineplexCode() {
        return cineplexCode;
    }

    /**
     * Getter for the Cineplex Name.
     * @return Cineplex Name
     */
    public String getCineplexName() {
        return cineplexName;
    }

    /**
     * Getter for the List of Cinemas.
     * @return List of Cinemas
     */
    public ArrayList<Cinema> getCinemaList() {
        return cinemaList;
    }

    /**
     * Getter for the Number of Cinemas.
     * @return Number of Cinemas
     */
    public int getNoOfCinemas() {
        return noOfCinemas;
    }

    /**
     * Setter for the Cineplex Code.
     * @param cineplexCode Cineplex Code
     */
    public void setCineplexCode(String cineplexCode) {
        this.cineplexCode = cineplexCode;
    }

    /**
     * Setter for the Cineplex Name.
     * @param cineplexName Cineplex Name
     */
    public void setCineplexName(String cineplexName) {
        this.cineplexName = cineplexName;
    }
    /**
     * Setter for the List of Cinemas.
     * @param cinemaList List of Cinemas
     */
    public void setCinemaList(ArrayList<Cinema> cinemaList) {
        this.cinemaList = cinemaList;
    }
    /**
     * Setter for the Number of Cinemas.
     * @param noOfCinemas Number of Cinemas
     */
    public void setNoOfCinemas(int noOfCinemas) {
        this.noOfCinemas = noOfCinemas;
    }

    /**
     * Loads Cineplex File which contains the cineplex name and its number of cinemas.
     * @param cineplexCode Cineplex Code in format "AAA"
     * @throws IOException
     */
    private void loadCineplexFile(String cineplexCode) throws IOException {
        try {
            // Get root file path for us to append to
            String filePath = FilePathFinder.findRootPath();
            filePath = filePath + "/src/data/cineplexes/" + cineplexCode +".txt";

            FileReader frStream = new FileReader( filePath );
            BufferedReader brStream = new BufferedReader( frStream );
            String inputLine;

            // read in Cineplex name, and no of cinemas for each cineplex.
            int lineCount = 0;

            do {
                inputLine = brStream.readLine();

                if(lineCount == 0){
                    this.setCineplexName(inputLine);
                } else if(lineCount == 1){
                    this.setNoOfCinemas(Integer.parseInt(inputLine));
                }

                lineCount++;
            } while(inputLine != null);

        } catch(IOException e) {
            System.out.println("IO Error: " + e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }

        // generate the cinemas for the cineplex instance
        this.generateCinemas(cineplexCode,getNoOfCinemas());
    }

    /**
     * Generates cinema objects based on the cineplexes available cinema layouts, and adds it to the Cinema attribute.
     * @param cineplexCode Cineplex Code
     * @param noOfCinemas Number of Cinemas
     * @throws IOException
     */
    public void generateCinemas(String cineplexCode, int noOfCinemas) throws IOException {
        for(int i = 1; i <= noOfCinemas; i++) {
            String a = cineplexCode + "_" + i;
            Cinema cinema = new Cinema(a);
            this.cinemaList.add(cinema);
//            break; // REMOVE THIS AFTER all txt files for cinemas are settled
        }
    }
}
