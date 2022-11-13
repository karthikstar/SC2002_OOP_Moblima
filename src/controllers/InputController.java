package controllers;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * InputController is a class that handles all the input related operations such as getting integers, strings from users
 */
public class InputController {
    /**
     * Stores an instance of Scanner to read input
     */
    private static Scanner sc = new Scanner(System.in);

    /**
     * Get integer from user
     * @return an integer, which is keyed in by the user
     */
    public static int getUserInt() {
        int userInt = -1;
        boolean isValidInt = false;
        while(!isValidInt) {
            if(sc.hasNextInt()){
                userInt = sc.nextInt();
                isValidInt = true;
            } else {
                System.out.println("Wrong Input! Please enter a valid integer");
            }
            sc.nextLine();
        }
        return userInt;
    }

    /**
     * Gets user integer, with additional check to ensure it is within a specified range of integers
     * @param minIntChoice an integer representing minimum choice that can be made by the user
     * @param maxIntChoice an integer representing the maximum choice that can be made by the user
     * @return a integer keyed in by the user, that falls in the range of minIntChoice to maxIntChoice
     */
    public static int getUserInt(int minIntChoice, int maxIntChoice) {
        int userInt = -1;
        boolean isValidInt = false;
        while(!isValidInt) {
            if(sc.hasNextInt()){
                userInt = sc.nextInt();
                if(userInt >= minIntChoice && userInt <= maxIntChoice) {
                    isValidInt = true;
                } else {
                    System.out.printf("Invalid Input! Please enter a valid integer between %d to %d.\n", minIntChoice, maxIntChoice);
                }
            } else {
                System.out.printf("Invalid Input! Please enter a valid integer between %d to %d.\n", minIntChoice, maxIntChoice);
            }
            sc.nextLine();
        }
        return userInt;
    }

    /**
     * Gets a long from the user
     * @return a long that is keyed in by the user
     */
    public static long getUserLong() {
        long userLong = -1;
        boolean isValidLong = false;
        while(!isValidLong) {
            if(sc.hasNextLong()){
                userLong = sc.nextLong();
                isValidLong = true;
            } else {
                System.out.println("Wrong Input! Please enter a valid long");
            }
            sc.nextLine();
        }
        return userLong;
    }

    /**
     * Gets a double from the user
     * @return a double keyed in by the user
     */
    public static double getUserDouble() {
        double userDouble = -1.0;
        boolean isValidDouble = false;
        while(!isValidDouble) {
            if(sc.hasNextDouble()){
                userDouble = sc.nextDouble();
                isValidDouble = true;
            } else {
                System.out.println("Wrong Input! Please enter a valid double.");
            }
            sc.nextLine();
        }
        return userDouble;
    }

    /**
     * Gets a String from the user
     * @return a String that is keyed in by the user
     */
    public static String getUserString() {
        String userString = "";

        while(userString.equals("")) {
            userString = sc.nextLine();
            if(userString.equals("")) {
                System.out.println("Please enter a non-empty input!");
            }
        }
        return userString;
    }

    /**
     * Gets a valid date from the user
     * @return a LocalDate object representing the date keyed in by the user
     */
    public static LocalDate getDate(){
        String DateKeyed = getUserString();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(DateKeyed, dateFormat);
        return date;
    }

    /**
     * Gets a valid date from the user
     * @return a LocalDateTime object representing the date keyed in by the user
     */
    public static LocalDateTime getDateTimeFromUser(){
        LocalDateTime userDateTime = null;
        String date;
        boolean validInput = false;
        while(!validInput){
            try{
                date = sc.nextLine();
                userDateTime = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                validInput = true;
            }
            catch(DateTimeParseException e){
                System.out.println("Date and time entered must be of pattern dd/MM/yyyy HH:mm!");
            }
        }
        return userDateTime;
    }

    /**
     * Gets a valid email from user
     * @return a String representing the email keyed in by the user
     */
    public static String getUserEmail(){
        String pattern = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        String input = "";
        boolean validInput = false;
        while(!validInput){
            input = InputController.getUserString();
            if(input.matches(pattern)){
                validInput = true;
            }
            else{
                System.out.println("Must match email pattern!");
            }
        }
        return input;
    }

    /**
     * Gets a valid mobile number from the user that is compliant with local mobile name conventions (8 digits long, start with either 8 or 9)
     * @return a String representing the mobile number entered in by the user
     */
    public static String getUserMobileNumber(){
        String pattern = "\\d{8}";
        String input = "";
        boolean validInput = false;
        while(!validInput){
            input = InputController.getUserString();
            if(input.matches(pattern) && (input.startsWith("9")||input.startsWith("8"))){
                validInput = true;
            }
            else{
                System.out.println("Must be valid mobile number (8 digits long, starts with either 8 or 9)");
            }
        }
        return input;
    }

}
