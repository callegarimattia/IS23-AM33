package server.model.commonGoals;

import common.Tile;


public class CommonGoal12 extends CommonGoal{
    private final int COL_NUMBER = 5;
    public boolean isSolved(Tile[][] matrix) {
        int check = 1;
        int i;
        for (i = 0; i < COL_NUMBER; i++) {
            if (matrix[i][i] == Tile.EMPTY) check = 0;
            System.out.println("SETTO CHECK A 0 : " + check);
            if (i != 0) {
                if (matrix[i - 1][i] != Tile.EMPTY) check = 0;
                System.out.println("SETTO CHECK A 0 : " + check);
            }
        }
        System.out.println("TRUE O FALSE : " + check);
        if (check == 1) return true;

        check = 1;
        System.out.println("FACCIO TORNARE CHECK A 1");

        for (i = 0; i < COL_NUMBER; i++) {
            if (matrix[i + 1][i] == Tile.EMPTY || matrix[i][i] != Tile.EMPTY) check = 0;
            System.out.println("SETTO CHECK A 0 : " + check);
        }
        if (check == 1) return true;

        check = 1;
        System.out.println("FACCIO TORNARE CHECK A 1");

        for (i = 0; i < COL_NUMBER; i++) {
            if (matrix[COL_NUMBER - 1 - i][i] == Tile.EMPTY) check = 0;
            System.out.println("SETTO CHECK A 0 : " + check);
            if (i != COL_NUMBER - 1) {
                if (matrix[COL_NUMBER - 2 - i][i] != Tile.EMPTY) check = 0;
                System.out.println("SETTO CHECK A 0 : " + check);
            }
        }
        if (check == 1) return true;

        check = 1;
        System.out.println("FACCIO TORNARE CHECK A 1");
        for (i = 0; i < COL_NUMBER; i++) {
            if (matrix[COL_NUMBER - i][i] == Tile.EMPTY || matrix[COL_NUMBER - i - 1][i] != Tile.EMPTY) check = 0;
            System.out.println("SETTO IL CHECK A 0 : " + check);
        }
        return check == 1;
    }


}
