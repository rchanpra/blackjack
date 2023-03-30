package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {
    private Person player;
    private Person dealer;
    private Hand hand;
    private Card card1;
    private Card card2;


    @BeforeEach
    public void runBefore() {
        player = new Player("", 1);
        dealer = new Dealer();
        hand = new Hand();
        card1 = new Card(1, 1);
        card2 = new Card(13, 4);
        hand.addCard(card1);
        hand.addCard(card2);
    }

    @Test
    public void getHand() {
        assertEquals(0, player.getHand().getCards().size());
        assertEquals(0, dealer.getHand().getCards().size());
    }

    @Test
    public void setHand() {
        player.setHand(hand);
        assertEquals(2, player.getHand().getCards().size());
        assertEquals(card1, player.getHand().getCards().get(0));
        assertEquals(card2, player.getHand().getCards().get(1));
    }
}
