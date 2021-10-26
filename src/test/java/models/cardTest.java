package models;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class cardTest {
    card testCard;

    {
        try {
            testCard = new card('H',2);
        } catch (InvalidCardValueException | InvalidCardSuitException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testToString() {
        assertEquals("",testCard.toString());
    }

    @Test
    void getSuitName() {
        assertEquals("Hearts",testCard.getSuitName());
    }

    @Test
    void getSuitDesignator() {
        assertEquals("suit",testCard.getSuitDesignator());
    }

    @Test
    void getValueName() {

    }

    @Test
    void getValue() {
        assertEquals(2,testCard.getValue());
    }

    @Test
    void compareSuit() {
    }

    @Test
    void compareValue() {
    }

    @Test
    void compareTo() {
    }
}