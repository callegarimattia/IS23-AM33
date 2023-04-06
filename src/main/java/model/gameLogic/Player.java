package model.gameLogic;

import model.gameLogic.personalGoals.PersonalGoal;
import model.gameLogic.personalGoals.PersonalGoalException;

public class Player {
        private final Shelf myShelf = new Shelf();
        private PersonalGoal personalGoal;
        private int score;
        private final String userName;

        public void setScore(int score) {
                this.score = score;
        }

        public Player(String userName) {
                this.userName = userName;
        }

        public void setPersonalGoal(PersonalGoal personalGoal) {
                this.personalGoal = personalGoal;
        }

        public String getUserName() {
                return userName;
        }

        public Shelf getMyShelf() {
                return myShelf;
        }

        public int getPersonalGoalScoreAndCluster() throws PersonalGoalException {
               score = personalGoal.calcPoints(myShelf.getShelf());
               score += myShelf.ClusterScore();
               return score;
        }


}
