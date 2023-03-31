package model.GameLogic.CommonGoals;

import model.GameLogic.Tile;

public abstract class CommonGoal {

    /**
     * players will be sorted in this list by order of goal completion
     */
//   private ArrayList<Player> achievementOrder;  commento perché non è ancora definita la classe Player

    /**
     * Boolean method that iterates on a player's matrix
     * @return 1 if the goal's condition is satisfied, otherwise it returns 0
     */
    public abstract boolean isSolved(Tile[][] matrix);
}
