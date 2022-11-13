package controllers;

import boundaries.review.AddReviewUI;
import boundaries.review.EditReviewUI;
import entities.movie.Movie;
import entities.movie.MovieReview;
import utils.DataSerializer;
import utils.FilePathFinder;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ReviewController {
    private Scanner sc = new Scanner(System.in);
    private Map<Integer, MovieReview> reviews;

    private static ReviewController single_instance = null;
    public static ReviewController getInstance() {
        if (single_instance == null)
            single_instance = new ReviewController();
        return single_instance;
    }

    // Constructor
    private ReviewController() {
        reviews = new HashMap<Integer, MovieReview>();
        load();
    }



    public void printReviews(ArrayList<MovieReview> reviews) {
        int i=0;

        for (MovieReview reviewPrint : reviews) {
            i++;

            System.out.println("---------------- REVIEW " + i + " ----------------");
            System.out.println(reviewPrint);
            System.out.println("--------------------------------------------------\n");
        }

        if (i==0) {
            System.out.println("No reviews found for this movie.");
        }
    }

    public void addReview(int movieID) {
        MovieReview newReview = new MovieReview();

        ////////////// INPUT VALIDATION NEEDED???

        // Is this not from login details?

//        System.out.println("Enter your name: ");
//        newReview.setUser(sc.nextLine());

//        System.out.println("Enter title of review: ");
//        newReview.setReviewTitle(sc.nextLine());
        System.out.println("Enter your name: ");
        newReview.setUsername(InputController.getUserString());

        System.out.println("Enter review: ");
        newReview.setComment(InputController.getUserString());

        System.out.println("Enter the number of stars between 0-5: ");
        newReview.setNumOfStars(InputController.getUserInt(0,5));

        int choice;

        do {
            AddReviewUI.printMenu();

            System.out.println("Your current review: ");
            System.out.println(newReview.toString());
            System.out.println("\nEnter choice: ");

            choice = InputController.getUserInt(0,2);

            switch (choice) {
                case 1:
                    newReview.setReviewId(MovieReview.requestId());
                    this.save(newReview);
                    MovieController.getInstance().addReview(movieID, newReview);

                    reviews.put(newReview.getReviewId(), newReview);

                    System.out.println("Review added! Back to Review Menu Options......");
                    choice = 0;
                    break;
                case 2:
                    this.editReview(newReview);
                    break;
                case 0:
                    System.out.println("Review discarded. Back to Movie Listing...");
                    break;
                default:
                    break;
            }
        } while (choice != 0);
    }

    public void deleteReview(ArrayList<MovieReview> reviews) {
        this.printReviews(reviews);
        if (reviews.size() == 0) {
            System.out.println("Back to movie listing...");
            return;
        };

        int choice;

        do {
            System.out.println("\nEnter review number to delete. Input 0 to go back to movie listing.");

            choice = InputController.getUserInt(0,reviews.size());

            if (choice == 0) {
                System.out.println("Back to movie listing...");
                return;
            }
            int reviewID = reviews.get(choice-1).getReviewId();
            MovieController.getInstance().removeReview(reviews.get(reviewID).getMovieId(), reviewID);

            String root = "";
            File file = new File(FilePathFinder.findRootPath() + "/src/data/reviews/review_" + reviewID + ".dat");
            file.delete();
            load();
            choice = 0;
        } while (choice != 0);
    }

    private void editReview(MovieReview review) {
        int choice;

        do {
            EditReviewUI.printMenu();

            choice = InputController.getUserInt(0,3);

            switch (choice) {
                case 1:
                    System.out.println("Enter new review comment: ");
                    review.setComment(InputController.getUserString());
                    break;
                case 2:
                    System.out.println("Enter the new number of stars between 0-5: ");
                    review.setNumOfStars(InputController.getUserInt(0,5));
                    break;
                case 3:
                    System.out.println("Enter your new name entry: ");
                    review.setUsername(InputController.getUserString());
                    break;
                case 0:
                    System.out.println("Back to Review Adding Menu...");
                    break;
                default:
                    break;
            }
        } while (choice != 0);
    }

    private void load() {
        File[] listOfFiles = new File[0];
        listOfFiles = new File(FilePathFinder.findRootPath() + "/src/data/reviews").listFiles();

        if(listOfFiles != null){
            for(int i=0;i<listOfFiles.length;i++){
                if (listOfFiles[i].getName().equalsIgnoreCase(".gitkeep")) continue;
                String path = listOfFiles[i].getPath(); // Returns full path incl file name and type
                MovieReview review = (MovieReview) DataSerializer.ObjectDeserializer(path);
                reviews.put(review.getReviewId(), review);
            }
        }
    }

    private void save(MovieReview review) {
        String path = FilePathFinder.findRootPath() + "/src/data/reviews/review_"+review.getReviewId()+".dat";
        DataSerializer.ObjectSerializer(path,review);
        System.out.println("Review updated!");
    }

}
