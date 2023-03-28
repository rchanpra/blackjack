package ui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// Represents a GUI for a game of blackjack
public class BlackjackGUI extends JFrame {
    private Game game;
    private JPanel pane;
    private JPanel start;
    private JPanel create;
    private JPanel board;
    private JPanel blackjack;
    private JPanel menu;
    private JPanel history;
    private JPanel handHistory;
    private JPanel betting;
    private JPanel round;
    private JPanel dealerHand;
    private JPanel playerHand;
    private JPanel decision;
    private JPanel first;
    private JPanel rest;
    private JPanel split;
    private JPanel end;
    private JPanel reveal;
    private JPanel dealer;
    private JPanel endend;
    private JButton newButton;
    private JButton loadButton;
    private JButton createButton;
    private JButton playButton;
    private JButton historyButton;
    private JButton saveButton;
    private JButton exitButton;
    private JButton betButton;
    private JButton hitButton;
    private JButton standButton;
    private JButton doubleDownButton;
    private JButton splitButton;
    private JButton surrenderButton;
    private JButton hitButton1;
    private JButton standButton1;
    private JButton doubleDownButton1;
    private JButton hitButton2;
    private JButton standButton2;
    private JButton doubleDownButton2;
    private JButton backButton;
    private JButton backButton1;
    private JButton nextButton;
    private JButton nextButton1;
    private JTextField nameInput;
    private JTextField initialInput;
    private JTextField goalInput;
    private JTextField betInput;
    private JLabel name;
    private JLabel balance;
    private JLabel initial;
    private JLabel goal;
    private JLabel bet;
    private CardLayout paneCL;
    private CardLayout blackjackCL;
    private CardLayout decisionCL;
    private CardLayout endCL;
    private boolean temp;

    // EFFECTS: initializes game and configures JFrame then sets visible
    public BlackjackGUI() {
        game = new Game();
        setTitle("Blackjack");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);
        setSize(800, 600);
        setMaximumSize(new Dimension(1600, 1200));
        getContentPane().setBackground(new Color(0x013220));
        add(pane);
        setVisible(true);
        setup();
    }

    // MODIFIES: this
    // EFFECTS: setups JFrame
    public void setup() {
        initializeCL();
        setTransparentPanels();
        setStartButtons();
        setMenuButtons();
        setTransitionButtons();
        setTransitionButtons1();
        setFirstButtons();
        setFirstButtons1();
        setRestButtons();
        setSplitButtons();
    }

    // MODIFIES: this
    // EFFECTS: initializes CardLayouts
    public void initializeCL() {
        paneCL = (CardLayout) pane.getLayout();
        blackjackCL = (CardLayout) blackjack.getLayout();
        decisionCL = (CardLayout) decision.getLayout();
        endCL = (CardLayout) end.getLayout();
    }

    // MODIFIES: this
    // EFFECTS: sets all panels to transparent
    public void setTransparentPanels() {
        pane.setOpaque(false);
        start.setOpaque(false);
        create.setOpaque(false);
        board.setOpaque(false);
        blackjack.setOpaque(false);
        menu.setOpaque(false);
        history.setOpaque(false);
        handHistory.setOpaque(false);
        betting.setOpaque(false);
        round.setOpaque(false);
        dealerHand.setOpaque(false);
        playerHand.setOpaque(false);
        decision.setOpaque(false);
        first.setOpaque(false);
        rest.setOpaque(false);
        split.setOpaque(false);
        end.setOpaque(false);
        reveal.setOpaque(false);
        dealer.setOpaque(false);
        endend.setOpaque(false);
    }

    // MODIFIES: this
    // EFFECTS: add ActionListener to button for start
    public void setStartButtons() {
        newButton.addActionListener(e -> {
            paneCL.show(pane, "create");
        });
        loadButton.addActionListener(e -> {
            game.loadPlayer();
            paneCL.show(pane, "board");
            setInfo();
            if (game.gameEnd()) {
                dispose();
            }
        });
        createButton.addActionListener(e -> {
            game.setPlayer(new Player(nameInput.getText(), Integer.parseInt(initialInput.getText()),
                    Integer.parseInt(initialInput.getText()), Integer.parseInt(goalInput.getText())));
            paneCL.show(pane, "board");
            setInfo();
            if (game.gameEnd()) {
                dispose();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: add ActionListener to button for menu
    public void setMenuButtons() {
        playButton.addActionListener(e -> {
            blackjackCL.show(blackjack, "betting");
        });
        historyButton.addActionListener(e -> {
            blackjackCL.show(blackjack, "history");
            addHandHistory();
        });
        saveButton.addActionListener(e -> {
            game.savePlayer();
        });
        exitButton.addActionListener(e -> {
            dispose();
        });
    }

    // MODIFIES: this
    // EFFECTS: add ActionListener to button for transition
    public void setTransitionButtons() {
        betButton.addActionListener(e -> {
            game.getPlayer().getHand().setBet(Integer.parseInt(betInput.getText()));
            betInput.setText("");
            blackjackCL.show(blackjack, "round");
            game.startRound();
            addPlayerHand();
            addDealerHandInitial();
            if (game.getPlayer().getHand().hasBlackjack()) {
                decisionCL.show(decision, "end");
            }
            setInfo();
        });
        backButton.addActionListener(e -> {
            blackjackCL.show(blackjack, "menu");
        });
        backButton1.addActionListener(e -> {
            blackjackCL.show(blackjack, "menu");
            decisionCL.show(decision, "first");
            endCL.show(end, "reveal");
            if (game.gameEnd()) {
                dispose();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: add ActionListener to button for transition
    public void setTransitionButtons1() {
        nextButton.addActionListener(e -> {
            if (game.getPlayer().getAltHand() != null && temp) {
                decisionCL.show(decision, "split");
                temp = false;
            } else {
                addDealerHand();
                endCL.show(end, "dealer");
            }
            setInfo();
        });
        nextButton1.addActionListener(e -> {
            if (game.getDealer().canDraw()) {
                game.getDealer().getHand().draw(game.getDeck());
                addDealerHand();
            } else {
                game.payout(game.getPlayer().getHand());
                if (game.getPlayer().getAltHand() != null) {
                    game.payout(game.getPlayer().getAltHand());
                }
                game.shuffle();
                setInfo();
                endCL.show(end, "endend");
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: add ActionListener to button for first
    public void setFirstButtons() {
        hitButton.addActionListener(e -> {
            game.playerFirstTurn("h");
            if (game.getPlayer().getHand().canDraw()) {
                decisionCL.show(decision, "rest");
            } else {
                decisionCL.show(decision, "end");
            }
            addPlayerHand();
        });
        standButton.addActionListener(e -> {
            game.playerFirstTurn("s");
            decisionCL.show(decision, "end");
        });
        doubleDownButton.addActionListener(e -> {
            if (game.getPlayer().getHand().getBet() * 2 <= game.getPlayer().getBalance()) {
                game.playerFirstTurn("d");
                addPlayerHand();
                decisionCL.show(decision, "end");
                setInfo();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: add ActionListener to button for first
    public void setFirstButtons1() {
        splitButton.addActionListener(e -> {
            if (game.getPlayer().getHand().canSplit()) {
                temp = true;
                game.playerFirstTurn("sp");
                addPlayerHand();
                decisionCL.show(decision, "rest");
                setInfo();
            }
        });
        surrenderButton.addActionListener(e -> {
            game.playerFirstTurn("su");
            decisionCL.show(decision, "end");
            endCL.show(end, "endend");
            setInfo();
        });
    }

    // MODIFIES: this
    // EFFECTS: add ActionListener to button for rest
    public void setRestButtons() {
        hitButton1.addActionListener(e -> {
            game.playerRestTurn(game.getPlayer().getHand(), "h");
            if (game.getPlayer().getHand().canDraw()) {
                decisionCL.show(decision, "rest");
            } else {
                decisionCL.show(decision, "end");
            }
            addPlayerHand();
        });
        standButton1.addActionListener(e -> {
            game.playerRestTurn(game.getPlayer().getHand(), "s");
            decisionCL.show(decision, "end");
        });
        doubleDownButton1.addActionListener(e -> {
            if (game.getPlayer().getHand().getBet() * 2 <= game.getPlayer().getBalance()) {
                game.playerRestTurn(game.getPlayer().getHand(), "d");
                addPlayerHand();
                decisionCL.show(decision, "end");
                setInfo();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: add ActionListener to button for split
    public void setSplitButtons() {
        hitButton2.addActionListener(e -> {
            game.playerRestTurn(game.getPlayer().getAltHand(), "h");
            if (game.getPlayer().getAltHand().canDraw()) {
                decisionCL.show(decision, "split");
            } else {
                decisionCL.show(decision, "end");
            }
            addPlayerHand();
        });
        standButton2.addActionListener(e -> {
            game.playerRestTurn(game.getPlayer().getAltHand(), "s");
            decisionCL.show(decision, "end");
        });
        doubleDownButton2.addActionListener(e -> {
            game.playerRestTurn(game.getPlayer().getAltHand(), "d");
            addPlayerHand();
            decisionCL.show(decision, "end");
            setInfo();
        });
    }

    // EFFECTS: adds/updates player info at the top
    public void setInfo() {
        name.setText("Name: " + game.getPlayer().getName());
        balance.setText("Balance: " + game.getPlayer().getBalance());
        initial.setText("Starting: " + game.getPlayer().getStarting());
        goal.setText("Goal: " + game.getPlayer().getGoal());
        bet.setText("Bet: " + game.getPlayer().getHand().getBet());
    }

    // EFFECTS: returns handPanel created from hand
    public JPanel getHandPanel(Hand hand) {
        JPanel handPanel = new JPanel();
        handPanel.setOpaque(false);
        List<JLabel> cards = new ArrayList<>();
        for (Card c : hand.getCards()) {
            ImageIcon icon = new ImageIcon("./data/cards/" + c.getCard() + ".png");
            JLabel card = new JLabel();
            card.setIcon(new ImageIcon(icon.getImage().getScaledInstance(125, 181, java.awt.Image.SCALE_SMOOTH)));
            cards.add(card);
        }
        for (JLabel l : cards) {
            handPanel.add(l);
        }
        return handPanel;
    }

    // MODIFIES: this
    // EFFECTS: adds handPanel to playerHand
    public void addPlayerHand() {
        playerHand.removeAll();
        playerHand.add(getHandPanel(game.getPlayer().getHand()));
        if (game.getPlayer().getAltHand() != null) {
            playerHand.add(getHandPanel(game.getPlayer().getAltHand()));
        }
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: adds handPanel to dealerHand
    public void addDealerHand() {
        dealerHand.removeAll();
        dealerHand.add(getHandPanel(game.getDealer().getHand()));
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: adds a card labels to dealerHand
    public void addDealerHandInitial() {
        dealerHand.removeAll();
        JLabel card = new JLabel();
        card.setIcon(new ImageIcon(new ImageIcon("./data/cards/" + game.getDealer().getHand().getCards().get(0)
                .getCard() + ".png").getImage().getScaledInstance(125, 181, java.awt.Image.SCALE_SMOOTH)));
        dealerHand.add(card);
        JLabel card1 = new JLabel();
        card1.setIcon(new ImageIcon(new ImageIcon("./data/back.png").getImage()
                .getScaledInstance(125, 181, java.awt.Image.SCALE_SMOOTH)));
        dealerHand.add(card1);
    }

    // MODIFIES: this
    // EFFECTS: adds handPanel(s) to handHistory
    public void addHandHistory() {
        handHistory.removeAll();
        List<JPanel> handPanels = new ArrayList<>();
        for (Hand h : game.getPlayer().getHandHistory()) {
            handPanels.add(getHandPanel(h));
        }
        for (JPanel handPanel : handPanels) {
            handHistory.add(handPanel);
        }
    }
}
