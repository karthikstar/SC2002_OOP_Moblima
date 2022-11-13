package entities.cinema;

import utils.FilePathFinder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class that represents the company entity, which manages our cineplexes.
 */
public class Company implements Serializable {
    /**
     * Name of the company.
     */
    private String companyName;
    /**
     * List of cineplexes objects.
     */
    private ArrayList<Cineplex> cineplexList;
    /**
     * List of Strings containing the cineplex codes under the company.
     */
    private ArrayList<String> cineplexCodeList;

    /**
     * Constructor for the Company object, loads the company information from the company.txt file.
     */
    public Company() {
        this.cineplexList = new ArrayList<Cineplex>();
        this.cineplexCodeList = new ArrayList<String>();
        this.loadCompanyFile();
    }

    /**
     * Getter for the Company Name.
     * @return Company Name.
     */
    public String getCompanyName() {
        return this.companyName;
    }

    /**
     * Getter for the List of Cineplexes.
     * @return List of Cineplexes.
     */
    public ArrayList<Cineplex> getCineplexList() {
        return cineplexList;
    }

    /**
     * Getter for the List of Cineplex Codes.
     * @return List of Cineplex Codes.
     */
    public ArrayList<String> getCineplexCodeList() {
        return cineplexCodeList;
    }

    /**
     * Setter for the List of Cineplexes.
     * @param cineplexList List of Cineplexes
     */
    public void setCineplexList(ArrayList<Cineplex> cineplexList) {
        this.cineplexList = cineplexList;
    }

    /**
     * Setter for the Company Name.
     * @param companyName Company Name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Setter for the List of Cineplex Codes.
     * @param cineplexCodeList List of Cineplex Codes.
     */
    public void setCineplexCodeList(ArrayList<String> cineplexCodeList) {
        this.cineplexCodeList = cineplexCodeList;
    }

    /**
     * Method that adds a new cineplex based on the given cineplex code and adds them to the cineplex and cineplex code lists.
     * @param cineplexCode Cineplex Code in format "AAA"
     */
    public void addCineplex(String cineplexCode) {
        // initialise a new cineplex by passing a cineplex code
        Cineplex newCineplex = null;
        try {
            newCineplex = new Cineplex(cineplexCode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.cineplexList.add(newCineplex);
        this.cineplexCodeList.add(newCineplex.getCineplexCode());
    }

    /**
     * Loads the Company data from the company.txt file which gives the company name and the respective cineplex codes.
     */
    private void loadCompanyFile() {
        try {
            String filePath = FilePathFinder.findRootPath() + "/src/data/company/company.txt";

            // open file
            FileReader frStream = new FileReader( filePath );
            BufferedReader brStream = new BufferedReader( frStream );
            String inputLine;

            int lineCount = 0;

            do {
                inputLine = brStream.readLine();
                if(inputLine == null){
                    break;
                };

                if(lineCount == 0){
                    this.setCompanyName(inputLine);
                } else {
                    // remaining lines are cineplex codes belonging to the company
                    this.addCineplex(inputLine);
                }
                lineCount++;

            } while(inputLine != "");
            brStream.close();
        } catch (IOException e) {
            System.out.println("IO Error: " + e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }
    }
}
