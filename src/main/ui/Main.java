package ui;

import java.util.Scanner;

// Represents a main class to declare and initiate game
public class Main {

    // EFFECTS: declare and initiate a game object with given input, then call run on game object
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter your initial balance: ");
        int initialBalance = input.nextInt();
        Game blackjack = new Game(initialBalance);
        blackjack.run();
    }
}
