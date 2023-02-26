package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for the Deck class
public class DeckTest {
    private Deck deck;

    @BeforeEach
    public void runBefore() {
        deck = new Deck();
    }

    @Test
    public void testGetDeck() {
        assertEquals(52, deck.getDeck().size());
    }

    @Test
    public void testDeal() {
        deck.deal();
        assertEquals(51, deck.getDeck().size());
    }

    @Test
    public void testReset() {
        deck.deal();
        deck.reset();
        assertEquals(52, deck.getDeck().size());
    }
}
