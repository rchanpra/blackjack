package ui;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter your initial balance: ");
        int initialBalance = input.nextInt();
        Game blackjack = new Game(initialBalance);
        blackjack.run();
    }
}
