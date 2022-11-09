package controllers;

import entities.booking.Holiday;
import utils.FilePathFinder;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class HolidayController {
    private static HolidayController single_instance = null;

    public static HolidayController getInstance() {
        if (single_instance == null)
            single_instance = new HolidayController();
        return single_instance;
    }
    public static String path = FilePathFinder.findRootPath() + "src/data/holidays.txt";

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

        }
    }

    public boolean isHoliday(LocalDate valueToSearch) {
        ArrayList<Holiday> allData = read();
        for (int i=0; i<allData.size(); i++){
            if (allData.get(i).getHolidayDate().equals(valueToSearch))
                return true;
        }
        return false;
    }

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

        }
    }



}
