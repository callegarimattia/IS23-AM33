package java.server.model.commonGoals;
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
        assertFalse(commonGoal10.isSolved(emptyMatrix));
    }

    @Test
    void correctMatrixTest(){
        assertTrue(commonGoal10.isSolved(correctMatrix));
    }
    @Test
    void wrongMatrixTest(){
        assertFalse(commonGoal10.isSolved(wrongMatrix));
    }
}
