package model.GameLogic;

import model.GameLogic.CommonGoals.CommonGoal;
import model.GameLogic.CommonGoals.CommonGoal1;
import model.GameLogic.PersonalGoals.PersonalGoalDrawer;
import model.GameLogic.PersonalGoals.PersonalGoalException;
import model.Lobbies.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private Board mainBoard;
    private Random random = new Random();
    private PersonalGoalDrawer personalGoalDrawer = new PersonalGoalDrawer();
    private int indexCurrentPlayer = random.nextInt() % 4;
    private List<Player> players;

    // dovranno in realta poi essere scritte e generate randomicamente dal file Json:
    private CommonGoal comG1;
    private CommonGoal comG2;
    ArrayList<String> solvOrder1;
    ArrayList<String> solvOrder2;



    public Game(List<Player> players) {
        this.players = players;
        players.forEach(player -> player.setPersonalGoal(personalGoalDrawer.draw()));
        //pick two common goal
        mainBoard = new Board(players.size());
        comG1 = new CommonGoal1();    // dovranno in realta poi essere scritte e generate randomicamente dal file Json:
        comG2 = new CommonGoal1();
        indexCurrentPlayer = random.nextInt() % players.size();
    }

    private void pickNextPlayer() {
        indexCurrentPlayer = (indexCurrentPlayer + 1) % 4;
    }

    private int updateCurrPlayerScore() throws PersonalGoalException {
        int score = players.get(indexCurrentPlayer).getPersonalGoalScoreAndCluster();

        if(!solvOrder1.contains(players.get(indexCurrentPlayer).getUserName())){
            if(comG1.isSolved(players.get(indexCurrentPlayer).getMyShelf().getShelf()))
                solvOrder1.add(players.get(indexCurrentPlayer).getUserName());
        }
        else score += ( solvOrder1.indexOf(players.get(indexCurrentPlayer).getUserName()) +1)*2;

        // oppure passiamo i 2 common goal a tutti i player durante la costruzione di game cosi da non esporre
        // myshelf e fare il calcolo nella classe player

        if(!solvOrder2.contains(players.get(indexCurrentPlayer).getUserName())){
            if(comG2.isSolved(players.get(indexCurrentPlayer).getMyShelf().getShelf()))
                solvOrder2.add(players.get(indexCurrentPlayer).getUserName());
        }
        else score += ( solvOrder2.indexOf(players.get(indexCurrentPlayer).getUserName()) +1)*2;
        return score;
    }

    public boolean pickAndInsert(User user, List<Integer> xPos, List<Integer> yPos, int column) throws inputException, PersonalGoalException {
        if (!user.getUserName().equals(players.get(indexCurrentPlayer).getUserName()))
            return false;                // controlli che user è currPlayer
        if (!players.get(indexCurrentPlayer).getMyShelf().isColumnValid(xPos.size(), column)) return false;

        ArrayList<Tile> pickedTiles = mainBoard.removeTiles(xPos, yPos);  // bisogna gestire le exception
        // aggiornare la shelf
        if(players.get(indexCurrentPlayer).getMyShelf().insertTiles(column, pickedTiles)) return false;

        players.get(indexCurrentPlayer).setScore(updateCurrPlayerScore());

        pickNextPlayer();
        return true;

        // da sistemare
    }
}