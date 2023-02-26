package model;

// Represents a player in a game of blackjack
public class Player extends Hand {
    private int balance;

    // REQUIRES: initialAmount > 0
    // EFFECTS: initialize balance with n
    public Player(int initialAmount) {
        balance = initialAmount;
    }

    // EFFECTS: return balance
    public int getBalance() {
        return balance;
    }

    // MODIFIES: this
    // EFFECTS: add amount to balance
    public void addBalance(int amount) {
        balance += amount;
    }

    // REQUIRES: balance > 0
    // MODIFIES: this
    // EFFECTS: subtract amount from balance
    public void subBalance(int amount) {
        balance -= amount;
    }
}
