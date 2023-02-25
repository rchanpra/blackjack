package model;

import java.util.ArrayList;

public class Suits {
    private ArrayList<String> suits;

    public Suits() {
        suits = new ArrayList<>();
        suits.add("C");
        suits.add("D");
        suits.add("H");
        suits.add("S");
    }

    public ArrayList<String> getSuits() {
        return suits;
    }
}
