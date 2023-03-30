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
    private JPanel deposit;
    private JPanel withdraw;
    private JPanel handHistoryPanel;
    private JPanel bet;
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
    private JPanel conclusion;
    private JButton newButton;
    private JButton loadButton;
    private JButton createButton;
    private JButton playButton;
    private JButton depositButton;
    private JButton withdrawButton;
    private JButton historyButton;
    private JButton saveButton;
    private JButton exitButton;
    private JButton depositButton1;
    private JButton withdrawButton1;
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
    private JTextField balanceInput;
    private JTextField betInput;
    private JTextField depositInput;
    private JTextField withdrawInput;
    private JLabel nameLabel;
    private JLabel balanceLabel;
    private JLabel betLabel;
    private JLabel titleLabel;
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
        getContentPane().setBackground(new Color(0x013220));
        add(pane);
        setVisible(true);
        setup();
    }

    // MODIFIES: this
    // EFFECTS: setups JFrame
    public void setup() {
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 32));
        initializeCL();
        setTransparentPanels();
        setStartButtons();
        setMenuButtons();
        setSubMenuButtons();
        setTransitionButtons();
        setNextButtons();
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
        deposit.setOpaque(false);
        withdraw.setOpaque(false);
        handHistoryPanel.setOpaque(false);
        bet.setOpaque(false);
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
        conclusion.setOpaque(false);
    }

    // MODIFIES: this
    // EFFECTS: adds ActionListener to buttons for start panel
    public void setStartButtons() {
        newButton.addActionListener(e -> {
            paneCL.show(pane, "create");
        });
        loadButton.addActionListener(e -> {
            game.loadPlayer();
            paneCL.show(pane, "board");
            updateStatus();
        });
        createButton.addActionListener(e -> {
            game.setPlayer(new Player(nameInput.getText(), Integer.parseInt(balanceInput.getText())));
            paneCL.show(pane, "board");
            updateStatus();
        });
    }

    // MODIFIES: this
    // EFFECTS: adds ActionListener to buttons in menu panel
    public void setMenuButtons() {
        playButton.addActionListener(e -> {
            if (game.getPlayer().getBalance() > 0) {
                blackjackCL.show(blackjack, "bet");
            }
        });
        depositButton.addActionListener(e -> {
            blackjackCL.show(blackjack, "deposit");
        });
        withdrawButton.addActionListener(e -> {
            blackjackCL.show(blackjack, "withdraw");
        });
        historyButton.addActionListener(e -> {
            blackjackCL.show(blackjack, "history");
            setHandHistoryPanel();
        });
        saveButton.addActionListener(e -> {
            game.savePlayer();
        });
        exitButton.addActionListener(e -> {
            dispose();
        });
    }

    // MODIFIES: this
    // EFFECTS: adds ActionListener to buttons in submenu panels
    public void setSubMenuButtons() {
        depositButton1.addActionListener(e -> {
            game.getPlayer().addBalance(Integer.parseInt(depositInput.getText()));
            depositInput.setText("");
            updateStatus();
            blackjackCL.show(blackjack, "menu");
        });
        withdrawButton1.addActionListener(e -> {
            game.getPlayer().subBalance(Integer.parseInt(withdrawInput.getText()));
            depositInput.setText("");
            updateStatus();
            blackjackCL.show(blackjack, "menu");
        });
        backButton.addActionListener(e -> {
            blackjackCL.show(blackjack, "menu");
        });
    }

    // MODIFIES: this
    // EFFECTS: adds ActionListener to transition buttons
    public void setTransitionButtons() {
        betButton.addActionListener(e -> {
            if (game.getPlayer().getBalance() >= Integer.parseInt(betInput.getText())) {
                game.getPlayer().getHand().setBet(Integer.parseInt(betInput.getText()));
                betInput.setText("");
                blackjackCL.show(blackjack, "round");
                game.startRound();
                addPlayerHand();
                addDealerHandInitial();
                if (game.getPlayer().getHand().hasBlackjack()) {
                    decisionCL.show(decision, "end");
                }
                updateStatus();
            }
        });
        backButton1.addActionListener(e -> {
            blackjackCL.show(blackjack, "menu");
            decisionCL.show(decision, "first");
            endCL.show(end, "reveal");
        });
    }

    // MODIFIES: this
    // EFFECTS: adds ActionListener to next buttons
    public void setNextButtons() {
        nextButton.addActionListener(e -> {
            if (game.getPlayer().getAltHand() != null && temp) {
                decisionCL.show(decision, "split");
                temp = false;
            } else {
                addDealerHand();
                endCL.show(end, "dealer");
            }
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
                updateStatus();
                endCL.show(end, "conclusion");
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: adds ActionListener to buttons in first panel
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
                updateStatus();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: adds ActionListener to buttons in first panel
    public void setFirstButtons1() {
        splitButton.addActionListener(e -> {
            if (game.getPlayer().getHand().canSplit()) {
                temp = true;
                game.playerFirstTurn("sp");
                addPlayerHand();
                decisionCL.show(decision, "rest");
                updateStatus();
            }
        });
        surrenderButton.addActionListener(e -> {
            game.playerFirstTurn("su");
            decisionCL.show(decision, "end");
            endCL.show(end, "conclusion");
            game.shuffle();
            updateStatus();
        });
    }

    // MODIFIES: this
    // EFFECTS: adds ActionListener to buttons in rest panel
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
                updateStatus();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: adds ActionListener to buttons in split panel
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
            updateStatus();
        });
    }

    // EFFECTS: updates status panel
    public void updateStatus() {
        nameLabel.setText("Name: " + game.getPlayer().getName());
        balanceLabel.setText("Balance: " + game.getPlayer().getBalance());
        betLabel.setText("Bet: " + game.getPlayer().getHand().getBet());
    }

    // EFFECTS: returns handPanel created from hand
    public JPanel getHandPanel(Hand hand) {
        JPanel handPanel = new JPanel();
        handPanel.setLayout(new BorderLayout());
        handPanel.setOpaque(false);
        JLabel valueLabel = new JLabel();
        valueLabel.setForeground(new Color(0xFFFFFF));
        valueLabel.setText(Integer.toString(hand.getCardsValue()));
        JPanel valuePanel = new JPanel();
        valuePanel.setOpaque(false);
        valuePanel.add(valueLabel);
        handPanel.add(valuePanel, BorderLayout.NORTH);
        handPanel.add(getCardsPanel(hand), BorderLayout.CENTER);
        return handPanel;
    }

    // EFFECTS: returns cardsPanel created from hand
    public JPanel getCardsPanel(Hand hand) {
        JPanel cardsPanel = new JPanel();
        cardsPanel.setOpaque(false);
        List<JLabel> cardLabels = new ArrayList<>();
        for (Card card : hand.getCards()) {
            cardLabels.add(getCardLabel(card, 125, 181));
        }
        for (JLabel l : cardLabels) {
            cardsPanel.add(l);
        }
        return cardsPanel;
    }

    // EFFECTS: returns cardLabel created from card
    public JLabel getCardLabel(Card card, int width, int height) {
        ImageIcon icon = new ImageIcon("./data/cards/" + card.getCard() + ".png");
        JLabel cardLabel = new JLabel();
        cardLabel.setIcon(new ImageIcon(icon.getImage()
                .getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH)));
        return cardLabel;
    }

    // MODIFIES: this
    // EFFECTS: adds handPanel to playerHand panel
    public void addPlayerHand() {
        playerHand.removeAll();
        playerHand.add(getHandPanel(game.getPlayer().getHand()));
        if (game.getPlayer().getAltHand() != null) {
            playerHand.add(getHandPanel(game.getPlayer().getAltHand()));
        }
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: adds handPanel to dealerHand panel
    public void addDealerHand() {
        dealerHand.removeAll();
        dealerHand.add(getHandPanel(game.getDealer().getHand()));
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: adds handPanel to dealerHand panel initially
    public void addDealerHandInitial() {
        dealerHand.removeAll();
        dealerHand.add(getCardLabel(game.getDealer().getHand().getCards().get(0), 125, 181));
        JLabel card = new JLabel();
        card.setIcon(new ImageIcon(new ImageIcon("./data/cards/XX.png").getImage()
                .getScaledInstance(125, 181, java.awt.Image.SCALE_SMOOTH)));
        dealerHand.add(card);
    }

    // MODIFIES: this
    // EFFECTS: adds handPanel(s) to handHistory
    public void setHandHistoryPanel() {
        handHistoryPanel.removeAll();
        List<JPanel> handPanels = new ArrayList<>();
        for (Hand hand : game.getPlayer().getHandHistory()) {
            handPanels.add(getHandPanel1(hand));
        }
        for (JPanel handPanel : handPanels) {
            handHistoryPanel.add(handPanel);
        }
        setVisible(true);
    }

    // EFFECTS: returns handPanel created from hand for hand history
    public JPanel getHandPanel1(Hand hand) {
        JPanel handPanel = new JPanel();
        handPanel.setLayout(new BorderLayout());
        handPanel.setOpaque(false);
        JLabel valueLabel = new JLabel();
        valueLabel.setForeground(new Color(0xFFFFFF));
        valueLabel.setText("Bet: " + hand.getBet() + " | Value: " + hand.getCardsValue());
        JPanel valuePanel = new JPanel();
        valuePanel.setOpaque(false);
        valuePanel.add(valueLabel);
        handPanel.add(valuePanel, BorderLayout.NORTH);
        handPanel.add(getCardsPanel1(hand), BorderLayout.CENTER);
        return handPanel;
    }

    // EFFECTS: returns cardsPanel created from hand for hand history
    public JPanel getCardsPanel1(Hand hand) {
        JPanel cardsPanel = new JPanel();
        cardsPanel.setOpaque(false);
        List<JLabel> cardLabels = new ArrayList<>();
        for (Card card : hand.getCards()) {
            cardLabels.add(getCardLabel(card, 62, 90));
        }
        for (JLabel l : cardLabels) {
            cardsPanel.add(l);
        }
        return cardsPanel;
    }
}
