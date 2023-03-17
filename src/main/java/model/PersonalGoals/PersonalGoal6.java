package model.PersonalGoals;

import model.Tile;

public class PersonalGoal6 extends PersonalGoal{
    public int countRights(Tile[][] playerMatrix) throws PersonalGoalException {
        int collected = 0;
        if (playerMatrix[0][4] == Tile.FRAME) collected++;
        if (playerMatrix[1][1] == Tile.CAT) collected++;
        if (playerMatrix[2][2] == Tile.TROPHY) collected++;
        if (playerMatrix[3][0] == Tile.PLANT) collected++;
        if (playerMatrix[4][3] == Tile.BOOK) collected++;
        if (playerMatrix[5][3] == Tile.GAME) collected++;
        return collected;
    }
}