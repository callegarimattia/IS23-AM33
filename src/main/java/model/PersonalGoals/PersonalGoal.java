package model.PersonalGoals;


import model.Tile;

public abstract class PersonalGoal {
    /**
     * @param playerMatrix is the Matrix on which the method will iterate
     * @return the points the player earned from this PersonalGoal
     * @method countRights controls if the Player achieved the goals described by his PersonalGoal
     */
    public abstract int countRights(Tile[][] playerMatrix) throws PersonalGoalException;

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
