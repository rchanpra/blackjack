package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for the Card class
public class CardTest {
    private Card card1;
    private Card card2;
    private Card card3;

    @BeforeEach
    public void runBefore() {
        card1 = new Card(1, 1);
        card2 = new Card(7, 3);
        card3 = new Card(13, 4);
    }

    @Test
    public void testGetRank() {
        assertEquals(1, card1.getRank());
        assertEquals(7, card2.getRank());
        assertEquals(13, card3.getRank());
    }

    @Test
    public void testGetSuit() {
        assertEquals(1, card1.getSuit());
        assertEquals(3, card2.getSuit());
        assertEquals(4, card3.getSuit());
    }

    @Test
    public void testGetCard() {
        assertEquals("AC", card1.getCard());
        assertEquals("7H", card2.getCard());
        assertEquals("KS", card3.getCard());
    }

    @Test
    public void testGetValue() {
        assertEquals(1, card1.getValue());
        assertEquals(7, card2.getValue());
        assertEquals(10, card3.getValue());
    }
}
