package java.server.model.commonGoals;
import org.junit.jupiter.api.Test;
import common.Tile;
import static org.junit.jupiter.api.Assertions.*;

public class CommonGoal7Test {
    CommonGoal7 commonGoal7 = new CommonGoal7();
    Tile[][] emptyMatrix = new Tile[][] {{Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
            {Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
            {Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
            {Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
            {Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
            {Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
    };

    Tile[][] correctMatrix = new Tile[][]
            {{Tile.TROPHY, Tile.TROPHY,Tile.BOOK,Tile.GAME,Tile.BOOK},
            {Tile.TROPHY, Tile.TROPHY,Tile.CAT,Tile.TROPHY,Tile.TROPHY},
            {Tile.CAT, Tile.CAT,Tile.PLANT,Tile.TROPHY,Tile.TROPHY},
            {Tile.CAT, Tile.PLANT,Tile.TROPHY,Tile.PLANT,Tile.CAT},
            {Tile.TROPHY, Tile.TROPHY,Tile.PLANT,Tile.CAT,Tile.BOOK},
            {Tile.GAME, Tile.FRAME,Tile.CAT,Tile.BOOK,Tile.BOOK},
            };

    Tile[][] wrongMatrix = new Tile[][]
            {{Tile.PLANT, Tile.GAME,Tile.BOOK,Tile.GAME,Tile.BOOK},
                    {Tile.FRAME, Tile.FRAME,Tile.PLANT,Tile.TROPHY,Tile.TROPHY},
                    {Tile.CAT, Tile.CAT,Tile.TROPHY,Tile.TROPHY,Tile.TROPHY},
                    {Tile.CAT, Tile.CAT,Tile.TROPHY,Tile.BOOK,Tile.BOOK},
                    {Tile.TROPHY, Tile.TROPHY,Tile.PLANT,Tile.BOOK,Tile.BOOK},
                    {Tile.FRAME, Tile.FRAME,Tile.BOOK,Tile.CAT,Tile.TROPHY},
            };
    static final boolean correct = true;
    @Test
    void emptyMatrixTest(){
        assertFalse(commonGoal7.isSolved(emptyMatrix));
    }

    @Test
    void correctMatrixTest(){
        assertTrue(commonGoal7.isSolved(correctMatrix));
    }
    @Test
    void wrongMatrixTest(){
        assertFalse(commonGoal7.isSolved(wrongMatrix));
    }
}
