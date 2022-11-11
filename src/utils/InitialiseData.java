package utils;

import entities.cinema.Cinema;
import entities.cinema.Showtime;
import entities.movie.Movie;
import entities.movie.MovieGenre;
import entities.movie.MovieRating;
import entities.movie.MovieType;

import java.io.*;
import java.nio.Buffer;
import java.sql.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class InitialiseData {
    public File[] getAllFiles(String path) {
        File directory = new File(path);
        return directory.listFiles();
    }

    public ArrayList<Movie> initMovieData(String path) {
        String pathOfFolder = path + "/movies";
        System.out.println(pathOfFolder);
        File[] files = getAllFiles(pathOfFolder);

        ArrayList<Movie> movies = new ArrayList<>();

        // iterate thru all the files in this folder
        for(int i = 0; i < files.length; i++) {
            String pathOfFile = files[i].getPath();

            try {
                FileReader frStream = new FileReader(pathOfFile);
                BufferedReader brStream = new BufferedReader(frStream);

                String lineString;

                Movie newMovie = new Movie();

                // MovieID
                newMovie.setId(newMovie.getId());

                // MovieTitle
                lineString = brStream.readLine();
                newMovie.setTitle(lineString);

                // Genres
                lineString = brStream.readLine();
                ArrayList<String> listOfGenres = new ArrayList<>(Arrays.asList(lineString.split(",")));

               ArrayList<MovieGenre> movieGenres = new ArrayList<>();

               for(int j = 0; j < listOfGenres.size(); j++) {
                   movieGenres.add(MovieGenre.valueOf(listOfGenres.get(j)));
               }

               newMovie.setGenres(movieGenres);

               // Director
                lineString = brStream.readLine();
                newMovie.setDirector(lineString);

                // Cast
                lineString = brStream.readLine();
                ArrayList<String> listOfCast = new ArrayList<>(Arrays.asList(lineString.split(",")));
                newMovie.setCast(listOfCast);

                // Synopsis
                lineString = brStream.readLine();
                newMovie.setSynopsis(lineString);

                // Movie rating
                lineString = brStream.readLine();
                newMovie.setRating(MovieRating.valueOf(lineString));

                // Movie Type
                lineString = brStream.readLine();
                ArrayList<String> listOfTypes = new ArrayList<>(Arrays.asList(lineString.split(",")));
                ArrayList<MovieType> movieTypes = new ArrayList<>();

                for(int k = 0; k < listOfTypes.size(); k++) {
                    movieTypes.add(MovieType.valueOf(listOfTypes.get(k)));
                }

                newMovie.setType(movieTypes);

                // Movie Duration
                lineString = brStream.readLine();
                newMovie.setDuration(Integer.valueOf(lineString));

                // Movie Language
                lineString = brStream.readLine();
                newMovie.setLanguage(lineString);

                // Opening Date
                lineString = brStream.readLine();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                LocalDate openingDate = LocalDate.parse(lineString, formatter);
                newMovie.setMovieOpeningDate(openingDate);

                // Ending Date
                lineString = brStream.readLine();
                LocalDate endingDate = LocalDate.parse(lineString, formatter);
                newMovie.setMovieEndDate(endingDate);

                // Set showtime IDs list to be empty
                newMovie.setShowtimeIDs(new ArrayList<Integer>());

                brStream.close();

                movies.add(newMovie);

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

//        return ArrayList of movies
        return movies;
    }

    public ArrayList<Movie> initShowtimeData(ArrayList<Movie> movies, String path) {
        String pathOfFolder = path + "/showtimes";

        File[] showtimeFiles = getAllFiles(pathOfFolder);

        // Load filename of cinemas into an array

        String pathForCinemas = path + "/cinemas";
        File[] cinemaFiles = getAllFiles(pathForCinemas);

        ArrayList<String> cinemas = new ArrayList<>();

        // store the cinema file names
        for(int i = 0; i < cinemaFiles.length; i++) {
            cinemas.add(cinemaFiles[i].getName());
        }

        // iterate thru the showtime files
        for(int i = 0; i < showtimeFiles.length; i++) {

            String showtimeFilePath = showtimeFiles[i].getPath();

            try {
                FileReader frStream = new FileReader(showtimeFilePath);
                BufferedReader brStream = new BufferedReader( frStream );
                String lineString;

                Showtime newShowtime = new Showtime();

                // showtime id
                newShowtime.setShowTimeId(newShowtime.getShowTimeId());

                // showtime date and time
                lineString = brStream.readLine();
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                newShowtime.setDateTime(LocalDateTime.parse(lineString, dateTimeFormatter));

                // Showtime movie_id

                // read the movieName first from the showtime file
                lineString = brStream.readLine();
                String movieName = lineString;

                // find the movieID in movies that matches this movieName, and setMovieID for showtime
                for(int j = 0; j < movies.size(); j++) {
                    if(movies.get(j).equals(movieName)) {
                        // set movieID for this particular new showtime
                        newShowtime.setMovieId(movies.get(j).getId());
                        // add showtimeID to that movie as well
                        movies.get(j).addShowtimeID(newShowtime.getShowTimeId());
                        break;
                    }
                }

                // Showtime movie type
                lineString = brStream.readLine();
                newShowtime.setMovieType(MovieType.valueOf(lineString));

                // Showtime cinema instance

                lineString = brStream.readLine();
                String cineplexCode = lineString.split("_")[0];
                String cinemaID = lineString.split("_")[1];
                String cinemaFilePath = path + "/" + lineString +".txt";


            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


    }

    public Cinema initCinemaData(String cinemaID, String path) {
        Cinema newCinema = new Cinema(cinemaID);
        try {
            FileReader frStream = new FileReader(path);
            BufferedReader brStream = new BufferedReader(frStream);
            String lineString;
            int i = 0;


            ArrayList<String> cinemaLayout = new ArrayList<>();

            do {
                lineString = brStream.readLine();
                if(lineString == null) {
                    break;
                }

                switch(i){
                    case 0:

                }

            } while(lineString != null);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
