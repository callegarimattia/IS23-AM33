package model;

import model.PersonalGoals.PersonalGoalDrawer;

import java.util.List;
import java.util.Random;


public class Game {
    private Board mainBoard;
    private Random random = new Random();

    private PersonalGoalDrawer personalGoalDrawer = new PersonalGoalDrawer();
    private int indexCurrentPlayer = random.nextInt() % 4;

    private long timeLastMove;
    private List<Player> players;

    public Game(List<Player> players) {
        this.players = players;
        players.forEach(player -> player.setPersonalGoal(personalGoalDrawer.draw()));
        mainBoard = new Board(players.size());
    }

    private void pickNextPlayer() {
        indexCurrentPlayer = (indexCurrentPlayer + 1) % 4;
    }

    public boolean isCommonGoalAchieved(Player player) {
        return true;
    }

    public int scorePersonalGoalAchieved(Player player) {
        int score = 0;
        return score;
    }

    public int scoreTileCluster(Player player){
        int score =0;
        return score;
    }

    public boolean pickedTileisAcceptable(Tile tile1) {

        return true;
    }
}