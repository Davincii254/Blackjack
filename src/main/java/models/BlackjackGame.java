package models;
import java.util.Scanner;


public class BlackjackGame {


    private Scanner ki = new Scanner(System.in);
    private int users;
    private player[] players;
    private deck deck;
    private dealer dealer = new dealer();

    // Starts game and displays the rules
    public void initializeGame(){
        String names;
        System.out.println("Welcome to Blackjack!");
        System.out.println("");
        System.out.println("  BLACKJACK RULES: ");
        System.out.println("	-Each player is dealt 2 cards. The dealer is dealt 2 cards with one face-up and one face-down.");
        System.out.println("	-Cards are equal to their value with face cards being 10 and an Ace being 1 or 11.");
        System.out.println("	-The players cards are added up for their total.");
        System.out.println("	-Players “Hit” to gain another card from the deck. Players “Stay” to keep their current card total.");
        System.out.println("	-Dealer “Hits” until they equal or exceed 17.");
        System.out.println("	-The goal is to have a higher card total than the dealer without going over 21.");
        System.out.println("	-If the player total equals the dealer total, it is a “Push” and the hand ends.");
        System.out.println("	-Players win their bet if they beat the dealer. Players win 1.5x their bet if they get “Blackjack” which is 21.");
        System.out.println("");
        System.out.println("");

        // Gets the amount of players and creates them
        do {
            System.out.print("How many people are playing (1-6)? ");
            users = ki.nextInt();


        } while (users > 6 || users < 0);

        players = new player[users];
        deck = new deck();

        // Asks for player names and assigns them
        for (int i = 0; i < users; i++) {
            System.out.print("What is player " + (i + 1) + "'s name? ");
            names = ki.next();
            players[i] = new player();
            players[i].setName(names);
        }
    }

    // Shuffles the deck
    public void shuffle() throws InvalidDeckPositionException, InvalidCardSuitException, InvalidCardValueException {
        deck.shuffle();

    }

    // Gets the bets from the players
    public void getBets(){
        int betValue;

        for (int i =0; i < users; i++) {
            if (players[i].getBank() > 0) {
                do {
                    System.out.print("How much do you want to bet " + players[i].getName()  + (" (1-" + players[i].getBank()) + ")? " );
                    betValue = ki.nextInt();
                    players[i].setBet(betValue);
                } while (!( betValue > 0 && betValue <= players[i].getBank()));
                System.out.println("");
            }

        }

    }

    // Deals the cards to the players and dealer
    public void dealCards(){
        for (int j = 0; j < 2; j++) {
            for (int i =0; i < users; i++) {
                if(players[i].getBank() > 0)
                {
                    players[i].addCard(deck.nextCard());
                }
            }

            dealer.addCard(deck.nextCard());
        }
    }

    // Initial check for dealer or player Blackjack
    public void checkBlackjack(){
        //System.out.println();

        if (dealer.isBlackjack() ) {
            System.out.println("Dealer has BlackJack!");
            for (int i =0; i < users; i++) {
                if (players[i].getTotal() == 21 ) {
                    System.out.println(players[i].getName() + " pushes");
                    players[i].push();
                } else {
                    System.out.println(players[i].getName() + " loses");
                    players[i].bust();
                }
            }
        } else {
            if (dealer.peek() ) {
                System.out.println("Dealer peeks and does not have a BlackJack");
            }

            for (int i =0; i < users; i++) {
                if (players[i].getTotal() == 21 ) {
                    System.out.println(players[i].getName() + " has blackjack!");
                    players[i].blackjack();
                }
            }
        }
    }

    // This code takes the user commands to hit or stand
    public void hitOrStand() {
        String command;
        char c;
        for (int i = 0; i < users; i++) {
            if ( players[i].getBet() > 0 ) {
                System.out.println();
                System.out.println(players[i].getName() + " has " + players[i].getHandString());

                do {
                    do {
                        System.out.print(players[i].getName() + " (H)it or (S)tand? ");
                        command = ki.next();
                        c = command.toUpperCase().charAt(0);
                    } while ( ! ( c == 'H' || c == 'S' ) );
                    if ( c == 'H' ) {
                        players[i].addCard(deck.nextCard());
                        System.out.println(players[i].getName() + " has " + players[i].getHandString());
                    }
                } while (c != 'S' && players[i].getTotal() <= 21 );
            }
        }
    }

    // Code for the dealer to play
    public void dealerPlays() {
        boolean isSomePlayerStillInTheGame = false;
        for (int i =0; i < users && isSomePlayerStillInTheGame == false; i++){
            if (players[i].getBet() > 0 && players[i].getTotal() <= 21 ) {
                isSomePlayerStillInTheGame = true;
            }
        }
        if (isSomePlayerStillInTheGame) {
            dealer.dealerPlay(deck);
        }
    }

    // This code calculates all possible outcomes and adds or removes the player bets
    public void settleBets() {
        System.out.println();

        for (int i = 0; i < users; i++) {
            if (players[i].getBet() > 0 ) {
                if( players[i].getTotal() > 21 ) {
                    System.out.println(players[i].getName() + " has busted");
                    players[i].bust();
                } else if ( players[i].getTotal() == dealer.calculateTotal() ) {
                    System.out.println(players[i].getName() + " has pushed");
                    players[i].push();
                } else if ( players[i].getTotal() < dealer.calculateTotal() && dealer.calculateTotal() <= 21 ) {
                    System.out.println(players[i].getName() + " has lost");
                    players[i].loss();
                } else if (players[i].getTotal() == 21) {
                    System.out.println(players[i].getName() + " has won with blackjack!");
                    players[i].blackjack();
                } else {
                    System.out.println(players[i].getName() + " has won");
                    players[i].win();

                }
            }
        }

    }

    // This prints the players hands
    public void printStatus() {
        for (int i = 0; i < users; i++) {
            if(players[i].getBank() > 0)
            {
                System.out.println(players[i].getName() + " has " + players[i].getHandString());
            }
        }
        System.out.println("Dealer has " + dealer.getHandString(true, true));
    }

    // This prints the players banks and tells the player if s/he is out of the game
    public void printMoney() {
        for (int i = 0; i < users; i++) {
            if(players[i].getBank() > 0)
            {
                System.out.println(players[i].getName() + " has " + players[i].getBank());
            }
            if(players[i].getBank() == 0)
            {
                System.out.println(players[i].getName() + " has " + players[i].getBank() + " and is out of the game.");
                players[i].removeFromGame();
            }
        }
    }

    // This code resets all hands
    public void clearHands() {
        for (int i = 0; i < users; i++) {
            players[i].clearHand();
        }
        dealer.clearHand();

    }

    // This decides to force the game to end when all players lose or lets players choose to keep playing or not
    public boolean playAgain() {
        String command;
        char c;
        Boolean playState = true;
        if(forceEnd()) {
            playState = false;
        }
        else {
            do {
                System.out.println("");
                System.out.print("Do you want to play again (Y)es or (N)o? ");
                command = ki.next();
                c = command.toUpperCase().charAt(0);
            } while ( ! ( c == 'Y' || c == 'N' ) );
            if(c == 'N')
            {
                playState = false;
            }
        }
        return playState;
    }

    // This says true or false to forcing the game to end
    public boolean forceEnd() {
        boolean end = false;
        int endCount = 0;

        for (int i = 0; i < users; i++) {
            if(players[i].getBank() == -1)
            {
                endCount++;
            }
        }
        if(endCount == users)
        {
            end = true;
        }
        if(end)
        {
            System.out.println("");
            System.out.println("All players have lost and the game ends.");
        }

        return end;
    }

    // This is the endgame code for when all players are out of the game or players decide to stop playing
    public void endGame() {
        int endAmount;
        String endState = " no change.";
        System.out.println("");
        for (int i = 0; i < users; i++) {
            if(players[i].getBank() == -1)
            {
                players[i].resetBank();
            }
            endAmount = players[i].getBank() - 100;
            if(endAmount > 0)
            {
                endState = " gain of ";
            }
            else if(endAmount < 0)
            {
                endState = " loss of ";
            }
            System.out.println(players[i].getName() + " has ended the game with " + players[i].getBank() + ".");
            if(endState != " no change.")
            {
                System.out.println("A" + endState + Math.abs(endAmount) + ".");
            }
            else
            {
                System.out.println("No change from their starting value.");
            }
            System.out.println("");
        }
        System.out.println("");
        System.out.println("");
        System.out.println("Thank you for playing!");
    }


} //End class