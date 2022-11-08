package controllers;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;


public class InputController {
    private static Scanner sc = new Scanner(System.in);

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



}
