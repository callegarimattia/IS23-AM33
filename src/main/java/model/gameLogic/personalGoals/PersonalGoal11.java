package model.gameLogic.personalGoals;

import model.gameLogic.Tile;

public class PersonalGoal11 extends PersonalGoal{
    public int countRights(Tile[][] playerMatrix) throws PersonalGoalException {
        int collected = 0;
        if (playerMatrix[0][0] == Tile.CAT) collected++;
        if (playerMatrix[1][3] == Tile.FRAME) collected++;
        if (playerMatrix[2][1] == Tile.PLANT) collected++;
        if (playerMatrix[3][0] == Tile.TROPHY) collected++;
        if (playerMatrix[4][4] == Tile.GAME) collected++;
        if (playerMatrix[5][2] == Tile.BOOK) collected++;
        return collected;
    }
}