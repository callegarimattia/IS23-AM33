package model.gameLogic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BoardTest {
    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4})
    @DisplayName("Mainboard initialisation test")
    void initializeBoard(int numOfPlayers) {
        Board testBoard = new Board(numOfPlayers);
        Tile[][] mainBoard = testBoard.getMainBoard();
        if (numOfPlayers == 4) assertTrue(true);
        if (numOfPlayers == 3) assertTrue(mainBoard[3][1] == Tile.UNAVAILABLE &&
                mainBoard[4][0] == Tile.UNAVAILABLE &&
                mainBoard[7][3] == Tile.UNAVAILABLE &&
                mainBoard[8][4] == Tile.UNAVAILABLE &&
                mainBoard[0][4] == Tile.UNAVAILABLE &&
                mainBoard[1][5] == Tile.UNAVAILABLE &&
                mainBoard[4][8] == Tile.UNAVAILABLE &&
                mainBoard[5][7] == Tile.UNAVAILABLE);
        if (numOfPlayers == 2) assertTrue(mainBoard[0][3] == Tile.UNAVAILABLE &&
                mainBoard[2][2] == Tile.UNAVAILABLE &&
                mainBoard[2][6] == Tile.UNAVAILABLE &&
                mainBoard[3][8] == Tile.UNAVAILABLE &&
                mainBoard[5][0] == Tile.UNAVAILABLE &&
                mainBoard[6][2] == Tile.UNAVAILABLE &&
                mainBoard[6][6] == Tile.UNAVAILABLE &&
                mainBoard[8][5] == Tile.UNAVAILABLE);
    }
}