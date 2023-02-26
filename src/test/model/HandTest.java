package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for the Hand class
public class HandTest {
    private Hand hand;
    private Card card1;
    private Card card2;
    private Card card3;
    private Card card4;
    private Card card5;
    private Card card6;

    @BeforeEach
    public void runBefore() {
        hand = new Hand();
        card1 = new Card(1, 1);
        card2 = new Card(7, 3);
        card3 = new Card(13, 4);
        card4 = new Card(1, 2);
        card5 = new Card(1, 3);
        card6 = new Card(11, 3);
    }

    @Test
    public void testGetHand() {
        assertEquals(0, hand.getHand().size());
        hand.draw(card1);
        hand.draw(card2);
        assertEquals(2, hand.getHand().size());
        assertEquals(card1, hand.getHand().get(0));
        assertEquals(card2, hand.getHand().get(1));
    }

    @Test
    public void testGetValue() {
        assertEquals(0, hand.getValue());
        hand.draw(card1);
        hand.draw(card2);
        assertEquals(8, hand.getValue());
    }

    @Test
    public void testGetHandString() {
        assertEquals("(0)", hand.getHandString());
        hand.draw(card1);
        hand.draw(card2);
        assertEquals("AC 7H (8/18)", hand.getHandString());
    }

    @Test
    public void testDraw() {
        hand.draw(card1);
        hand.draw(card2);
        hand.draw(card3);
        assertEquals(card1, hand.getHand().get(0));
        assertEquals(card2, hand.getHand().get(1));
        assertEquals(card3, hand.getHand().get(2));
    }

    @Test
    public void testEmptyHand() {
        hand.draw(card1);
        hand.emptyHand();
        assertEquals(0, hand.getHand().size());
    }

    @Test
    public void testIsBusted() {
        assertFalse(hand.isBusted());
        hand.draw(card6);
        assertFalse(hand.isBusted());
        hand.draw(card2);
        assertFalse(hand.isBusted());
        hand.draw(card3);
        assertTrue(hand.isBusted());
    }

    @Test
    public void testHasBlackjack() {
        assertFalse(hand.hasBlackjack());
        hand.draw(card1);
        assertFalse(hand.hasBlackjack());
        hand.draw(card3);
        assertTrue(hand.hasBlackjack());
        hand.draw(card2);
        assertFalse(hand.hasBlackjack());
    }

    @Test
    public void has5CardCharlie() {
        assertFalse(hand.has5CardCharlie());
        hand.draw(card1);
        assertFalse(hand.has5CardCharlie());
        hand.draw(card2);
        assertFalse(hand.has5CardCharlie());
        hand.draw(card3);
        assertFalse(hand.has5CardCharlie());
        hand.draw(card4);
        assertFalse(hand.has5CardCharlie());
        hand.draw(card5);
        assertTrue(hand.has5CardCharlie());
    }

    @Test
    public void hasAce() {
        assertFalse(hand.hasAce());
        hand.draw(card1);
        assertTrue(hand.hasAce());
    }
}
