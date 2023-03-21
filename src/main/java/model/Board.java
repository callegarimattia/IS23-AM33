package model;

public class Board {
    private final Tile[][] mainBoard;
    private final TilesBag MyBag;


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
        //to be implemented
    }

    private void RandomTiles(TilesBag myBag, Tile[][] mainBoard) {
        for (int x = 0; x < 9; x++)
            for (int y = 0; y < 9; y++)
                if (mainBoard[x][y].equals(Tile.EMPTY))
                    mainBoard[x][y] = myBag.pickedTile();
    }

    /* Da togliere c'è solo per test di stampa */
    public void Print(){
        for(int x =0 ; x < 9; x++){
            for(int y =0; y< 9; y++ ){
                System.out.print(mainBoard[x][y]+"\t");
            }
            System.out.println("\n");
        }
    }
    public Tile[][] getMainBoard(){
        return mainBoard;
    }
}