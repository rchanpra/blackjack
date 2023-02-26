package model;

import java.util.ArrayList;

// Represents a deck of cards
public class Deck {
    private ArrayList<Card> deck;

    // EFFECTS: initialize deck and add 52 unique cards to it
    public Deck() {
        deck = new ArrayList<>();
        addCards();
    }

    // EFFECTS: return deck
    public ArrayList<Card> getDeck() {
        return deck;
    }

    // MODIFIES: this
    // EFFECTS: add 52 unique cards to deck
    public void addCards() {
        int rank = 1;
        while (rank <= 13) {
            int suit = 1;
            while (suit <= 4) {
                Card card = new Card(rank, suit);
                deck.add(card);
                suit++;
            }
            rank++;
        }
    }

    // MODIFIES: this
    // EFFECTS: randomly remove a card from deck and return it
    public Card deal() {
        return deck.remove((int) (Math.random() * (deck.size() - 1)));
    }

    // MODIFIES: this
    // EFFECTS: reinitialize deck and add 52 unique cards to it
    public void reset() {
        deck = new ArrayList<>();
        addCards();
    }
}
