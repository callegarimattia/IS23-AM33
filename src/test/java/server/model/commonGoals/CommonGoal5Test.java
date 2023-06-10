package server.model.commonGoals;
import org.junit.jupiter.api.Test;
import common.Tile;
import static org.junit.jupiter.api.Assertions.*;

public class CommonGoal5Test {
    CommonGoal5 commonGoal5 = new CommonGoal5();
    Tile[][] emptyMatrix = new Tile[][] {{Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
            {Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
            {Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
            {Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
            {Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
            {Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
    };

    Tile[][] correctMatrix = new Tile[][]
            {{Tile.PLANT, Tile.PLANT,Tile.BOOK,Tile.GAME,Tile.BOOK},
                    {Tile.PLANT, Tile.PLANT,Tile.CAT,Tile.TROPHY,Tile.TROPHY},
                    {Tile.CAT, Tile.CAT,Tile.PLANT,Tile.TROPHY,Tile.TROPHY},
                    {Tile.CAT, Tile.CAT,Tile.TROPHY,Tile.PLANT,Tile.CAT},
                    {Tile.TROPHY, Tile.TROPHY,Tile.PLANT,Tile.BOOK,Tile.BOOK},
                    {Tile.GAME, Tile.FRAME,Tile.CAT,Tile.BOOK,Tile.BOOK},
            };

    Tile[][] wrongMatrix = new Tile[][]
            {{Tile.PLANT, Tile.GAME,Tile.BOOK,Tile.GAME,Tile.BOOK},
                    {Tile.FRAME, Tile.FRAME,Tile.PLANT,Tile.FRAME,Tile.TROPHY},
                    {Tile.CAT, Tile.CAT,Tile.TROPHY,Tile.TROPHY,Tile.TROPHY},
                    {Tile.BOOK, Tile.CAT,Tile.TROPHY,Tile.PLANT,Tile.BOOK},
                    {Tile.TROPHY, Tile.TROPHY,Tile.PLANT,Tile.BOOK,Tile.BOOK},
                    {Tile.FRAME, Tile.FRAME,Tile.BOOK,Tile.CAT,Tile.TROPHY},
            };
    static final boolean wrong = false;
    static final boolean correct = true;

    @Test
    void emptyMatrixTest(){
        assertEquals(wrong,commonGoal5.isSolved(emptyMatrix));
    }
    @Test
    void correctMatrixTest(){
        assertEquals(correct,commonGoal5.isSolved(correctMatrix));
    }
    @Test
    void wrongMatrixTest(){
        assertEquals(wrong,commonGoal5.isSolved(wrongMatrix));
    }
}
