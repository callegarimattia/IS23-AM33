package server.model.commonGoals;
import org.junit.jupiter.api.Test;
import common.Tile;
import static org.junit.jupiter.api.Assertions.*;

public class CommonGoal8Test {
    CommonGoal8 commonGoal8 = new CommonGoal8();
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
            {Tile.CAT, Tile.CAT,Tile.TROPHY,Tile.TROPHY,Tile.TROPHY},
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
            {Tile.TROPHY, Tile.FRAME,Tile.BOOK,Tile.CAT,Tile.CAT},
            };

    @Test
    void emptyMatrixTest(){
        boolean wrong = false;
        boolean actual = commonGoal8.isSolved(emptyMatrix);
        assertEquals(wrong,actual);
    }
    @Test
    void correctMatrixTest(){
        boolean correct = true;
        boolean actual = commonGoal8.isSolved(correctMatrix);
        assertEquals(correct,actual);
    }
    @Test
    void wrongMatrixTest(){
        boolean wrong = false;
        boolean actual = commonGoal8.isSolved(wrongMatrix);
        assertEquals(wrong,actual);
    }


}
