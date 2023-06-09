package server.model.commonGoals;
import org.junit.jupiter.api.Test;
import common.Tile;
import static org.junit.jupiter.api.Assertions.*;


class CommonGoal1Test {
    CommonGoal1 commonGoal1 = new CommonGoal1();

    Tile[][] emptyMatrix = new Tile[][] {{Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
            {Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
            {Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
            {Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
            {Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
            {Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
    };

    Tile[][] correctMatrix = new Tile[][] {{Tile.CAT, Tile.EMPTY,Tile.EMPTY,Tile.TROPHY,Tile.TROPHY},
            {Tile.CAT, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
            {Tile.EMPTY, Tile.EMPTY,Tile.TROPHY,Tile.EMPTY,Tile.EMPTY},
            {Tile.EMPTY, Tile.EMPTY,Tile.TROPHY,Tile.PLANT,Tile.EMPTY},
            {Tile.GAME, Tile.FRAME,Tile.EMPTY,Tile.PLANT,Tile.EMPTY},
            {Tile.GAME, Tile.FRAME,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
    };
    Tile[][] wrongMatrix = new Tile[][]  {{Tile.PLANT, Tile.GAME,Tile.FRAME,Tile.GAME,Tile.BOOK},
            {Tile.BOOK, Tile.FRAME,Tile.PLANT,Tile.FRAME,Tile.TROPHY},
            {Tile.CAT, Tile.CAT,Tile.TROPHY,Tile.BOOK,Tile.TROPHY},
            {Tile.CAT, Tile.CAT,Tile.PLANT,Tile.PLANT,Tile.BOOK},
            {Tile.TROPHY, Tile.CAT,Tile.PLANT,Tile.BOOK,Tile.BOOK},
            {Tile.PLANT, Tile.EMPTY,Tile.BOOK,Tile.CAT,Tile.TROPHY},
    };
    Tile[][] tooManyCatMatrix = new Tile[][] {{Tile.CAT, Tile.EMPTY,Tile.EMPTY,Tile.TROPHY,Tile.TROPHY},
            {Tile.CAT, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
            {Tile.CAT, Tile.EMPTY,Tile.TROPHY,Tile.EMPTY,Tile.EMPTY},
            {Tile.EMPTY, Tile.EMPTY,Tile.TROPHY,Tile.PLANT,Tile.EMPTY},
            {Tile.GAME, Tile.FRAME,Tile.EMPTY,Tile.PLANT,Tile.EMPTY},
            {Tile.GAME, Tile.FRAME,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
    };
    Tile[][] tooManyCouplesMatrix = new Tile[][] {{Tile.CAT, Tile.EMPTY,Tile.EMPTY,Tile.TROPHY,Tile.TROPHY},
            {Tile.CAT, Tile.PLANT,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
            {Tile.EMPTY, Tile.PLANT,Tile.TROPHY,Tile.EMPTY,Tile.EMPTY},
            {Tile.EMPTY, Tile.EMPTY,Tile.TROPHY,Tile.PLANT,Tile.EMPTY},
            {Tile.GAME, Tile.FRAME,Tile.EMPTY,Tile.PLANT,Tile.EMPTY},
            {Tile.GAME, Tile.FRAME,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
    };
//to be converted into json files
   @Test
    void IncorrectMatrixTest(){
        boolean wrong = false;
        boolean actual = commonGoal1.isSolved(wrongMatrix);
        assertEquals(wrong, actual);
    }
    @Test
    void CorrectMatrixTest(){
        boolean correct = true;
        boolean actual = commonGoal1.isSolved(correctMatrix);
        assertEquals(correct, actual);
    }
@Test
    void EmptyMatrixTest(){
        boolean wrong = false;
        boolean actual = commonGoal1.isSolved(emptyMatrix);
        assertEquals(wrong, actual);
    }
    @Test
    void TooManyCatsTest(){
        boolean wrong = false;
        boolean actual = commonGoal1.isSolved(tooManyCatMatrix);
        assertEquals(wrong, actual);
    }
    @Test
    void TooManyCoupleTest(){
        boolean wrong = false;
        boolean actual = commonGoal1.isSolved(tooManyCouplesMatrix);
        assertEquals(wrong, actual);
    }

}