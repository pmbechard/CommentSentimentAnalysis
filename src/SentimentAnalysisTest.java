import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SentimentAnalysisTest {

    SentimentAnalysis sa = new SentimentAnalysis();

    @Test
    void getRatingTest() {
        // Empty
        assertEquals(0.0, sa.getRating(""));
        // Null Entry
        assertEquals(0.0, sa.getRating(null));
        // Single Word (positive)
        assertEquals(2.99, sa.getRating("actually"));
        // Single Word (negative)
        assertEquals(2.88, sa.getRating("ability"));
        // Single Word (not found)
        assertEquals(0.0, sa.getRating("the"));
        // Min (0) and Max (5) word scores range
        assertEquals(0.0, sa.getRating("ugly"));
        assertEquals(5.0, sa.getRating("serene"));
        // Sentence
        assertEquals(2.96, sa.getRating("adam actually ate the beans"));
        // Cleaning
        assertEquals(2.96, sa.getRating("ADAM actually, !aTe the BeAnS..."));
        assertEquals(2.42, sa.getRating("The meat was undercooked."));
    }
}