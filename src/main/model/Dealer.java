package model;

// Represents a dealer in a game of blackjack
public class Dealer extends Hand {

    // EFFECTS: constructs dealer
    public Dealer() {

    }

    // REQUIRES: hand.size() == 2
    // EFFECTS: return dealer's initial hand as appropriate string
    public String getInitialHandString() {
        String result = "XX " + cards.get(0).getCard() + " (" + cards.get(0).getValue();
        if (cards.get(0).getValue() == 1) {
            result += "/11";
        }
        return result + ")";
    }
}
