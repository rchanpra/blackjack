package model;

import java.util.ArrayList;

// Represents ranks in a deck of cards
public class Ranks {
    private final ArrayList<String> ranks;

    // EFFECTS: initialize ranks and add appropriate card ranks in an appropriate order
    public Ranks() {
        ranks = new ArrayList<>();
        ranks.add("0");
        ranks.add("A");
        ranks.add("2");
        ranks.add("3");
        ranks.add("4");
        ranks.add("5");
        ranks.add("6");
        ranks.add("7");
        ranks.add("8");
        ranks.add("9");
        ranks.add("10");
        ranks.add("J");
        ranks.add("Q");
        ranks.add("K");
    }

    // EFFECTS: return ranks
    public ArrayList<String> getRanks() {
        return ranks;
    }
}
