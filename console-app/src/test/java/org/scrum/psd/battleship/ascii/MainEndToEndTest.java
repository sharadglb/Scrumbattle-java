package org.scrum.psd.battleship.ascii;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import java.util.NoSuchElementException;

import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

public class MainEndToEndTest {
    @ClassRule
    public static final SystemOutRule systemOutRule = new SystemOutRule().enableLog();
    @ClassRule
    public static final TextFromStandardInputStream gameInput = emptyStandardInputStream();

    @Test
    public void testPlayGameShotHits() {
        try {
            gameInput.provideLines("B4", "B5", "B6", "B7", "B8", "E6", "E7", "E8", "E9", "A3", "B2", "C3", "F8", "G8", "H8", "C5", "C6","B4");

            Main.main(new String[]{});
        } catch(NoSuchElementException e) {
            Assert.assertTrue(systemOutRule.getLog().contains("Welcome to Battleship"));
            Assert.assertTrue(systemOutRule.getLog().contains("Yeah ! Nice hit !"));
        }
    }

    @Test
    public void testPlayGameShotMisses() {
        try {
            gameInput.provideLines("a1", "a2", "a3", "a4", "a5", "b1", "b2", "b3", "b4", "c1", "c2", "c3", "d1", "d2", "d3", "e1", "e2", "e4");

            Main.main(new String[]{});
        } catch(NoSuchElementException e) {
            Assert.assertTrue(systemOutRule.getLog().contains("Welcome to Battleship"));
            Assert.assertTrue(systemOutRule.getLog().contains("Miss"));
        }
    }

    @Test
    public void testPlayerWins() {
        try {
            // Provide moves that would result in all enemy ships being sunk
            gameInput.provideLines("B4", "B5", "B6", "B7", "B8", "E6", "E7", "E8", "E9", "A3", "B3", "C3", "F8", "G8", "H8", "C5", "C6");

            Main.main(new String[]{});
        } catch (NoSuchElementException e) {
            Assert.assertTrue(systemOutRule.getLog().contains("Congratulations! You are the winner!"));
        }
    }

    @Test
    public void testPlayerLoses() {
        try {
            // Provide moves that would result in the player's ships being sunk by the computer
            // The computer moves would be deterministic in this case and would sink all player ships
            gameInput.provideLines("a1", "a2", "a3", "a4", "a5", "b1", "b2", "b3", "b4", "c1", "c8", "c3", "d1", "d2", "d3", "e1", "e2");

            Main.main(new String[]{});
        } catch (NoSuchElementException e) {
            Assert.assertTrue(systemOutRule.getLog().contains("You lost! Better luck next time."));
        }
    }
}
