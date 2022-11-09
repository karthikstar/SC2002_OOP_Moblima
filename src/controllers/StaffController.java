package controllers;

import utils.FilePathFinder;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class StaffController {
    private static StaffController single_instance = null;

    public static StaffController getInstance() {
        if(single_instance == null) {
            single_instance = new StaffController();
        }
        return single_instance;
    }

    private StaffController() {}

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

    // to logout the staff, to enforce that next person who tries to enter staff app will have to login again.
    public boolean logout() {
        return false;
    }
}

