package model.PersonalGoals;

import model.Tile;

public class PersonalGoal7 extends PersonalGoal{
    public int countRights(Tile[][] playerMatrix) throws PersonalGoalException {
        int collected = 0;
        if (playerMatrix[0][4] == Tile.TROPHY) collected++;
        if (playerMatrix[1][1] == Tile.GAME) collected++;
        if (playerMatrix[2][0] == Tile.BOOK) collected++;
        if (playerMatrix[3][3] == Tile.CAT) collected++;
        if (playerMatrix[4][1] == Tile.FRAME) collected++;
        if (playerMatrix[5][3] == Tile.PLANT) collected++;
        return collected;
    }
}