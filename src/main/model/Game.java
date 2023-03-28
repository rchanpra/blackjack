package model;

import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;

// Represents a game of blackjack
public class Game {
    private static final String JSON_STORE = "./data/blackjack.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private Deck deck;
    private Dealer dealer;
    private Player player;

    // EFFECTS: initializes all fields except player
    public Game() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        deck = new Deck();
        dealer = new Dealer();
    }

    // EFFECTS: returns deck
    public Deck getDeck() {
        return deck;
    }

    // EFFECTS: returns deck
    public Dealer getDealer() {
        return dealer;
    }

    // EFFECTS: gets player
    public Player getPlayer() {
        return player;
    }

    // EFFECTS: sets player with parameter
    public void setPlayer(Player player) {
        this.player = player;
    }

    // EFFECTS: starts round
    public void startRound() {
        player.subBalance(player.getHand().getBet());
        deal();
    }

    // MODIFIES: this
    // EFFECTS: collects cards and shuffles deck
    public void deal() {
        player.getHand().draw(deck);
        dealer.getHand().draw(deck);
        player.getHand().draw(deck);
        dealer.getHand().draw(deck);
    }

    // MODIFIES: this
    // EFFECTS: collects cards and shuffles deck
    public void shuffle() {
        player.shuffle();
        dealer.shuffle();
        deck.shuffle();
    }

    // EFFECTS: returns true if player is broke or reaches goal else false
    public boolean gameEnd() {
        return player.getBalance() <= 0 || player.getBalance() >= player.getGoal();
    }

    // MODIFIES: this
    // EFFECTS: determines round outcome for player hand and proceeds with payouts
    public void payout(Hand hand) {
        if (hand.hasBlackjack()) {
            if (dealer.getHand().hasBlackjack()) {
                player.addBalance(hand.getBet());
            } else {
                player.addBalance(hand.getBet() * 2);
            }
        } else if (hand.has5CardCharlie()) {
            if (dealer.getHand().has5CardCharlie()) {
                player.addBalance(hand.getBet());
            } else {
                player.addBalance(hand.getBet() * 2);
            }
        } else if (!hand.isBusted()) {
            if (!dealer.getHand().isBusted()) {
                if (hand.getCardsValue() > dealer.getHand().getCardsValue()) {
                    player.addBalance(hand.getBet() * 2);
                } else if (hand.getCardsValue() == dealer.getHand().getCardsValue()) {
                    player.addBalance(hand.getBet());
                }
            } else {
                player.addBalance(hand.getBet() * 2);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: runs first turn of player
    public void playerFirstTurn(String decision) {
        switch (decision) {
            case "h":
                player.getHand().addCard(deck.deal());
                break;
            case "s": break;
            case "d":
                player.subBalance(player.getHand().getBet());
                player.getHand().setBet(player.getHand().getBet() * 2);
                player.getHand().addCard(deck.deal());
                break;
            case "sp":
                player.subBalance(player.getHand().getBet());
                player.setAltHand(player.getHand().split());
                player.getHand().addCard(deck.deal());
                player.getAltHand().addCard(deck.deal());
                break;
            case "su":
                player.addBalance((int) Math.floor((double) player.getHand().getBet() / 2));
                player.getHand().setBet((int) Math.floor((double) player.getHand().getBet() / 2));
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: runs rest turn of player hand
    public void playerRestTurn(Hand hand, String decision) {
        switch (decision) {
            case "h":
                hand.addCard(deck.deal());
                break;
            case "s": break;
            case "d":
                player.subBalance(hand.getBet());
                hand.setBet(hand.getBet() * 2);
                hand.addCard(deck.deal());
                break;
        }
    }

    // EFFECTS: saves player to file
    public void savePlayer() {
        try {
            jsonWriter.open();
            jsonWriter.write(player);
            jsonWriter.close();
            System.out.println("Saved " + player.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: return true if able to load player from file
    public boolean loadPlayer() {
        try {
            player = jsonReader.read();
            System.out.println("Loaded " + player.getName() + " from " + JSON_STORE);
            return true;
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
            return false;
        }
    }
}
