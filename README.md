# Blackjack Simulator

## Introduction

Welcome to Blackjack, a classical playing card game that is the most widely played casino banking game. The objective 
of the game is to win obtain the highest balance possible by obtaining a blackjack or creating a hand that is higher in 
value than the dealer's hand, but not exceeding 21. The dealer will be an AI that acts exactly the same as a casino 
dealer, in which the dealer will draw until the dealer's hand is at least 17 in value.

## Blackjack Ruling
- Decisions are hit, stand, and double down.
- Blackjack is the strongest, followed by 5-card Charlie, and then normal hand.
- Winnings are 2 times the bet.

## How to Play
1. Bet
2. Inspect cards dealt and make decisions
3. Wait for the dealer's turn
4. Repeat until broke, reach goal, or satisfy

## Targeted Users

The game application is designed for individuals who need a small break and are in the mood for a lighthearted game
session. The game is not too serious but those who wants to be a more competitive can easily do so by trying to obtain 
the highest balance possible.

## My Interest in this Project

This project is important to me as a developer because playing card games have always been nostalgic to me. They are the
perfect activities to get closer to friends and family. I was exposed to a lot of playing card games as a kid and as a 
result, I am rather passionate about it. Therefore, this project could also be considered a passion work.

## User Stories

- As a user, I want to be able to have a deck of 52 unique cards.
- As a user, I want to be able to draw cards from the deck and add them to my hand.
- As a user, I want to be able to have a balance that I can add to and subtract from.
- As a user, I want to be able to bet against a dealer, win/lose/tie, and have payouts.
- As a user, I want to be able to repeat the rounds of blackjack until I am broke or satisfy.
- As a user, when I finish a blackjack round, I want to be able to save my player name, balance, hand to file.
- As a user, when I start the application, I want to be able to start a new game or load previous game from file.

## Citation

- The basis of the persistence module has been adapted from the CPSC210 team's work. You can find their code here:
https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
- The PNG files of playing cards were taken from an open source archive, which is located at the following URL:
https://code.google.com/archive/p/vector-playing-cards/.

## Future Plans

- Add multiple players
- Add Baccarat and Pok Deng and potentially turn application into Playing Cards Simulator

## Instructions for Grader

- You can generate the first required action related to adding Xs to a Y by...
  - clicking the play button in which cards are added to a hand(s).
- You can generate the second required action related to adding Xs to a Y by...
  - choosing to hit/double-down/split as your decision in which cards are added to a hand(s).
- You can locate my visual component by...
  - running the Main class.
- You can save the state of my application by...
  - clicking the save button in the main menu.
- You can reload the state of my application by...
  - clicking load in the start menu.
- You can create the *blackjack.json* file by saving and exiting the application, after which the file will appear in 
the data folder.
