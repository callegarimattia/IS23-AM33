package model.GameLogic;

import model.GameLogic.PersonalGoals.PersonalGoal;

public class Player {
        private Shelf myShelf = new Shelf();
        private PersonalGoal personalGoal;
        private int score;
        private final String userName;

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

        public PersonalGoal getPersonalGoal() {
                return personalGoal;
        }

        public int pointsNearTiles() { return 0;}


}
