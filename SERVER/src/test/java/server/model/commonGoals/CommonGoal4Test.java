package server.model.commonGoals;

import common.Tile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class CommonGoal4Test {
    CommonGoal4 commonGoal4= new CommonGoal4();
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
            {Tile.CAT, Tile.CAT,Tile.TROPHY,Tile.PLANT,Tile.BOOK},
            {Tile.TROPHY, Tile.TROPHY,Tile.PLANT,Tile.BOOK,Tile.BOOK},
            {Tile.TROPHY, Tile.FRAME,Tile.BOOK,Tile.CAT,Tile.TROPHY},
    };
    Tile[][] wrongMatrix = new Tile[][]
            {{Tile.PLANT, Tile.GAME,Tile.BOOK,Tile.CAT,Tile.BOOK},
            {Tile.PLANT, Tile.FRAME,Tile.PLANT,Tile.CAT,Tile.TROPHY},
            {Tile.GAME, Tile.CAT,Tile.TROPHY,Tile.TROPHY,Tile.TROPHY},
            {Tile.CAT, Tile.CAT,Tile.TROPHY,Tile.PLANT,Tile.BOOK},
            {Tile.TROPHY, Tile.TROPHY,Tile.PLANT,Tile.BOOK,Tile.BOOK},
            {Tile.TROPHY, Tile.FRAME,Tile.BOOK,Tile.CAT,Tile.TROPHY},
            };
    //to be converted into json files
    @Test
    void emptyMatrix(){
       assertFalse(commonGoal4.isSolved(emptyMatrix));
    }
    @Test
    void correctMatrix(){
        assertTrue(commonGoal4.isSolved(correctMatrix));
    }
    @Test
    void wrongMatrix(){
        assertFalse(commonGoal4.isSolved(emptyMatrix));
    }
}
