package server.model.commonGoals;
import org.junit.jupiter.api.Test;
import common.Tile;
import static org.junit.jupiter.api.Assertions.*;


public class CommonGoal6Test {
    CommonGoal6 commonGoal6 = new CommonGoal6();
    Tile[][] emptyMatrix = new Tile[][] {{Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
            {Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
            {Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
            {Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
            {Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
            {Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
    };

    Tile[][] correctMatrix = new Tile[][]
            {{Tile.PLANT, Tile.GAME,Tile.BOOK,Tile.GAME,Tile.BOOK},
                    {Tile.FRAME, Tile.FRAME,Tile.PLANT,Tile.FRAME,Tile.TROPHY},
                    {Tile.CAT, Tile.CAT,Tile.TROPHY,Tile.TROPHY,Tile.TROPHY},
                    {Tile.BOOK, Tile.CAT,Tile.TROPHY,Tile.PLANT,Tile.BOOK},
                    {Tile.TROPHY, Tile.TROPHY,Tile.PLANT,Tile.BOOK,Tile.BOOK},
                    {Tile.GAME, Tile.FRAME,Tile.BOOK,Tile.CAT,Tile.TROPHY},
            };

    Tile[][] wrongMatrix = new Tile[][]
            {{Tile.PLANT, Tile.GAME,Tile.BOOK,Tile.GAME,Tile.BOOK},
            {Tile.FRAME, Tile.FRAME,Tile.PLANT,Tile.FRAME,Tile.TROPHY},
            {Tile.CAT, Tile.CAT,Tile.TROPHY,Tile.TROPHY,Tile.TROPHY},
            {Tile.BOOK, Tile.CAT,Tile.TROPHY,Tile.PLANT,Tile.BOOK},
            {Tile.TROPHY, Tile.TROPHY,Tile.PLANT,Tile.BOOK,Tile.BOOK},
            {Tile.FRAME, Tile.FRAME,Tile.BOOK,Tile.CAT,Tile.TROPHY},
            };
    //to be converted into json files

    @Test
    void emptyMatrixTest(){
        boolean wrong = false;
        boolean actual = commonGoal6.isSolved(emptyMatrix);
        assertEquals(wrong,actual);
    }

    @Test
    void correctMatrixTest(){
        boolean correct =  true;
        boolean actual = commonGoal6.isSolved(correctMatrix);
        assertEquals(correct,actual);
    }
    @Test
    void wrongMatrixTest(){
        boolean wrong = false;
        boolean actual = commonGoal6.isSolved(wrongMatrix);
        assertEquals(wrong,actual);
    }
}
