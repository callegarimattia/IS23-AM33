package server.model;

import client.Client;
import server.model.personalGoals.PersonalGoal;

public class Player {
        private final Shelf myShelf = new Shelf();
        private PersonalGoal personalGoal;
        private int score;
        private final String userName;
        private Client myClient;

        public void setScore(int score) {
                this.score = score;
        }

        public Player(String userName, Client client) {
                this.userName = userName;
                myClient = client;
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

        public int getPersonalGoalScoreAndCluster() {
               score = personalGoal.calcPoints(myShelf.getShelf());
               score += myShelf.ClusterScore();
               return score;
        }

        public Client getMyClient() {
                return myClient;
        }

        public void setMyClient(Client myClient) {
                this.myClient = myClient;
        }
}
