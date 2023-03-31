package model.GameLogic.PersonalGoals;

import model.GameLogic.Tile;

public class PersonalGoal9 extends PersonalGoal{
    public int countRights(Tile[][] playerMatrix) throws PersonalGoalException {
        int collected = 0;
        if (playerMatrix[0][2] == Tile.GAME) collected++;
        if (playerMatrix[2][2] == Tile.CAT) collected++;
        if (playerMatrix[3][4] == Tile.BOOK) collected++;
        if (playerMatrix[4][1] == Tile.TROPHY) collected++;
        if (playerMatrix[4][4] == Tile.PLANT) collected++;
        if (playerMatrix[5][0] == Tile.FRAME) collected++;
        return collected;
    }
}