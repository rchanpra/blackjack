package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// Represents a player in a game of blackjack
public class Player extends Person {
    private String name;
    private int balance;
    private Hand altHand;
    private List<Hand> handHistory;

    // REQUIRES: starting > 0 and goal > initial
    // EFFECTS: initializes name, balance, starting, and goal with parameters, initializes hands empty
    public Player(String name, int balance) {
        super();
        this.name = name;
        this.balance = balance;
        handHistory = new ArrayList<>();
    }

    // EFFECTS: returns name
    public String getName() {
        return name;
    }

    // EFFECTS: returns balance
    public int getBalance() {
        return balance;
    }

    // EFFECTS: returns altHand
    public Hand getAltHand() {
        return altHand;
    }

    // EFFECTS: returns handHistory
    public List<Hand> getHandHistory() {
        return handHistory;
    }

    // EFFECTS: returns name and balance as appropriate string
    public String getBalanceString() {
        return name + "'s balance: " + balance;
    }

    // EFFECTS: returns handHistory as appropriate string
    public String getHandHistoryString() {
        String handsString = "";
        for (Hand hand : handHistory) {
            handsString += hand.getString() + " [" + hand.getBet() + "], ";
        }
        return handsString;
    }

    // MODIFIES: this
    // EFFECTS: sets altHand as hand
    public void setAltHand(Hand hand) {
        altHand = hand;
    }

    // MODIFIES: this
    // EFFECTS: adds hand to handHistory
    public void addHandHistory(Hand hand) {
        handHistory.add(hand);
    }


    // MODIFIES: this
    // EFFECTS: adds hand(s) to handHistory to max of 5 and reinitialize hand(s)
    @Override
    public void shuffle() {
        handHistory.add(hand);
        EventLog.getInstance().logEvent(new Event("All cards removed from player's hand"));
        if (altHand != null) {
            handHistory.add(altHand);
            EventLog.getInstance().logEvent(new Event("All cards removed from player's second hand"));
        }
        while (handHistory.size() > 5) {
            handHistory.remove(0);
        }
        hand = new Hand();
        altHand = null;
    }

    // MODIFIES: super, deck
    // EFFECTS: draw a card from deck and add it to hand
    @Override
    public void draw(Deck deck) {
        Card card = deck.deal();
        hand.addCard(card);
        EventLog.getInstance().logEvent(new Event("Card added to player's hand: " + card.getString()));
    }

    // MODIFIES: this, deck
    // EFFECTS: draw a card from deck and add it to altHand
    public void drawAlt(Deck deck) {
        Card card = deck.deal();
        altHand.addCard(card);
        EventLog.getInstance().logEvent(new Event("Card added to player's second hand: " + card.getString()));
    }

    // MODIFIES: this
    // EFFECTS: adds amount to balance
    public void addBalance(int amount) {
        balance += amount;
    }

    // REQUIRES: amount <= balance
    // MODIFIES: this
    // EFFECTS: subtracts amount from balance
    public void subBalance(int amount) {
        balance -= amount;
    }

    // EFFECTS: returns this as JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("balance", balance);
        json.put("handHistory", handHistoryToJson());
        return json;
    }

    // EFFECTS: returns handHistory in this player as a JSON array
    public JSONArray handHistoryToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Hand hand : handHistory) {
            jsonArray.put(hand.toJson());
        }
        return jsonArray;
    }
}
