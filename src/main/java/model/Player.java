package model;

import model.PersonalGoals.PersonalGoal;

public class Player {
        private Shelf myShelf = new Shelf();
        private PersonalGoal personalGoal;
        private int score;
        private final String userName;
        //private PersonalGoal personalGoal;

        public Player(String userName) {
                this.userName = userName;
        }

        public int calculateScore() {
                return 0;
        }

        public void setPersonalGoal(PersonalGoal personalGoal) {
                this.personalGoal = personalGoal;
        }
}
