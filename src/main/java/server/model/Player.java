package server.model;

import client.VirtualView;
import server.model.personalGoals.PersonalGoal;

import java.io.ObjectOutputStream;

public class Player {
    private final Shelf myShelf = new Shelf();
    private final String userName;
    private PersonalGoal personalGoal;
    private int score;
    private VirtualView myClient;
    private ObjectOutputStream out;

    public Player(String userName, VirtualView client) {
        this.userName = userName;
        myClient = client;
    }

    public Player(String userName, ObjectOutputStream out) {
        this.userName = userName;
        this.out = out;
    }

    public Player(String userName) {
        this.userName = userName;
    }

    public void setScore(int score) {
        this.score = score;
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

    public ObjectOutputStream getOut() {
        return out;
    }

    public int getPersonalGoalScoreAndCluster() {
        score = personalGoal.calcPoints(myShelf.getShelf());
        score += myShelf.ClusterScore();
        return score;
    }

    public VirtualView getMyClient() {
        return myClient;
    }

    public void setMyClient(VirtualView myClient) {
        this.myClient = myClient;
    }
}
