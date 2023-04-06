package model.gameLogic.personalGoals;

import model.gameLogic.Tile;

public class PersonalGoal4 extends PersonalGoal{
    public int countRights(Tile[][] playerMatrix) throws PersonalGoalException {
        int collected = 0;
        if (playerMatrix[0][4] == Tile.GAME) collected++;
        if (playerMatrix[2][0] == Tile.TROPHY) collected++;
        if (playerMatrix[2][2] == Tile.FRAME) collected++;
        if (playerMatrix[3][3] == Tile.PLANT) collected++;
        if (playerMatrix[4][1] == Tile.BOOK) collected++;
        if (playerMatrix[4][2] == Tile.CAT) collected++;
        return collected;
    }
}