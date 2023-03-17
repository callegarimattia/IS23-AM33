package model.PersonalGoals;

import model.Tile;

public class PersonalGoal10 extends PersonalGoal{
    public int countRights(Tile[][] playerMatrix) throws PersonalGoalException {
        int collected = 0;
        if (playerMatrix[1][1] == Tile.TROPHY) collected++;
        if (playerMatrix[3][1] == Tile.FRAME) collected++;
        if (playerMatrix[3][2] == Tile.BOOK) collected++;
        if (playerMatrix[4][4] == Tile.PLANT) collected++;
        if (playerMatrix[5][0] == Tile.GAME) collected++;
        if (playerMatrix[5][3] == Tile.CAT) collected++;
        return collected;
    }
}