package server.model;

import server.exceptions.GameEndedException;
import server.exceptions.LastRoundException;
import server.listenerStuff.GameUpdateEvent;
import server.model.commonGoals.CommonGoal;
import server.model.commonGoals.CommonGoal1;
import server.model.personalGoals.PersonalGoalDrawer;

import java.rmi.RemoteException;
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

    public Game(List<Player> players) {
        try {
            personalGoalDrawer = new PersonalGoalDrawer();
        }
        catch (Exception e){
            System.out.println("Personal Goal Json reading error");
        }
        this.players = players;
        players.forEach(player -> player.setPersonalGoal(personalGoalDrawer.draw()));
        //pick two common goal
        mainBoard = new Board(players.size());
        comG1 = new CommonGoal1();    // dovranno in realta poi essere scritte e generate randomicamente dal file Json:
        comG2 = new CommonGoal1();
        Collections.shuffle(players);  // ordine casuale per scegliere primo giocatore

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
        //  potremmo anche controllare che la socket/riferim client dal quale arriva il comando sia effettivamente quella del current player,
        //  altrimenti controllando solo la stringa potrebbe arrivare il comando da un altro client che si spaccia per il current player

        if (!nickName.equals(players.get(indexCurrentPlayer).getUserName()))
            return false;                // controlli che user è currPlayer
        if (!players.get(indexCurrentPlayer).getMyShelf().isColumnValid(coordinates.size(), column)) return false;
        ArrayList<Tile> pickedTiles = null;
        try {
            pickedTiles = mainBoard.removeTiles(coordinates);   //  prendo le tiles da pickare
        }
        catch (Exception e){
            System.out.println("Invalid Pick");
            return false;
        }

        // e poi le metto nella shelf del player
        try {
            if (!players.get(indexCurrentPlayer).getMyShelf().insertTiles(column, pickedTiles)) return false;
        } catch (LastRoundException e) {  // inizia l utimo giro se primo a completare shelf, da gestire le conseguenze
            if (lastPlayer == -1) lastPlayer = (indexCurrentPlayer - 1) % players.size();
        }

        players.get(indexCurrentPlayer).setScore(updateCurrPlayerScore());

        GameUpdateEvent ev = new GameUpdateEvent(this,players.get(indexCurrentPlayer).getMyShelf().copyMatrix(),mainBoard.copyMatrix(),players.get(indexCurrentPlayer).getUserName());
        OnGameUpdate(ev);

        pickNextPlayer();

        return true;
        // da sistemare
    }

    private void OnGameUpdate(GameUpdateEvent evt){
        for(Player player: players){
            if(player.getMyClient()!=null){
                try {
                    player.getMyClient().GameUpdate(evt);
                }
                catch (RemoteException e){
                    System.out.println("remote method invocation failed");
                }
            }

         //   if(socket!=null)
                 //   gameupdt sulla socket

        }


    }

    public List<Player> getPlayers() {
        return players;
    }
}