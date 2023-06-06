/**
 * Allows users to manage comments for a fictional product/service
 * and provides them with sentiment analysis ratings to evaluate
 * positivity/negativity in the comments.
 *
 * @author Peyton Bechard
 * @version 1.0.0
 * @since 6 June 2023
 */

import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class App {
    static SentimentAnalysis sa = new SentimentAnalysis();
    static CommentManager cm = new CommentManager();
    static Map<Integer, Runnable> COMMANDS = Map.of(
            0, () -> {
                System.out.println("Thanks for stopping by!");
                System.exit(0);
            },
            1, App::viewAllComments,
            2, App::viewCommentByIndex,
            3, App::addNewComment,
            4, App::removeComment,
            5, App::showMainMenu);


    /**
     * Starts the program loop
     */
    public static void main(String[] args) {
        showMainMenu();
    }

    /**
     * The Main Menu allows the user to navigate to different parts of the
     * interface to perform different operations.
     */
    public static void showMainMenu() {
        while (true) {
            System.out.println("\n----------MAIN MENU----------");
            System.out.println("Welcome to the comment and sentiment analysis system for MR BECHARD'S BURGERS.");
            int menuSelection = -1;
            do {
                System.out.println("Enter a number to choose one of the options below:");
                System.out.println("\t( 1 ) View all comments");
                System.out.println("\t( 2 ) View a specific comment");
                System.out.println("\t( 3 ) Add a new comment");
                System.out.println("\t( 4 ) Remove a comment");
                System.out.println("\t( 0 ) Exit program");

                try {
                    System.out.print("Your selection: ");
                    Scanner input = new Scanner(System.in);
                    menuSelection = input.nextInt();
                } catch (InputMismatchException e) {
                    menuSelection = -1;
                }

                if (menuSelection < 0 || menuSelection > 4) {
                    System.out.println("Invalid Selection. Try again.\n");
                }
            } while (menuSelection < 0 || menuSelection > 4);

            COMMANDS.get(menuSelection).run();
        }
    }

    /**
     * Shows all recorded comments, their individual sentiment analysis rating,
     * and an overall sentiment analysis rating for all comments. Users are able
     * to navigate back to the Main Menu from here.
     */
    public static void viewAllComments() {
        System.out.println("\n----------ALL COMMENTS----------");
        int counter = 1;
        double totalSentiment = 0;
        for (String comment : cm.getComments()) {
            System.out.println(counter++ + ". " + comment);
            double rating = sa.getRating(comment);
            System.out.println("Sentiment Rating: " + rating + "\n");
            totalSentiment += rating;
        }
        totalSentiment = Math.round((totalSentiment / counter) * 100) / 100.0;
        System.out.println("Overall Sentiment Rating: " + totalSentiment + "\n");
    }

    /**
     * Allows users to choose a specific comment by its index to see its content
     * and its individual sentiment analysis score. Users are able
     * to navigate back to the Main Menu from here.
     */
    public static void viewCommentByIndex() {
        System.out.println("\n----------VIEW COMMENT BY INDEX----------");
        if (cm.getCommentCount() == 0) {
            System.out.println("No comments.");
            return;
        }
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the number indicated on the comment you'd like to see (1 - " + cm.getCommentCount() +") or enter another character to return to the Main Menu: ");
        int index = -1;
        try {
            index = input.nextInt();
        } catch (InputMismatchException ignored) {}

        if (index >= 1 && index <= cm.getCommentCount()) {
            String comment = cm.getCommentAtIndex(index - 1);
            System.out.println(comment);
            System.out.println("Sentiment Rating: " + sa.getRating(comment) + "\n");
        } else {
            System.out.println("Returning to Main Menu...\n");
        }
    }

    /**
     * Allows users to enter a new comment, after which a sentiment analysis
     * rating is shown. Users can navigate back to the Main Menu from here.
     */
    public static void addNewComment() {
        System.out.println("\n----------ADD NEW COMMENT----------");
        Scanner input = new Scanner(System.in);
        System.out.println("Enter your comment below and press Enter to finish:");
        String newComment = input.nextLine();
        cm.addComment(newComment);
        System.out.println("\nAdding comment and returning to Main Menu...\n");
    }

    /**
     * Allows users to remove a comment specified by its index. Users can
     * navigate back to the Main Menu from here.
     */
    public static void removeComment() {
        System.out.println("\n----------REMOVE COMMENT----------");
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the index (1 - " + cm.getCommentCount() + ") of the comment you'd like to remove: ");
        int index = -1;
        try {
            index = input.nextInt();
        } catch (InputMismatchException ignore) {}

        if (index >= 1 && index <= cm.getCommentCount()) {
            System.out.println("You will delete the following comment: ");
            System.out.println(cm.getCommentAtIndex(index - 1));
            System.out.print("Press y to confirm: ");
            String confirmation = input.next();
            if (confirmation.toLowerCase().equals("y")) {
                System.out.println("Removing comment at index " + index+ "...");
                cm.removeCommentAtIndex(index - 1);
            }
        }

        System.out.println("\nReturning to Main Menu...\n");
    }
}