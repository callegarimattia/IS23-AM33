package java.server.model.commonGoals;
import org.junit.jupiter.api.Test;
import common.Tile;
import static org.junit.jupiter.api.Assertions.*;
public class CommonGoal12Test {
    CommonGoal12 commonGoal12 = new CommonGoal12();
    Tile[][] emptyMatrix = new Tile[][] {{Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
            {Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
            {Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
            {Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
            {Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
            {Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
    };

    Tile[][] firstDiagonalMatrix = new Tile[][]
            {{Tile.PLANT, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
                    {Tile.PLANT, Tile.FRAME,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
                    {Tile.TROPHY, Tile.CAT,Tile.TROPHY,Tile.EMPTY,Tile.EMPTY},
                    {Tile.FRAME, Tile.CAT,Tile.TROPHY,Tile.PLANT,Tile.EMPTY},
                    {Tile.TROPHY, Tile.TROPHY,Tile.PLANT,Tile.BOOK,Tile.BOOK},
                    {Tile.TROPHY, Tile.FRAME,Tile.BOOK,Tile.CAT,Tile.GAME},
            };
    Tile[][] secondDiagonalMatrix = new Tile[][]
            {{Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
                    {Tile.PLANT, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
                    {Tile.TROPHY, Tile.CAT,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
                    {Tile.FRAME, Tile.CAT,Tile.TROPHY,Tile.EMPTY,Tile.EMPTY},
                    {Tile.TROPHY, Tile.TROPHY,Tile.PLANT,Tile.BOOK,Tile.EMPTY},
                    {Tile.TROPHY, Tile.FRAME,Tile.BOOK,Tile.CAT,Tile.GAME},
            };
    Tile[][] thirdDiagonalMatrix = new Tile[][]
            {{Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.BOOK},
                    {Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.FRAME,Tile.TROPHY},
                    {Tile.EMPTY, Tile.EMPTY,Tile.TROPHY,Tile.TROPHY,Tile.TROPHY},
                    {Tile.EMPTY, Tile.CAT,Tile.TROPHY,Tile.PLANT,Tile.BOOK},
                    {Tile.TROPHY, Tile.TROPHY,Tile.PLANT,Tile.BOOK,Tile.BOOK},
                    {Tile.TROPHY, Tile.FRAME,Tile.BOOK,Tile.CAT,Tile.CAT},
            };
    Tile[][] fourthDiagonalMatrix = new Tile[][]
            {{Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.EMPTY},
                    {Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.EMPTY,Tile.TROPHY},
                    {Tile.EMPTY, Tile.EMPTY,Tile.EMPTY,Tile.TROPHY,Tile.TROPHY},
                    {Tile.EMPTY, Tile.EMPTY,Tile.TROPHY,Tile.PLANT,Tile.BOOK},
                    {Tile.EMPTY, Tile.TROPHY,Tile.PLANT,Tile.BOOK,Tile.BOOK},
                    {Tile.TROPHY, Tile.FRAME,Tile.BOOK,Tile.CAT,Tile.CAT},
            };
    Tile[][] wrongMatrix = new Tile[][]
            {{Tile.PLANT, Tile.GAME,Tile.BOOK,Tile.GAME,Tile.BOOK},
                    {Tile.PLANT, Tile.FRAME,Tile.PLANT,Tile.FRAME,Tile.TROPHY},
                    {Tile.CAT, Tile.CAT,Tile.TROPHY,Tile.TROPHY,Tile.TROPHY},
                    {Tile.FRAME, Tile.CAT,Tile.TROPHY,Tile.PLANT,Tile.BOOK},
                    {Tile.TROPHY, Tile.TROPHY,Tile.PLANT,Tile.BOOK,Tile.BOOK},
                    {Tile.TROPHY, Tile.FRAME,Tile.BOOK,Tile.CAT,Tile.CAT},
            };
    static final boolean wrong = false;
    static final boolean correct = true;
    boolean actual;

    @Test
    void emptyMatrixTest(){
        assertFalse(commonGoal12.isSolved(emptyMatrix));
    }
    @Test
    void correctMatrixesTest(){
        assertEquals(correct,commonGoal12.isSolved(firstDiagonalMatrix));
    }
    @Test
    void secondDiagonalMatrixTest(){assertEquals(correct,commonGoal12.isSolved(secondDiagonalMatrix));}
    @Test
    void thirdDiagonalMatrixesTest(){
        assertEquals(correct,commonGoal12.isSolved(thirdDiagonalMatrix));
    }
    @Test
    void fourthDiagonalMatrixesTest(){
        assertEquals(correct,commonGoal12.isSolved(fourthDiagonalMatrix));
    }

    @Test
    void wrongMatrixTest(){
        assertEquals(wrong,commonGoal12.isSolved(wrongMatrix));

    }}
