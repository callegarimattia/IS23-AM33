package client.clientModel;

import common.Tile;

public class ClientPersonalGoal {

    Tile[][] matrix;

    public ClientPersonalGoal(int[] coordinates, Tile[] values) {
        matrix = new Tile[6][5];
        for (int i = 0; i < 6; i++)
            for(int j = 0; j < 5; j++)
                matrix[i][j] = Tile.EMPTY;
        int k = 0;
        for (int i = 0; i < values.length; i++){
            matrix[coordinates[k]][coordinates[k+1]] = values[i];
            k = k+2;
        }

    }


    public void print() {
        System.out.println("\nmy personal goal:\n");
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++)
                System.out.printf("%20s ",matrix[i][j]);
            System.out.println();
        }
    }


}
