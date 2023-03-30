package persistence;

import model.Card;
import model.Player;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/nonExistentFile.json");
        try {
            Player player = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyHandHistory() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyHandHistory.json");
        try {
            Player player = reader.read();
            assertEquals("X", player.getName());
            assertEquals(1, player.getBalance());
            assertEquals(0, player.getHandHistory().size());
        } catch (IOException e) {
            fail("IOException unexpected");
        }
    }

    @Test
    void testReaderGeneralHandHistory() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralHandHistory.json");
        try {
            Player player = reader.read();
            assertEquals("X", player.getName());
            assertEquals(1, player.getBalance());
            assertEquals(1, player.getHandHistory().size());
            List<Card> hand = player.getHandHistory().get(0).getCards();
            assertEquals(2, hand.size());
            assertEquals(1, hand.get(0).getRank());
            assertEquals(1, hand.get(0).getSuit());
            assertEquals(13, hand.get(1).getRank());
            assertEquals(4, hand.get(1).getSuit());
        } catch (IOException e) {
            fail("IOException unexpected");
        }
    }
}
