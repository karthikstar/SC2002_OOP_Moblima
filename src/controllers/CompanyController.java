package controllers;

import entities.cinema.Cinema;
import entities.cinema.Cineplex;
import entities.cinema.Company;
import utils.DataSerializer;
import utils.FilePathFinder;


public class CompanyController {
    private Company company;

    private static CompanyController single_instance = null;

    public static CompanyController getInstance() {
        if(single_instance == null) {
            single_instance = new CompanyController();
        }
        return single_instance;
    }

    // constructor of CompanyController - will try loading data first, if data not found, will create a file
    private CompanyController() {
        Company storedDataOfCompany = this.loadData();
        // if no data found
        if(storedDataOfCompany == null) {
            this.company = new Company();
            this.saveData();
            System.out.println("File has been created for company");
        } else { // if there is data
            this.company = storedDataOfCompany;
        }
    }

    public Company getCompany() {
        return company;
    }


//  returns a new cloned copy of cinema for showtimeController to create a new showtime.
    // this copy is
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
        return null;
    }

    public Company loadData() {
        String filePath = FilePathFinder.findRootPath() + "/src/data/company/company.dat";
        return (Company) DataSerializer.ObjectDeserializer(filePath);
    }

    public void saveData() {
        String filePath = FilePathFinder.findRootPath() + "/src/data/company/company.dat";
        DataSerializer.ObjectSerializer(filePath, this.company);
    }

}
