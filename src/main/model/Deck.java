package model;

import java.util.ArrayList;
import java.util.List;

// Represents a deck of cards
public class Deck {
    private List<Card> cards;

    // EFFECTS: initialize deck and add 52 unique cards to it
    public Deck() {
        cards = new ArrayList<>();
        addCards();
    }

    // EFFECTS: return deck
    public List<Card> getCards() {
        return cards;
    }

    // MODIFIES: this
    // EFFECTS: add 52 unique cards to deck
    public void addCards() {
        int rank = 1;
        while (rank <= 13) {
            int suit = 1;
            while (suit <= 4) {
                Card card = new Card(rank, suit);
                cards.add(card);
                suit++;
            }
            rank++;
        }
    }

    // MODIFIES: this
    // EFFECTS: randomly remove a card from deck and return it
    public Card deal() {
        return cards.remove((int) (Math.random() * (cards.size() - 1)));
    }

    // MODIFIES: this
    // EFFECTS: reinitialize deck and adds 52 unique cards to it
    public void shuffle() {
        cards = new ArrayList<>();
        addCards();
    }
}
