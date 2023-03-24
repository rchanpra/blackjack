package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for the Player class
public class PlayerTest {
    private Player player1;
    private Player player2;
    private Hand hand;

    @BeforeEach
    public void runBefore() {
        player1 = new Player("a", 100, 10, 1000);
        player2 = new Player("b", 1, 2, 3);
        hand = new Hand();
        hand.addCard(new Card(1, 1));
        hand.addCard(new Card(7, 3));
        hand.addCard(new Card(13, 4));
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
    public void testGetInitial() {
        assertEquals(10, player1.getInitial());
        assertEquals(2, player2.getInitial());
    }

    @Test
    public void testGetGoal() {
        assertEquals(1000, player1.getGoal());
        assertEquals(3, player2.getGoal());
    }

    @Test
    public void testGetHands() {
        assertEquals(0, player1.getHands().size());
        assertEquals(0, player2.getHands().size());
    }

    @Test
    public void testGetBalanceString() {
        assertEquals("a's balance: 100", player1.getBalanceString());
        assertEquals("b's balance: 1", player2.getBalanceString());
    }

    @Test
    public void testGetHandsString() {
        player1.addHand(hand);
        assertEquals("AC 7H KS (18), ", player1.getHandsString());
        assertEquals("", player2.getHandsString());
    }

    @Test
    public void testAddHand() {
        player1.addHand(hand);
        assertEquals(3, player1.getHands().get(0).getCards().size());
        assertEquals(1, player1.getHands().get(0).getCards().get(0).getRank());
        assertEquals(7, player1.getHands().get(0).getCards().get(1).getRank());
        assertEquals(13, player1.getHands().get(0).getCards().get(2).getRank());
        assertEquals(1, player1.getHands().get(0).getCards().get(0).getSuit());
        assertEquals(3, player1.getHands().get(0).getCards().get(1).getSuit());
        assertEquals(4, player1.getHands().get(0).getCards().get(2).getSuit());
    }

    @Test
    public void testResetHands() {
        player1.resetHands();
        assertEquals(1, player1.getHands().size());
        assertEquals(0, player1.getHands().get(0).getCards().size());
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
