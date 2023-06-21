package server.model.commonGoals;

import common.Tile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommonGoal2Test {
    CommonGoal2 commonGoal2 = new CommonGoal2();
    Tile[][] emptyMatrix =  new Tile[][]{{Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
            {Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
            {Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
            {Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
            {Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
            {Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
    };

    Tile[][] correctMatrixFirstDiagonal = new Tile[][]{{Tile.PLANT, Tile.GAME,Tile.FRAME,Tile.GAME,Tile.PLANT},
                                                        {Tile.BOOK, Tile.PLANT,Tile.PLANT,Tile.FRAME,Tile.TROPHY},
                                                        {Tile.CAT, Tile.CAT,Tile.PLANT,Tile.BOOK,Tile.TROPHY},
                                                        {Tile.CAT, Tile.CAT,Tile.PLANT,Tile.PLANT,Tile.BOOK},
                                                        {Tile.TROPHY, Tile.CAT,Tile.PLANT,Tile.BOOK,Tile.PLANT},
                                                        {Tile.PLANT, Tile.EMPTY,Tile.BOOK,Tile.CAT,Tile.PLANT},
    };

    Tile[][] correctMatrixSecondDiagonal = new Tile[][]{{Tile.PLANT, Tile.GAME,Tile.FRAME,Tile.GAME,Tile.BOOK},
            {Tile.BOOK, Tile.FRAME,Tile.PLANT,Tile.FRAME,Tile.TROPHY},
            {Tile.CAT, Tile.BOOK,Tile.TROPHY,Tile.BOOK,Tile.TROPHY},
            {Tile.CAT, Tile.CAT,Tile.BOOK,Tile.PLANT,Tile.BOOK},
            {Tile.TROPHY, Tile.CAT,Tile.PLANT,Tile.BOOK,Tile.BOOK},
            {Tile.PLANT, Tile.PLANT,Tile.BOOK,Tile.CAT,Tile.BOOK},
    };

    Tile[][] correctMatrixThirdDiagonal = new Tile[][]{{Tile.PLANT, Tile.GAME,Tile.FRAME,Tile.GAME,Tile.BOOK},
            {Tile.BOOK, Tile.FRAME,Tile.PLANT,Tile.BOOK,Tile.TROPHY},
            {Tile.CAT, Tile.CAT,Tile.BOOK,Tile.BOOK,Tile.TROPHY},
            {Tile.CAT, Tile.BOOK,Tile.PLANT,Tile.PLANT,Tile.BOOK},
            {Tile.BOOK, Tile.CAT,Tile.PLANT,Tile.BOOK,Tile.BOOK},
            {Tile.PLANT, Tile.EMPTY,Tile.BOOK,Tile.CAT,Tile.TROPHY},
    };

    Tile[][] correctMatrixFourthDiagonal = new Tile[][]{{Tile.PLANT, Tile.GAME,Tile.FRAME,Tile.GAME,Tile.BOOK},
            {Tile.BOOK, Tile.FRAME,Tile.PLANT,Tile.FRAME,Tile.TROPHY},
            {Tile.CAT, Tile.CAT,Tile.TROPHY,Tile.TROPHY,Tile.TROPHY},
            {Tile.CAT, Tile.CAT,Tile.TROPHY,Tile.PLANT,Tile.BOOK},
            {Tile.TROPHY, Tile.TROPHY,Tile.PLANT,Tile.BOOK,Tile.BOOK},
            {Tile.TROPHY, Tile.EMPTY,Tile.BOOK,Tile.CAT,Tile.TROPHY},
    };
    Tile[][] incorrectMatrix = new Tile[][]{{Tile.PLANT, Tile.GAME,Tile.FRAME,Tile.GAME,Tile.BOOK},
            {Tile.BOOK, Tile.FRAME,Tile.PLANT,Tile.FRAME,Tile.TROPHY},
            {Tile.CAT, Tile.CAT,Tile.TROPHY,Tile.BOOK,Tile.TROPHY},
            {Tile.CAT, Tile.CAT,Tile.PLANT,Tile.PLANT,Tile.BOOK},
            {Tile.TROPHY, Tile.CAT,Tile.PLANT,Tile.BOOK,Tile.BOOK},
            {Tile.PLANT, Tile.EMPTY,Tile.BOOK,Tile.CAT,Tile.TROPHY},
    };

    //to be converted into json files
@Test
void EmptyMatrixIsSolvedTest(){
    assertFalse(commonGoal2.isSolved(emptyMatrix));
}
@Test
    void FirstDiagonalCorrectTest(){
    assertTrue(commonGoal2.isSolved(correctMatrixFirstDiagonal));
    }
    @Test
    void SecondDiagonalCorrectTest(){
        assertTrue(commonGoal2.isSolved(correctMatrixSecondDiagonal));
    }
    @Test
    void ThirdDiagonalCorrectTest(){
        assertTrue(commonGoal2.isSolved(correctMatrixThirdDiagonal));
    }
    @Test
    void FourthDiagonalCorrectTest(){
        assertTrue(commonGoal2.isSolved(correctMatrixFourthDiagonal));
    }
    @Test
    void IncorrectMatrixTest(){
        assertFalse(commonGoal2.isSolved(incorrectMatrix));
    }

}