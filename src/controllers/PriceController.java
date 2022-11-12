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

public class PriceController implements Serializable {
    // Attributes
    private static PriceController single_instance = null;

    public static PriceController getInstance() {
        if (single_instance != null) save();
        File f = new File (FilePathFinder.findRootPath() + "/src/data/system_settings/prices.dat");
        if (f.exists()) single_instance = load();
        if (single_instance == null) {
            single_instance = new PriceController();
        }

        return single_instance;
    }

    private Map<PriceChanger,Double> priceMap = new HashMap<PriceChanger,Double>();

    public PriceController(){
        populateDefaultPrices(priceMap);
    }

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

    // if fri to sun or eve of ph or ph, 14.5 regardless of student/senior etc. student senior only available for 2d normal
    public double computePrice(Showtime session, Cinema cinema, TicketType priceType){
        double bookingFee = 1.5;
        double addToPrice = getPrice(session.getMovieType()) + getPrice(cinema.getCinemaType()) + bookingFee;
        if (session.isWeekend())
            addToPrice += getPrice(TicketType.WEEKEND);
        if(HolidayController.getInstance().isHoliday(session.getDateTime().toLocalDate())){
            return getPrice(TicketType.HOLIDAY) + addToPrice;
        }
        else{
            return getPrice(priceType) + addToPrice;
        }
    }

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

    // Getters
    public double getPrice(PriceChanger priceChanger){
        return priceMap.getOrDefault(priceChanger, 0.0);
    }

    // Map Modifiers
    public void addPriceChanger(PriceChanger priceChanger, double value){
        priceMap.put(priceChanger, value);
    }

    public void changePriceChanger(PriceChanger priceChanger, double newPrice){
        if(priceMap.containsKey(priceChanger)){
            priceMap.replace(priceChanger, newPrice);
        }
        else {
            System.out.printf("Price List does not contain %s", priceChanger);
        }
    }

    public void removePriceChanger(PriceChanger priceChanger){
        priceMap.remove(priceChanger);
    }

    public Map<PriceChanger,Double> getAllPriceChangers(){
        return priceMap;
    }

    public static void save() {
        String path = FilePathFinder.findRootPath() + "/src/data/system_settings/prices.dat";
        DataSerializer.ObjectSerializer(path, single_instance);
    }

    public static PriceController load() {
        String path = FilePathFinder.findRootPath() + "/src/data/system_settings/prices.dat";
        return (PriceController) DataSerializer.ObjectDeserializer(path);
    }
}
