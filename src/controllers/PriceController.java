package controllers;

import entities.booking.PriceChanger;
import entities.booking.TicketType;
import entities.cinema.Cinema;
import entities.cinema.CinemaType;
import entities.cinema.Showtime;
import entities.movie.MovieType;
import entities.booking.Holiday;
import utils.DataSerializer;
import utils.FilePathFinder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
/**
 * Class that manages the functionalities required in that of computing/managing prices of a ticket.
 */
public class PriceController implements Serializable {
    /**
     * Singleton Constructor
     */
    private static PriceController single_instance = null;
    /**
     * Singleton Constructor
     */
    public static PriceController getInstance() {
        if (single_instance != null) save();
        File f = new File (FilePathFinder.findRootPath() + "/src/data/system_settings/prices.dat");
        if (f.exists()) single_instance = load();
        if (single_instance == null) {
            single_instance = new PriceController();
        }

        return single_instance;
    }

    /**
     * HashMap that stores the various object that affects price of ticket and its respective price increase.
     */
    private Map<PriceChanger,Double> priceMap = new HashMap<PriceChanger,Double>();

    /**
     * Constructor for Price Controller. Loads the Default Prices into the priceMap HashMap.
     */
    public PriceController(){
        populateDefaultPrices(priceMap);
    }

    /**
     * Sets the default values for the prices of ticket types, as well as the additional fees that come with each priceChanger.
     * @param priceMap HashMap that stores the various object that affects price of ticket and its respective price increase.
     */
    private void populateDefaultPrices(Map<PriceChanger,Double> priceMap) {
        priceMap.put(MovieType.TWO_D, 0.0);
        priceMap.put(MovieType.THREE_D, 1.5);
        priceMap.put(MovieType.ATMOS, 4.0);
        priceMap.put(MovieType.BLOCKBUSTER, 0.5);

        priceMap.put(CinemaType.PLATINUM, 10.0);
        priceMap.put(CinemaType.STANDARD, 0.0);

        priceMap.put(TicketType.WEEKEND, 4.5);
        priceMap.put(TicketType.HOLIDAY, 4.5);

        priceMap.put(TicketType.STUDENT, 7.0);
        priceMap.put(TicketType.ADULT, 10.0);
        priceMap.put(TicketType.SENIOR, 5.0);
    }

    /**
     * Prints the price list such as base fees of each ticket type, followed by the additional fees according to each PriceChanger.
     */
    public void printAllPriceChangers(){
        String result1 = "";
        for(PriceChanger priceChanger : priceMap.keySet()){
            if (priceChanger.equals(TicketType.valueOf("STUDENT")) || priceChanger.equals(TicketType.valueOf("SENIOR")) || priceChanger.equals(TicketType.valueOf("ADULT")) )
                result1 += priceChanger.toString() + " - $" + priceMap.get(priceChanger) + "\n";
        }
        String result2 = "";
        for(PriceChanger priceChanger : priceMap.keySet()){
            if (!priceChanger.equals(MovieType.valueOf("TWO_D")) && !priceChanger.equals(CinemaType.valueOf("STANDARD")) && !priceChanger.equals(TicketType.valueOf("STUDENT")) && !priceChanger.equals(TicketType.valueOf("SENIOR")) && !priceChanger.equals(TicketType.valueOf("ADULT")) )
                result2 += priceChanger.toString() + " - $" + priceMap.get(priceChanger) + "\n";
        }

        System.out.printf("Base Price for each Ticket Type: \n%s",result1);
        System.out.printf("\nAdditional Fees according to Type: \n%s\n",result2);
    }

    /**
     * Getter for the Price of each ticket based on the PriceChanger.
     * @param priceChanger PriceChanger object
     * @return Price set to the PriceChanger
     */
    public double getPrice(PriceChanger priceChanger){
        return priceMap.getOrDefault(priceChanger, 0.0);
    }

    /**
     * Method that adds a new price changer and its respective price into the HashMap stored in the controller.
     * @param priceChanger PriceChanger Object
     * @param value Price indicated by the PriceChanger
     */
    public void addPriceChanger(PriceChanger priceChanger, double value){
        priceMap.put(priceChanger, value);
    }

    /**
     * Modifies the price increase/base price of the priceChanger object
     * @param priceChanger PriceChanger object
     * @param newPrice New Price to be changed to
     */
    public void changePriceChanger(PriceChanger priceChanger, double newPrice){
        if(priceMap.containsKey(priceChanger)){
            priceMap.replace(priceChanger, newPrice);
        }
        else {
            System.out.printf("Price List does not contain %s", priceChanger);
        }
    }

    /**
     * Method that removes the current PriceChanger from the HashMap.
     * @param priceChanger PriceChanger object
     */
    public void removePriceChanger(PriceChanger priceChanger){
        priceMap.remove(priceChanger);
    }

    /**
     * Saves the updated prices into the database.
     */
    public static void save() {
        String path = FilePathFinder.findRootPath() + "/src/data/system_settings/prices.dat";
        DataSerializer.ObjectSerializer(path, single_instance);
    }

    /**
     * Loads the stored prices from the database.
     * @return Price Controller object
     */
    public static PriceController load() {
        String path = FilePathFinder.findRootPath() + "/src/data/system_settings/prices.dat";
        return (PriceController) DataSerializer.ObjectDeserializer(path);
    }
}
