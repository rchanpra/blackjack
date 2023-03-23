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
    void testReaderEmptyHand() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyHand.json");
        try {
            Player player = reader.read();
            assertEquals("X", player.getName());
            assertEquals(10, player.getBalance());
            assertEquals(0, player.getHand().getCards().size());
        } catch (IOException e) {
            fail("IOException unexpected");
        }
    }

    @Test
    void testReaderGeneralHand() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralHand.json");
        try {
            Player player = reader.read();
            assertEquals("X", player.getName());
            assertEquals(10, player.getBalance());
            List<Card> hand = player.getHand().getCards();
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
