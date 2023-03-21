package model;

public class Board3 extends Board4 {
    private final Tile[][] mainBoard = new Tile[9][9];
    private final TilesBag MyBag = new TilesBag();


    public Board3(){
        super();
        this.MyBag.RestoreTile(mainBoard[3][1]);
        mainBoard[3][1] = Tile.UNAVAILABLE;
        this.MyBag.RestoreTile(mainBoard[4][0]);
        mainBoard[4][0] = Tile.UNAVAILABLE;
        this.MyBag.RestoreTile(mainBoard[7][3]);
        mainBoard[7][3] = Tile.UNAVAILABLE;
        this.MyBag.RestoreTile(mainBoard[8][4]);
        mainBoard[8][4] = Tile.UNAVAILABLE;
        this.MyBag.RestoreTile(mainBoard[0][4]);
        mainBoard[0][4] = Tile.UNAVAILABLE;
        this.MyBag.RestoreTile(mainBoard[1][5]);
        mainBoard[1][5] = Tile.UNAVAILABLE;
        this.MyBag.RestoreTile(mainBoard[4][8]);
        mainBoard[4][8] = Tile.UNAVAILABLE;
        this.MyBag.RestoreTile(mainBoard[5][7]);
        mainBoard[5][7] = Tile.UNAVAILABLE;
    }

}
