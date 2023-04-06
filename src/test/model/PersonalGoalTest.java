package model;

import model.gameLogic.personalGoals.PersonalGoal;
import model.gameLogic.personalGoals.PersonalGoal2;
import model.gameLogic.personalGoals.PersonalGoalException;
import model.gameLogic.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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