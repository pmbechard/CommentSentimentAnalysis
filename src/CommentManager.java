import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CommentManager {
    private ArrayList<String> comments;
    private File commentsFile;
    private Scanner sc;

    /**
     * Checks for the existence of a comments.txt file and creates one if not found.
     * Initializes Scanner sc for reading data.
     * Retrieves data from the comments.txt file and stores the data as elements in comments ArrayList.
     */
    public CommentManager() {
        commentsFile = new File("comments.txt");
        if (!commentsFile.exists()) {
            try {
                boolean created = commentsFile.createNewFile();
                comments = new ArrayList<>();
                sc = new Scanner(commentsFile);
            } catch (IOException e) {
                System.out.println("Error creating comment storage: " + e);
            }
        } else {
            try {
                sc = new Scanner(commentsFile);
                comments = new ArrayList<>();
            } catch (FileNotFoundException e) {
                System.out.println("Comment file not found.");
            }
            while (sc.hasNextLine()) {
                comments.add(sc.nextLine());
            }
        }
    }

    /**
     * Provides an ArrayList of all comments.
     * @return ArrayList - All saved comments.
     */
    public ArrayList<String> getComments() {
        return comments;
    }

    /**
     * Commits a new comment to the comment list and adds it to the text file for persistence.
     * @param comment - Comment to be added.
     */
    public void addComment(String comment) {
        if (comment == null) return;
        comments.add(comment);
        updateStoredComments();
    }

    /**
     * Retrieves the individual comment at a specified index.
     * @param index - The index of the comment to be retrieved.
     * @return String comment at the index specified in the index argument.
     */
    public String getCommentAtIndex(int index) {
        if (index < 0 || index >= getCommentCount()) return "Invalid index";
        return comments.get(index);
    }

    /**
     * Removes the comment at the specified index from the ArrayList comments and the comments.txt file.
     * @param index - Index of the comment to be removed.
     */
    public void removeCommentAtIndex(int index) {
        if (index > comments.size() - 1) return;
        comments.remove(index);
        updateStoredComments();
    }

    /**
     * Returns the number of saved comments.
     * @return int the number of comments.
     */
    public int getCommentCount() {
        return comments.size();
    }

    /**
     * Writes the data stored in the ArrayList comments to the comments.txt file.
     */
    private void updateStoredComments() {
        boolean deleted = commentsFile.delete();
        try {
            boolean created = commentsFile.createNewFile();
        } catch (IOException e) {
            System.out.println("Error creating new file: " + e);
        }

        try {
            FileWriter fw = new FileWriter("comments.txt");
            for (String comment : comments)
                fw.write(comment + "\n");
            fw.close();
        } catch (IOException e) {
            System.out.println("Error writing to new file: " + e);
        }
    }
}
