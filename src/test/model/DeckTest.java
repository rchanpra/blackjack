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
    public void testGetCards() {
        assertEquals(52, deck.getCards().size());
    }

    @Test
    public void testDeal() {
        deck.deal();
        assertEquals(51, deck.getCards().size());
    }

    @Test
    public void testShuffle() {
        deck.deal();
        deck.shuffle();
        assertEquals(52, deck.getCards().size());
    }
}
