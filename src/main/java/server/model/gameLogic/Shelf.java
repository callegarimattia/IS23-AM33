package server.model.gameLogic;

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
        for (int ROW = 0; ROW < ROW_NUMBER; ROW++) {
            if (shelf[ROW][column] != Tile.EMPTY)
                break;
            count++;
        }
        return count >= numTilesToBeInserted;
    }

    public boolean insertTiles(int column, ArrayList<Tile> pickedTiles) throws LastRoundException {
        int ROW = 0;
        if (isColumnValid(pickedTiles.size(), column)) return false;
        for ( ROW = ROW_NUMBER-1; shelf[ROW][column]==Tile.EMPTY; ROW--)
            for (int x=0; x< pickedTiles.size(); x++){
                shelf[ROW - x][column]=pickedTiles.get(x);
            }
        if(isFull()) throw new LastRoundException();  // to be handled
        return true;
    }

    private boolean isFull(){
        for (int ROW = 0; ROW < ROW_NUMBER; ROW++)
            for (int COL = 0; COL < COL_NUMBER; COL++) {
                if(!shelf[ROW][COL].equals(Tile.EMPTY))
                    return false;
            }
        return true;
    }

    public Tile[][] getShelf() {
        return shelf;
    }

    public int ClusterScore(){
        int[][] clusterShelf = new int[ROW_NUMBER][COL_NUMBER];
        for (int ROW = 0; ROW < ROW_NUMBER; ROW++)
            for (int COL = 0; COL < COL_NUMBER; COL++) {
                clusterShelf[ROW][COL] = -1;
                if (shelf[ROW][COL] == Tile.EMPTY)
                    clusterShelf[ROW][COL] = 0;
            }

        int x = 1;
        for (int ROW = 0; ROW < ROW_NUMBER; ROW++) {
            for (int COL = 0; COL < COL_NUMBER; COL++) {
                if (clusterShelf[ROW][COL] == -1)
                    clusterShelf[ROW][COL] = x;
                if (!shelf[ROW][COL].equals(Tile.EMPTY) && COL!=COL_NUMBER-1 && shelf[ROW][COL + 1].equals(shelf[ROW][COL]) )
                    clusterShelf[ROW][COL + 1] = clusterShelf[ROW][COL];
                if (!shelf[ROW][COL].equals(Tile.EMPTY) && ROW!=ROW_NUMBER-1 && shelf[ROW + 1][COL].equals(shelf[ROW][COL])) {
                    clusterShelf[ROW + 1][COL] = clusterShelf[ROW][COL];
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
}
