package model;

// Represents a dealer in a game of blackjack
public class Dealer extends Person {

    // EFFECTS: constructs dealer
    public Dealer() {
        super();
    }

    // REQUIRES: hand.size() == 2
    // EFFECTS: returns dealer's initial hand as appropriate string
    public String getInitialHandString() {
        String result = "XX " + hand.getCards().get(0).getString() + " (";
        if (hand.getCards().get(0).getValue() == 1) {
            return result + "1/11+)";
        }
        return result + hand.getCards().get(0).getValue() + "+)";
    }

    // EFFECTS: returns true if dealer should draw else false
    public boolean canDraw() {
        if (hand.canDraw()) {
            return hand.getCardsValue() < 17;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: reinitialize hand
    @Override
    public void shuffle() {
        hand = new Hand();
    }
}
