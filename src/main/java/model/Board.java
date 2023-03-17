package model;

import java.util.Random;

public class Board {
    private Tile[][] mainBoard;
    private Random random = new Random();
    private int numOfCat;
    private int numOfBook;
    private int numOfGame;
    private int numOfFrame;
    private int numOfTrophy;
    private int numOfPlant;

    private void createBoard(){

    }

    public void RefillBoard(){

    }
    public Tile[][] getMainBoard(){
        return mainBoard;
    }
}
