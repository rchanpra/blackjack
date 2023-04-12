package model;

// Represents a person in a game of blackjack
public abstract class Person {
    protected Hand hand;

    // EFFECTS: constructs dealer
    public Person() {
        hand = new Hand();
    }

    // EFFECTS: returns hand
    public Hand getHand() {
        return hand;
    }

    // MODIFIES: this
    // EFFECTS: sets hand with parameter
    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public abstract void shuffle();

    public abstract void draw(Deck deck);
}
