package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// Represents a player in a game of blackjack
public class Player {
    private String name;
    private int balance;
    private int initial;
    private int goal;
    private List<Hand> hands;

    // REQUIRES: initial > 0 and goal > initial
    // EFFECTS: initializes name, balance, initial, and goal with parameters, initializes hands empty
    public Player(String name, int balance, int initial, int goal) {
        this.name = name;
        this.balance = balance;
        this.initial = initial;
        this.goal = goal;
        hands = new ArrayList<>();
    }

    // EFFECTS: returns name
    public String getName() {
        return name;
    }

    // EFFECTS: returns balance
    public int getBalance() {
        return balance;
    }

    // EFFECTS: returns initial
    public int getInitial() {
        return initial;
    }

    // EFFECTS: returns goal
    public int getGoal() {
        return goal;
    }

    // EFFECTS: returns hands
    public List<Hand> getHands() {
        return hands;
    }

    // EFFECTS: returns name and balance as appropriate string
    public String getBalanceString() {
        return name + "'s balance: " + balance;
    }

    // EFFECTS: returns hands as appropriate string
    public String getHandsString() {
        String handsString = "";
        for (Hand hand : hands) {
            handsString += hand.getCardsString() + ", ";
        }
        return handsString;
    }

    // MODIFIES: this
    // EFFECTS: adds hand to hands
    public void addHand(Hand hand) {
        hands.add(hand);
    }

    // MODIFIES: this
    // EFFECTS: reinitialize hands and adds a new hand to it
    public void resetHands() {
        hands = new ArrayList<>();
        hands.add(new Hand());
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
        json.put("initial", initial);
        json.put("goal", goal);
        json.put("hands", handsToJson());
        return json;
    }

    // EFFECTS: returns hands in this player as a JSON array
    public JSONArray handsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Hand hand : hands) {
            jsonArray.put(hand.toJson());
        }
        return jsonArray;
    }
}
