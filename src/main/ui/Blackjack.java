package ui;

import model.*;
import persistence.*;

import java.io.FileNotFoundException;
import java.io.IOException;
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

    // EFFECTS: initializes all fields except player then run
    public Blackjack() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        input = new Scanner(System.in);
        deck = new Deck();
        dealer = new Dealer();
        run();
    }

    // EFFECTS: loops rounds of blackjack
    private void run() {
        introduction();
        start();
        boolean running = menu();
        while (running) {
            player.getHand().setBet(askBet());
            deal();
            if (hasBlackjack()) {
                payout(player.getHand());
            } else if (playerFirstTurn()) {
                dealerTurn();
                payout(player.getHand());
                if (player.getAltHand() != null) {
                    payout(player.getAltHand());
                }
            }
            shuffle();
            running = menu();
        }
    }

    // EFFECTS: returns true if there is a blackjack in the round else false
    private boolean hasBlackjack() {
        if (player.getHand().hasBlackjack()) {
            System.out.println("\n" + player.getName() + "'s Hand: " + player.getHand().getCardsString());
            System.out.println("Blackjack!");
            System.out.println("Dealer's Hand: " + dealer.getInitialHandString());
            if (dealer.getHand().hasBlackjack()) {
                System.out.println("Blackjack!");
            }
            return true;
        }
        return false;
    }

    // EFFECTS: prints introduction
    private void introduction() {
        System.out.println("\nWelcome to Blackjack Simulator!");
        System.out.println("Just like casinos, the dealer will hit until the hand value is 17 or higher.");
        System.out.println("The payout is 1:1.");
        System.out.println("The goal of this game simulator is to bet until you reach the goal you set.");
        System.out.println("Good luck!");
        System.out.print("Press <ENTER> to continue.");
        input.nextLine();
    }

    // MODIFIES: this
    // EFFECTS: runs start sequence
    private void start() {
        boolean running = true;
        while (running) {
            printStartOptions();
            System.out.print(">>> ");
            String selection = input.next().toLowerCase();
            switch (selection) {
                case "n": System.out.println("Enter your name: ");
                    System.out.print(">>> ");
                    String name = input.next();
                    int balance = askInitial();
                    player = new Player(name, balance);
                    running = false;
                    break;
                case "l": running = !loadPlayer();
                    break;
                default:
                    System.out.println("Invalid input.");
            }
        }
    }

    // EFFECTS: print start options
    private void printStartOptions() {
        System.out.println("\nSelect from:");
        System.out.println("\tn -> new player");
        System.out.println("\tl -> load player");
    }

    // EFFECTS: asks for input for initial balance that > 0
    private int askInitial() {
        while (true) {
            System.out.println("Input your initial balance: ");
            try {
                System.out.print(">>> ");
                int amount = input.nextInt();
                if (amount > 0) {
                    return amount;
                }
            } catch (Exception e) {
                input.nextLine();
            }
            System.out.println("Invalid input.");
        }
    }

    // EFFECTS: asks for input for goal balance that > initial
    private int askGoal(int initial) {
        while (true) {
            System.out.println("Input your goal balance: ");
            try {
                System.out.print(">>> ");
                int amount = input.nextInt();
                if (amount > initial) {
                    return amount;
                }
            } catch (Exception e) {
                input.nextLine();
            }
            System.out.println("Invalid input.");
        }
    }

    // EFFECTS: runs menu
    private boolean menu() {
        while (true) {
            if (gameEnd()) {
                return false;
            }
            printSelection();
            System.out.print(">>> ");
            String selection = input.next().toLowerCase();
            switch (selection) {
                case "p": return true;
                case "h": System.out.println("Hand History: " + player.getHandHistoryString());
                    enterToContinue();
                    break;
                case "s": savePlayer();
                    enterToContinue();
                    break;
                case "q": return false;
                default: System.out.println("Invalid input.");
            }
        }
    }

    // EFFECTS: returns true if balance is either <= 0 or > goal which automatically saves else false
    private boolean gameEnd() {
        if (player.getBalance() <= 0) {
            System.out.println("Broke. Game Over!");
            savePlayer();
            return true;
        }
        return false;
    }

    // EFFECTS: print selections
    private void printSelection() {
        System.out.println("\n" + player.getBalanceString());
        System.out.println("Select from:");
        System.out.println("\tp -> play");
        System.out.println("\th -> hand history");
        System.out.println("\ts -> save");
        System.out.println("\tq -> quit");
    }

    // EFFECTS: asks for input for bet that >= player's balance
    private int askBet() {
        while (true) {
            System.out.println("\n" + player.getBalanceString());
            System.out.println("Input bet: ");
            try {
                System.out.print(">>> ");
                int bet = input.nextInt();
                if (bet <= player.getBalance()) {
                    return bet;
                }
            } catch (Exception e) {
                input.nextLine();
            }
            System.out.println("Invalid input.");
        }
    }

    // MODIFIES: this
    // EFFECTS: resets deck and hands
    private void shuffle() {
        deck.shuffle();
        dealer.shuffle();
        player.shuffle();
    }

    // MODIFIES: this
    // EFFECTS: deals 2 cards to each hand
    private void deal() {
        dealer.getHand().addCard(deck.deal());
        player.getHand().addCard(deck.deal());
        dealer.getHand().addCard(deck.deal());
        player.getHand().addCard(deck.deal());
    }

    // MODIFIES: this
    // EFFECTS: ends if dealer has blackjack else loop drawing cards until dealer's hand value is at least 17 or dealer
    // has 5-card Charlie
    private void dealerTurn() {
        enterToContinue();
        System.out.println("\nDealer's Hand: " + dealer.getHand().getCardsString());
        if (dealer.getHand().hasBlackjack()) {
            System.out.println("Blackjack!");
        } else {
            while (dealer.canDraw()) {
                dealer.getHand().addCard(deck.deal());
                System.out.println("Dealer's Hand: " + dealer.getHand().getCardsString());
                if (dealer.getHand().has5CardCharlie() && !dealer.getHand().isBusted()) {
                    System.out.println("5-card charlie!");
                    break;
                }
            }
            if (dealer.getHand().isBusted()) {
                System.out.println("Busted!");
            }
        }
        enterToContinue();
    }

    // MODIFIES: this
    // EFFECTS: determines round outcome and proceeds with payouts for each hand
    private void payout(Hand hand) {
        if (hand.hasBlackjack() && !dealer.getHand().hasBlackjack()) {
            player.addBalance(hand.getBet());
        } else if (!hand.hasBlackjack() && dealer.getHand().hasBlackjack()) {
            player.subBalance(hand.getBet());
        } else if (hand.has5CardCharlie() && !dealer.getHand().has5CardCharlie()) {
            player.addBalance(hand.getBet());
        } else if (!hand.has5CardCharlie() && dealer.getHand().has5CardCharlie()) {
            player.subBalance(hand.getBet());
        } else {
            if (!hand.isBusted() && (dealer.getHand().isBusted() || hand.getCardsValue() > dealer.getHand()
                    .getCardsValue())) {
                player.addBalance(hand.getBet());
            } else if (!dealer.getHand().isBusted() && (hand.isBusted()) || hand.getCardsValue() > dealer.getHand()
                    .getCardsValue()) {
                player.subBalance(hand.getBet());
            }
        }
    }

    // EFFECTS: returns true if player bust or has 5-card Charlie else false
    private boolean checkBustedOr5CardCharlie(Hand hand) {
        if (hand.isBusted()) {
            System.out.println("\n" + player.getName() + "'s Hand: " + hand.getCardsString());
            System.out.println("Busted!");
            return true;
        } else if (hand.has5CardCharlie()) {
            System.out.println("\n" + player.getName() + "'s Hand: " + hand.getCardsString());
            System.out.println("5-card charlie!");
            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: runs first turn of player
    private boolean playerFirstTurn() {
        String decision = askFirstDecision();
        switch (decision) {
            case "h": player.getHand().addCard(deck.deal());
                if (!checkBustedOr5CardCharlie(player.getHand())) {
                    playerRestTurn(player.getHand());
                }
                return true;
            case "s": return true;
            case "d": runDoubleDown();
                return true;
            case "sp": runSplit();
                return true;
            case "su":
                player.getHand().setBet((int) Math.round((double) player.getHand().getBet() / 2));
                player.subBalance(player.getHand().getBet());
            default: return false;
        }
    }

    // EFFECTS: asks for input for first decision that adheres to format and returns it
    private String askFirstDecision() {
        while (true) {
            printFirstDecisions();
            System.out.print(">>> ");
            String decision = input.next().toLowerCase();
            switch (decision) {
                case "h": case "s": case "su": return decision;
                case "d":
                    if (player.getHand().getBet() * 2 <= player.getBalance()) {
                        return decision;
                    }
                    break;
                case "sp":
                    if (player.getHand().canSplit()
                            && player.getHand().getBet() * 2 <= player.getBalance()) {
                        return decision;
                    }
                    break;
            }
            System.out.println("Invalid input.\n");
        }
    }

    // EFFECTS: prints first decisions
    private void printFirstDecisions() {
        System.out.println("\n" + player.getName() + "'s Hand: " + player.getHand().getCardsString());
        System.out.println("Dealer's Hand: " + dealer.getInitialHandString());
        System.out.println("Select decision from:");
        System.out.println("\th -> hit");
        System.out.println("\ts -> stand");
        System.out.println("\td -> double down");
        System.out.println("\tsp -> split");
        System.out.println("\tsu -> surrender");
    }

    // MODIFIES: this
    // EFFECTS: runs double down as first decision
    private void runDoubleDown() {
        player.getHand().setBet(player.getHand().getBet() * 2);
        player.getHand().addCard(deck.deal());
        System.out.println(player.getName() + "'s Hand: " + player.getHand().getCardsString());
        checkBustedOr5CardCharlie(player.getHand());
    }

    // MODIFIES: this
    // EFFECTS: runs split as first decision
    private void runSplit() {
        player.setAltHand(player.getHand().split());
        player.getHand().addCard(deck.deal());
        player.getAltHand().addCard(deck.deal());
        System.out.println(player.getName() + "'s Hand: " + player.getHand().getCardsString());
        System.out.println(player.getName() + "'s Hand: " + player.getAltHand().getCardsString());
        playerRestTurn(player.getHand());
        playerRestTurn(player.getAltHand());
    }

    // MODIFIES: this
    // EFFECTS: runs rest turns of player
    private void playerRestTurn(Hand hand) {
        boolean running = true;
        while (running) {
            String decision = askRestDecision(hand);
            switch (decision) {
                case "h": hand.addCard(deck.deal());
                    if (checkBustedOr5CardCharlie(hand)) {
                        running = false;
                    }
                    break;
                case "d": hand.setBet(hand.getBet() * 2);
                    hand.addCard(deck.deal());
                    checkBustedOr5CardCharlie(hand);
                case "s": running = false;
                    break;
            }
        }
    }

    // EFFECTS: asks for input for rest decision that adheres to format and returns it
    private String askRestDecision(Hand hand) {
        while (true) {
            printRestDecisions(hand);
            System.out.print(">>> ");
            String decision = input.next().toLowerCase();
            switch (decision) {
                case "h": case "s": return decision;
                case "d":
                    if (hand.getBet() * 2 <= player.getBalance()) {
                        return decision;
                    }
            }
            System.out.println("Invalid input.\n");
        }
    }

    // EFFECTS: prints rest decisions
    private void printRestDecisions(Hand hand) {
        System.out.println("\n" + player.getName() + "'s Hand: " + hand.getCardsString());
        System.out.println("Dealer's Hand: " + dealer.getInitialHandString());
        System.out.println("Select decision from:");
        System.out.println("\th -> hit");
        System.out.println("\ts -> stand");
        System.out.println("\td -> double down");
    }

    // EFFECTS: runs "Press <ENTER> to continue." sequence
    private void enterToContinue() {
        System.out.print("Press <ENTER> to continue.");
        input.nextLine();
        input.nextLine();
    }

    // EFFECTS: runs "Press <ENTER> to continue." sequence but 2 input.nextLine();
    private void enterToContinue2() {
        enterToContinue();
        input.nextLine();
    }

    // EFFECTS: saves player to file
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
    // EFFECTS: return true if able to load player from file
    private boolean loadPlayer() {
        try {
            player = jsonReader.read();
            System.out.println("Loaded " + player.getName() + " from " + JSON_STORE);
            return true;
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
            return false;
        }
    }
}
