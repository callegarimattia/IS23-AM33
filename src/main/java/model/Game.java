package model;

import java.time.LocalTime;
import java.util.List;


public class Game {
    private Board4 MainBoard;
    private Player currentTurnPlayer;
    private int gameID;
    private LocalTime lastMove;
    private List<Player> players;
    /*private CommonGoal pickCommonGoal(){
    }*/

    public Player getCurrentTurnPlayer(){
        return currentTurnPlayer;
    }

    public void nextTurn()
    {

    }
    public boolean timeElapsed(){
        return true;
    }

    public boolean isCommonGoalAchieved(Player player){
        return true;
    }

    public int scorePersonalGoalAchieved(Player player){
        int score=0;
        return score;
    }

    public int scoreTileCluster(Player player){
        int score =0;
        return score;
    }

    public boolean PickTileisAcceptable(Tile tile1){

        return true;
    }
}