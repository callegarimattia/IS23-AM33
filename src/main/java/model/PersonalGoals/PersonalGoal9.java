package model.PersonalGoals;

import model.Tile;

public class PersonalGoal9 extends PersonalGoal{
    public int calcPoints(Tile[][] playerMatrix) throws PersonalGoalException {
        int collected = 0;
        if(playerMatrix[0][2] == Tile.GAME) collected++;
        if(playerMatrix[2][2] == Tile.CAT) collected++;
        if(playerMatrix[3][4] == Tile.BOOK) collected++;
        if(playerMatrix[4][1] == Tile.TROPHY) collected++;
        if(playerMatrix[4][4] == Tile.PLANT) collected++;
        if(playerMatrix[5][0] == Tile.FRAME) collected++;
        if(collected == 0) return 0;
        if(collected == 1) return 1;
        if(collected == 2) return 2;
        if(collected == 3) return 4;
        if(collected == 4) return 6;
        if(collected == 5) return 9;
        if(collected == 6) return 12;
        else throw new PersonalGoalException("Bad count, n° of collected: " + collected);
    }
}