package controllers;

import boundaries.systemsettings.SystemSettingMainPageUI;
import entities.booking.Holiday;
import entities.booking.TicketType;
import entities.cinema.CinemaType;
import entities.movie.Movie;
import entities.movie.MovieType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SystemSettingsController {
    private static SystemSettingsController single_instance = null;

    public static SystemSettingsController getInstance() {
        if(single_instance == null) {
            single_instance = new SystemSettingsController();
        }
        return single_instance;
    }

    /**
     * Main method to load - display all available options and ask user to choose one
     */
    public void main() {
        boolean exit  = false;
        while (!exit){
            SystemSettingMainPageUI.printMenu();
            int choice = InputController.getUserInt(0,11);
            switch (choice) {
                case 1:
                    listAllHolidays();
                    break;
                case 2:
                    createHoliday();
                    break;
                case 3:
                    deleteHoliday();
                    break;
                case 4:
                    System.out.println("\n---------------PRICE LIST---------------");
                    PriceController.getInstance().printAllPriceChangers();
                    break;
                case 5:
                    updateMovieTypePrice();
                    break;
                case 6:
                    updateCinemaTypePrice();
                    break;
                case 7:
                    updateStudentPrice();
                    break;
                case 8:
                    updateSeniorPrice();
                    break;
                case 9:
                    updateAdultPrice();
                    break;
                case 10:
                    updateWeekendPrice();
                    break;
                case 11:
                    updateHolidayPrice();
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    break;
            }
            if (choice >= 5 && choice <= 11) {
                System.out.println("Price updated!");
            }
        }
    }

    /**
     * Create a new holiday - ask user to enter date
     * If the holiday already exists, the user has to try again
     */
    public void createHoliday() {
        System.out.println("Enter holiday date to add: ");
        LocalDate holiday = InputController.getDate();
        if (HolidayController.getInstance().isHoliday(holiday)) {
            System.out.println("\nHoliday date already exists in database!\n");
            return;
        }
        HolidayController.getInstance().create(holiday);
    }

    /**
     * List all available holidays (If there are any)
     * @return		If there are available holidays or not
     */
    public boolean listAllHolidays(){
        ArrayList<Holiday> holList = HolidayController.getInstance().read();
        if(holList.isEmpty()){
            System.out.println("\nThere are no holiday dates stored in the database!");
            return false;
        }
        else{
            System.out.println("\nCurrently declared holidays: (sorted by date - earliest to latest)");
            Collections.sort(holList);
            holList.forEach(Holiday ->  System.out.println(Holiday.getHolidayDateToString()));
        }
        System.out.println();
        return true;
    }

    /**
     * Delete a holiday from database
     * If the user's input is invalid, they have to try again
     */
    public void deleteHoliday() {
        if(listAllHolidays()){
            ArrayList<Holiday> holList = HolidayController.getInstance().read();
            System.out.println("Enter holiday date choice to delete: ");
            int choice = InputController.getUserInt(1,holList.size())-1;
            if (!HolidayController.getInstance().isHoliday(holList.get(choice).getHolidayDate())) {
                System.out.println("Holiday date does not exist!\n");
                return;
            }
            HolidayController.getInstance().delete(holList.get(choice).getHolidayDate());
        }
    }

    /**
     * Update the price of a specific movie type (user's choosing)
     * Invalid choice will return the user back to main menu
     */
    public void updateMovieTypePrice() {
        System.out.println("Choose Movie Type: \n" +
                "1. 2D\n" +
                "2. 3D\n" +
                "3. Atmos\n" +
                "4. Blockbuster\n" +
                "0. Exit\n" +
                "Choice: ");
        int choice = InputController.getUserInt(0,4);
        System.out.println("Enter new price: ");
        double newPrice = InputController.getUserDouble();
        switch(choice) {
            case 1:
                PriceController.getInstance().changePriceChanger(MovieType.TWO_D, newPrice);
                break;
            case 2:
                PriceController.getInstance().changePriceChanger(MovieType.THREE_D, newPrice);
                break;
            case 3:
                PriceController.getInstance().changePriceChanger(MovieType.ATMOS, newPrice);
                break;
            case 4:
                PriceController.getInstance().changePriceChanger(MovieType.BLOCKBUSTER, newPrice);
                break;
            default:
                System.out.println("Returning to menu...");
                break;
        }
    }

    /**
     * Update the price of a specific cinema type (user's choosing)
     * Invalid choice will return the user back to main menu
     */
    public void updateCinemaTypePrice() {
        System.out.println("Choose Cinema Type: \n" +
                "1. Standard\n" +
                "2. Platinum\n" +
                "0. Exit\n" +
                "Choice: ");
        int choice = InputController.getUserInt(0,2);
        System.out.println("Enter new price: ");
        double newPrice = InputController.getUserDouble();
        switch(choice) {
            case 1:
                PriceController.getInstance().changePriceChanger(CinemaType.STANDARD, newPrice);
                break;
            case 2:
                PriceController.getInstance().changePriceChanger(CinemaType.PLATINUM, newPrice);
                break;
            default:
                System.out.println("Returning to menu...");
                break;
        }
    }

    public void updateStudentPrice () {
        System.out.println("Enter new price: ");
        double newPrice = InputController.getUserDouble();
        PriceController.getInstance().changePriceChanger(TicketType.STUDENT, newPrice);
    }

    public void updateSeniorPrice () {
        System.out.println("Enter new price: ");
        double newPrice = InputController.getUserDouble();
        PriceController.getInstance().changePriceChanger(TicketType.SENIOR, newPrice);
    }

    public void updateAdultPrice () {
        System.out.println("Enter new price: ");
        double newPrice = InputController.getUserDouble();
        PriceController.getInstance().changePriceChanger(TicketType.ADULT, newPrice);
    }

    public void updateWeekendPrice () {
        System.out.println("Enter new price: ");
        double newPrice = InputController.getUserDouble();
        PriceController.getInstance().changePriceChanger(TicketType.WEEKEND, newPrice);
    }

    public void updateHolidayPrice () {
        System.out.println("Enter new price: ");
        double newPrice = InputController.getUserDouble();
        PriceController.getInstance().changePriceChanger(TicketType.HOLIDAY, newPrice);
    }




}
