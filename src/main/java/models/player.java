package models;

import java.io.Serializable;

public class player implements Serializable {

    private int bank;
    private int bet;
    private String name;
    private hand hand;

    // Creates a player object
    public player() {
        bank = 100;
        hand = new hand();

    }

    // Gets a player's bank amount
    public int getBank() {
        return bank;
    }

    // Removes a player's bet from their bank if they bust. Sets bet to zero afterwards.
    public void bust() {
        bank -= bet;
        bet = 0;
    }

    // Adds a player's bet from their bank if they win. Sets bet to zero afterwards.
    public void win() {
        bank += bet;
        bet = 0;
    }

    // Removes a player's bet from their bank if they lose. Sets bet to zero afterwards.
    public void loss() {
        bank -= bet;
        bet = 0;
    }

    // This sets the player bank to -1. -1 is unreachable and they are removed from the game
    public void removeFromGame() {
        bank = -1;
    }

    // This resets the bank to 0. Currently used to reset a removed player's bank from -1 to 0.
    public void resetBank() {
        bank = 0;
    }

    // This calculate the bet for a player who has a Blackjack
    public void blackjack() {
        bank += bet * 1.5;
        bet = 0;
    }

    // Sets a player's bet to 0 if the "push". Notice, no bet is added or removed.
    public void push() {
        bet = 0;
    }

    // Sets a player's bet
    public void setBet(int newBet) {
        bet = newBet;
    }

    // Sets a player's name
    public void setName(String name1){
        name = name1;
    }

    // Gets a player's name
    public String getName() {
        return name;
    }

    // Gets a player's hand total
    public int getTotal() {
        return hand.calculateTotal();
    }

    // Gets a player's bet
    public int getBet(){
        return this.bet;
    }

    // Adds a card to a player's hand
    public void addCard(card card) {
        hand.addCard(card);

    }

    // Gets the player's cards to print as a string
    public String getHandString() {
        String str = "Cards:" + hand.toString();

        return str;
    }

    // Clears a player's hand
    public void clearHand() {
        hand.clearHand();
    }

} //End class