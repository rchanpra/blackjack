package model;

import java.util.ArrayList;

public class Player {
    private static final int STARTING_BALANCE = 1000;

    int balance;
    Hand hand;

    public Player() {
        balance = STARTING_BALANCE;
        hand = new Hand();
    }

    public int getBalance() {
        return balance;
    }

    public Hand getHand() {
        return hand;
    }

    public void addBalance(int n) {
        balance += n;
    }

    public void subBalance(int n) {
        balance -= n;
    }

    public void emptyHand() {
        hand = new Hand();
    }
}
