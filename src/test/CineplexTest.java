package test;

import entities.cinema.Cineplex;

import java.io.IOException;

public class CineplexTest {
    public static void main(String[] args) throws IOException {
        Cineplex cineplex1 = new Cineplex("AMK");
        cineplex1.getCinemaList().get(0).printCinemaSeatLayout();
    }
}
