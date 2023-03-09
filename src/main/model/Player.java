package model;

import org.json.JSONObject;

// Represents a player in a game of blackjack
public class Player {
    private String name;
    private int balance;
    private Hand hand;

    // REQUIRES: initialAmount > 0
    // EFFECTS: initialize name and balance with respective parameters and initialize hand
    public Player(String name, int amount) {
        this.name = name;
        balance = amount;
        hand = new Hand();
    }

    // EFFECTS: return name
    public String getName() {
        return name;
    }

    // EFFECTS: return balance
    public int getBalance() {
        return balance;
    }

    // EFFECTS: return hand
    public Hand getHand() {
        return hand;
    }

    // MODIFIES: this
    // EFFECTS: set hand as parameter
    public void setHand(Hand hand) {
        this.hand = hand;
    }

    // MODIFIES: this
    // EFFECTS: add amount to balance
    public void addBalance(int amount) {
        balance += amount;
    }

    // REQUIRES: amount <= balance
    // MODIFIES: this
    // EFFECTS: subtract amount from balance
    public void subBalance(int amount) {
        balance -= amount;
    }

    // EFFECTS: returns this as JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("balance", balance);
        json.put("hand", hand.toJson());
        return json;
    }
}
