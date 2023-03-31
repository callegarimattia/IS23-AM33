package model.GameLogic;

import model.GameLogic.PersonalGoals.PersonalGoalDrawer;
import model.Lobbies.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private Board mainBoard;
    private Random random = new Random();
    private List<Tile> pickedTiles = new ArrayList<>();
    private PersonalGoalDrawer personalGoalDrawer = new PersonalGoalDrawer();
    private int indexCurrentPlayer = random.nextInt() % 4;
    private List<Player> players;

    public Game(List<Player> players) {
        this.players = players;
        players.forEach(player -> player.setPersonalGoal(personalGoalDrawer.draw()));
        //pick two common goal
        mainBoard = new Board(players.size());
    }

    private void pickNextPlayer() {
        indexCurrentPlayer = (indexCurrentPlayer + 1) % 4;
    }

    public int scorePersonalGoalAchieved(Player player) {
        int score = 0;
        // tbd
        return score;
    }

    public int scoreTileCluster(Player player) {
        int score = 0;
        return score;
    }

    public boolean pickAndInsert(User user, List<Integer> xPos, List<Integer> yPos, int column) {
        if (!user.getUserName().equals(players.get(indexCurrentPlayer).getUserName()))
            return false;                // controlli che user è currPlayer
        if (!players.get(indexCurrentPlayer).getMyShelf().isColumnValid(xPos.size(), column)) return false;

        if (!mainBoard.removeTiles(xPos, yPos)) return false;
        // aggiornare la shelf

        return true;

        // da sistemare
    }
}