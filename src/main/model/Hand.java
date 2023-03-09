package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

// Represents a hand of a player or a dealer in a game of blackjack
public class Hand {
    protected ArrayList<Card> cards;
    protected int value;

    // EFFECTS: initialize hand and value
    public Hand() {
        cards = new ArrayList<>();
        value = 0;
    }

    // EFFECTS: return hand
    public ArrayList<Card> getCards() {
        return cards;
    }

    // EFFECTS: return value
    public int getValue() {
        return value;
    }

    // EFFECTS: return hand as appropriate string
    public String getHandString() {
        String result = "";
        for (Card card : cards) {
            result += card.getCard() + " ";
        }
        result += "(" + value;
        if (hasAdjustableAce()) {
            result += "/" + (value + 10);
        }
        return result + ")";
    }

    // MODIFIES: this
    // EFFECTS: add card to hand and card value to value
    public void draw(Card card) {
        cards.add(card);
        value += card.getValue();
    }

    // MODIFIES: this
    // EFFECTS: reinitialize hand and value
    public void reset() {
        cards = new ArrayList<>();
        value = 0;
    }

    // EFFECTS: return true if value is greater than 21 else false
    public boolean isBusted() {
        return value > 21;
    }

    // EFFECTS: return true if hand has 2 cards with an ace and value is 11 else false
    public boolean hasBlackjack() {
        return cards.size() == 2 && hasAce() & getValue() == 11;
    }

    // EFFECTS: return true if hand has 5 cards and not busted else false
    public boolean has5CardCharlie() {
        return cards.size() == 5 && !isBusted();
    }

    // EFFECTS: return true if hand has an ace else false
    public boolean hasAce() {
        for (Card card : cards) {
            if (card.getRank() == 1) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: return true if hand has an ace and hand value is 11 or less else false
    public boolean hasAdjustableAce() {
        return hasAce() && value <= 11;
    }

    // EFFECTS: returns this as JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("cards", cards);
        return json;
    }

    // EFFECTS: returns cards in this hand as a JSON array
    public JSONArray cardsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Card card : cards) {
            jsonArray.put(card);
        }
        return jsonArray;
    }
}
