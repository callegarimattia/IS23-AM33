package model.GameLogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Board {
    private final Tile[][] mainBoard;

    private final int MAX_ROW_NUM = 9;

    private final int MAX_COL_NUM = 9;
    private final TilesBag MyBag;

    // DA MODIFICARE TUTTI I CICLI FOR SOSTITUENDO I NUMERI CON DEI DEFINE!!!
    public Board(int numOfPlayers) {
        MyBag = new TilesBag();
        mainBoard = new Tile[9][9];
        this.EmptyMatrixInitializer();
        int k = 3;
        for (int i = 0; i <= 3; i++) {
            for (int j = 0; j <= k; j++) {
                if (i != 0 || j != 3) {
                    this.mainBoard[i][j] = Tile.UNAVAILABLE;
                    this.mainBoard[8 - j][i] = Tile.UNAVAILABLE;
                }
                if (8 - i != 8 || 8 - j != 5) {
                    this.mainBoard[8 - i][8 - j] = Tile.UNAVAILABLE;
                    this.mainBoard[j][8 - i] = Tile.UNAVAILABLE;
                }
            }
            k--;
        }

        if (numOfPlayers < 4) init3players();
        if (numOfPlayers < 3) init2players();

        RandomTiles(MyBag, mainBoard);
    }

    private void EmptyMatrixInitializer() {
        for (int x = 0; x < 9; x++)
            for (int y = 0; y < 9; y++)
                mainBoard[x][y] = Tile.EMPTY;
    }

    private void init3players() {
        mainBoard[3][1] = Tile.UNAVAILABLE;
        mainBoard[4][0] = Tile.UNAVAILABLE;
        mainBoard[7][3] = Tile.UNAVAILABLE;
        mainBoard[8][4] = Tile.UNAVAILABLE;
        mainBoard[0][4] = Tile.UNAVAILABLE;
        mainBoard[1][5] = Tile.UNAVAILABLE;
        mainBoard[4][8] = Tile.UNAVAILABLE;
        mainBoard[5][7] = Tile.UNAVAILABLE;
    }

    private void init2players() {
        mainBoard[0][3] = Tile.UNAVAILABLE;
        mainBoard[2][2] = Tile.UNAVAILABLE;
        mainBoard[2][6] = Tile.UNAVAILABLE;
        mainBoard[3][8] = Tile.UNAVAILABLE;
        mainBoard[5][0] = Tile.UNAVAILABLE;
        mainBoard[6][2] = Tile.UNAVAILABLE;
        mainBoard[6][6] = Tile.UNAVAILABLE;
        mainBoard[8][5] = Tile.UNAVAILABLE;
    }

    private void RandomTiles(TilesBag myBag, Tile[][] mainBoard) {
        for (int x = 0; x < 9; x++)
            for (int y = 0; y < 9; y++)
                if (mainBoard[x][y].equals(Tile.EMPTY))
                    mainBoard[x][y] = myBag.pickedTile();
    }

    /* Da togliere c'è solo per test di stampa */
    /*public void Print(){
        for(int x =0 ; x < 9; x++){
            for(int y =0; y< 9; y++ ){
                System.out.print(mainBoard[x][y]+"\t");
            }
            System.out.println("\n");
        }
    }*/

    private boolean areTilesPickable(List<Integer> xPos, List<Integer> yPos) {
        //controlla che siano adiacenti e che abbiano almeno una tile vuota vicina
        ArrayList<Integer> xSort = new ArrayList<>(xPos);
        ArrayList<Integer> ySort = new ArrayList<>(yPos);
        Collections.sort(xSort);
        Collections.sort(ySort);
        for(int i = 0; i < xPos.size(); i++){
            if(!isPickable(xPos.get(i), yPos.get(i))) return false;
        }

        if (xPos.size() == 1) return true;

        boolean isAligned = true;
        for(int i = 0;  i < xPos.size()-1; i++){
            if (xPos.get(i) != xPos.get(i+1)) isAligned = false;
        }
        if (isAligned == true) {
            for(int i = 0;  i < xPos.size()-1; i++){
                if (ySort.get(i) != ySort.get(i+1) - 1) isAligned = false;
            }
        }
        if (isAligned == true) {
            return true;
        }

        for(int i = 0;  i < yPos.size()-1; i++){
            if (yPos.get(i) != yPos.get(i+1)) return false;
        }
        for(int i = 0;  i < xPos.size()-1; i++){
            if (xSort.get(i) != xSort.get(i+1) - 1) return false;
        }
        return true;
    }

    private boolean isPickable(int xPos, int yPos){
        if(mainBoard[xPos][yPos].equals(Tile.UNAVAILABLE) || mainBoard[xPos][yPos].equals(Tile.EMPTY)) return false;
        if(xPos ==0 || yPos == 0 || xPos == 8 || yPos == 8) return true;
        if(mainBoard[xPos+1][yPos].equals(Tile.UNAVAILABLE) || mainBoard[xPos+1][yPos].equals(Tile.EMPTY)) return true;
        if(mainBoard[xPos-1][yPos].equals(Tile.UNAVAILABLE) || mainBoard[xPos-1][yPos].equals(Tile.EMPTY)) return true;
        if(mainBoard[xPos][yPos+1].equals(Tile.UNAVAILABLE) || mainBoard[xPos][yPos+1].equals(Tile.EMPTY)) return true;
        if(mainBoard[xPos][yPos-1].equals(Tile.UNAVAILABLE) || mainBoard[xPos][yPos-1].equals(Tile.EMPTY)) return true;
        return false;
    }


    public ArrayList<Tile> removeTiles(List<Integer> xPos, List<Integer> yPos) throws inputException {
        if (xPos.size() != yPos.size() || xPos.size() > 3 || xPos.isEmpty()) throw new inputException();
        for (Integer x : xPos) {
            if (x < 0 || x >= MAX_ROW_NUM) throw new inputException();
        }
        for (Integer y : yPos) {
            if (y < 0 || y >= MAX_COL_NUM) throw new inputException();
        }
        if (!areTilesPickable(xPos, yPos)) throw new inputException();

        ArrayList<Tile> pickedTiles = new ArrayList<Tile>();

        for (int i = 0; i < xPos.size(); i++) {
            pickedTiles.add(mainBoard[xPos.get(i)][yPos.get(i)]);
            mainBoard[xPos.get(i)][yPos.get(i)] = Tile.EMPTY;
        }

        return pickedTiles;
    }

    public int getMAX_ROW_NUM() {
        return MAX_ROW_NUM;
    }

    public int getMAX_COL_NUM() {
        return MAX_COL_NUM;
    }
}