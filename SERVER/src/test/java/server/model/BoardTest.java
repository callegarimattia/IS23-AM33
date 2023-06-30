package server.model;

import common.MainBoardCoordinates;
import common.Tile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import server.exceptions.NotPickableException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4})
    @DisplayName("Main board initialisation test")
    void initializeBoard(int numOfPlayers) {
        Board testBoard = new Board(numOfPlayers, null);
        Tile[][] mainBoard = testBoard.getMainBoard();
        if (numOfPlayers == 4) assertTrue(true); //da fare :C
        if (numOfPlayers == 3) assertTrue(
                mainBoard[3][1] == Tile.UNAVAILABLE &&
                        mainBoard[4][0] == Tile.UNAVAILABLE &&
                        mainBoard[7][3] == Tile.UNAVAILABLE &&
                        mainBoard[8][4] == Tile.UNAVAILABLE &&
                        mainBoard[0][4] == Tile.UNAVAILABLE &&
                        mainBoard[1][5] == Tile.UNAVAILABLE &&
                        mainBoard[4][8] == Tile.UNAVAILABLE &&
                        mainBoard[5][7] == Tile.UNAVAILABLE);
        if (numOfPlayers == 2) assertTrue(
                mainBoard[0][3] == Tile.UNAVAILABLE &&
                        mainBoard[2][2] == Tile.UNAVAILABLE &&
                        mainBoard[2][6] == Tile.UNAVAILABLE &&
                        mainBoard[3][8] == Tile.UNAVAILABLE &&
                        mainBoard[5][0] == Tile.UNAVAILABLE &&
                        mainBoard[6][2] == Tile.UNAVAILABLE &&
                        mainBoard[6][6] == Tile.UNAVAILABLE &&
                        mainBoard[8][5] == Tile.UNAVAILABLE);
    }



    @Test
    @DisplayName("Remove Tiles Test")
    void removeTilesTester() throws Exception {
        Board board = new Board(4,null);
        MainBoardCoordinates coordinates00 = new MainBoardCoordinates(0,0);
        MainBoardCoordinates coordinates08 = new MainBoardCoordinates(0,8);
        MainBoardCoordinates coordinates80 = new MainBoardCoordinates(8,0);
        MainBoardCoordinates coordinates88 = new MainBoardCoordinates(8,8);
        MainBoardCoordinates coordinates21 = new MainBoardCoordinates(2,1);
        MainBoardCoordinates coordinates55 = new MainBoardCoordinates(5,5);
        MainBoardCoordinates coordinates22 = new MainBoardCoordinates(2,2);
        MainBoardCoordinates coordinates03 = new MainBoardCoordinates(0,3);
        MainBoardCoordinates coordinates04 = new MainBoardCoordinates(0,4);
        MainBoardCoordinates coordinates52 = new MainBoardCoordinates(5,2);
        MainBoardCoordinates coordinates51 = new MainBoardCoordinates(5,1);

        //  null parameter

        List<MainBoardCoordinates> emptyCoordinatesList = null;
        try {
            assertNull(board.removeTiles(emptyCoordinatesList));
            System.out.println("correctly returns null");
        }
        catch (NotPickableException e){
            System.out.println("My method throw when I expected it to don't");
        }

        // examples of tiles that should never be pickable:

        List<MainBoardCoordinates> coordinatesList = new ArrayList<>();
        coordinatesList.add(coordinates00);

        try {
            assertNull(board.removeTiles(coordinatesList));
            fail( "My method didn't throw when I expected it to" );
        }
        catch (NotPickableException e){
            System.out.println("not pickable as it should be");
        }

        coordinatesList.remove(coordinates00);
        coordinatesList.add(coordinates08);
        try {
            assertNull(board.removeTiles(coordinatesList));
            fail( "My method didn't throw when I expected it to" );
        }
        catch (NotPickableException e){
            System.out.println("not pickable as it should be");
        }

        coordinatesList.remove(coordinates08);
        coordinatesList.add(coordinates80);
        try {
            assertNull(board.removeTiles(coordinatesList));
            fail( "My method didn't throw when I expected it to" );
        }
        catch (NotPickableException e){
            System.out.println("not pickable as it should be");
        }

        coordinatesList.remove(coordinates80);
        coordinatesList.add(coordinates88);
        try {
            assertNull(board.removeTiles(coordinatesList));
            fail( "My method didn't throw when I expected it to" );
        }
        catch (NotPickableException e){
            System.out.println("not pickable as it should be");
        }

        coordinatesList.remove(coordinates88);
        coordinatesList.add(coordinates21);
        try {
            assertNull(board.removeTiles(coordinatesList));
            fail( "My method didn't throw when I expected it to" );
        }
        catch (NotPickableException e){
            System.out.println("not pickable as it should be");
        }

        // example of tile that shouldn't be pickable in the first rounds of the game:

        coordinatesList.remove(coordinates21);
        coordinatesList.add(coordinates55);
        try {
            assertNull(board.removeTiles(coordinatesList));
            fail( "My method didn't throw when I expected it to" );
        }
        catch (NotPickableException e){
            System.out.println("not pickable as it should be");
        }


        // example of tile that should be pickable in the first round

        coordinatesList.remove(coordinates55);
        coordinatesList.add(coordinates22);
        try {
            List<Tile> lis = board.removeTiles(coordinatesList);
            assertNotNull(lis);
            for(int i = 0; i<lis.size(); i++){
                assertTrue(lis.get(i)==Tile.GAME || lis.get(i)!=Tile.PLANT || lis.get(i)!=Tile.BOOK ||
                        lis.get(i)!=Tile.FRAME ||lis.get(i)!=Tile.TROPHY ||lis.get(i)!=Tile.CAT);
            }
        }
        catch (NotPickableException e){
            fail( "My method throw when I didn't expected it to" );
        }

        // and then it's no more pickable

        try {
            assertNull(board.removeTiles(coordinatesList));
            fail( "My method didn't throw when I expected it to" );
        }
        catch (NotPickableException e){
            System.out.println("not pickable as it should be");
        }

        // example of tiles that should be pickable in the first round

        coordinatesList.remove(coordinates22);
        coordinatesList.add(coordinates03);
        coordinatesList.add(coordinates04);
        try {
            List<Tile> lis = board.removeTiles(coordinatesList);
            assertNotNull(lis);
            for(int i = 0; i<lis.size(); i++){
                assertTrue(lis.get(i)==Tile.GAME || lis.get(i)!=Tile.PLANT || lis.get(i)!=Tile.BOOK ||
                        lis.get(i)!=Tile.FRAME ||lis.get(i)!=Tile.TROPHY ||lis.get(i)!=Tile.CAT);
            }
        }
        catch (NotPickableException e){
            fail( "My method throw when I didn't expected it to" );
        }

        // and then they are no more pickable

        try {
            assertNull(board.removeTiles(coordinatesList));
            fail( "My method didn't throw when I expected it to" );
        }
        catch (NotPickableException e){
            System.out.println("not pickable as it should be");
        }

        // example of a tile that shouldn't be pickable in the first round

        coordinatesList.remove(coordinates03);
        coordinatesList.remove(coordinates04);
        coordinatesList.add(coordinates52);
        try {
            assertNull(board.removeTiles(coordinatesList));
            fail( "My method didn't throw when I expected it to" );
        }
        catch (NotPickableException e){
            System.out.println("not pickable as it should be");
        }

        // and still isn't pickable if picked together with a valid one next to it (51)

        coordinatesList.add(coordinates51);
        try {
            assertNull(board.removeTiles(coordinatesList));
            fail( "My method didn't throw when I expected it to" );
        }
        catch (NotPickableException e){
            System.out.println("not pickable as it should be");
        }

        // but it's gonna be pickable after picking the 51

        coordinatesList.remove(coordinates52);
        try {
            List<Tile> lis = board.removeTiles(coordinatesList);
            assertNotNull(lis);
            for(int i = 0; i<lis.size(); i++){
                assertTrue(lis.get(i)==Tile.GAME || lis.get(i)!=Tile.PLANT || lis.get(i)!=Tile.BOOK ||
                        lis.get(i)!=Tile.FRAME ||lis.get(i)!=Tile.TROPHY ||lis.get(i)!=Tile.CAT);
            }
        }
        catch (NotPickableException e){
            fail( "My method throw when I didn't expected it to" );
        }

        coordinatesList.remove(coordinates51);
        coordinatesList.add(coordinates52);
        try {
            List<Tile> lis = board.removeTiles(coordinatesList);
            assertNotNull(lis);
            for(int i = 0; i<lis.size(); i++){
                assertTrue(lis.get(i)==Tile.GAME || lis.get(i)!=Tile.PLANT || lis.get(i)!=Tile.BOOK ||
                        lis.get(i)!=Tile.FRAME ||lis.get(i)!=Tile.TROPHY ||lis.get(i)!=Tile.CAT);
            }
        }
        catch (NotPickableException e){
            fail( "My method throw when I didn't expected it to" );
        }

    }


}