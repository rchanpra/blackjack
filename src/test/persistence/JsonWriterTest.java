package persistence;

import model.Player;
import org.junit.jupiter.api.Test;

import java.io.IOException;

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
    void testWriterPlayer() {
        try {
            Player player = new Player("X", 10);
            JsonWriter writer = new JsonWriter("./data/testWriterPlayer.json");
            writer.open();
            writer.write(player);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterPlayer.json");
            player = reader.read();
            assertEquals("X", player.getName());
            assertEquals(10, player.getBalance());
        } catch (IOException e) {
            fail("IOException unexpected");
        }
    }
}
