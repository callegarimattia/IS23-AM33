package client.clientModel;

import common.Tile;

public class ClientPersonalGoal {
    int[] coordinates;
    Tile[] values;


    public ClientPersonalGoal(int[] coordinates, Tile[] values) {
        this.coordinates = coordinates;
        this.values = values;
    }

    public int[] getCoordinates() {
        return coordinates;
    }


    public Tile[] getValues() {
        return values;
    }

    public void print() {
        System.out.println("my personal goal: ");
        int k = 0;
        for (int i = 0; i < 6; i++) {
            System.out.print("[" + coordinates[k] + "][" + coordinates[k + 1] + "]: ");
            System.out.println(values[i]);
            k = k + 2;
        }
    }
}
