package model.GameLogic;

import model.GameLogic.PersonalGoals.PersonalGoal;
import model.GameLogic.PersonalGoals.PersonalGoalException;

public class Player {
        private Shelf myShelf = new Shelf();
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
               int score = personalGoal.calcPoints(myShelf.getShelf());
                score += myShelf.ClusterScore();
               return score;
        }

        public int pointsNearTiles() { return 0;}


}
