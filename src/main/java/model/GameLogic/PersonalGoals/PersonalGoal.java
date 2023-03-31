package model.GameLogic.PersonalGoals;


import model.GameLogic.Tile;

public abstract class PersonalGoal {
    /**
     * @param playerMatrix is the Matrix on which the method will iterate
     * @return the number of goal tiles the player collected from this PersonalGoal
     */
    public abstract int countRights(Tile[][] playerMatrix) throws PersonalGoalException;

    /**
     * @param count is the number of goal tiles the player collected from this PersonalGoal
     * @return the points obtained by the player from this Personal Goal
     */
    public int calcPoints(int count) {
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
