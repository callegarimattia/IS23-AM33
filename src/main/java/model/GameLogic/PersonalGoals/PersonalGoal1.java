package model.GameLogic.PersonalGoals;

import model.GameLogic.Tile;
public class PersonalGoal1 extends PersonalGoal {
    @Override
    public int countRights(Tile[][] playerMatrix) throws PersonalGoalException {
        int collected = 0;
        if (playerMatrix[1][1] == Tile.PLANT) collected++;
        if (playerMatrix[2][0] == Tile.CAT) collected++;
        if (playerMatrix[2][2] == Tile.GAME) collected++;
        if (playerMatrix[3][4] == Tile.BOOK) collected++;
        if (playerMatrix[4][3] == Tile.TROPHY) collected++;
        if (playerMatrix[5][4] == Tile.FRAME) collected++;
        return collected;
    }
}
