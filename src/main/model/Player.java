package model;

import org.json.JSONObject;

// Represents a player in a game of blackjack
public class Player extends Hand {
    private String name;
    private int balance;

    // REQUIRES: initialAmount > 0
    // EFFECTS: initialize balance with n
    public Player(String name, int amount) {
        this.name = name;
        balance = amount;
    }

    // EFFECTS: return name
    public String getName() {
        return name;
    }

    // EFFECTS: return balance
    public int getBalance() {
        return balance;
    }

    // MODIFIES: this
    // EFFECTS: add amount to balance
    public void addBalance(int amount) {
        balance += amount;
    }

    // REQUIRES: balance > 0
    // MODIFIES: this
    // EFFECTS: subtract amount from balance
    public void subBalance(int amount) {
        balance -= amount;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("balance", balance);
        return json;
    }
}
