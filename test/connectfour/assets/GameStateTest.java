/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connectfour.assets;

import connectfour.utils.GameState;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Juuso
 */
public class GameStateTest {

    public GameStateTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Test
    public void initialStateIsEmpty() {
        GameState state = new GameState();
        assertEquals(0L, state.getState());
    }

    @Test
    public void droppingAColorWorks() {
        GameState state = new GameState();
        state.dropColor(true, 4);
        assertEquals(true, state.getColor(4, 0));
        assertEquals(1, state.getHeightOfColumn(4));
    }

    @Test
    public void droppingColorsWorks() {
        GameState state = new GameState();

        state.dropColor(true, 4);
        state.dropColor(false, 4);
        state.dropColor(true, 3);

        assertEquals(true, state.getColor(4, 0));
        assertEquals(false, state.getColor(4, 1));
        assertEquals(true, state.getColor(3, 0));

        assertEquals(2, state.getHeightOfColumn(4));
        assertEquals(1, state.getHeightOfColumn(3));
    }

    @Test
    public void droppingColorsThrowsErrorsWithBadArguments() {
        GameState state = new GameState();

        try {
            state.dropColor(true, -1);
            fail("dropping to column -1 didn't throw an error");
        } catch (Exception e) {

        }

        try {
            state.dropColor(true, 7);
            fail("dropping to column 7 didn't throw an error");
        } catch (Exception e) {

        }

        state.dropColor(true, 0);
        state.dropColor(true, 6);
        state.dropColor(true, 3);

    }

}
