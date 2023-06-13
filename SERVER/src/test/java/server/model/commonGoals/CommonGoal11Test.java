package java.server.model.commonGoals;
import org.junit.jupiter.api.Test;
import common.Tile;
import static org.junit.jupiter.api.Assertions.*;


public class CommonGoal11Test {
    CommonGoal11 commonGoal11 = new CommonGoal11();
    Tile[][] emptyMatrix = new Tile[][] {{Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
            {Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
            {Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
            {Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
            {Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
            {Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
    };

    Tile[][] correctMatrix = new Tile[][]
            {{Tile.PLANT, Tile.GAME,Tile.BOOK,Tile.GAME,Tile.BOOK},
                    {Tile.PLANT, Tile.FRAME,Tile.PLANT,Tile.FRAME,Tile.TROPHY},
                    {Tile.FRAME, Tile.CAT,Tile.TROPHY,Tile.TROPHY,Tile.TROPHY},
                    {Tile.FRAME, Tile.CAT,Tile.TROPHY,Tile.PLANT,Tile.BOOK},
                    {Tile.TROPHY, Tile.TROPHY,Tile.PLANT,Tile.BOOK,Tile.BOOK},
                    {Tile.TROPHY, Tile.FRAME,Tile.BOOK,Tile.CAT,Tile.GAME},
            };
    Tile[][] wrongMatrix = new Tile[][]
            {{Tile.PLANT, Tile.GAME,Tile.BOOK,Tile.GAME,Tile.BOOK},
                    {Tile.PLANT, Tile.FRAME,Tile.PLANT,Tile.FRAME,Tile.TROPHY},
                    {Tile.CAT, Tile.CAT,Tile.TROPHY,Tile.TROPHY,Tile.TROPHY},
                    {Tile.FRAME, Tile.CAT,Tile.TROPHY,Tile.PLANT,Tile.BOOK},
                    {Tile.TROPHY, Tile.TROPHY,Tile.PLANT,Tile.BOOK,Tile.BOOK},
                    {Tile.CAT, Tile.FRAME,Tile.BOOK,Tile.CAT,Tile.CAT},
            };

    @Test
    void emptyMatrixTest(){
        assertFalse(commonGoal11.isSolved(emptyMatrix));
    }
    @Test
    void correctMatrixTest(){
        assertTrue(commonGoal11.isSolved(correctMatrix));
    }
    @Test
    void wrongMatrixTest(){
        assertFalse(commonGoal11.isSolved(wrongMatrix));
    }
}
