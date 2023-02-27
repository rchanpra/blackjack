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
        while (true) {
            int bet = askBet();
            deal();
            bet = playerTurn(bet);
            dealerTurn();
            payout(bet);
            reset();
            System.out.println("\nPlayer's balance: " + player.getBalance());
            if (player.getBalance() <= 0) {
                System.out.println("You are broke. Game Over!");
                break;
            }
            System.out.println("Press ENTER to continue or type \"exit\". ");
            if (input.nextLine().equals("exit")) {
                System.out.println("\nThank you for playing!");
                break;
            }
        }
    }

    // EFFECTS: ask for input for bet that is less than or equal to player's balance
    private int askBet() {
        while (true) {
            System.out.println("\nPlayer's balance: " + player.getBalance());
            System.out.print("Enter bet: ");
            int bet = input.nextInt();
            if (bet <= player.getBalance()) {
                return bet;
            }
            System.out.println("\nTry again.");
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
        player.subBalance(bet);
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
        if (player.hasAdjustableAce()) {
            playerHandValue += 10;
        }
        if (dealer.hasAdjustableAce()) {
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
    // EFFECTS: end if player has blackjack else loop drawing cards until they either double down, stand, bust, or has
    // 5-card Charlie
    private int playerTurn(int bet) {
        if (player.hasBlackjack()) {
            System.out.println("Blackjack!");
            return bet;
        } else {
            while (true) {
                String decision = askDecision(bet);
                if (decision.equals("h")) {
                    player.draw(deck.deal());
                    System.out.println("\nPlayer's Hand: " + player.getHandString());
                    if (checkBustedOr5CardCharlie()) {
                        return bet;
                    }
                } else if (decision.equals("d")) {
                    player.draw(deck.deal());
                    System.out.println("\nPlayer's Hand: " + player.getHandString());
                    checkBustedOr5CardCharlie();
                    return bet * 2;
                } else {
                    return bet;
                }
            }
        }
    }

    // EFFECTS: return true if player bust or has 5-card Charlie else false
    private boolean checkBustedOr5CardCharlie() {
        if (player.isBusted()) {
            System.out.println("Busted!");
            return true;
        } else if (player.has5CardCharlie()) {
            System.out.println("5-card charlie!");
            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: end if dealer has blackjack else loop drawing cards until dealer's hand value is at least 17 or dealer
    // has 5-card Charlie
    private void dealerTurn() {
        System.out.println("\nPress ENTER to continue.");
        input.nextLine();
        input.nextLine();
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
            System.out.println("\nPress ENTER to continue.");
            input.nextLine();
        }
    }

    // EFFECTS: ask for decision input that adhere to format and return it
    private String askDecision(int bet) {
        while (true) {
            printDecisions();
            String decision = input.next().toLowerCase();
            if (decision.equals("h") || decision.equals("s")
                    || (decision.equals("d") && bet * 2 <= player.getBalance())) {
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
        System.out.println("\td -> double down");
    }
}
