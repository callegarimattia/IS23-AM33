package model;

import java.util.Random;

public class Board4 {
    private Tile[][] mainBoard;
    private TilesBag MyBag;




    //  modificare usando l ereditarietà e togliendo numOfPlayer tra i parametri
    public Board4(Tile[][] mainBoard, int numOfPlayer) {
        for(int x = 0; x<9 ; x++)
            for(int y = 0; y<9; y++)
                mainBoard[x][y]= Tile.EMPTY;
        int i = 0, j = 0;
        int k=3;
        for(i=0;i<=3;i++){
            for (j=0;j<=k;j++){
                if(i!=0 || j!=3){
                    mainBoard[i][j] = Tile.UNAVAILABLE;
                    mainBoard[8-j][i] = Tile.UNAVAILABLE;
                }
                if(8-i!=8 || 8-j!=5){
                    mainBoard[8-i][8-j] = Tile.UNAVAILABLE;
                    mainBoard[j][8-i] = Tile.UNAVAILABLE;
                }
            }
            k --;
        }

        if(numOfPlayer <= 3){

            mainBoard[3][1] = Tile.UNAVAILABLE;
            mainBoard[4][0] = Tile.UNAVAILABLE;
            mainBoard[7][3] = Tile.UNAVAILABLE;
            mainBoard[8][4] = Tile.UNAVAILABLE;
            mainBoard[0][4] = Tile.UNAVAILABLE;
            mainBoard[1][5] = Tile.UNAVAILABLE;
            mainBoard[4][8] = Tile.UNAVAILABLE;
            mainBoard[5][7] = Tile.UNAVAILABLE;

        }
        if (numOfPlayer == 2){
            mainBoard[0][3] = Tile.UNAVAILABLE;
            mainBoard[2][2] = Tile.UNAVAILABLE;
            mainBoard[5][0] = Tile.UNAVAILABLE;
            mainBoard[6][2] = Tile.UNAVAILABLE;
            mainBoard[8][5] = Tile.UNAVAILABLE;
            mainBoard[6][6] =Tile.UNAVAILABLE;
            mainBoard[3][8] = Tile.UNAVAILABLE;
            mainBoard[2][6] = Tile.UNAVAILABLE;

        }
        this.RandomTiles(MyBag, mainBoard);
    }

    private void RandomTiles(TilesBag myBag, Tile[][] mainBoard){
        for(int x = 0; x<9 ; x++){
            for(int y = 0; y<9; y++){
                if(mainBoard[x][y].equals(Tile.EMPTY)){
                    int type = myBag.pickedTile();
                    switch (type) {
                        default:
                            mainBoard[x][y] = Tile.EMPTY;
                        case 0:
                            mainBoard[x][y] = Tile.CAT;
                        case 1:
                            mainBoard[x][y] = Tile.BOOK;
                        case 2:
                            mainBoard[x][y] = Tile.GAME;
                        case 3:
                            mainBoard[x][y] = Tile.FRAME;
                        case 4:
                            mainBoard[x][y] = Tile.TROPHY;
                        case 5:
                            mainBoard[x][y] = Tile.PLANT;
                    }
                }
            }
        }

    }
    public void RefillBoard(){

    }
    public Tile[][] getMainBoard(){
        return mainBoard;
    }
}
