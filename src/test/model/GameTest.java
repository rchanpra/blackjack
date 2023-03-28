package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    private static final String NAME = "";
    private static final int BALANCE = 10;
    private static final int STARTING = 1;
    private static final int GOAL = 100;
    private Game game;
    private Player player;

    @BeforeEach
    public void runBefore() {
        game = new Game();
        player = new Player(NAME, BALANCE, STARTING, GOAL);
    }

    @Test
    public void testGetDeck() {
        assertEquals(52, game.getDeck().getCards().size());
    }

    @Test
    public void testGetDealer() {
        assertEquals(0, game.getDealer().getHand().getCards().size());
    }

    @Test
    public void testGetPlayer() {
        assertNull(game.getPlayer());
    }

    @Test
    public void testSetPlayer() {
        game.setPlayer(player);
        assertEquals(player, game.getPlayer());
    }

    @Test
    public void testStartRound() {
        game.setPlayer(player);
        game.getPlayer().getHand().setBet(1);
        game.startRound();
        assertEquals(BALANCE - 1, game.getPlayer().getBalance());
    }

    @Test
    public void testDeal() {
        game.setPlayer(player);
        assertEquals(0, game.getPlayer().getHand().getCards().size());
        assertEquals(0, game.getDealer().getHand().getCards().size());
        game.deal();
        assertEquals(2, game.getPlayer().getHand().getCards().size());
        assertEquals(2, game.getDealer().getHand().getCards().size());
    }

    @Test
    public void testShuffle() {
        game.setPlayer(player);
        game.deal();
        game.shuffle();
        assertEquals(0, game.getPlayer().getHand().getCards().size());
        assertEquals(0, game.getDealer().getHand().getCards().size());
        assertEquals(0, game.getPlayer().getHand().getBet());
    }

    @Test
    public void testGameEnd() {
        game.setPlayer(player);
        assertFalse(game.gameEnd());
        game.getPlayer().subBalance(BALANCE);
        assertTrue(game.gameEnd());
        game.getPlayer().addBalance(GOAL - BALANCE - 1);
        assertFalse(game.gameEnd());
        game.getPlayer().addBalance(GOAL - BALANCE);
        assertTrue(game.gameEnd());
        game.getPlayer().addBalance(GOAL - BALANCE + 1);
        assertTrue(game.gameEnd());
    }

    @Test
    public void testPayoutBlackjack() {
        game.setPlayer(player);
        Hand blackjack = new Hand();
        blackjack.addCard(new Card(1, 1));
        blackjack.addCard(new Card(10, 1));
        Hand nonBlackjack = new Hand();
        nonBlackjack.addCard(new Card(1, 3));
        nonBlackjack.addCard(new Card(1, 4));
        game.shuffle();
        game.getPlayer().setHand(blackjack);
        game.getDealer().setHand(blackjack);
        game.getPlayer().getHand().setBet(1);
        game.getPlayer().subBalance(1);
        game.payout(game.getPlayer().getHand());
        assertEquals(BALANCE, game.getPlayer().getBalance());
        game.shuffle();
        game.getPlayer().setHand(blackjack);
        game.getDealer().setHand(nonBlackjack);
        game.getPlayer().getHand().setBet(1);
        game.getPlayer().subBalance(1);
        game.payout(game.getPlayer().getHand());
        assertEquals(BALANCE + 1, game.getPlayer().getBalance());
        game.getPlayer().subBalance(1);
        game.shuffle();
        game.getPlayer().setHand(nonBlackjack);
        game.getDealer().setHand(blackjack);
        game.getPlayer().getHand().setBet(1);
        game.getPlayer().subBalance(1);
        game.payout(game.getPlayer().getHand());
        assertEquals(BALANCE - 1, game.getPlayer().getBalance());
    }

    @Test
    public void testPayout5CardCharlie() {
        game.setPlayer(player);
        Hand fiveCardCharlie = new Hand();
        fiveCardCharlie.addCard(new Card(1, 1));
        fiveCardCharlie.addCard(new Card(1, 2));
        fiveCardCharlie.addCard(new Card(2, 1));
        fiveCardCharlie.addCard(new Card(2, 2));
        fiveCardCharlie.addCard(new Card(2, 3));
        Hand non5CardCharlie = new Hand();
        non5CardCharlie.addCard(new Card(1, 3));
        non5CardCharlie.addCard(new Card(1, 4));
        game.shuffle();
        game.getPlayer().setHand(fiveCardCharlie);
        game.getDealer().setHand(fiveCardCharlie);
        game.getPlayer().getHand().setBet(1);
        game.getPlayer().subBalance(1);
        game.payout(game.getPlayer().getHand());
        assertEquals(BALANCE, game.getPlayer().getBalance());
        game.shuffle();
        game.getPlayer().setHand(fiveCardCharlie);
        game.getDealer().setHand(non5CardCharlie);
        game.getPlayer().getHand().setBet(1);
        game.getPlayer().subBalance(1);
        game.payout(game.getPlayer().getHand());
        assertEquals(BALANCE + 1, game.getPlayer().getBalance());
        game.getPlayer().subBalance(1);
        game.shuffle();
        game.getPlayer().setHand(non5CardCharlie);
        game.getDealer().setHand(fiveCardCharlie);
        game.getPlayer().getHand().setBet(1);
        game.getPlayer().subBalance(1);
        game.payout(game.getPlayer().getHand());
        assertEquals(BALANCE - 1, game.getPlayer().getBalance());
    }

    @Test
    public void testPayoutBusted() {
        game.setPlayer(player);
        Hand bustedHand = new Hand();
        bustedHand.addCard(new Card(13, 1));
        bustedHand.addCard(new Card(13, 2));
        bustedHand.addCard(new Card(2, 3));
        Hand nonBustedHand = new Hand();
        nonBustedHand.addCard(new Card(1, 1));
        nonBustedHand.addCard(new Card(1, 2));
        game.shuffle();
        game.getPlayer().setHand(bustedHand);
        game.getDealer().setHand(bustedHand);
        game.getPlayer().getHand().setBet(1);
        game.getPlayer().subBalance(1);
        game.payout(game.getPlayer().getHand());
        assertEquals(BALANCE, game.getPlayer().getBalance());
        game.shuffle();
        game.getPlayer().setHand(nonBustedHand);
        game.getDealer().setHand(bustedHand);
        game.getPlayer().getHand().setBet(1);
        game.getPlayer().subBalance(1);
        game.payout(game.getPlayer().getHand());
        assertEquals(BALANCE + 1, game.getPlayer().getBalance());
        game.getPlayer().subBalance(1);
        game.shuffle();
        game.getPlayer().setHand(bustedHand);
        game.getDealer().setHand(nonBustedHand);
        game.getPlayer().getHand().setBet(1);
        game.getPlayer().subBalance(1);
        game.payout(game.getPlayer().getHand());
        assertEquals(BALANCE - 1, game.getPlayer().getBalance());
    }

    @Test
    public void testPayoutValue() {
        game.setPlayer(player);
        Hand greaterHand = new Hand();
        greaterHand.addCard(new Card(1, 1));
        greaterHand.addCard(new Card(1, 2));
        Hand lesserHand = new Hand();
        lesserHand.addCard(new Card(2, 1));
        lesserHand.addCard(new Card(2, 2));
        game.shuffle();
        game.getPlayer().setHand(greaterHand);
        game.getDealer().setHand(greaterHand);
        game.getPlayer().getHand().setBet(1);
        game.getPlayer().subBalance(1);
        game.payout(game.getPlayer().getHand());
        assertEquals(BALANCE, game.getPlayer().getBalance());
        game.getPlayer().setHand(greaterHand);
        game.getDealer().setHand(lesserHand);
        game.getPlayer().getHand().setBet(1);
        game.getPlayer().subBalance(1);
        game.payout(game.getPlayer().getHand());
        assertEquals(BALANCE + 1, game.getPlayer().getBalance());
        game.getPlayer().subBalance(1);
        game.shuffle();
        game.getPlayer().setHand(lesserHand);
        game.getDealer().setHand(greaterHand);
        game.getPlayer().getHand().setBet(1);
        game.getPlayer().subBalance(1);
        game.payout(game.getPlayer().getHand());
        assertEquals(BALANCE - 1, game.getPlayer().getBalance());
    }

    @Test
    public void testPlayerFirstTurn() {
        game.setPlayer(player);
        game.shuffle();
        game.deal();
        game.getPlayer().getHand().setBet(1);
        game.playerFirstTurn("");
        assertEquals(2, game.getPlayer().getHand().getCards().size());
        assertNull(game.getPlayer().getAltHand());
        assertEquals(1, game.getPlayer().getHand().getBet());
        game.shuffle();
        game.deal();
        game.getPlayer().getHand().setBet(1);
        game.playerFirstTurn("h");
        assertEquals(3, game.getPlayer().getHand().getCards().size());
        assertNull(game.getPlayer().getAltHand());
        assertEquals(1, game.getPlayer().getHand().getBet());
        game.shuffle();
        game.deal();
        game.getPlayer().getHand().setBet(1);
        game.playerFirstTurn("s");
        assertEquals(2, game.getPlayer().getHand().getCards().size());
        assertNull(game.getPlayer().getAltHand());
        assertEquals(1, game.getPlayer().getHand().getBet());
        game.shuffle();
        game.deal();
        game.getPlayer().getHand().setBet(1);
        game.playerFirstTurn("d");
        assertEquals(3, game.getPlayer().getHand().getCards().size());
        assertNull(game.getPlayer().getAltHand());
        assertEquals(2, game.getPlayer().getHand().getBet());
        game.shuffle();
        game.deal();
        game.getPlayer().getHand().setBet(1);
        Hand hand = new Hand();
        hand.addCard(new Card(1, 1));
        hand.addCard(new Card(1, 2));
        game.playerFirstTurn("sp");
        assertEquals(2, game.getPlayer().getHand().getCards().size());
        assertEquals(2, game.getPlayer().getAltHand().getCards().size());
        assertEquals(1, game.getPlayer().getHand().getBet());
        assertEquals(1, game.getPlayer().getAltHand().getBet());
        game.shuffle();
        game.deal();
        game.getPlayer().getHand().setBet(2);
        game.playerFirstTurn("su");
        assertEquals(2, game.getPlayer().getHand().getCards().size());
        assertNull(game.getPlayer().getAltHand());
        assertEquals(1, game.getPlayer().getHand().getBet());
        game.shuffle();
        game.deal();
        game.getPlayer().getHand().setBet(3);
        game.playerFirstTurn("su");
        assertEquals(2, game.getPlayer().getHand().getCards().size());
        assertNull(game.getPlayer().getAltHand());
        assertEquals(1, game.getPlayer().getHand().getBet());
    }

    @Test
    public void testPlayerRestTurn() {
        game.setPlayer(player);
        game.shuffle();
        game.deal();
        game.getPlayer().getHand().draw(game.getDeck());
        game.getPlayer().getHand().setBet(1);
        game.playerRestTurn(game.getPlayer().getHand(), "");
        assertEquals(3, game.getPlayer().getHand().getCards().size());
        assertEquals(1, game.getPlayer().getHand().getBet());
        game.shuffle();
        game.deal();
        game.getPlayer().getHand().draw(game.getDeck());
        game.getPlayer().getHand().setBet(1);
        game.playerRestTurn(game.getPlayer().getHand(), "h");
        assertEquals(4, game.getPlayer().getHand().getCards().size());
        assertEquals(1, game.getPlayer().getHand().getBet());
        game.shuffle();
        game.deal();
        game.getPlayer().getHand().draw(game.getDeck());
        game.getPlayer().getHand().setBet(1);
        game.playerRestTurn(game.getPlayer().getHand(), "s");
        assertEquals(3, game.getPlayer().getHand().getCards().size());
        assertEquals(1, game.getPlayer().getHand().getBet());
        game.shuffle();
        game.deal();
        game.getPlayer().getHand().draw(game.getDeck());
        game.getPlayer().getHand().setBet(1);
        game.playerRestTurn(game.getPlayer().getHand(), "d");
        assertEquals(4, game.getPlayer().getHand().getCards().size());
        assertEquals(2, game.getPlayer().getHand().getBet());
    }

    @Test
    public void testJson() {
        String destination = "./data/testJsonGame.json";
        game.setPlayer(player);
        game.setJsonWriterDestination(destination);
        game.setJsonReaderDestination(destination);
        game.savePlayer();
        assertTrue(game.loadPlayer());
    }
}
