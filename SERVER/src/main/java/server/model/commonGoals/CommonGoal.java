package server.model.commonGoals;

import common.Tile;

public abstract class CommonGoal {

    private String description;
    /**
     * Boolean method that iterates on a player's matrix
     * @return 1 if the goal's condition is satisfied, otherwise it returns 0
     */
    public abstract boolean isSolved(Tile[][] matrix);

    public String getDescription() {
        return description;
    }
}
