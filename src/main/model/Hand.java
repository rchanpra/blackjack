package model;

import java.util.ArrayList;

public class Hand {
    private ArrayList<Card> hand;

    public Hand() {
        hand = new ArrayList<>();
    }

    public void draw(Card c) {
        hand.add(c);
    }

    public int getValue() {
        int value = 0;
        for (Card c : hand) {
            value += c.getValue();
        }
        return value;
    }

    public String print() {
        String result = "";

        for (Card c : hand) {
            result += c.getCard() + " ";
        }

        result += "(" + getValue() + ")";

        return result;
    }

    public boolean hasBlackjack() {
        for (Card c : hand) {
            if (c.getRank() == 1) {
                return getValue() + 10 == 21;
            }
        }
        return false;
    }

    public boolean isBusted() {
        return getValue() > 21;
    }

    public boolean has5CardCharlie() {
        return hand.size() == 5;
    }
}
