package model;

import java.util.ArrayList;

// Represents suits in a deck of cards
public class Suits {
    private ArrayList<String> suits;

    // EFFECTS: initialize suits and add appropriate card suits in an appropriate order
    public Suits() {
        suits = new ArrayList<>();
        suits.add("");
        suits.add("C");
        suits.add("D");
        suits.add("H");
        suits.add("S");
    }

    // EFFECTS: return suits
    public ArrayList<String> getSuits() {
        return suits;
    }
}
