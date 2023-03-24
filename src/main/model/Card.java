package model;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

// Represents a card in the deck
public class Card {
    public static final List<String> RANKS =
            Arrays.asList("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K");
    public static final List<String> SUITS = Arrays.asList("C", "D", "H", "S");

    private int rank;
    private int suit;

    // REQUIRES: 1 <= rank <= 13 and 1 <= suit <= 4
    // EFFECTS: initializes rank and suit with parameters
    public Card(int rank, int suit) {
        this.rank = rank;
        this.suit = suit;
    }

    // EFFECTS: returns rank
    public int getRank() {
        return rank;
    }

    // EFFECTS: returns suit
    public int getSuit() {
        return suit;
    }

    // EFFECTS: returns rank and suit as string
    public String getCard() {
        return RANKS.get(rank - 1) + SUITS.get(suit - 1);
    }

    // EFFECTS: returns card value
    public int getValue() {
        return Math.min(rank, 10);
    }

    // EFFECTS: returns this as JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("rank", rank);
        json.put("suit", suit);
        return json;
    }
}
