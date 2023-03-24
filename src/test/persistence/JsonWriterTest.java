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
            Player player = new Player("X", 1, 1,2);
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
            Player player = new Player("X", 1, 2,3);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyHand.json");
            writer.open();
            writer.write(player);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterEmptyHand.json");
            player = reader.read();
            assertEquals("X", player.getName());
            assertEquals(1, player.getBalance());
            assertEquals(2, player.getInitial());
            assertEquals(3, player.getGoal());
            assertEquals(0, player.getHands().size());
        } catch (IOException e) {
            fail("IOException unexpected");
        }
    }

    @Test
    void testWriterGeneralHand() {
        try {
            Player player = new Player("X", 1, 2,3);
            Hand hand0 = new Hand();
            hand0.addCard(new Card(1, 1));
            hand0.addCard(new Card(13, 4));
            player.addHand(hand0);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralHand.json");
            writer.open();
            writer.write(player);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterGeneralHand.json");
            player = reader.read();
            assertEquals("X", player.getName());
            assertEquals(1, player.getBalance());
            assertEquals(2, player.getInitial());
            assertEquals(3, player.getGoal());
            assertEquals(1, player.getHands().size());
            List<Card> hand = player.getHands().get(0).getCards();
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
