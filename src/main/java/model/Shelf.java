package model;

public class Shelf {
    private Tile[][] shelf;

    public Shelf() {
        // crea shelf empty
    }

    public int getMaxEmptyInColumns() {
        // DA MODIFICARE
        int count = 0;
        for (int i = 0; i < 0; i++) {
            if (shelf[1][1].equals(Tile.EMPTY)) {
                count++;
            }
        }
        return count;
    }

    public void insertTiles(int column, Tile tile1, Tile tile2, Tile tile3) {
    }
}
