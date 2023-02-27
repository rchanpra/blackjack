package model;

import java.util.ArrayList;

// Represents a hand of a player or a dealer in a game of blackjack
public class Hand {
    protected ArrayList<Card> hand;
    protected int value;

    // EFFECTS: initialize hand and value
    public Hand() {
        hand = new ArrayList<>();
        value = 0;
    }

    // EFFECTS: return hand
    public ArrayList<Card> getHand() {
        return hand;
    }

    // EFFECTS: return value
    public int getValue() {
        return value;
    }

    // EFFECTS: return hand as appropriate string
    public String getHandString() {
        String result = "";
        for (Card card : hand) {
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
        hand.add(card);
        value += card.getValue();
    }

    // MODIFIES: this
    // EFFECTS: reinitialize hand and value
    public void reset() {
        hand = new ArrayList<>();
        value = 0;
    }

    // EFFECTS: return true if value is greater than 21 else false
    public boolean isBusted() {
        return value > 21;
    }

    // EFFECTS: return true if hand has 2 cards with an ace and value is 11 else false
    public boolean hasBlackjack() {
        return hand.size() == 2 && hasAce() & getValue() == 11;
    }

    // EFFECTS: return true if hand has 5 cards and not busted else false
    public boolean has5CardCharlie() {
        return hand.size() == 5 && !isBusted();
    }

    // EFFECTS: return true if hand has an ace else false
    public boolean hasAce() {
        for (Card card : hand) {
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
}