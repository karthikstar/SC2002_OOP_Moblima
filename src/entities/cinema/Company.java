package entities.cinema;

import utils.FilePathFinder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

// Company entity, which comprises cineplexes.
public class Company implements Serializable {
    private String companyName;

    private ArrayList<Cineplex> cineplexList;

    private ArrayList<String> cineplexCodeList;

    public Company() {
        this.cineplexList = new ArrayList<Cineplex>();
        this.cineplexCodeList = new ArrayList<String>();
        this.loadCompanyFile();
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public ArrayList<Cineplex> getCineplexList() {
        return cineplexList;
    }

    public ArrayList<String> getCineplexCodeList() {
        return cineplexCodeList;
    }

    public void setCineplexList(ArrayList<Cineplex> cineplexList) {
        this.cineplexList = cineplexList;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setCineplexCodeList(ArrayList<String> cineplexCodeList) {
        this.cineplexCodeList = cineplexCodeList;
    }

    public void addCineplex(String cineplexCode) throws IOException {
        // initialise a new cineplex by passing a cineplex code
        Cineplex newCineplex = new Cineplex(cineplexCode);
        this.cineplexList.add(newCineplex);
        this.cineplexCodeList.add(newCineplex.getCineplexCode());
    }

    private void loadCompanyFile() {
        try {
            String filePath = FilePathFinder.findRootPath() + "/src/data/company/company.txt";

            // open file
            FileReader frStream = new FileReader( filePath );
            BufferedReader brStream = new BufferedReader( frStream );
            String inputLine;

            int lineCount = 0;

            do {
                lineCount = 0;
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

            } while(inputLine != null);
            brStream.close();
        } catch (IOException e) {
            System.out.println("IO Error: " + e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }
    }
}
