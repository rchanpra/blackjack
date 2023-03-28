package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// Represents a player in a game of blackjack
public class Player extends Person {
    private String name;
    private int balance;
    private int starting;
    private int goal;
    private Hand altHand;
    private List<Hand> handHistory;

    // REQUIRES: starting > 0 and goal > initial
    // EFFECTS: initializes name, balance, starting, and goal with parameters, initializes hands empty
    public Player(String name, int balance, int starting, int goal) {
        super();
        this.name = name;
        this.balance = balance;
        this.starting = starting;
        this.goal = goal;
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

    // EFFECTS: returns starting
    public int getStarting() {
        return starting;
    }

    // EFFECTS: returns goal
    public int getGoal() {
        return goal;
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
            handsString += hand.getCardsString() + " [" + hand.getBet() + "], ";
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
    // EFFECTS: adds hand(s) to handHistory and reinitialize hand(s)
    @Override
    public void shuffle() {
        handHistory.add(hand);
        if (altHand != null) {
            handHistory.add(altHand);
        }
        hand = new Hand();
        altHand = null;
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
        json.put("starting", starting);
        json.put("goal", goal);
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
