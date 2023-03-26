package model;

import model.PersonalGoals.PersonalGoalDrawer;

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

    public int scoreTileCluster(Player player) {
        int score = 0;
        return score;
    }

    public boolean pickTiles(User user, int posX1, int posX2, int posY1, int posY2) {
        // controlli che user è currPlayer
        if (!user.getUserName().equals(players.get(indexCurrentPlayer).getUserName())) return false;
        // controlla che il player ha spazio per le tiles nella sua shelf (ha almeno una colonna libera
        // di 1/2/3 tiles)
        // controlli che le tiles possono essere scelte
        // aggiunge le tiles a quelle pescate
        // aggiorna la board

        return true;
    }

    public boolean insertTiles(User user, int column, Tile tile1, Tile tile2, Tile tile3) {
        // controlli che user è currPlayer
        // controlli che le tiles da inserire sono state pescate
        // controlli che nella colonna column c'è spazio per le tiles
        // aggiorna la shelf
        // pulisce la lista di tiles pescate
        return true;
    }
}