package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    private Player player1;
    private Player player2;
    private Hand hand;
    private Deck deck;

    @BeforeEach
    public void runBefore() {
        player1 = new Player("a", 100);
        player2 = new Player("b", 1);
        hand = new Hand();
        hand.addCard(new Card(1, 1));
        hand.addCard(new Card(7, 3));
        hand.addCard(new Card(13, 4));
        deck = new Deck();
    }

    @Test
    public void testGetName() {
        assertEquals("a", player1.getName());
        assertEquals("b", player2.getName());
    }

    @Test
    public void testGetBalance() {
        assertEquals(100, player1.getBalance());
        assertEquals(1, player2.getBalance());
    }

    @Test
    public void testGetAltHands() {
        assertNull(player1.getAltHand());
        assertNull(player2.getAltHand());
    }

    @Test
    public void testGetHandHistory() {
        assertEquals(0, player1.getHandHistory().size());
        assertEquals(0, player2.getHandHistory().size());
    }

    @Test
    public void testGetBalanceString() {
        assertEquals("a's balance: 100", player1.getBalanceString());
        assertEquals("b's balance: 1", player2.getBalanceString());
    }

    @Test
    public void testGetHandHistoryString() {
        player1.addHandHistory(hand);
        assertEquals("AC 7H KS (18) [0], ", player1.getHandHistoryString());
        assertEquals("", player2.getHandHistoryString());
    }

    @Test
    public void testSetAltHands() {
        player1.setAltHand(hand);
        assertEquals(hand, player1.getAltHand());
        player2.setAltHand(hand);
        assertEquals(hand, player2.getAltHand());
    }

    @Test
    public void testAddHandHistory() {
        player1.addHandHistory(hand);
        assertEquals(3, player1.getHandHistory().get(0).getCards().size());
        assertEquals(1, player1.getHandHistory().get(0).getCards().get(0).getRank());
        assertEquals(7, player1.getHandHistory().get(0).getCards().get(1).getRank());
        assertEquals(13, player1.getHandHistory().get(0).getCards().get(2).getRank());
        assertEquals(1, player1.getHandHistory().get(0).getCards().get(0).getSuit());
        assertEquals(3, player1.getHandHistory().get(0).getCards().get(1).getSuit());
        assertEquals(4, player1.getHandHistory().get(0).getCards().get(2).getSuit());
    }

    @Test
    public void testShuffleNullAltHand() {
        player1.setHand(hand);
        assertEquals(3, player1.getHand().getCards().size());
        assertNull(player1.getAltHand());
        assertEquals(0, player1.getHandHistory().size());
        player1.shuffle();
        assertEquals(0, player1.getHand().getCards().size());
        assertNull(player1.getAltHand());
        assertEquals(1, player1.getHandHistory().size());
        assertEquals(hand, player1.getHandHistory().get(0));
    }

    @Test
    public void testShuffleDefinedAltHand() {
        player1.setHand(hand);
        player1.setAltHand(hand);
        assertEquals(3, player1.getHand().getCards().size());
        assertEquals(hand, player1.getAltHand());
        assertEquals(0, player1.getHandHistory().size());
        player1.shuffle();
        assertEquals(0, player1.getHand().getCards().size());
        assertNull(player1.getAltHand());
        assertEquals(2, player1.getHandHistory().size());
        assertEquals(hand, player1.getHandHistory().get(0));
        assertEquals(hand, player1.getHandHistory().get(1));
    }

    @Test
    public void testDraw() {
        assertEquals(0, player1.getHand().getCards().size());
        assertEquals(52, deck.getCards().size());
        player1.draw(deck);
        assertEquals(1, player1.getHand().getCards().size());
        assertEquals(51, deck.getCards().size());
    }

    @Test
    public void testDrawAlt() {
        player1.setAltHand(new Hand());
        assertEquals(0, player1.getAltHand().getCards().size());
        assertEquals(52, deck.getCards().size());
        player1.drawAlt(deck);
        assertEquals(1, player1.getAltHand().getCards().size());
        assertEquals(51, deck.getCards().size());
    }

    @Test
    public void testAddBalance() {
        player1.addBalance(1);
        assertEquals(101, player1.getBalance());
        player2.addBalance(100);
        assertEquals(101, player2.getBalance());
    }

    @Test
    public void testSubBalance() {
        player1.subBalance(50);
        assertEquals(50, player1.getBalance());
        player2.subBalance(1);
        assertEquals(0, player2.getBalance());
    }
}
