package model.PersonalGoals;

import model.Tile;

public class PersonalGoal2 extends PersonalGoal{
    public int countRights(Tile[][] playerMatrix) throws PersonalGoalException {
        int collected = 0;
        if (playerMatrix[0][2] == Tile.TROPHY) collected++;
        if (playerMatrix[0][4] == Tile.CAT) collected++;
        if (playerMatrix[2][3] == Tile.BOOK) collected++;
        if (playerMatrix[4][1] == Tile.GAME) collected++;
        if (playerMatrix[4][3] == Tile.FRAME) collected++;
        if (playerMatrix[5][0] == Tile.PLANT) collected++;
        return collected;
    }
}