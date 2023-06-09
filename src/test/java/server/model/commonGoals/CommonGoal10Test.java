package server.model.commonGoals;
import org.junit.jupiter.api.Test;
import common.Tile;
import static org.junit.jupiter.api.Assertions.*;
public class CommonGoal10Test {
    CommonGoal10 commonGoal10 = new CommonGoal10();
    Tile[][] emptyMatrix = new Tile[][] {{Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
            {Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
            {Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
            {Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
            {Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
            {Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
    };
    Tile[][] correctMatrix = new Tile[][]
            {{Tile.PLANT, Tile.GAME,Tile.BOOK,Tile.GAME,Tile.BOOK},
                    {Tile.PLANT, Tile.TROPHY,Tile.PLANT,Tile.TROPHY,Tile.CAT},
                    {Tile.TROPHY, Tile.CAT,Tile.TROPHY,Tile.FRAME,Tile.TROPHY},
                    {Tile.FRAME, Tile.TROPHY,Tile.CAT,Tile.TROPHY,Tile.BOOK},
                    {Tile.TROPHY, Tile.PLANT,Tile.PLANT,Tile.BOOK,Tile.BOOK},
                    {Tile.TROPHY, Tile.FRAME,Tile.BOOK,Tile.CAT,Tile.GAME},
            };

    Tile[][] wrongMatrix = new Tile[][]
            {{Tile.PLANT, Tile.GAME,Tile.BOOK,Tile.GAME,Tile.BOOK},
                    {Tile.PLANT, Tile.TROPHY,Tile.PLANT,Tile.TROPHY,Tile.CAT},
                    {Tile.TROPHY, Tile.CAT,Tile.CAT,Tile.FRAME,Tile.TROPHY},
                    {Tile.FRAME, Tile.TROPHY,Tile.CAT,Tile.TROPHY,Tile.BOOK},
                    {Tile.TROPHY, Tile.PLANT,Tile.PLANT,Tile.BOOK,Tile.BOOK},
                    {Tile.TROPHY, Tile.FRAME,Tile.BOOK,Tile.CAT,Tile.GAME},
            };
    //to be converted into json files

    @Test
    void emptyMatrixTest(){
        boolean wrong = false;
        boolean actual = commonGoal10.isSolved(emptyMatrix);
        assertEquals(wrong,actual);
    }

    @Test
    void correctMatrixTest(){
        boolean correct = true;
        boolean actual = commonGoal10.isSolved(correctMatrix);
        assertEquals(correct,actual);
    }
    @Test
    void wrongMatrixTest(){
        boolean wrong = false;
        boolean actual = commonGoal10.isSolved(wrongMatrix);
        assertEquals(wrong,actual);
    }
}
