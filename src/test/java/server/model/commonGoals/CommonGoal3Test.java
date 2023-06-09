package server.model.commonGoals;

import org.junit.jupiter.api.Test;
import common.Tile;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoal3Test {
    CommonGoal3 commonGoal3 = new CommonGoal3();
    Tile[][] emptyMatrix =  new Tile[][]{{Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
            {Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
            {Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
            {Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
            {Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
            {Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
    };

    Tile[][] correctMatrix = new Tile[][]{{Tile.PLANT, Tile.GAME,Tile.FRAME,Tile.GAME,Tile.PLANT},
            {Tile.BOOK, Tile.FRAME,Tile.PLANT,Tile.FRAME,Tile.TROPHY},
            {Tile.CAT, Tile.CAT,Tile.TROPHY,Tile.BOOK,Tile.TROPHY},
            {Tile.CAT, Tile.CAT,Tile.PLANT,Tile.PLANT,Tile.BOOK},
            {Tile.TROPHY, Tile.CAT,Tile.PLANT,Tile.BOOK,Tile.BOOK},
            {Tile.PLANT, Tile.EMPTY,Tile.BOOK,Tile.CAT,Tile.PLANT},
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
        boolean wrong = false;
        boolean actual = commonGoal3.isSolved(emptyMatrix);
        assertEquals(wrong, actual);
    }
    @Test
    void CorrectMatrixIsSolvedTest(){
        boolean correct = true;
        boolean actual = commonGoal3.isSolved(correctMatrix);
        assertEquals(correct, actual);
    }
    @Test
    void WrongMatrixIsSolvedTest(){
        boolean wrong = false;
        boolean actual = commonGoal3.isSolved(incorrectMatrix);
        assertEquals(wrong, actual);
    }

}