package server.model.commonGoals;

import common.Tile;

public class CommonGoal5 extends CommonGoal{
    private final int ROW_NUMBER = 6;
    private final int COL_NUMBER = 5;
    public boolean isSolved(Tile[][] matrix) {
        int count = 0;
        int[][] clusterShelf = new int[ROW_NUMBER][COL_NUMBER];
        for (int ROW = 0; ROW < ROW_NUMBER; ROW++)
            for (int COL = 0; COL < COL_NUMBER; COL++) {
                clusterShelf[ROW][COL] = -1;
                if (matrix[ROW][COL] == Tile.EMPTY)
                    clusterShelf[ROW][COL] = 0;
            }
        int x = 1;
        for (int ROW = 0; ROW < ROW_NUMBER; ROW++) {
            for (int COL = 0; COL < COL_NUMBER; COL++) {
                if (clusterShelf[ROW][COL] == -1)
                    clusterShelf[ROW][COL] = x;
                if (!matrix[ROW][COL].equals(Tile.EMPTY) && COL!=COL_NUMBER-1 && matrix[ROW][COL + 1].equals(matrix[ROW][COL]) )
                    clusterShelf[ROW][COL + 1] = clusterShelf[ROW][COL];
                if (!matrix[ROW][COL].equals(Tile.EMPTY) && ROW!=ROW_NUMBER-1 && matrix[ROW + 1][COL].equals(matrix[ROW][COL])) {
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
        for (int k = 1; k < clustArr.length-1; k++) if (clustArr[k] == 4) {
            count++;
        }
        return count == 4;
    }

    String description = "4 separate groups each formed by 4 adjacent tiles of the same type. The tiles in one group can be different from those in another group.";

    public String getDescription() {
        return description;
    }

}
