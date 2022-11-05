package controllers;
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
}
