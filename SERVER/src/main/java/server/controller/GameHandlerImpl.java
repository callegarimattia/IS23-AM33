package server.controller;
import org.json.simple.JSONObject;
import server.model.Game;
import common.MainBoardCoordinates;
import server.model.Player;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;


public class GameHandlerImpl implements GameHandler {   // controller per Game

    private Game myGame;

    @Override
    public int pickAndInsert(String userName, List<MainBoardCoordinates> coordinates, int column) {
        for (Player ply : myGame.getPlayers()) {
            if (userName.equals(ply.getUserName())) {
                return myGame.pickAndInsert(ply.getUserName(), coordinates, column);
            }
        }
        return 99;  // never reached
    }

    @Override
    public void abortGame(String disconnectedPlayer) {
        // manda messaggi finali e chiude socket / connessioni RMI

        for (Player player : myGame.getPlayers()){
            if(player.getOut() != null){
                JSONObject answer = new JSONObject();
                answer.put("type", -1);
                answer.put("answer", "2");
                answer.put("disconnectedPlayer", disconnectedPlayer);
                ObjectOutputStream out = player.getOut();
                String message = answer.toString();
                try {
                    out.writeObject(message);
                    out.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
            // else RMI
        }
    }

    @Override
    public void refresh() {   // debug purpose only
        myGame.refresh();
    }

    @Override
    public String getCurrPlayer() {
        return myGame.getCurrentPlayer();
    }

    public int chatMessage(String text, String recipient, String addresser){
        return myGame.chatMessage(text,recipient,addresser);
    }




    public GameHandlerImpl(Game myGame) {
        this.myGame = myGame;
    }
}
