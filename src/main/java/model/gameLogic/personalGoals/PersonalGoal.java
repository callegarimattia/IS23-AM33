package model.gameLogic.personalGoals;


import model.gameLogic.Tile;

public abstract class PersonalGoal {
    /**
     * @param playerMatrix is the Matrix on which the method will iterate
     * @return the number of goal tiles the player collected from this PersonalGoal
     */
    public abstract int countRights(Tile[][] playerMatrix) throws PersonalGoalException;

    /**
     * @return the points obtained by the player from this Personal Goal
     */
    public int calcPoints(Tile[][] playerMatrix) throws PersonalGoalException {
        int count = countRights(playerMatrix);
        switch (count) {
            default:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 4;
            case 4:
                return 6;
            case 5:
                return 9;
            case 6:
                return 12;
        }
    }
}
