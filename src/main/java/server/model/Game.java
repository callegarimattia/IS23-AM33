package server.model;

import server.exceptions.GameEndedException;
import server.exceptions.LastRoundException;
import server.listenerStuff.GameUpdateEvent;
import server.listenerStuff.ListenerModel;
import server.model.commonGoals.CommonGoal;
import server.model.commonGoals.CommonGoal1;
import server.model.personalGoals.PersonalGoalDrawer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Game {
    private final Board mainBoard;
    private final Random random = new Random();
    private PersonalGoalDrawer personalGoalDrawer = null;
    private int indexCurrentPlayer = 0;
    private final List<Player> players;

    // dovranno in realta poi essere scritte e generate randomicamente dal file Json:
    private final CommonGoal comG1;
    private final CommonGoal comG2;
    private List<String> solvOrder1;  // tiene traccia dell' ordine di completamento del primo common goal
    private List<String> solvOrder2;
    private int lastPlayer = -1;
    private final ListenerModel myListener = new ListenerModel();

    public Game(List<Player> players) {
        try {
            personalGoalDrawer = new PersonalGoalDrawer();
        }
        catch (Exception e){
            System.out.println("Json reading error");
        }
        this.players = players;
        players.forEach(player -> player.setPersonalGoal(personalGoalDrawer.draw()));
        //pick two common goal
        mainBoard = new Board(players.size());
        comG1 = new CommonGoal1();    // dovranno in realta poi essere scritte e generate randomicamente dal file Json:
        comG2 = new CommonGoal1();
        Collections.shuffle(players);

        // creo il listener e gli passo i "4" socket che mi arrivano da sopra (lobby/gamehandler)

    }

    public Board getMainBoard() {
        return mainBoard;
    }

    private boolean pickNextPlayer() {
        if (indexCurrentPlayer != lastPlayer) {
            indexCurrentPlayer = (indexCurrentPlayer + 1) % players.size();
            return true;
        }
        gameEnd();
        return false;
    }

    public void gameEnd() {
        // mostra i punteggi
        // mostra il vincitore
    }

    //  ho lasciato il metodo updateCurrPlayerScore nella classe Game altrimenti se l avessi messo in Player avrei
    // ogni volta dovuto passargli i 2 common goal e l ordine di completamento dei common goal (le due liste)
    private int updateCurrPlayerScore() {
        int score = players.get(indexCurrentPlayer).getPersonalGoalScoreAndCluster();

        if (!solvOrder1.contains(players.get(indexCurrentPlayer).getUserName())) {
            if (comG1.isSolved(players.get(indexCurrentPlayer).getMyShelf().getShelf()))
                solvOrder1.add(players.get(indexCurrentPlayer).getUserName());
        }
        else score += ( solvOrder1.indexOf(players.get(indexCurrentPlayer).getUserName()) +1)*2;

        if(!solvOrder2.contains(players.get(indexCurrentPlayer).getUserName())){
            if(comG2.isSolved(players.get(indexCurrentPlayer).getMyShelf().getShelf()))
                solvOrder2.add(players.get(indexCurrentPlayer).getUserName());
        }
        else score += ( solvOrder2.indexOf(players.get(indexCurrentPlayer).getUserName()) +1)*2;

        return score;
    }

    public boolean pickAndInsert(String nickName, List<MainBoardCoordinates> coordinates, int column) throws GameEndedException {
        if (nickName.equals(players.get(indexCurrentPlayer).getUserName()))
            return false;                // controlli che user è currPlayer
        if (!players.get(indexCurrentPlayer).getMyShelf().isColumnValid(coordinates.size(), column)) return false;

        ArrayList<Tile> pickedTiles = mainBoard.removeTiles(coordinates);  // bisogna gestire le exception
        // aggiornare la shelf
        try {
            if (!players.get(indexCurrentPlayer).getMyShelf().insertTiles(column, pickedTiles)) return false;
        } catch (LastRoundException e) {  // inizia l utimo giro se primo a completare shelf, da gestire le conseguenze
            if (lastPlayer == -1) lastPlayer = (indexCurrentPlayer - 1) % players.size();
        }

        players.get(indexCurrentPlayer).setScore(updateCurrPlayerScore());

        GameUpdateEvent ev = new GameUpdateEvent(this,players.get(indexCurrentPlayer).getMyShelf().copyMatrix(),mainBoard.copyMatrix(),players.get(indexCurrentPlayer).getUserName());
        this.myListener.OnGameUpdate(ev);

        if (!pickNextPlayer()) {
            throw new GameEndedException();
        }

        return true;
        // da sistemare
    }

    public List<Player> getPlayers() {
        return players;
    }
}