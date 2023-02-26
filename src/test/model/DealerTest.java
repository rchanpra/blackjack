package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for the Dealer class
public class DealerTest {
    private Dealer dealer;
    private Card card1;
    private Card card2;
    private Card card3;

    @BeforeEach
    public void runBefore() {
        dealer = new Dealer();
        card1 = new Card(1, 1);
        card2 = new Card(7, 3);
        card3 = new Card(13, 4);
    }

    @Test
    public void testGetInitialHandString() {
        dealer.draw(card1);
        dealer.draw(card2);
        assertEquals("XX AC (1/11)", dealer.getInitialHandString());
    }
}
