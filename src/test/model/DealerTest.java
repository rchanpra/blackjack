package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for the Dealer class
public class DealerTest {
    private Dealer dealer;
    private Deck deck;

    @BeforeEach
    public void runBefore() {
        dealer = new Dealer();
        deck = new Deck();
    }

    @Test
    public void testGetInitialHandString() {
        dealer.getHand().addCard(new Card(1, 1));
        dealer.getHand().addCard(new Card(7, 4));
        assertEquals("XX AC (1/11+)", dealer.getInitialHandString());
        dealer.shuffle();
        dealer.getHand().addCard(new Card(7, 4));
        dealer.getHand().addCard(new Card(1, 1));
        assertEquals("XX 7S (7+)", dealer.getInitialHandString());
    }

    @Test
    public void testCanDraw() {
        dealer.getHand().addCard(new Card(1, 1));
        dealer.getHand().addCard(new Card(1, 4));
        assertTrue(dealer.canDraw());
        dealer.setHand(new Hand());
        dealer.getHand().addCard(new Card(13, 4));
        dealer.getHand().addCard(new Card(13, 1));
        dealer.getHand().addCard(new Card(1, 1));
        assertFalse(dealer.canDraw());
        dealer.setHand(new Hand());
        dealer.getHand().addCard(new Card(7, 4));
        dealer.getHand().addCard(new Card(13, 1));
        assertFalse(dealer.canDraw());
        dealer.setHand(new Hand());
        dealer.getHand().addCard(new Card(2, 4));
        dealer.getHand().addCard(new Card(3, 1));
        assertTrue(dealer.canDraw());
        dealer.setHand(new Hand());
        dealer.getHand().addCard(new Card(1, 1));
        dealer.getHand().addCard(new Card(1, 2));
        dealer.getHand().addCard(new Card(1, 3));
        dealer.getHand().addCard(new Card(1, 4));
        dealer.getHand().addCard(new Card(2, 1));
        assertFalse(dealer.canDraw());
    }

    @Test
    public void testShuffle() {
        dealer.getHand().addCard(new Card(1, 1));
        dealer.shuffle();
        assertEquals(0, dealer.getHand().getCards().size());
    }

    @Test
    public void testDraw() {
        assertEquals(0, dealer.getHand().getCards().size());
        assertEquals(52, deck.getCards().size());
        dealer.draw(deck);
        assertEquals(1, dealer.getHand().getCards().size());
        assertEquals(51, deck.getCards().size());
    }
}
