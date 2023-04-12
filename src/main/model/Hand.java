package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

// Represents a hand in a game of blackjack
public class Hand {
    protected ArrayList<Card> cards;
    private int bet;

    // EFFECTS: initializes cards and bet
    public Hand() {
        cards = new ArrayList<>();
        bet = 0;
    }

    // EFFECTS: returns cards
    public ArrayList<Card> getCards() {
        return cards;
    }

    // EFFECTS: returns bet
    public int getBet() {
        return bet;
    }

    // MODIFIES: this
    // EFFECTS: sets bet
    public void setBet(int bet) {
        this.bet = bet;
    }

    // EFFECTS: returns hand value
    public int getValue() {
        int value = 0;
        for (Card card : cards) {
            value += card.getValue();
        }
        if (hasAce() && value <= 11) {
            value += 10;
        }
        return value;
    }

    // EFFECTS: returns hand value as appropriate string
    public String getValueString() {
        String result = "";
        int value = 0;
        for (Card card : cards) {
            value += card.getValue();
        }
        result += value;
        if (hasAce() && value <= 11) {
            result += "/" + getValue();
        }
        return result;
    }

    // EFFECTS: returns hand as appropriate string
    public String getString() {
        String result = "";
        for (Card card : cards) {
            result += card.getString() + " ";
        }
        return result + "(" + getValueString() + ")";
    }

    // MODIFIES: this
    // EFFECTS: adds card to cards
    public void addCard(Card card) {
        cards.add(card);
    }

    // MODIFIES: this
    // EFFECTS: returns new hand split from this hand
    public Hand split() {
        Hand hand = new Hand();
        Card card = cards.remove(1);
        EventLog.getInstance().logEvent(new Event("Card removed from player's hand: " + card.getString()));
        hand.addCard(card);
        EventLog.getInstance().logEvent(new Event("Card added to player's second hand: " + card.getString()));
        hand.setBet(bet);
        return hand;
    }

    // EFFECTS: returns true if cards value is greater than 21 else false
    public boolean isBusted() {
        return getValue() > 21;
    }

    // EFFECTS: returns true if cards have an ace else false
    public boolean hasAce() {
        for (Card card : cards) {
            if (card.getRank() == 1) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: returns true if cards have 2 cards and a value of 21 else false
    public boolean hasBlackjack() {
        return cards.size() == 2 && getValue() == 21;
    }

    // EFFECTS: returns true if cards have 5 cards and not busted else false
    public boolean has5CardCharlie() {
        return cards.size() == 5 && !isBusted();
    }

    // EFFECTS: returns true if neither calls to has5CardCharlie() and isBusted() return true
    public boolean canDraw() {
        return !(has5CardCharlie() || isBusted());
    }

    // EFFECTS: returns true if cards have 2 cards with same rank else false
    public boolean canSplit() {
        return cards.size() == 2 && cards.get(0).getRank() == cards.get(1).getRank();
    }

    // EFFECTS: returns this as JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("cards", cardsToJson());
        json.put("bet", bet);
        return json;
    }

    // EFFECTS: returns cards in this hand as a JSON array
    public JSONArray cardsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Card card : cards) {
            jsonArray.put(card.toJson());
        }
        return jsonArray;
    }
}
