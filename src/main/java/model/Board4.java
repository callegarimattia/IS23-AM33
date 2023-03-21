package model;

public class Board4 {
    private final Tile[][] mainBoard;
    private final TilesBag MyBag;


    private void EmptyMatrixInitializer(){
        for(int x = 0; x<9 ; x++)
            for(int y = 0; y<9; y++)
                mainBoard[x][y]= Tile.EMPTY;
    }
    //  modificare usando l ereditarietà e togliendo numOfPlayer tra i parametri
    public Board4() {
        MyBag = new TilesBag();
        mainBoard = new Tile[9][9];
        this.EmptyMatrixInitializer();
        int k=3;
        for(int i=0;i<=3;i++){
            for (int j=0;j<=k;j++){
                if(i!=0 || j!=3){
                    this.mainBoard[i][j] = Tile.UNAVAILABLE;
                    this.mainBoard[8-j][i] = Tile.UNAVAILABLE;
                }
                if(8-i!=8 || 8-j!=5){
                    this.mainBoard[8-i][8-j] = Tile.UNAVAILABLE;
                    this.mainBoard[j][8-i] = Tile.UNAVAILABLE;
                }
            }
            k --;
        }
        this.RandomTiles(MyBag, mainBoard);
        System.out.println(":::::::"+MyBag.getTotalTiles());
    }

    private void RandomTiles(TilesBag myBag, Tile[][] mainBoard){
        for(int x = 0; x<9 ; x++)
            for(int y = 0; y<9; y++)
                if(mainBoard[x][y].equals(Tile.EMPTY))
                    mainBoard[x][y]=myBag.pickedTile();
    }
    public void RefillBoard(){

    }

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