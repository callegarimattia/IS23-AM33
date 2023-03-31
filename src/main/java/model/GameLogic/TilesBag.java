package model.GameLogic;

import java.util.concurrent.ThreadLocalRandom;

public class TilesBag {
    private int numOfCat;
    private int numOfBook;
    private int numOfGame;
    private int numOfFrame;
    private int numOfTrophy;
    private int numOfPlant;
    private int totalTiles;

    public TilesBag() {
        numOfCat = 22;
        numOfBook = 22;
        numOfGame = 22;
        numOfFrame = 22;
        numOfTrophy = 22;
        numOfPlant = 22;
        totalTiles = 132;
    }

    public int getTotalTiles() {
        return totalTiles;
    }

    public void RestoreTile(Tile myTile){
        totalTiles ++;
        switch (myTile){
            default:
                numOfCat ++;
            case BOOK:
                numOfBook ++;
            case GAME:
                numOfGame ++;
            case FRAME:
                numOfFrame ++;
            case TROPHY:
                numOfTrophy ++;
            case PLANT:
                numOfPlant ++;
        }
    }

    public Tile pickedTile(){
        Tile returnTile = Tile.EMPTY;
        if(totalTiles==0) return returnTile;
        int type = ThreadLocalRandom.current().nextInt(0, 5 + 1);
        switch (type) {
            case 0:
                if(this.numOfCat > 0){
                    this.numOfCat--;
                    totalTiles --;
                    return returnTile = Tile.CAT;
                }
                else return returnTile = this.pickedTile();
            case 1:
                if(numOfBook > 0){
                    this.numOfBook--;
                    totalTiles --;
                    return returnTile = Tile.BOOK;
                }
                else return returnTile = this.pickedTile();
            case 2:
                if(numOfGame > 0){
                    this.numOfGame--;
                    totalTiles --;
                    return returnTile = Tile.GAME;
                }
                else return returnTile = this.pickedTile();

            case 3:
                if(numOfFrame > 0){
                    this.numOfFrame--;
                    totalTiles --;
                    return returnTile = Tile.FRAME;
                }
                else return returnTile = this.pickedTile();

            case 4:
                if(numOfTrophy > 0){
                    this.numOfTrophy--;
                    totalTiles --;
                    return returnTile = Tile.TROPHY;
                }
                else return returnTile = this.pickedTile();

            case 5:
                if(numOfPlant > 0){
                    this.numOfPlant--;
                    totalTiles --;
                    return returnTile = Tile.PLANT;
                }
                else return returnTile = this.pickedTile();
        }
        return returnTile;
    }
}
