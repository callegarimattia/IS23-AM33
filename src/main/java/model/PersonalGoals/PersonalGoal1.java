package model.PersonalGoals;

import model.Tile;
public class PersonalGoal1 extends PersonalGoal{
    /*
    private Tile[][] goalMatrix = { { Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY },
                                    { Tile.EMPTY, Tile.PLANT, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY },
                                    { Tile.CAT, Tile.EMPTY, Tile.GAME, Tile.EMPTY, Tile.EMPTY },
                                    { Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.BOOK },
                                    { Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.TROPHY, Tile.EMPTY },
                                    { Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.FRAME },
    };
    */

    public int calcPoints(Tile[][] playerMatrix) throws PersonalGoalException {
        int collected = 0;
        if(playerMatrix[1][1] == Tile.PLANT) collected++;
        if(playerMatrix[2][0] == Tile.CAT) collected++;
        if(playerMatrix[2][2] == Tile.GAME) collected++;
        if(playerMatrix[3][4] == Tile.BOOK) collected++;
        if(playerMatrix[4][3] == Tile.TROPHY) collected++;
        if(playerMatrix[5][4] == Tile.FRAME) collected++;
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
