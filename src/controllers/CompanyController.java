package controllers;

import entities.cinema.Cinema;
import entities.cinema.Cineplex;
import entities.cinema.Company;
import utils.DataSerializer;
import utils.FilePathFinder;

import java.io.File;

/**
 * CompanyController is a class which will handle all the company related issues, and it will instantiate a company entity comprising of 3 cineplexes, each having 3 cinemas.
 */
public class CompanyController {
    /**
     * This is the company object that we will be working with.
     */
    private Company company;

    /**
     * This single_instance tracks whether CompanyController has been instantiated previously.
     */
    private static CompanyController single_instance = null;

    /**
     * Instantiates the CompanyController singleton. It will create a new instance if there is no previous instance created.
     * @return an instance of CompanyController
     */
    public static CompanyController getInstance() {
        if(single_instance == null) {
            single_instance = new CompanyController();
        }
        return single_instance;
    }

    /**
     * Constructor of CompanyController. It attempts to load serialized data to create a Companny object. If data not available, it will create these files from our src/data/initialisation folder
     */
    private CompanyController() {
        Company storedDataOfCompany = this.loadData();
        // if no data found
        if(storedDataOfCompany == null) {
            this.company = new Company();
            this.saveData();
            System.out.println("File has been created for company.");
        } else { // if there is data
            this.company = storedDataOfCompany;
        }
    }

    /**
     * Gets the company for us to work with
     * @return a Company object
     */
    public Company getCompany() {
        return company;
    }


    /**
     * Returns a new cloned copy of cinema for showtimeController to create a new showtime.
     * @param cinemaID a String representing the ID of the cinema
     * @return a Cinema object, which is a cloned copy
     */
    public Cinema generateNewCinema(String cinemaID) {
        for(int i = 0; i < this.company.getCineplexList().size(); i++) {
            Cineplex cineplex = this.company.getCineplexList().get(i);
            if(cineplex.getCineplexCode().equals(cinemaID.split("_")[0])){
                Cinema newCinema = new Cinema(cinemaID);

                // get a copy of the oldCinema - position of cinema in list is cinemaID - 1.
                Cinema oldCinema = cineplex.getCinemaList().get(Integer.parseInt(cinemaID.split("_")[1]) - 1);

                // copy over same details to this newCinema
                newCinema.setCineplexCode(oldCinema.getCineplexCode());
                newCinema.setCineplexName(oldCinema.getCineplexName());
                newCinema.setCinemaID(oldCinema.getCinemaID());
                newCinema.setCinemaType(oldCinema.getCinemaType());
                newCinema.setTotalNOfSeats(oldCinema.getTotalNOfSeats());
                newCinema.setCinemaSeatLayout(oldCinema.getCinemaSeatLayout());
                return newCinema;
            }
        }
        System.out.println("Failed to generate new cinema!");
        return null;
    }

    /**
     * Creates a company object from serialized file
     * @return a Company object
     */
    public Company loadData() {
        System.out.println("Company Files loaded!");
        String filePath = FilePathFinder.findRootPath() + "/src/data/company/company.dat";
        File f = new File(filePath);
        if (f.exists()) return (Company) DataSerializer.ObjectDeserializer(filePath);
        else return null;
    }

    /**
     * Saves the entire company object and serializes it
     */
    public void saveData() {
        String filePath = FilePathFinder.findRootPath() + "/src/data/company/company.dat";
        DataSerializer.ObjectSerializer(filePath, this.company);
    }

}
