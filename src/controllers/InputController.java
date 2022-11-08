package controllers;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    public static LocalDate getDate(){
        String DateKeyed = getUserString();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(DateKeyed, dateFormat);
        return date;
    }



}
