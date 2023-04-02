package model.GameLogic;

import java.util.ArrayList;
import java.util.List;

public class Shelf {

    private final int ROW_NUMBER = 6;
    private final int COL_NUMBER = 5;
    private Tile[][] shelf = new Tile[ROW_NUMBER][COL_NUMBER];

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

    public boolean insertTiles(int column, ArrayList<Tile> pickedTiles) {
        int ROW = 0;
        if (isColumnValid(pickedTiles.size(), column)) return false;
        for ( ROW = ROW_NUMBER-1; shelf[ROW][column]==Tile.EMPTY; ROW--);
        for (int x=0; x< pickedTiles.size(); x++){
            shelf[ROW - x][column]=pickedTiles.get(x);
        }
        return true;
    }

    public Tile[][] getShelf() {
        return shelf;
    }

    public int ClusterScore(){
        int[][] clusterShelf = new int[ROW_NUMBER][COL_NUMBER];
        for (int ROW = 0; ROW < ROW_NUMBER; ROW++)
            for (int COL = 0; COL < COL_NUMBER; COL++){
                clusterShelf[ROW][COL] = -1;
                if(shelf[ROW][COL] == Tile.EMPTY)
                    clusterShelf[ROW][COL] = 0;
            }
        int x = 1;
        for (int ROW = 0; ROW < ROW_NUMBER; ROW++){
            for (int COL = 0; COL < COL_NUMBER; COL++){
                if(clusterShelf[ROW][COL]==-1)
                    clusterShelf[ROW][COL] = x;
                if(!shelf[ROW][COL].equals(Tile.EMPTY) && shelf[ROW][COL+1].equals(shelf[ROW][COL]) && COL+1 <= COL_NUMBER-1)
                    clusterShelf[ROW][COL+1] = clusterShelf[ROW][COL];
                if(!shelf[ROW][COL].equals(Tile.EMPTY) && shelf[ROW+1][COL].equals(shelf[ROW][COL]) && ROW+1 <= ROW_NUMBER-1){
                    clusterShelf[ROW+1][COL] = clusterShelf[ROW][COL];
                }
                x++;
            }
        }
        int points = 0;
        int[] clustArr = new int[x];  // 0 by default
        for (int ROW = 0; ROW < ROW_NUMBER; ROW++){
            for (int COL = 0; COL < COL_NUMBER; COL++){
                clustArr[clusterShelf[ROW][COL]]++;
            }
        }
        for(int y = 1; y <= x; y++ ){
            switch (y){
                default:
                    ;
                case 3:
                    points += 2;
                case 4:
                    points += 3;
                case 5:
                    points += 5;;
               /* case >=6:
                    points += 8;*/
            }
            if (y >= 6)
                points += 8;
        }

        return points;
    }
}
