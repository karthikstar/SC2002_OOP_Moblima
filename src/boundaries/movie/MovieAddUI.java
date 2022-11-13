package boundaries.movie;

public class MovieAddUI extends MovieUI{
    public static void printMenu() {
        System.out.println(
                generateTitleWithLines("ADD MOVIE") +
                " 1. Submit movie                                      \n" +
                " 2. Edit movie                                        \n" +
                " 0. Discard movie, back to Movie Menu                 \n"+
                generateFullLine());
        promptUserChoice();
    }
}
