package utils;

import controllers.CompanyController;
import controllers.HolidayController;
import controllers.PriceController;
import controllers.SystemSettingsController;
import entities.cinema.Cinema;
import entities.cinema.CinemaType;
import entities.cinema.Company;
import entities.cinema.Showtime;
import entities.movie.*;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class that is called by ResetData in order to reset the database to its original initialised state.
 */
public class InitialiseData {
    /**
     * Method that returns the array of Files retrieved from the provided file path directory.
     * @param path Directory File Path
     * @return Array of Files of all files stored in the directory
     */
    public File[] getAllFiles(String path) {
        File directory = new File(path);
        return directory.listFiles();
    }

    /**
     * Method that loads all the initial movie data from the /src/data/initialisation/movies directory, after a deletion of the old files has been done.
     * @param path Initialisation folder path in string format
     * @return List of Movie objects
     */
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

        return movies;
    }
    /**
     * Method that loads all the initial showtime data from the /src/data/initialisation/showtimes directory, after a deletion of the old files has been done.
     * @param path Initialisation folder path in string format
     * @param movies List of movie objects
     * @return List of Showtime objects
     */
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
                BufferedReader brStream = new BufferedReader(frStream);
                String lineString;

                Showtime newShowtime = new Showtime();

                // showtime date and time
                lineString = brStream.readLine();
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                newShowtime.setDateTime(LocalDateTime.parse(lineString, dateTimeFormatter));

                // Showtime movie_id
                lineString = brStream.readLine();
                newShowtime.setMovieId(Integer.parseInt(lineString));
                for (Movie movie : movies) {
                    if (movie.getId() == Integer.parseInt(lineString)) {
                        movie.addShowtimeID(newShowtime.getShowTimeId());
                        break;
                    }
                }

                // Showtime movie type
                lineString = brStream.readLine();
                newShowtime.setMovieType(MovieType.valueOf(lineString));

                // Showtime cinema creation
                lineString = brStream.readLine(); // CinemaID
                Cinema newCinema;

                if (cinemas.contains(lineString + ".txt")) {
                    String cinemaFilePath = pathForCinemas + "/" + lineString + ".txt";
                    newCinema = initialiseCinemaData(lineString, cinemaFilePath);
                }
                else {
                    String cinemaFilePath = pathForCinemas + "/" + lineString + ".txt";
                    newCinema = initialiseCinemaData(lineString, cinemaFilePath);
                }
                newShowtime.setCinema(newCinema);

                // Set seating layout for showtime to cinema's (empty for now)
                newShowtime.setCinemaSeatLayout(newCinema.getCinemaSeatLayout());

                // Update Availability Status
                newShowtime.updateAvailability();

                brStream.close(); // Close file

                // Serialize showtime file
                String storagePath = FilePathFinder.findRootPath() + "/src/data/showtimes/showtime_" + newShowtime.getShowTimeId() + ".dat";

                DataSerializer.ObjectSerializer(storagePath, newShowtime);

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return movies;
    }
    /**
     * Method that loads all the initial cinema data from the /src/data/initialisation/cinemas directory, after a deletion of the old files has been done.
     * @param filePath Initialisation folder path in string format
     * @param cinemaID ID of cinema in format "AAA_1"
     * @return List of Cinema objects
     */
    public Cinema initialiseCinemaData(String cinemaID, String filePath) {
        Cinema newCinema = new Cinema(cinemaID);

        try {
            FileReader frStream = new FileReader( filePath );
            BufferedReader brStream = new BufferedReader( frStream );
            String lineString;

            int i = 0;
            ArrayList<String> seatingPlan = new ArrayList<String>();

            do {
                lineString = brStream.readLine();

                switch (i) {
                    case 0:
                        newCinema.setCineplexName(lineString);
                        break;
                    case 1:
                        newCinema.setCinemaID(lineString);
                        newCinema.setCineplexCode(lineString.split("_")[0]);
                        break;
                    case 2:
                        newCinema.setCinemaType(CinemaType.valueOf(lineString));
                        break;
                    case 3:
                        newCinema.setTotalNOfSeats(Integer.parseInt(lineString));
                        break;
                    default:
                        seatingPlan.add(lineString);
                        break;
                }
                i++;
            } while (lineString != null);

            brStream.close();

            newCinema.setCinemaSeatLayout(seatingPlan);

            return newCinema;

        } catch (FileNotFoundException e) {
            System.out.println("File not found:" + e.getMessage());
            e.printStackTrace();
            System.exit(0);
        } catch (IOException e) {
            System.out.println("IO Error: " + e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }
        return newCinema;
    }

    /**
     * Method that loads all the initial review data from the /src/data/initialisation/reviews directory, after a deletion of the old files has been done.
     * @param path Initialisation folder path in string format
     * @param movies List of movie objects
     * @return List of MovieReview objects
     */
    public ArrayList<Movie> initReviewData(ArrayList<Movie> movies, String path) {
        String pathOfFolder = path + "/reviews";

        File[] files = getAllFiles(pathOfFolder);

        try {
            for(int i = 0; i < files.length; i++) {
                String pathOfFile = files[i].getPath();
                FileReader frStream = new FileReader( pathOfFile );
                BufferedReader brStream = new BufferedReader( frStream );
                String lineString;

                MovieReview newReview = new MovieReview();

                // review id alr set during instantiation

                // Set movie id based on checking match of title
                lineString = brStream.readLine();
                for(int j = 0; j < movies.size(); j++) {
                    if(movies.get(j).getTitle().equals(lineString)){
                        newReview.setMovieId(movies.get(j).getId());
                        break;
                    }
                }

                // Set reviewer name
                lineString = brStream.readLine();
                newReview.setUsername(lineString);

                // Review score (checking if numOfStars <5?)
                lineString = brStream.readLine();
                newReview.setNumOfStars(Integer.valueOf(lineString));

                // Review DateTime
                lineString = brStream.readLine();
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                newReview.setDateTime(LocalDateTime.parse(lineString, dateTimeFormatter));

                // Review Comment
                lineString = brStream.readLine();
                newReview.setComment(lineString);

                // update movie
                for(int k = 0; k < movies.size(); k++) {
                    if(movies.get(k).getId() == newReview.getMovieId()) {
                        movies.get(k).addMovieReview(newReview);
                        break;
                    }
                }
                brStream.close();

                String savePath = FilePathFinder.findRootPath() + "/src/data/reviews/review_" + newReview.getReviewId() + ".dat";

                DataSerializer.ObjectSerializer(savePath, newReview);
            }
        } catch(FileNotFoundException e) {
            System.out.println("File not found error "+ e.getMessage());
            e.printStackTrace();
            System.exit(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return movies;
    }

    /**
     * Method that constructs the controller objects.
     */
    public void initSystemFiles() {
        CompanyController.getInstance();
        SystemSettingsController.getInstance();
        PriceController.getInstance();
        HolidayController.getInstance();
    }

    /**
     * Method that deletes a file in its given specified path.
     * @param fileName File Path to be deleted
     */
    public void resetFiles(String fileName) {
        String path = FilePathFinder.findRootPath() + "/" + fileName;

        File file = new File(path);
        file.delete();
    }

    /**
     * Method that deletes all the data in a given folder path.
     * @param folderName Directory path for all files to be deleted
     */
    public void resetFolders(String folderName) {
        int z = 0;
        String path = FilePathFinder.findRootPath() + "/src/data/" + folderName;
        System.out.println(path);
        File directory = new File(path);
        File[] list = directory.listFiles();

        if(list != null) {
            do {
                for(int i = 0; i < list.length; i++) {
                    if(!list[i].isDirectory()) {
                        if (list[i].getName().equalsIgnoreCase(".gitkeep")) continue;
                        list[i].delete();
                    }
                }
                z++;
                if (z>1000)break;
            } while (list.length!=1);
        }
    }

    /**
     * Method that resets all the necessary folders in the database to be empty, so that the application is restored to its original state and the initialised objects are put into a clean database.
     */
    public void resetAllData() {
        this.resetFolders("movies");
        this.resetFolders("showtimes");
        this.resetFolders("reviews");
        this.resetFolders("transactions");
        this.resetFolders("bookings");
        this.resetFolders("customers");
        this.resetFolders("system_settings");
        this.resetFiles("/src/data/company/company.dat");
    }
}
