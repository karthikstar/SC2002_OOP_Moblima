package entities;

import entities.cinema.Cineplex;

import java.io.Serializable;
import java.util.ArrayList;

public class Company implements Serializable {
    private String companyName;

    private ArrayList<Cineplex> cineplexes;


    // Constructor
    public Company(){
        this.cineplexes = new ArrayList<Cineplex>();
        openCompanyFile();
    }

    // Getters & Setters
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public ArrayList<Cineplex> getCineplexes() {
        return cineplexes;
    }

    public void setCineplexes(ArrayList<Cineplex> cineplexes) {
        this.cineplexes = cineplexes;
    }

    public void addCineplex(Cineplex cineplex){
        cineplexes.add(cineplex);
    }
}
