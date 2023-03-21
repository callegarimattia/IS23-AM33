package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class Board4Test {
    Tile[][] EmptyMatrix = { { Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY },
                             { Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY },
                             { Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY },
                             { Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY },
                             { Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY },
                             { Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY },
                             { Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY },
                             { Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY },
                             { Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY },
    };
    @Test
    public void EmptyMatrixInitializer(){
        Tile[][] myBoard = new Tile[9][9];
        for(int x = 0; x<9 ; x++)
            for(int y = 0; y<9; y++)
                myBoard[x][y]= Tile.EMPTY;
        for(int x = 0; x<9 ; x++)
            for(int y = 0; y<9; y++)
                assertEquals(myBoard[x][y], EmptyMatrix[x][y]);
    }

    @Test
    public void WrongEmptyMatrixInitializer(){
        EmptyMatrix[0][5] = Tile.CAT;
        Tile[][] myBoard = new Tile[9][9];
        for(int x = 0; x<9 ; x++)
            for(int y = 0; y<9; y++)
                myBoard[x][y]= Tile.EMPTY;
        assertNotEquals(myBoard[0][5], EmptyMatrix[0][5]);
    }

    @Test
    void refillBoard() {

    }

    @Test
    void getMainBoard() {
    }
}