package models;

import java.io.Serializable;
import java.util.Random;
import java.util.Random;
import java.util.Scanner;

public class deck extends Exception implements Serializable {

    private int nextCardIndex;


    card[] deck = new card[52];

    public deck(){

        int count = 0;
        try{
            for (int i = 1; i <= 13; i++) {
                deck[count++] = new card('H', i);
            }
            for (int i = 1; i <= 13; i++) {
                deck[count++] = new card('S', i);
            }
            for (int i = 1; i <= 13; i++) {
                deck[count++] = new card('C', i);
            }
            for (int i = 1; i <= 13; i++) {
                deck[count++] = new card('D', i);
            }
        }

        catch(InvalidCardValueException | InvalidCardSuitException exp1) {

        }
        nextCardIndex = 0;
    }
    private void isIndexGood(int index) throws InvalidDeckPositionException {
        if (index < 0 || index > 51) {
            throw new InvalidDeckPositionException(index);
        }
    }

    public String toString(){

        String str = "";

        for (int i = 0; i < deck.length; i++) {
            str +=	deck[i].toString() + " ";
        }
        return str;
    }


    private void swapCards(int index1, int index2) throws InvalidDeckPositionException {
        card hold;

        isIndexGood(index1);
        isIndexGood(index2);
        hold = deck[index1];
        deck[index1] = deck[index2];
        deck[index2] = hold;
    }

    public void shuffle() throws InvalidDeckPositionException {
        Random rn = new Random();
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < deck.length; j++) {
                swapCards(i, rn.nextInt(52));
            }
        }
        nextCardIndex = 0;
    }

    public card getCard(int index) throws InvalidDeckPositionException{
        isIndexGood(index);
        return deck[index];
    }



    public boolean compareTo(deck otherDeck) throws InvalidDeckPositionException {
        for (int i=0; i < deck.length; i++){
            if (! deck[i].compareTo(otherDeck.getCard(i)) ) {
                return false;
            }
        }
        return true;
    }

    public card nextCard() {

        if (nextCardIndex < 0 || nextCardIndex > 51) {
            System.out.println("Future exception goes here");
        }
        return deck[nextCardIndex++];
    }

} //End class
