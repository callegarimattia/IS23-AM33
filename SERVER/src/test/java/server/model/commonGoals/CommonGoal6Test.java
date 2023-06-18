package server.model.commonGoals;

import common.Tile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


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
        assertFalse(commonGoal6.isSolved(emptyMatrix));
    }

    @Test
    void correctMatrixTest(){
        assertTrue(commonGoal6.isSolved(correctMatrix));
    }
    @Test
    void wrongMatrixTest(){
        assertFalse(commonGoal6.isSolved(wrongMatrix));
    }
}
