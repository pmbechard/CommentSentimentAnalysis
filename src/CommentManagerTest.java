import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

class CommentManagerTest {
    static CommentManager cm;
    static ArrayList<String> originalComments;
    static ArrayList<String> testComments;

    @BeforeAll
    static void storeComments() {
        cm = new CommentManager();

        originalComments = new ArrayList<>();
        originalComments.addAll(cm.getComments());
        for (int i = cm.getCommentCount() - 1; i >= 0; i--)
            cm.removeCommentAtIndex(i);

        testComments = new ArrayList<>();
        testComments.add("The food is delicious and the atmosphere was beautiful!");
        testComments.add("The food was vile and the waitress was vile.");
        for (String comment : testComments)
            cm.addComment(comment);
    }

    @AfterAll
    static void restoreOriginalComments() {
        for (int i = cm.getCommentCount() - 1; i >= 0; i--)
            cm.removeCommentAtIndex(i);
        for (String comment : originalComments)
            cm.addComment(comment);
    }

    @Test
    void getCommentCount() {
        // With Comments
        assertEquals(2, cm.getCommentCount());

        // Without Comments
        cm.removeCommentAtIndex(1);
        cm.removeCommentAtIndex(0);
        assertEquals(0, cm.getCommentCount());
        cm.addComment("The food is delicious and the atmosphere was beautiful!");
        cm.addComment("The food was vile and the waitress was vile.");
    }

    @Test
    void getCommentsTest() {
        // With Comments
        assertEquals(testComments, cm.getComments());

        // Without Comments
        cm.removeCommentAtIndex(1);
        cm.removeCommentAtIndex(0);
        assertEquals(0, cm.getComments().size());
        cm.addComment("The food is delicious and the atmosphere was beautiful!");
        cm.addComment("The food was vile and the waitress was vile.");
    }

    @Test
    void getCommentAtIndexTest() {
        // Valid Indices
        assertEquals("The food is delicious and the atmosphere was beautiful!", cm.getCommentAtIndex(0));
        assertEquals("The food was vile and the waitress was vile.", cm.getCommentAtIndex(1));

        // Invalid Indices
        assertEquals("Invalid index", cm.getCommentAtIndex(-1));
        assertEquals("Invalid index", cm.getCommentAtIndex(2));
    }

    @Test
    void addCommentTest() {
        // Valid Comment
        cm.addComment("This is a new comment!");
        assertEquals(3, cm.getCommentCount());

        // Null Comment
        cm.addComment(null);
        assertEquals(3, cm.getCommentCount());
    }

    @Test
    void removeCommentAtIndex() {
        // Valid Indices
        cm.removeCommentAtIndex(2);
        assertEquals(2, cm.getCommentCount());

        // Invalid Indices
        cm.removeCommentAtIndex(2);
        assertEquals(2, cm.getCommentCount());
    }
}