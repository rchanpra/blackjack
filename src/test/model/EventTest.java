package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class EventTest {
    private static final int HASH_CONSTANT = 13;
    private Event event;
    private Date date;
    private String description;

    @BeforeEach
    public void runBefore() {
        description = "Sensor open at door";
        event = new Event(description);
        date = Calendar.getInstance().getTime();
    }

    @Test
    public void testEvent() {
        assertEquals(description, event.getDescription());
        assertEquals(date.toString(), event.getDate().toString());
    }

    @Test
    public void testEqualsFalse() {
        assertFalse(event.equals(null));
        assertFalse(event.equals(date));
        assertTrue(event.equals(event));
    }

    @Test
    public void testHashCode() {
        assertEquals(HASH_CONSTANT * date.hashCode() + description.hashCode(), event.hashCode());
    }

    @Test
    public void testToString() {
        assertEquals(date.toString() + "\n" + description, event.toString());
    }
}
