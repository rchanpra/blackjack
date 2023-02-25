package ui;

import model.Deck;
import model.Player;

import java.util.Scanner;

public class Game {
    private Scanner input;
    private Deck deck;
    private Player dealer;
    private Player player;

    public Game() {
        deck = new Deck();
        dealer = new Player();
        player = new Player();
        input = new Scanner(System.in);
    }

    public void run() {
        while (player.getBalance() > 0) {
            System.out.println("Player's balance: " + player.getBalance());
            System.out.print("Enter bet: ");
            int bet = input.nextInt();
            deal();
            payout(bet);
            reset();
            System.out.println("Player's balance: " + player.getBalance());
            System.out.print("Press enter to continue or type \"exit\": ");
            if (input.next().equals("exit")) {
                break;
            }
        }
    }

    private void payout(int bet) {
        boolean p = playerTurn();
        boolean d = dealerTurn();
        if (p) {
            if (d) {
                player.addBalance(bet);
            } else {
                player.addBalance(bet * 2);
            }
        } else {
            if (!player.getHand().isBusted()
                    && (dealer.getHand().isBusted()) || player.getHand().getValue() > dealer.getHand().getValue()) {
                player.addBalance(bet * 2);
            }
        }
    }

    private void deal() {
        dealer.getHand().draw(deck.deal());
        player.getHand().draw(deck.deal());
        dealer.getHand().draw(deck.deal());
        player.getHand().draw(deck.deal());
        System.out.println("Player's Hand: " + player.getHand().print());
    }

    private void reset() {
        deck.reshuffle();
        dealer.emptyHand();
        player.emptyHand();
    }

    private boolean playerTurn() {
        if (player.getHand().hasBlackjack()) {
            System.out.println("Blackjack!");
            return true;
        } else {
            while (true) {
                String decision = getDecision();
                if (decision.equals("hit") || decision.equals("h")) {
                    player.getHand().draw(deck.deal());
                    System.out.println(player.getHand().print());
                } else {
                    return false;
                }
                if (player.getHand().isBusted()) {
                    System.out.println("Busted!");
                    return false;
                }
                if (player.getHand().has5CardCharlie()) {
                    System.out.println("5-card charlie!");
                    return true;
                }
            }
        }
    }

    private String getDecision() {
        while (true) {
            System.out.print("Enter decision: ");
            String decision = input.next().toLowerCase();
            if (decision.equals("hit") || decision.equals("stand") || decision.equals("h") || decision.equals("s")) {
                return decision;
            }
            System.out.println("Try again.");
        }
    }

    private boolean dealerTurn() {
        System.out.println("Dealer's Hand: " + dealer.getHand().print());
        if (dealer.getHand().hasBlackjack()) {
            System.out.println("Blackjack!");
            return true;
        } else {
            while (dealer.getHand().getValue() < 17) {
                dealer.getHand().draw(deck.deal());
                System.out.println(dealer.getHand().print());
                if (dealer.getHand().isBusted()) {
                    System.out.println("Busted!");
                    return false;
                }
                if (dealer.getHand().has5CardCharlie()) {
                    System.out.println("5-card charlie!");
                    return true;
                }
            }
            return false;
        }
    }
}
