package client.clientModel;

import common.Tile;

public class ClientPlayer {
    String userName;
    Tile[][] myShelf;

    public ClientPlayer(String userName) {
        this.userName = userName;
        myShelf = new Tile[6][5];
        initShelf();
    }

    private void initShelf() {
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 5; j++)
                myShelf[i][j] = Tile.EMPTY;
    }

    private void printShelf() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++)
                System.out.printf("%20s ", myShelf[i][j]);
            System.out.println();
        }

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void refresh() {
        System.out.println(userName);
        printShelf();
    }

    public void addTile(Tile newTile, int col) {
        int row = 5;
        while (!myShelf[row][col].equals(Tile.EMPTY))
            row--;
        myShelf[row][col] = newTile;
    }


}
