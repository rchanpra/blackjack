package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HandTest {
    private Hand hand;
    private Card card1;
    private Card card2;
    private Card card3;
    private Card card4;
    private Card card5;
    private Card card6;
    private Card card7;

    @BeforeEach
    public void runBefore() {
        hand = new Hand();
        card1 = new Card(1, 1);
        card2 = new Card(7, 3);
        card3 = new Card(13, 4);
        card4 = new Card(1, 2);
        card5 = new Card(1, 3);
        card6 = new Card(11, 3);
        card7 = new Card(4, 2);
    }

    @Test
    public void testGetCards() {
        assertEquals(0, hand.getCards().size());
        hand.addCard(card1);
        hand.addCard(card2);
        assertEquals(2, hand.getCards().size());
        assertEquals(card1, hand.getCards().get(0));
        assertEquals(card2, hand.getCards().get(1));
    }

    @Test
    public void testGetBet() {
        assertEquals(0, hand.getBet());
    }

    @Test
    public void testSetBet() {
        hand.setBet(1);
        assertEquals(1, hand.getBet());
    }

    @Test
    public void testGetValue() {
        assertEquals(0, hand.getValue());
        hand.addCard(card1);
        hand.addCard(card2);
        assertEquals(18, hand.getValue());
    }

    @Test
    public void testGetValueString() {
        assertEquals("0", hand.getValueString());
        hand.addCard(card1);
        hand.addCard(card2);
        assertEquals("8/18", hand.getValueString());
        hand.addCard(card3);
        assertEquals("18", hand.getValueString());
    }

    @Test
    public void testGetString() {
        assertEquals("(0)", hand.getString());
        hand.addCard(card1);
        hand.addCard(card2);
        assertEquals("AC 7H (8/18)", hand.getString());
        hand.addCard(card3);
        assertEquals("AC 7H KS (18)", hand.getString());
    }

    @Test
    public void testAddCard() {
        hand.addCard(card1);
        hand.addCard(card2);
        hand.addCard(card3);
        assertEquals(card1, hand.getCards().get(0));
        assertEquals(card2, hand.getCards().get(1));
        assertEquals(card3, hand.getCards().get(2));
    }

    @Test
    public void testSplit() {
        hand.addCard(card1);
        hand.addCard(card3);
        Hand newHand = hand.split();
        assertEquals(1, newHand.getCards().size());
        assertEquals(1, hand.getCards().size());
        assertEquals(card1, hand.getCards().get(0));
        assertEquals(card3, newHand.getCards().get(0));
    }

    @Test
    public void testIsBusted() {
        assertFalse(hand.isBusted());
        hand.addCard(card6);
        assertFalse(hand.isBusted());
        hand.addCard(card2);
        assertFalse(hand.isBusted());
        hand.addCard(card3);
        assertTrue(hand.isBusted());
    }

    @Test
    public void testHasAce() {
        assertFalse(hand.hasAce());
        hand.addCard(card2);
        assertFalse(hand.hasAce());
        hand.addCard(card1);
        assertTrue(hand.hasAce());
    }

    @Test
    public void testHasBlackjack() {
        assertFalse(hand.hasBlackjack());
        hand.addCard(card1);
        assertFalse(hand.hasBlackjack());
        hand.addCard(card3);
        assertTrue(hand.hasBlackjack());
        hand.addCard(card2);
        assertFalse(hand.hasBlackjack());
        hand = new Hand();
        assertFalse(hand.hasBlackjack());
        hand.addCard(card7);
        assertFalse(hand.hasBlackjack());
        hand.addCard(card2);
        assertFalse(hand.hasBlackjack());
        hand.addCard(card1);
        assertFalse(hand.hasBlackjack());
        hand = new Hand();
        assertFalse(hand.hasBlackjack());
        hand.addCard(card1);
        assertFalse(hand.hasBlackjack());
        hand.addCard(card2);
        assertFalse(hand.hasBlackjack());
        hand.addCard(card3);
        assertFalse(hand.hasBlackjack());
    }

    @Test
    public void testHas5CardCharlie() {
        assertFalse(hand.has5CardCharlie());
        hand.addCard(card1);
        assertFalse(hand.has5CardCharlie());
        hand.addCard(card2);
        assertFalse(hand.has5CardCharlie());
        hand.addCard(card3);
        assertFalse(hand.has5CardCharlie());
        hand.addCard(card4);
        assertFalse(hand.has5CardCharlie());
        hand.addCard(card5);
        assertTrue(hand.has5CardCharlie());
        hand = new Hand();
        assertFalse(hand.has5CardCharlie());
        hand.addCard(card1);
        assertFalse(hand.has5CardCharlie());
        hand.addCard(card2);
        assertFalse(hand.has5CardCharlie());
        hand.addCard(card3);
        assertFalse(hand.has5CardCharlie());
        hand.addCard(card4);
        assertFalse(hand.has5CardCharlie());
        hand.addCard(card6);
        assertFalse(hand.has5CardCharlie());
    }

    @Test
    public void testCanDraw() {
        assertTrue(hand.canDraw());
        hand.addCard(card6);
        assertTrue(hand.canDraw());
        hand.addCard(card2);
        assertTrue(hand.canDraw());
        hand.addCard(card3);
        assertFalse(hand.canDraw());
        hand = new Hand();
        assertTrue(hand.canDraw());
        hand.addCard(card1);
        assertTrue(hand.canDraw());
        hand.addCard(card2);
        assertTrue(hand.canDraw());
        hand.addCard(card3);
        assertTrue(hand.canDraw());
        hand.addCard(card4);
        assertTrue(hand.canDraw());
        hand.addCard(card5);
        assertFalse(hand.canDraw());
        hand = new Hand();
        assertTrue(hand.canDraw());
        hand.addCard(card1);
        assertTrue(hand.canDraw());
        hand.addCard(card2);
        assertTrue(hand.canDraw());
        hand.addCard(card3);
        assertTrue(hand.canDraw());
        hand.addCard(card4);
        assertTrue(hand.canDraw());
        hand.addCard(card6);
        assertFalse(hand.canDraw());
    }

    @Test
    public void testCanSplit() {
        assertFalse(hand.canSplit());
        hand.addCard(card4);
        assertFalse(hand.canSplit());
        hand.addCard(card5);
        assertTrue(hand.canSplit());
        hand.addCard(card1);
        assertFalse(hand.canSplit());
        hand = new Hand();
        hand.addCard(card1);
        hand.addCard(card2);
        assertFalse(hand.canSplit());
    }
}
