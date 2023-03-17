package model.PersonalGoals;

import model.Tile;

public class PersonalGoal5 extends PersonalGoal{
    public int countRights(Tile[][] playerMatrix) throws PersonalGoalException {
        int collected = 0;
        if (playerMatrix[1][0] == Tile.FRAME) collected++;
        if (playerMatrix[1][3] == Tile.GAME) collected++;
        if (playerMatrix[2][2] == Tile.PLANT) collected++;
        if (playerMatrix[3][1] == Tile.CAT) collected++;
        if (playerMatrix[3][4] == Tile.TROPHY) collected++;
        if (playerMatrix[5][0] == Tile.BOOK) collected++;
        return collected;
    }
}