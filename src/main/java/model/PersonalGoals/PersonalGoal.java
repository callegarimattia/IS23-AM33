package model.PersonalGoals;


import model.Tile;

public abstract class PersonalGoal {
    /**
     * @attribute goalMatrix is the matrix corresponding to this card goals
     */
//    private Tile[][] goalMatrix;  inutile, gli eredi non sono forzati a implementarla

    /**
     * @param playerMatrix is the Matrix on which the method will iterate
     * @method calcPoints controls if the Player achieved the goals described by his PersonalGoal
     * @return the points the player earned from this PersonalGoal
     */
    public abstract int calcPoints(Tile[][] playerMatrix) throws PersonalGoalException;
    // in realtà non avrei bisogno di mettere playerMatrix come parametro in quanto sia
    // la matrice del giocatore che il personal goal sono entrambi nella classe player ma l ho messo per provarla
}
