package test;

import entities.cinema.Cineplex;

import java.io.IOException;

public class CineplexTest {
    public static void main(String[] args) throws IOException {
        Cineplex cineplex1 = new Cineplex("AMK");
        cineplex1.getCinemaList().get(0).printCinemaSeatLayout();
//        for(int row = 0; row < cineplex1.getCinemaList().get(0).getCinemaSeatLayout().size(); row++) {
//            String rowString = cineplex1.getCinemaList().get(0).getCinemaSeatLayout().get(row);
//
//            System.out.println("row "+ row + " " + rowString);
//        }
    }
}
