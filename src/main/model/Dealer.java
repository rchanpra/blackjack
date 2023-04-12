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
            return hand.getValue() < 17;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: reinitialize hand
    @Override
    public void shuffle() {
        hand = new Hand();
        EventLog.getInstance().logEvent(new Event("All cards removed from dealer's hand"));
    }

    // MODIFIES: super, deck
    // EFFECTS: draw a card from deck and add it to hand
    @Override
    public void draw(Deck deck) {
        Card card = deck.deal();
        hand.addCard(card);
        EventLog.getInstance().logEvent(new Event("Card added to dealer's hand: " + card.getString()));
    }
}
