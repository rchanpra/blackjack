# Blackjack Simulator

## Introduction

Welcome to Blackjack Simulator, a classical playing card game that is the most widely played casino banking game. The 
objective of the game is to earn profit through gambling by obtaining a blackjack or creating a hand that is higher in 
value than the dealer's hand, but not exceeding 21. The dealer will be an AI that acts exactly the same as a casino 
dealer.

## Blackjack Ruling
- Decisions are hit, stand, and double down.
- First decisions also include split and surrender.
- Splitting can only be done when 2 initial cards dealt have the same rank.
- Blackjack is the strongest, followed by 5-card Charlie, and then normal hand.
- Winnings are 1:1.
- Dealer will hit until their hand value is at least 17 with ace counting as 11 without busting.

## How to Play
1. Bet
2. Inspect cards dealt and make decisions
3. Wait for the dealer's turn
4. Repeat until bored or satisfied

## Targeted Users

The game application is designed for individuals who need a small break and are in the mood for a lighthearted game
session. The game is not too serious but can help lessen the desire to gamble with no harm done through this simulation.

## My Interest in this Project

This project is important to me as a developer because playing card games have always been nostalgic to me. They are the
perfect activities to get closer to friends and family. I was exposed to a lot of playing card games as a kid and as a 
result, I am rather passionate about it. Therefore, this project could also be considered a passion work.

## User Stories

- As a user, I want to be able to have a deck of 52 unique cards.
- As a user, I want to be able to draw cards from the deck and add them to my hand.
- As a user, I want to be able to choose to hit, stand, double-down, split, and surrender as my decision.
- As a user, I want to be able to have a balance that I can deposit and withdraw from.
- As a user, I want to be able to bet against a dealer, win/lose/tie, and have payouts.
- As a user, I want to be able to repeat rounds of blackjack until I am satisfied.
- As a user, when I finish a blackjack round, I want to be able to save my player name, balance, hand to file.
- As a user, when I start the application, I want to be able to start a new game or load previous game from file.

## Citation

- The basis of the persistence module has been adapted from the CPSC210 team's work. You can find their code here:
https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.
- The PNG files of playing cards were taken from an open source archive, which is located at the following URL:
https://code.google.com/archive/p/vector-playing-cards/.
- Event and EventLog along with their tests have been adapted from the CPSC210 team's work at the following URL:
https://github.students.cs.ubc.ca/CPSC210/AlarmSystem.

## Instructions for Grader

- You can generate the first required action related to adding Xs to a Y by...
  - clicking the play button in which cards are added to a hand(s).
- You can generate the second required action related to adding Xs to a Y by...
  - choosing to hit or double-down as your decision in which cards are added to a hand(s).
  - choosing split as your decision when the initial 2 cards dealt are of the same rank will remove a card from hand and 
  add it to an alternative hand, then a card will be dealt to each hand from the deck.
- You can locate my visual component by...
  - looking inside the *cards* folder in the *data* folder
- You can save the state of my application by...
  - clicking the save button in the main menu.
- You can reload the state of my application by...
  - clicking load in the start menu.
- You can create the *blackjack.json* file by saving and exiting the application, after which the file will appear in 
the data folder.
- Additionally, when you clicked the play button, 52 cards are added to a new deck that will be used to play the new 
round of blackjack.

## Phase 4: Task 2

A representative sample of the events that occur when the program runs:
>Wed Apr 12 03:24:16 PDT 2023  
Card added to player's hand: 3D  
Wed Apr 12 03:24:16 PDT 2023  
Card added to dealer's hand: 7S  
Wed Apr 12 03:24:16 PDT 2023  
Card added to player's hand: JS  
Wed Apr 12 03:24:16 PDT 2023  
Card added to dealer's hand: 8S  
Wed Apr 12 03:24:17 PDT 2023  
Card added to player's hand: 8H  
Wed Apr 12 03:24:19 PDT 2023  
Card added to dealer's hand: 7H  
Wed Apr 12 03:24:20 PDT 2023  
All cards removed from player's hand  
Wed Apr 12 03:24:20 PDT 2023  
All cards removed from dealer's hand  

## Phase 4: Task 3

**If you had more time to work on the project, what substantive refactoring might you use to improve your design?**

If I had more time to work on the project, I might improve my design by refactoring the GUI components to reduce the 
number of fields inside the BlackjackGUI class. The refactoring I am imagining is making some of the current JPanel 
fields inside the BlackjackGUI class into JPanel-extended classes. Any JPanel field with CardLayout can be made 
into its own class that contains JPanel fields that are part of its card set, which will help reduce the number of 
fields inside the BlackjackGUI class. Also, most of the other significant JPanel fields with other layouts can also be 
made into its own class that contains JButton, JLabel, JTextField, or other JPanel fields. This will help reduce the 
number of fields,  initialization, and addActionListener calls inside the BlackjackGUI class currently. Since the 
BlackjackGUI class currently has over 500 lines, this will greatly help reduce the number of lines it has.

One example is that the field named `pane`, which is a JPanel object with CardLayout, can be made into its own class 
named Pane that extends JPanel and has the following JPanel objects as fields: `start`, `create`, and `board`. Another 
example is that the field named `create`, which is a JPanel object with GridBadLayout, can also be made into its own 
class named Create that extends JPanel and has the following JTextField and JButton objects as fields: `nameInput`, 
`balanceInput`, and `createButton`. The Create class can also include a call to addActionListener to add an action to 
createButton. These two examples will help reduce the number of lines in the BlackjackGUI class and make it more 
organized. However, since I am utilizing the Swing UI Designer or GUI Form, this is impossible to do unless I use GUI 
Form as a tradeoff. This is not necessarily a bad tradeoff, but it would make it more complicated when adding new 
JPanel objects. It would also require a lot of work and time to make this happen as I would have to put a lot of effort 
into keeping the current look of the GUI console the same even without GUI Form, which would require a lot of more 
method calls.
