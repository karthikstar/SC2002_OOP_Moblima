package controllers;

import utils.FilePathFinder;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
/**
 * StaffController is a class that manages the functionalities required for staff.
 */
public class StaffController {
    /**
     * Singleton Constructor.
     */
    private static StaffController single_instance = null;
    /**
     * Singleton Constructor.
     */
    public static StaffController getInstance() {
        if(single_instance == null) {
            single_instance = new StaffController();
        }
        return single_instance;
    }

    /**
     * Constructor for StaffController.
     */
    private StaffController() {}

    /**
     * Allows users to login if they are a staff member with their respective login details. Checks if their login details are correct based on staff login informations stored in the database.
     * @param username String object for username entered by the user.
     * @param password String object for password entered by the user.
     * @return True if login is accepted, False if login is rejected (wrong details)
     */
    public boolean login(String username, String password) {
        try {
            String Filepath = FilePathFinder.findRootPath() + "/src/data/staffs/staff_list.csv";

            BufferedReader br = new BufferedReader(new FileReader(Filepath));
            String readLine;

            while((readLine = br.readLine()) != null) {
                String[] values = readLine.split(",");
                if (values[0].equals(username) && values[1].equals(password)) {
                    br.close();
                    return true;
                }
            }
            br.close();
            return false;
        } catch (FileNotFoundException e) {
            System.out.println("File not found error:" + e.getMessage());
            e.printStackTrace();
            System.exit(0);
        } catch (IOException e) {
            System.out.println("IO Error: " + e.getMessage());
            System.exit(0);
        }
        return false;
    }

    /**
     * Logs out of Staff App.
     * @return False boolean value
     */
    public boolean logout() {
        return false;
    }
}

