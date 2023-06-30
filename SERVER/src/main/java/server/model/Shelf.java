package server.model;

import common.Tile;

import java.util.ArrayList;

public class Shelf {
    private final int ROW_NUMBER = 6;
    private final int COL_NUMBER = 5;
    private final Tile[][] shelf = new Tile[ROW_NUMBER][COL_NUMBER];

    public Shelf() {
        for (int ROW = 0; ROW < ROW_NUMBER; ROW++)
            for (int COL = 0; COL < COL_NUMBER; COL++)
                shelf[ROW][COL] = Tile.EMPTY;
    }

    public boolean isColumnValid(int numTilesToBeInserted, int column) {
        int count = 0;
        if(column < 0 || column > 4)
            return false;
        for (int ROW = 0; ROW < ROW_NUMBER; ROW++) {
            if (shelf[ROW][column] != Tile.EMPTY)
                break;
            count++;
        }
        return count >= numTilesToBeInserted;
    }

    public int insertTiles(int column, ArrayList<Tile> pickedTiles) {
        int ROW =  ROW_NUMBER-1;  // 6-1
        if (!isColumnValid(pickedTiles.size(), column)) {
            System.out.println("la colonna non va bene");
            return 0;
        }
        while (!shelf[ROW][column].equals(Tile.EMPTY))
            ROW--;
        for (int x=0; x< pickedTiles.size(); x++){
            shelf[ROW - x][column]=pickedTiles.get(x);
        }
        if(isFull()) return 2;
        return 1;
    }

    private boolean isFull(){
        for (int ROW = 0; ROW < ROW_NUMBER; ROW++)
            for (int COL = 0; COL < COL_NUMBER; COL++) {
                if(!shelf[ROW][COL].equals(Tile.EMPTY) || !shelf[ROW][COL].equals(Tile.UNAVAILABLE))
                    return false;
            }
        return true;
    }

    public Tile[][] getShelf() {
        return shelf;
    }

    //  crea una matrice copia di interi in cui attribuisco un intero diverso ad ogni cluster mentre scansiono
    //  la matrice originale di Tiles, in questo modo nella nuova matrice le tiles dello stesso tipo tra loro vicine
    //  avranno come valore lo stesso numero. Scansiono poi la nuova matrice per contare il numero di occorrenze di
    //  ogni intero, in modo da valutare quanto è grande il cluster ed attribuire il punteggio corrispondente

    public int clusterScore(Tile[][] myShelf){   // l ho messo come argomento anche se è una variabile interna per facilita nel testing
        int[][] clusterShelf = new int[ROW_NUMBER][COL_NUMBER];
        for (int ROW = 0; ROW < ROW_NUMBER; ROW++)
            for (int COL = 0; COL < COL_NUMBER; COL++) {
                clusterShelf[ROW][COL] = -1;
                if (myShelf[ROW][COL] == Tile.EMPTY)
                    clusterShelf[ROW][COL] = 0;
            }

        int x = 1;
        for (int ROW = 0; ROW < ROW_NUMBER; ROW++) {
            for (int COL = 0; COL < COL_NUMBER; COL++) {
                if (clusterShelf[ROW][COL] == -1)
                    clusterShelf[ROW][COL] = x;
                if (!myShelf[ROW][COL].equals(Tile.EMPTY) && COL!=COL_NUMBER-1 && myShelf[ROW][COL + 1].equals(myShelf[ROW][COL])){
                    if(clusterShelf[ROW][COL + 1] < 0)
                        clusterShelf[ROW][COL + 1] = clusterShelf[ROW][COL];
                    else clusterShelf[ROW][COL] = clusterShelf[ROW][COL + 1];
                }
                if (!myShelf[ROW][COL].equals(Tile.EMPTY) && ROW!=ROW_NUMBER-1 && myShelf[ROW + 1][COL].equals(myShelf[ROW][COL])) {
                    if(clusterShelf[ROW + 1][COL] < 0)
                        clusterShelf[ROW + 1][COL] = clusterShelf[ROW][COL];
                    else clusterShelf[ROW][COL] = clusterShelf[ROW + 1][COL];
                }
                x++;
            }
        }

        int[] clustArr = new int[x];  // 0 by default
        for (int ROW = 0; ROW < ROW_NUMBER; ROW++) {
            for (int COL = 0; COL < COL_NUMBER; COL++) {
                clustArr[clusterShelf[ROW][COL]]++;
            }
        }
        int points = 0;
        for (int k = 1; k < clustArr.length-1; k++) {
            if(clustArr[k]==3) points +=2;
            if(clustArr[k]==4) points +=3;
            if(clustArr[k]==5) points +=5;
            if(clustArr[k]>=6) points +=8;
        }
        return points;
    }

    public Tile[][] copyMatrix (){
        Tile[][] copy = new Tile[6][5];
        for (int x = 0; x < 6; x++)
            System.arraycopy(shelf[x], 0, copy[x], 0, 5);
        return copy;
    }


    public void refresh(){  // debug purpose only
        System.out.println("shelf: ");
        for(int i = 0; i < 6; i++){
            for (int j = 0; j<5; j++)
                System.out.printf("%20s", shelf[i][j]);
            System.out.println();
        }
    }

}
