package model;

// Represents a card in the deck
public class Card {
    private int rank;
    private int suit;
    private Ranks ranks;
    private Suits suits;

    // REQUIRES: 1 <= rank <= 13 and 1 <= suit <= 4
    // EFFECTS: initialize rank and suit as respective parameters and initialize ranks and suits
    public Card(int rank, int suit) {
        this.rank = rank;
        this.suit = suit;
        ranks = new Ranks();
        suits = new Suits();
    }

    // EFFECTS: return rank
    public int getRank() {
        return rank;
    }

    // EFFECTS: return suit
    public int getSuit() {
        return suit;
    }

    // EFFECTS: return rank and suit as string
    public String getCard() {
        return ranks.getRanks().get(rank) + suits.getSuits().get(suit);
    }

    // EFFECTS: return card value
    public int getValue() {
        return Math.min(rank, 10);
    }
}
