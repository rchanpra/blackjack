package model;

public class Card {
    private int rank;
    private int suit;
    private Ranks ranks;
    private Suits suits;

    public Card(int rank, int suit) {
        this.rank = rank;
        this.suit = suit;
        ranks = new Ranks();
        suits = new Suits();
    }

    public int getRank() {
        return rank;
    }

    public int getSuit() {
        return suit;
    }

    public String getCard() {
        return ranks.getRanks().get(rank) + suits.getSuits().get(suit);
    }

    public int getValue() {
        if (rank > 9) {
            return 10;
        }
        return rank;
    }
}
