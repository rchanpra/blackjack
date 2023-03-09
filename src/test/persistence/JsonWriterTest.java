package persistence;

import model.Card;
import model.Hand;
import model.Player;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonWriterTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Player player = new Player("X", 10);
            JsonWriter writer = new JsonWriter("./data/<invalidFile>.json");
            writer.open();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyHand() {
        try {
            Player player = new Player("X", 10);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyHand.json");
            writer.open();
            writer.write(player);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterEmptyHand.json");
            player = reader.read();
            assertEquals("X", player.getName());
            assertEquals(10, player.getBalance());
            assertEquals(0, player.getHand().getCards().size());
        } catch (IOException e) {
            fail("IOException unexpected");
        }
    }

    @Test
    void testWriterGeneralHand() {
        try {
            Player player = new Player("X", 10);
            Hand playerHand = new Hand();
            playerHand.draw(new Card(1, 1));
            playerHand.draw(new Card(13, 4));
            player.setHand(playerHand);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralHand.json");
            writer.open();
            writer.write(player);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterGeneralHand.json");
            player = reader.read();
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
