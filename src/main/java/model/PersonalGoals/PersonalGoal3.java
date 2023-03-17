package model.PersonalGoals;

import model.Tile;

public class PersonalGoal3 extends PersonalGoal{
    public int countRights(Tile[][] playerMatrix) throws PersonalGoalException {
        int collected = 0;
        if (playerMatrix[0][0] == Tile.PLANT) collected++;
        if (playerMatrix[0][2] == Tile.FRAME) collected++;
        if (playerMatrix[1][4] == Tile.CAT) collected++;
        if (playerMatrix[2][3] == Tile.BOOK) collected++;
        if (playerMatrix[3][1] == Tile.GAME) collected++;
        if (playerMatrix[5][2] == Tile.TROPHY) collected++;
        return collected;
    }
}