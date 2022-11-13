package utils;

import entities.movie.Movie;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Class that is called in order for the staff to reset all the data in the database into its original intialised state.
 */
public class ResetData {
    public static void main(String[] args) throws IOException {
        InitialiseData initialiseData = new InitialiseData();

        // Path of folder where we can initialise data from
        String initPath = FilePathFinder.findRootPath() + "/src/data/initialisation";

        // Delete all data
        initialiseData.resetAllData();

        System.out.println("Resetted all data");

        ArrayList<Movie> movieList = initialiseData.initMovieData(initPath);

        // init showtimes
        movieList = initialiseData.initShowtimeData(movieList, initPath);

        // init reviews
        movieList = initialiseData.initReviewData(movieList, initPath);


        // Lastly, Serialise the movie files with showtimes & ticket sales
        for(int j = 0; j < movieList.size(); j++) {
            String serializeFilePath = FilePathFinder.findRootPath() + "/src/data/movies/movie_"+ movieList.get(j).getId() +".dat";
            DataSerializer.ObjectSerializer(serializeFilePath, movieList.get(j));

            initialiseData.initSystemFiles();

            System.out.println("Initialised System files.");
        }
    }
}
