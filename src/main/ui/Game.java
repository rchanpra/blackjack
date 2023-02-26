package ui;

import model.Dealer;
import model.Deck;
import model.Player;

import java.util.Scanner;

// Represents a game of blackjack
public class Game {
    private Scanner input;
    private Deck deck;
    private Dealer dealer;
    private Player player;

    // EFFECTS: initialize deck, dealer, input, and player with initialBalance
    public Game(int initialBalance) {
        deck = new Deck();
        dealer = new Dealer();
        player = new Player(initialBalance);
        input = new Scanner(System.in);
    }

    // EFFECTS: loop rounds of blackjack until player types "exit" or become broke
    public void run() {
        while (player.getBalance() > 0) {
            System.out.println("Player's balance: " + player.getBalance());
            System.out.print("Enter bet: ");
            int bet = input.nextInt();
            player.subBalance(bet);
            deal();
            playerTurn();
            dealerTurn();
            payout(bet);
            reset();
            System.out.println("\nPlayer's balance: " + player.getBalance());
            System.out.print("Press enter to continue or type \"exit\": ");
            if (input.next().equals("exit")) {
                break;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: deal 2 cards each and print hands
    private void deal() {
        dealer.draw(deck.deal());
        player.draw(deck.deal());
        dealer.draw(deck.deal());
        player.draw(deck.deal());
        System.out.println("\nPlayer's Hand: " + player.getHandString());
        System.out.println("Dealer's Hand: " + dealer.getInitialHandString());
    }

    // MODIFIES: this
    // EFFECTS: determine result and proceed with payouts
    private void payout(int bet) {
        if (player.hasBlackjack()) {
            if (dealer.hasBlackjack()) {
                player.addBalance(bet);
            } else {
                player.addBalance(bet * 2);
            }
        } else if (player.has5CardCharlie()) {
            if (dealer.has5CardCharlie()) {
                player.addBalance(bet);
            } else {
                player.addBalance(bet * 2);
            }
        } else {
            int outcome = playerWinsViaValue();
            if (!player.isBusted() && (dealer.isBusted() || outcome > 0)) {
                player.addBalance(bet * 2);
            } else if ((player.isBusted() && dealer.isBusted()) || outcome == 0) {
                player.addBalance(bet);
            }
        }
    }

    // EFFECTS: return a value greater than 0 if player's hand value is greater than dealer's hand value, 0 if tied,
    //          else a value less than 0
    private int playerWinsViaValue() {
        int playerHandValue = player.getValue();
        int dealerHandValue = dealer.getValue();
        if (player.hasAce() && playerHandValue <= 11) {
            playerHandValue += 10;
        }
        if (dealer.hasAce() && dealerHandValue <= 11) {
            dealerHandValue += 10;
        }
        return Integer.compare(playerHandValue, dealerHandValue);
    }

    // MODIFIES: this
    // EFFECTS: reset deck and hands
    private void reset() {
        deck.reset();
        dealer.reset();
        player.reset();
    }

    // MODIFIES: this
    // EFFECTS: end if player has blackjack else loop drawing cards until they either stand, bust, or has 5-card Charlie
    private void playerTurn() {
        if (player.hasBlackjack()) {
            System.out.println("Blackjack!");
        } else {
            while (true) {
                if (getDecision().equals("h")) {
                    player.draw(deck.deal());
                    System.out.println("\nPlayer's Hand: " + player.getHandString());
                } else {
                    break;
                }
                if (player.isBusted()) {
                    System.out.println("Busted!");
                    break;
                }
                if (player.has5CardCharlie()) {
                    System.out.println("5-card charlie!");
                    break;
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: end if dealer has blackjack else loop drawing cards until dealer's hand value is at least 17 or dealer
    //          has 5 card Charlie
    private void dealerTurn() {
        System.out.println("\nDealer's Hand: " + dealer.getHandString());
        if (dealer.hasBlackjack()) {
            System.out.println("Blackjack!");
        } else {
            while (dealer.getValue() < 17) {
                dealer.draw(deck.deal());
                System.out.println("\nDealer's Hand: " + dealer.getHandString());
                if (dealer.has5CardCharlie() && !dealer.isBusted()) {
                    System.out.println("5-card charlie!");
                    break;
                }
            }
            if (dealer.isBusted()) {
                System.out.println("Busted!");
            }
        }
    }

    // EFFECTS: ask for input for decision that adhere to format and return it
    private String getDecision() {
        while (true) {
            printDecisions();
            String decision = input.next().toLowerCase();
            if (decision.equals("h") || decision.equals("s")) {
                return decision;
            }
            System.out.println("Try again.");
        }
    }

    // EFFECTS: print list of decisions
    private void printDecisions() {
        System.out.println("\nEnter decision from:");
        System.out.println("\th -> hit");
        System.out.println("\ts -> stand");
    }
}
