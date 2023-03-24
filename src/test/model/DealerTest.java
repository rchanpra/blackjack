package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for the Dealer class
public class DealerTest {
    private Dealer dealer;
    private Card card1;
    private Card card2;

    @BeforeEach
    public void runBefore() {
        dealer = new Dealer();
        card1 = new Card(1, 1);
        card2 = new Card(7, 3);
    }

    @Test
    public void testGetInitialHandString() {
        dealer.addCard(card1);
        dealer.addCard(card2);
        assertEquals("XX AC (1/11+)", dealer.getInitialHandString());
        dealer.reset();
        dealer.addCard(card2);
        dealer.addCard(card1);
        assertEquals("XX 7H (7+)", dealer.getInitialHandString());
    }
}
