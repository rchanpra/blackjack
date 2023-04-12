package ui;

import model.*;

import java.util.Scanner;

// Represents a game of blackjack
public class Blackjack {
    private Scanner input;
    private Game game;

    // EFFECTS: initializes all fields except player then run
    public Blackjack() {
        input = new Scanner(System.in);
        game = new Game();
        run();
    }

    // EFFECTS: loops rounds of blackjack
    private void run() {
        introduction();
        start();
        boolean running = menu();
        while (running) {
            game.getPlayer().getHand().setBet(askBet());
            game.deal();
            if (hasBlackjack()) {
                game.payout(game.getPlayer().getHand());
            } else if (playerFirstTurn()) {
                dealerTurn();
                game.payout(game.getPlayer().getHand());
                if (game.getPlayer().getAltHand() != null) {
                    game.payout(game.getPlayer().getAltHand());
                }
            }
            game.shuffle();
            running = menu();
        }
    }

    // EFFECTS: returns true if there is a blackjack in the round else false
    private boolean hasBlackjack() {
        if (game.getPlayer().getHand().hasBlackjack()) {
            System.out.println("\n" + game.getPlayer().getName() + "'s Hand: " + game.getPlayer().getHand()
                    .getCardsString());
            System.out.println("Blackjack!");
            System.out.println("Dealer's Hand: " + game.getDealer().getInitialHandString());
            if (game.getDealer().getHand().hasBlackjack()) {
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
                    game.setPlayer(new Player(name, balance));
                    running = false;
                    break;
                case "l": running = !game.loadPlayer();
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
                case "h": System.out.println("Hand History: " + game.getPlayer().getHandHistoryString());
                    enterToContinue();
                    break;
                case "s": game.savePlayer();
                    enterToContinue();
                    break;
                case "q": return false;
                default: System.out.println("Invalid input.");
            }
        }
    }

    // EFFECTS: returns true if balance is either <= 0 or > goal which automatically saves else false
    private boolean gameEnd() {
        if (game.getPlayer().getBalance() <= 0) {
            System.out.println("Broke. Game Over!");
            game.savePlayer();
            return true;
        }
        return false;
    }

    // EFFECTS: print selections
    private void printSelection() {
        System.out.println("\n" + game.getPlayer().getBalanceString());
        System.out.println("Select from:");
        System.out.println("\tp -> play");
        System.out.println("\th -> hand history");
        System.out.println("\ts -> save");
        System.out.println("\tq -> quit");
    }

    // EFFECTS: asks for input for bet that >= player's balance
    private int askBet() {
        while (true) {
            System.out.println("\n" + game.getPlayer().getBalanceString());
            System.out.println("Input bet: ");
            try {
                System.out.print(">>> ");
                int bet = input.nextInt();
                if (bet <= game.getPlayer().getBalance()) {
                    return bet;
                }
            } catch (Exception e) {
                input.nextLine();
            }
            System.out.println("Invalid input.");
        }
    }

    // MODIFIES: this
    // EFFECTS: ends if dealer has blackjack else loop drawing cards until dealer's hand value is at least 17 or dealer
    // has 5-card Charlie
    private void dealerTurn() {
        enterToContinue();
        System.out.println("\nDealer's Hand: " + game.getDealer().getHand().getCardsString());
        if (game.getDealer().getHand().hasBlackjack()) {
            System.out.println("Blackjack!");
        } else {
            while (game.getDealer().canDraw()) {
                game.getDealer().getHand().addCard(game.getDeck().deal());
                System.out.println("Dealer's Hand: " + game.getDealer().getHand().getCardsString());
                if (game.getDealer().getHand().has5CardCharlie() && !game.getDealer().getHand().isBusted()) {
                    System.out.println("5-card charlie!");
                    break;
                }
            }
            if (game.getDealer().getHand().isBusted()) {
                System.out.println("Busted!");
            }
        }
        enterToContinue();
    }

    // EFFECTS: returns true if player bust or has 5-card Charlie else false
    private boolean checkBustedOr5CardCharlie(Hand hand) {
        if (hand.isBusted()) {
            System.out.println("\n" + game.getPlayer().getName() + "'s Hand: " + hand.getCardsString());
            System.out.println("Busted!");
            return true;
        } else if (hand.has5CardCharlie()) {
            System.out.println("\n" + game.getPlayer().getName() + "'s Hand: " + hand.getCardsString());
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
            case "h": game.getPlayer().getHand().addCard(game.getDeck().deal());
                if (!checkBustedOr5CardCharlie(game.getPlayer().getHand())) {
                    playerRestTurn(game.getPlayer().getHand());
                }
                return true;
            case "s": return true;
            case "d": runDoubleDown();
                return true;
            case "sp": runSplit();
                return true;
            case "su":
                game.getPlayer().getHand().setBet((int) Math.round((double) game.getPlayer().getHand().getBet() / 2));
                game.getPlayer().subBalance(game.getPlayer().getHand().getBet());
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
                    if (game.getPlayer().getHand().getBet() * 2 <= game.getPlayer().getBalance()) {
                        return decision;
                    }
                    break;
                case "sp":
                    if (game.getPlayer().getHand().canSplit()
                            && game.getPlayer().getHand().getBet() * 2 <= game.getPlayer().getBalance()) {
                        return decision;
                    }
                    break;
            }
            System.out.println("Invalid input.\n");
        }
    }

    // EFFECTS: prints first decisions
    private void printFirstDecisions() {
        System.out.println("\n" + game.getPlayer().getName() + "'s Hand: " + game.getPlayer().getHand()
                .getCardsString());
        System.out.println("Dealer's Hand: " + game.getDealer().getInitialHandString());
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
        game.getPlayer().getHand().setBet(game.getPlayer().getHand().getBet() * 2);
        game.getPlayer().getHand().addCard(game.getDeck().deal());
        System.out.println(game.getPlayer().getName() + "'s Hand: " + game.getPlayer().getHand().getCardsString());
        checkBustedOr5CardCharlie(game.getPlayer().getHand());
    }

    // MODIFIES: this
    // EFFECTS: runs split as first decision
    private void runSplit() {
        game.getPlayer().setAltHand(game.getPlayer().getHand().split());
        game.getPlayer().getHand().addCard(game.getDeck().deal());
        game.getPlayer().getAltHand().addCard(game.getDeck().deal());
        System.out.println(game.getPlayer().getName() + "'s Hand: " + game.getPlayer().getHand().getCardsString());
        System.out.println(game.getPlayer().getName() + "'s Hand: " + game.getPlayer().getAltHand().getCardsString());
        playerRestTurn(game.getPlayer().getHand());
        playerRestTurn(game.getPlayer().getAltHand());
    }

    // MODIFIES: this
    // EFFECTS: runs rest turns of player
    private void playerRestTurn(Hand hand) {
        boolean running = true;
        while (running) {
            String decision = askRestDecision(hand);
            switch (decision) {
                case "h": hand.addCard(game.getDeck().deal());
                    if (checkBustedOr5CardCharlie(hand)) {
                        running = false;
                    }
                    break;
                case "d": hand.setBet(hand.getBet() * 2);
                    hand.addCard(game.getDeck().deal());
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
                    if (hand.getBet() * 2 <= game.getPlayer().getBalance()) {
                        return decision;
                    }
            }
            System.out.println("Invalid input.\n");
        }
    }

    // EFFECTS: prints rest decisions
    private void printRestDecisions(Hand hand) {
        System.out.println("\n" + game.getPlayer().getName() + "'s Hand: " + hand.getCardsString());
        System.out.println("Dealer's Hand: " + game.getDealer().getInitialHandString());
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
}
