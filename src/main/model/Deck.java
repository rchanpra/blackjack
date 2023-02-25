package model;

import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> deck;

    public Deck() {
        deck = new ArrayList<>();
        addCards();
    }

    public Card deal() {
        return deck.remove((int) (Math.random() * (deck.size() - 1)));
    }

    public void reshuffle() {
        deck = new ArrayList<>();
        addCards();
    }

    public void addCards() {
        int rank = 1;
        while (rank <= 13) {
            int suit = 0;
            while (suit < 4) {
                Card card = new Card(rank, suit);
                deck.add(card);
                suit++;
            }
            rank++;
        }
    }
}
