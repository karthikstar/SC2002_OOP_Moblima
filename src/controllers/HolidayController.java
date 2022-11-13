package controllers;

import entities.booking.Holiday;
import utils.DataSerializer;
import utils.FilePathFinder;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * HolidayController is a class that handles all the holiday related issues, such as loading of holidays, which is important for ticket price calculation
 */
public class HolidayController implements Serializable{
    /**
     * single_instance ensures that only one instance of HolidayController can be instantiated
     */
    private static HolidayController single_instance = null;

    /**
     * instantiates a HolidayController singleton, and creates a instance if there is not a instance already present
     * @return an instance of HolidayController
     */
    public static HolidayController getInstance() {
        if (single_instance != null) save();
        File f = new File (FilePathFinder.findRootPath() + "/src/data/system_settings/holidays.dat");
        if (f.exists()) single_instance = load();
        if (single_instance == null) {
            single_instance = new HolidayController();
        }

        return single_instance;
    }

    /**
     * Path to extract holidays data from
     */
    public static String path = FilePathFinder.findRootPath() + "/src/data/holidays.txt";

    /**
     * Create a holiday date
     * @param holidayDate
     */
    public void create(LocalDate holidayDate) {
        Holiday holiday = new Holiday(holidayDate);
        ArrayList<Holiday> allData = new ArrayList<Holiday>();
        File tempFile = new File(path);
        if (tempFile.exists())
            allData = read();
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path));
            allData.add(holiday);
            out.writeObject(allData);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if a date is a holiday
     * @param valueToSearch is a LocalDate object to be checked
     * @return a boolean, whether it is a date is a holiday or not
     */
    public boolean isHoliday(LocalDate valueToSearch) {
        ArrayList<Holiday> allData = read();
        for (int i=0; i<allData.size(); i++){
            if (allData.get(i).getHolidayDate().equals(valueToSearch))
                return true;
        }
        return false;
    }

    /**
     * Reads in output to create Holiday objects
     * @return an ArrayList of Holiday objects
     */
    public ArrayList<Holiday> read() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
            ArrayList<Holiday> holidayListing = (ArrayList<Holiday>) ois.readObject();
            ois.close();
            return holidayListing;
        } catch (ClassNotFoundException | IOException e) {
            return new ArrayList<Holiday>();
        }
    }

    /**
     * Delete a date that was classified as a Holiday
     * @param valueToSearch a LocalDate to be deleted from list of holidays
     */
    public void delete(LocalDate valueToSearch) {
        ArrayList<Holiday> allData = read();
        ArrayList<Holiday> returnData = new ArrayList<Holiday>();

        for (int i=0; i<allData.size(); i++){
            Holiday h = allData.get(i);
            if (!(h.getHolidayDate().equals(valueToSearch)))
                returnData.add(h);
        }

        replaceExistingFile(path, returnData);
    }

    /**
     * Helper function existing file with a new one
     * @param filename a String referring to the name of the file
     * @param data an ArrayList of Holiday objects
     */
    public void replaceExistingFile(String filename, ArrayList<Holiday> data){
        File tempFile = new File(filename);
        if (tempFile.exists())
            tempFile.delete();
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
            out.writeObject(data);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Save the holiday data and serializes it
     */
    public static void save() {
        String path = FilePathFinder.findRootPath() + "/src/data/system_settings/holidays.dat";
        DataSerializer.ObjectSerializer(path, single_instance);
    }

    /**
     * Load stored data from specified folder
     * @return a HolidayController object
     */
    public static HolidayController load() {
        String path = FilePathFinder.findRootPath() + "/src/data/system_settings/holidays.dat";
        return (HolidayController) DataSerializer.ObjectDeserializer(path);
    }
}
