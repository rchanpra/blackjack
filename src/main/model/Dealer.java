package model;

// Represents a dealer in a game of blackjack
public class Dealer extends Hand {

    // EFFECTS: constructs dealer
    public Dealer() {

    }

    // REQUIRES: hand.size() == 2
    // EFFECTS: return first card in hand and an unknown card indicator
    public String getInitialHandString() {
        String result = "XX " + hand.get(0).getCard() + " (" + hand.get(0).getValue();
        if (hand.get(0).getValue() == 1) {
            result += "/11";
        }
        return result + ")";
    }
}
