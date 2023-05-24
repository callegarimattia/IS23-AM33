package server.model.commonGoals;

import org.junit.jupiter.api.Test;
import server.model.Tile;

import static org.junit.jupiter.api.Assertions.*;

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
@Test
    void EmptyMatrixIsSolvedTest(){
        boolean wrong = false;
        boolean actual = commonGoal2.isSolved(emptyMatrix);
        assertEquals(wrong, actual);
    }
@Test
    void FirstDiagonalCorrectTest(){
        boolean correct = true;
        boolean actual = commonGoal2.isSolved(correctMatrixFirstDiagonal);
        assertEquals(correct, actual);
    }
    @Test
    void SecondDiagonalCorrectTest(){
        boolean correct = true;
        boolean actual = commonGoal2.isSolved(correctMatrixSecondDiagonal);
        assertEquals(correct, actual);
    }
    @Test
    void ThirdDiagonalCorrectTest(){
        boolean correct = true;
        boolean actual = commonGoal2.isSolved(correctMatrixThirdDiagonal);
        assertEquals(correct, actual);
    }
    @Test
    void FourthDiagonalCorrectTest(){
        boolean correct = true;
        boolean actual = commonGoal2.isSolved(correctMatrixFourthDiagonal);
        assertEquals(correct, actual);
    }
    @Test
    void IncorrectMatrixTest(){
        boolean wrong = false;
        boolean actual = commonGoal2.isSolved(incorrectMatrix);
        assertEquals(wrong, actual);
    }

}