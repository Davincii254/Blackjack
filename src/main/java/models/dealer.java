package models;

import java.io.Serializable;

public class dealer implements Serializable {

    private hand hand = new hand();

    // Determines if dealer has a blackjack
    public boolean isBlackjack(){
        if (hand.calculateTotal() == 21){
            return true;
        } else {
            return false;
        }
    }

    // This automates the dealer's play
    public void dealerPlay(deck deck){
        System.out.println();
        while (hand.calculateTotal() <= 16) {
            System.out.println("Dealer has " + hand.calculateTotal()+ " and hits");
            hand.addCard(deck.nextCard());
            System.out.println("Dealer " + this.getHandString(true, false));
        }
        if ( hand.calculateTotal() > 21) {
            System.out.println("Dealer busts. " + this.getHandString(true, false));
        } else {
            System.out.println("Dealer stands. " + this.getHandString(true, false));
        }
    }

    // Adds a card o the dealer's hand
    public void addCard(card card) {
        hand.addCard(card);

    }

    // Gets the dealer's hand as a string
    public String getHandString(boolean isDealer, boolean hideHoleCard ) {
        String str = "Cards:" + hand.toString(isDealer, hideHoleCard);

        return str;
    }

    // Calculates the dealer's hand total
    public int calculateTotal() {
        return hand.calculateTotal();
    }

    // Clears the dealer's hand
    public void clearHand() {
        hand.clearHand();
    }

    // Peeks the dealer's face-down card
    public boolean peek() {
        return hand.dealerPeek();
    }
} //End class