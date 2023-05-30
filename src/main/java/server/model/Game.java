package server.model;

import common.Tile;
import org.json.simple.JSONObject;
import server.exceptions.GameEndedException;
import server.exceptions.LastRoundException;
import server.exceptions.NotPickableException;
import server.listenerStuff.GameUpdateEvent;
import server.model.commonGoals.CommonGoal;
import server.model.commonGoals.CommonGoal1;
import server.model.personalGoals.PersonalGoalDrawer;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Game {
    private final Board mainBoard;
    private PersonalGoalDrawer personalGoalDrawer = null;
    private int indexCurrentPlayer = 0;
    private final List<Player> players;

    // dovranno in realta poi essere scritte e generate randomicamente dal file Json:
    private final CommonGoal comG1;
    private final CommonGoal comG2;
    private final List<String> solvOrder1;  // tiene traccia dell' ordine di completamento del primo common goal
    private final List<String> solvOrder2;
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
        comG1 = new CommonGoal1();    // dovranno in realta poi essere scritte e generate randomicamente :
        comG2 = new CommonGoal1();
        Collections.shuffle(players);  // ordine casuale per scegliere primo giocatore
        // RMI:

        // TCP:   (messaggio iniziale di inizio partita + modello)
        this.clientStartGame();
        solvOrder1 = new ArrayList<>();
        solvOrder2 = new ArrayList<>();
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

    public int pickAndInsert(String nickName, List<MainBoardCoordinates> coordinates, int column) throws GameEndedException {
        if(coordinates.size()>3 || coordinates.size() < 1){
            System.out.println("size error");
            return -2;
        }
        if (!nickName.equals(players.get(indexCurrentPlayer).getUserName()))
            return -3;                // controlli che user è currPlayer
        if (!players.get(indexCurrentPlayer).getMyShelf().isColumnValid(coordinates.size(), column))
            return -4;    //  invalid player column
        ArrayList<Tile> pickedTiles = null;
        try {
            pickedTiles = mainBoard.removeTiles(coordinates);   //  prendo le tiles da pickare
        }
        catch (NotPickableException e){
            System.out.println("Invalid Pick");
            return -5;
        }

        // e poi le metto nella shelf del player

        System.out.print(players.get(indexCurrentPlayer).getUserName() + " has picked: ");
        for(int i = 0; i < pickedTiles.size(); i++)
            System.out.print("[" + coordinates.get(i).getRow() + "][" + coordinates.get(i).getCol() + "]: " + pickedTiles.get(i) + "   ");


        try {
            if (!players.get(indexCurrentPlayer).getMyShelf().insertTiles(column, pickedTiles))
                return -6;  //  non inseribili in player
        } catch (LastRoundException e) {  // inizia l utimo giro se primo a completare shelf, da gestire le conseguenze
            if (lastPlayer == -1) lastPlayer = (indexCurrentPlayer - 1) % players.size();
        }

        System.out.println(" and inserted in his column n°" + column);

        players.get(indexCurrentPlayer).setScore(updateCurrPlayerScore());
        String updater = players.get(indexCurrentPlayer).getUserName();
        pickNextPlayer();

        GameUpdateEvent ev = new GameUpdateEvent(this, coordinates,updater,column,players.get(indexCurrentPlayer).getUserName());
        OnGameUpdate(ev);


        return 1;

    }

    private void OnGameUpdate(GameUpdateEvent evt){

        JSONObject gameUpdateMessage = new JSONObject();
        String updater = evt.getUpdater();
        int column = evt.getColumn();
        String newCurrPlayer = evt.getNewCurrPlayer();
        List<Integer> rows = new ArrayList<>();
        List<Integer> cols = new ArrayList<>();
        for(int i = 0; i < evt.getCoordinates().size(); i++){
            rows.add(evt.getCoordinates().get(i).getRow());
            cols.add(evt.getCoordinates().get(i).getCol());
        }
        gameUpdateMessage.put("type", 100);
        gameUpdateMessage.put("updater", updater);
        gameUpdateMessage.put("column", column);
        gameUpdateMessage.put("rows", rows);
        gameUpdateMessage.put("cols", cols);
        gameUpdateMessage.put("newCurrPlayer", newCurrPlayer);

        for(Player player: players) {  // RMI
            if (player.getMyClient() != null) {
                try {
                    player.getMyClient().GameUpdate(evt);
                } catch (RemoteException e) {
                    System.out.println("remote method invocation failed");
                }
            }

            if (player.getOut() != null) {  // TCP
                try {
                    player.getOut().writeObject(gameUpdateMessage);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }

            }
        }

    }

    private void clientStartGame(){
        JSONObject startGameMessage = new JSONObject();   // messaggio comune a tutti i player
        startGameMessage.put("type", 777);
        List<List<Integer>> x = mainBoard.toInt();
        startGameMessage.put("mainBoard", x);
        List<String> playersUserNames = new ArrayList<>();
        for(int i = 0; i < players.size(); i++)
            playersUserNames.add(players.get(i).getUserName());
        startGameMessage.put("playersUsernames", playersUserNames);  //  already shuffled (first player at [0])
        for(Player player : players){
            if(player.getOut() != null){
                ObjectOutputStream out = player.getOut();
                String message = startGameMessage.toString();
                try {
                    out.writeObject(message);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
            //    else usare RMI
        }


        for (Player player: players){  // messaggio personale diverso per ogni player
            if(player.getOut() != null){
                JSONObject personalMessage = new JSONObject();
                personalMessage.put("type", 778);
                List<Integer> coordinates = player.getPersonalGoal().getCoordinatesList();
                List<Integer> values = player.getPersonalGoal().getValuesToIntList();
                personalMessage.put("coordinates", coordinates);
                personalMessage.put("values", values);
                ObjectOutputStream out = player.getOut();
                try {
                    out.writeObject(personalMessage.toString());
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }

            // else usare RMI

        }

    }

    public void refresh(){  // debug purpose only
        System.out.println("\nGAME STATUS: \nMAINBOARD: ");
        mainBoard.refresh();
        for (int i = 0; i < players.size(); i++)
            players.get(i).refresh();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public String getCurrentPlayer(){
        return players.get(indexCurrentPlayer).getUserName();
    }
}