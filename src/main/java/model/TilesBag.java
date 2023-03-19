package model;

import java.sql.Array;
import java.util.Random;

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

    public int pickedTile(){
        int x = -1;
        if(totalTiles==0) x = -1;
        Random rn = new Random();
        int type = rn.nextInt(5) + 0;  // in teoria da 0 a 5
        switch (type) {
            default:
                if(numOfCat > 0){
                    this.numOfCat--;
                    totalTiles --;
                    x = 0;
                }
                else pickedTile();
            case 1:
                if(numOfBook > 0){
                    this.numOfBook--;
                    totalTiles --;
                    x = 1;
                }
                else pickedTile();
            case 2:
                if(numOfGame > 0){
                    this.numOfGame--;
                    totalTiles --;
                    x = 2;
                }
                else pickedTile();
            case 3:
                if(numOfFrame > 0){
                    this.numOfFrame--;
                    totalTiles --;
                    x = 3;
                }
                else pickedTile();
            case 4:
                if(numOfTrophy > 0){
                    this.numOfTrophy--;
                    totalTiles --;
                    x = 4;
                }
                else pickedTile();
            case 5:
                if(numOfPlant > 0){
                    this.numOfPlant--;
                    totalTiles --;
                    x = 5;
                }
                else pickedTile();
        }
        return x;
    }
}
