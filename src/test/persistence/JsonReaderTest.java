package persistence;

import model.Player;
import org.junit.jupiter.api.Test;

import java.io.IOException;

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
    void testReaderPlayer() {
        JsonReader reader = new JsonReader("./data/testReaderPlayer.json");
        try {
            Player player = reader.read();
            assertEquals("X", player.getName());
            assertEquals(10, player.getBalance());
        } catch (IOException e) {
            fail("IOException unexpected");
        }
    }
}
