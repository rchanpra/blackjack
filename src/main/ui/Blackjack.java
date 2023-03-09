package ui;

import model.Dealer;
import model.Deck;
import model.Player;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// Represents a game of blackjack
public class Blackjack {
    private static final String JSON_STORE = "./data/blackjack.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private Scanner input;
    private Deck deck;
    private Dealer dealer;
    private Player player;

    // EFFECTS: initialize deck, dealer, and player then run
    public Blackjack() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        input = new Scanner(System.in);
        deck = new Deck();
        dealer = new Dealer();
        startMenu();
        run();
    }

    // MODIFIES: this
    // EFFECTS: construct new player or load player
    private void startMenu() {
        while (true) {
            System.out.println("\nSelect from:");
            System.out.println("\tn -> new game");
            System.out.println("\tl -> load game");
            String selection = input.next().toLowerCase();
            if (selection.equals("n")) {
                System.out.print("Enter your name: ");
                String name = input.next();
                int amount = askInitialBalance();
                player = new Player(name, amount);
                break;
            } else if (selection.equals("l")) {
                loadPlayer();
                break;
            } else {
                System.out.println("\nInvalid selection.");
            }
        }
    }

    // EFFECTS: ask for input for initial balance that > 0
    private int askInitialBalance() {
        while (true) {
            System.out.print("Enter your initial balance: ");
            int amount = input.nextInt();
            if (amount > 0) {
                return amount;
            }
            System.out.println("Invalid balance.");
        }
    }

    // EFFECTS: loop rounds of blackjack
    private void run() {
        boolean running = select();
        while (running) {
            reset();
            int bet = askBet();
            deal();
            bet = playerTurn(bet);
            dealerTurn();
            payout(bet);
            running = select();
        }
    }

    // EFFECTS: run selection sequence
    private boolean select() {
        while (true) {
            System.out.println("\n" + player.getName() + "'s balance: " + player.getBalance());
            if (player.getBalance() <= 0) {
                System.out.println("You are broke. Game Over!");
                return false;
            }
            printSelection();
            String selection = input.next().toLowerCase();
            if (selection.equals("s")) {
                savePlayer();
            } else if (selection.equals("h")) {
                System.out.println("Previous Hand: " + player.getHand().getHandString());
                System.out.print("\nPress ENTER to continue.");
                input.nextLine();
                input.nextLine();
            } else if (selection.equals("b")) {
                return true;
            } else {
                return false;
            }
        }
    }

    // EFFECTS: print list of selections
    private void printSelection() {
        System.out.println("\nSelect from:");
        System.out.println("\tp -> play");
        System.out.println("\th -> previous hand");
        System.out.println("\ts -> save");
        System.out.println("\tq -> quit");
    }

    // EFFECTS: ask for input for bet that is less than or equal to player's balance
    private int askBet() {
        while (true) {
            System.out.println("\n" + player.getName() + "'s balance: " + player.getBalance());
            System.out.print("Enter bet: ");
            int bet = input.nextInt();
            if (bet <= player.getBalance()) {
                return bet;
            }
            System.out.println("\nInvalid bet.");
        }
    }

    // MODIFIES: this
    // EFFECTS: deal 2 cards each and print hands
    private void deal() {
        dealer.draw(deck.deal());
        player.getHand().draw(deck.deal());
        dealer.draw(deck.deal());
        player.getHand().draw(deck.deal());
        System.out.println("\n" + player.getName() + "'s Hand: " + player.getHand().getHandString());
        System.out.println("Dealer's Hand: " + dealer.getInitialHandString());
    }

    // MODIFIES: this
    // EFFECTS: determine result and proceed with payouts
    private void payout(int bet) {
        if (player.getHand().hasBlackjack() && !dealer.hasBlackjack()) {
            player.addBalance(bet);
        } else if (!player.getHand().hasBlackjack() && dealer.hasBlackjack()) {
            player.subBalance(bet);
        } else if (player.getHand().has5CardCharlie() && !dealer.has5CardCharlie()) {
            player.addBalance(bet);
        } else if (!player.getHand().has5CardCharlie() && dealer.has5CardCharlie()) {
            player.subBalance(bet);
        } else {
            int outcome = compareHandValue();
            if (!player.getHand().isBusted() && (dealer.isBusted() || outcome > 0)) {
                player.addBalance(bet);
            } else if (!dealer.isBusted() && (player.getHand().isBusted()) || outcome < 0) {
                player.subBalance(bet);
            }
        }
    }

    // EFFECTS: return a value greater than 0 if player's hand value is greater than dealer's hand value, 0 if tied,
    //          else a value less than 0
    private int compareHandValue() {
        int playerHandValue = player.getHand().getValue();
        int dealerHandValue = dealer.getValue();
        if (player.getHand().hasAdjustableAce()) {
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
        player.getHand().reset();
    }

    // MODIFIES: this
    // EFFECTS: end if player has blackjack else loop drawing cards until they either double down, stand, bust, or has
    // 5-card Charlie
    private int playerTurn(int bet) {
        if (player.getHand().hasBlackjack()) {
            System.out.println("Blackjack!");
            return bet;
        } else {
            while (true) {
                String decision = askDecision(bet);
                if (decision.equals("h")) {
                    player.getHand().draw(deck.deal());
                    System.out.println("\n" + player.getName() + "'s Hand: " + player.getHand().getHandString());
                    if (checkBustedOr5CardCharlie()) {
                        return bet;
                    }
                } else if (decision.equals("d")) {
                    player.getHand().draw(deck.deal());
                    System.out.println("\n" + player.getName() + "'s Hand: " + player.getHand().getHandString());
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
        if (player.getHand().isBusted()) {
            System.out.println("Busted!");
            return true;
        } else if (player.getHand().has5CardCharlie()) {
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
        System.out.print("\nPress ENTER to continue.");
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
            System.out.print("\nPress ENTER to continue.");
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

    // EFFECTS: saves the workroom to file
    private void savePlayer() {
        try {
            jsonWriter.open();
            jsonWriter.write(player);
            jsonWriter.close();
            System.out.println("Saved " + player.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadPlayer() {
        try {
            player = jsonReader.read();
            System.out.println("Loaded " + player.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
