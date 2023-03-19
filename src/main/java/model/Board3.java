package model;

import java.util.Random;

public class Board3 /*extends Board4*/ {
    private Tile[][] mainBoard;
    private Random random = new Random();
    private int numOfCat;
    private int numOfBook;
    private int numOfGame;
    private int numOfFrame;
    private int numOfTrophy;
    private int numOfPlant;


    public Board3(int numOfPlayer){
     //   super{(mainBoard)};
        mainBoard[3][1] = Tile.UNAVAILABLE;
        mainBoard[4][0] = Tile.UNAVAILABLE;
        mainBoard[7][3] = Tile.UNAVAILABLE;
        mainBoard[8][4] = Tile.UNAVAILABLE;
        mainBoard[0][4] = Tile.UNAVAILABLE;
        mainBoard[1][5] = Tile.UNAVAILABLE;
        mainBoard[4][8] = Tile.UNAVAILABLE;
        mainBoard[5][7] = Tile.UNAVAILABLE;
    }
}
