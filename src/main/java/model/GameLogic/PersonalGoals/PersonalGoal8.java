package model.GameLogic.PersonalGoals;

import model.GameLogic.Tile;

public class PersonalGoal8 extends PersonalGoal{
    public int countRights(Tile[][] playerMatrix) throws PersonalGoalException {
        int collected = 0;
        if (playerMatrix[0][2] == Tile.BOOK) collected++;
        if (playerMatrix[1][1] == Tile.PLANT) collected++;
        if (playerMatrix[2][2] == Tile.FRAME) collected++;
        if (playerMatrix[3][3] == Tile.TROPHY) collected++;
        if (playerMatrix[4][4] == Tile.GAME) collected++;
        if (playerMatrix[5][0] == Tile.CAT) collected++;
        return collected;
    }
}