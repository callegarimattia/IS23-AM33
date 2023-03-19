package model.PersonalGoals;

import model.Tile;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PersonalGoalTest {

    PersonalGoal myGoal;

    @BeforeEach
    void setUp() {
        myGoal = new PersonalGoal2();
    }

    @Test
    @DisplayName("Personal Goal Collected Tiles Counter Test")
    void CountRightsTester() throws PersonalGoalException {
        Tile[][] goalMatrix = { { Tile.EMPTY, Tile.EMPTY, Tile.TROPHY, Tile.EMPTY, Tile.CAT },
                { Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY },
                { Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.BOOK, Tile.EMPTY },
                { Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY },
                { Tile.EMPTY, Tile.GAME, Tile.EMPTY, Tile.FRAME, Tile.EMPTY },
                { Tile.PLANT, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY },
        };
        assertEquals(6, myGoal.countRights(goalMatrix));

    }

    @Test
    @DisplayName("Personal Goal Points Counter Test")
    void TestCalcPointsTester() {
        assertEquals(9, myGoal.calcPoints(5));
    }



}