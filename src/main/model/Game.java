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
        player.draw(deck);
        dealer.draw(deck);
        player.draw(deck);
        dealer.draw(deck);
    }

    // MODIFIES: this
    // EFFECTS: collects cards and shuffles deck
    public void shuffle() {
        player.shuffle();
        dealer.shuffle();
        deck.shuffle();
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
        } else if (!hand.isBusted()
                && (dealer.getHand().isBusted() || hand.getValue() > dealer.getHand().getValue())) {
            player.addBalance(hand.getBet() * 2);
        } else if ((hand.isBusted() && dealer.getHand().isBusted())
                || hand.getValue() == dealer.getHand().getValue()) {
            player.addBalance(hand.getBet());
        }
    }

    // MODIFIES: this
    // EFFECTS: runs first turn of player
    public void playerFirstTurn(String decision) {
        switch (decision) {
            case "h":
                player.draw(deck);
                break;
            case "s": break;
            case "d":
                player.subBalance(player.getHand().getBet());
                player.getHand().setBet(player.getHand().getBet() * 2);
                player.draw(deck);
                break;
            case "sp":
                player.subBalance(player.getHand().getBet());
                player.setAltHand(player.getHand().split());
                player.draw(deck);
                player.drawAlt(deck);
                break;
            case "su":
                player.addBalance((int) Math.floor((double) player.getHand().getBet() / 2));
                player.getHand().setBet((int) Math.floor((double) player.getHand().getBet() / 2));
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: runs rest turn of player hand
    public void playerRestTurn(String decision) {
        switch (decision) {
            case "h":
                player.draw(deck);
                break;
            case "s": break;
            case "d":
                player.subBalance(player.getHand().getBet());
                player.getHand().setBet(player.getHand().getBet() * 2);
                player.draw(deck);
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: runs rest turn of player altHand
    public void playerRestTurnAlt(String decision) {
        switch (decision) {
            case "h":
                player.drawAlt(deck);
                break;
            case "s": break;
            case "d":
                player.subBalance(player.getAltHand().getBet());
                player.getAltHand().setBet(player.getAltHand().getBet() * 2);
                player.drawAlt(deck);
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: reinitialize jsonWriter with new destination
    public void setJsonWriterDestination(String destination) {
        jsonWriter = new JsonWriter(destination);
    }

    // MODIFIES: this
    // EFFECTS: reinitialize jsonReader with new destination
    public void setJsonReaderDestination(String destination) {
        jsonReader = new JsonReader(destination);
    }

    // EFFECTS: saves player to file
    public void savePlayer() {
        try {
            jsonWriter.open();
            jsonWriter.write(player);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            e.getCause();
        }
    }

    // MODIFIES: this
    // EFFECTS: return true if able to load player from file
    public boolean loadPlayer() {
        try {
            player = jsonReader.read();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
