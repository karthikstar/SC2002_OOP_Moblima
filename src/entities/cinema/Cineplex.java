package entities.cinema;

import utils.FilePathFinder;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Cineplex implements Serializable {
    private String cineplexCode;

    private String cineplexName;

    private ArrayList<Cinema> cinemaList;

    private int noOfCinemas;

    public Cineplex(String cineplexCode) throws IOException {
        this.cinemaList = new ArrayList<Cinema>();
        this.setCineplexCode(cineplexCode);
        this.loadCineplexFile(cineplexCode);
    }

    public String getCineplexCode() {
        return cineplexCode;
    }

    public String getCineplexName() {
        return cineplexName;
    }

    public ArrayList<Cinema> getCinemaList() {
        return cinemaList;
    }

    public int getNoOfCinemas() {
        return noOfCinemas;
    }

    public void setCineplexCode(String cineplexCode) {
        this.cineplexCode = cineplexCode;
    }

    public void setCineplexName(String cineplexName) {
        this.cineplexName = cineplexName;
    }

    public void setCinemaList(ArrayList<Cinema> cinemaList) {
        this.cinemaList = cinemaList;
    }

    public void setNoOfCinemas(int noOfCinemas) {
        this.noOfCinemas = noOfCinemas;
    }

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
    public void generateCinemas(String cineplexCode, int noOfCinemas) throws IOException {
        for(int i = 1; i <= noOfCinemas; i++) {
            Cinema cinema = new Cinema(cineplexCode, i);
            this.cinemaList.add(cinema);
//            break; // REMOVE THIS AFTER all txt files for cinemas are settled
        }
    }
}
