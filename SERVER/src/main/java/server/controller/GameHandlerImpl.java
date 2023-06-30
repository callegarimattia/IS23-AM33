package server.controller;

import common.GameServerRMI;
import common.MainBoardCoordinates;
import org.json.simple.JSONObject;
import server.model.Game;
import server.model.Player;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;


public class GameHandlerImpl  extends UnicastRemoteObject implements GameHandler, GameServerRMI {   // controller per Game

    private Game myGame;

    /**
     * <p>Constructor of the controller</p>
     *
     *
     */
    public GameHandlerImpl( int ID) throws RemoteException {
        super();
        startRMI(ID);
    }

    /**
     * <p>Pick and insert method: calls game corresponding method
     * Username is checked against the player list</p>
     *
     * @param userName    player that makes the move
     * @param coordinates coordinates of the tiles picked
     * @param column      column of the personal shelf where to drop the tiles
     * @return pickAndInsert value from game method
     */


    private int pickAndInsert2(String userName, List<MainBoardCoordinates> coordinates, int column) {
        for (Player ply : myGame.getPlayers()) {
            if (userName.equals(ply.getUserName())) {
                return myGame.pickAndInsert(ply.getUserName(), coordinates, column);
            }
        }
        return 99;  // never reached
    }

    @Override
    public JSONObject pickAndInsert(JSONObject obj) {
        JSONObject answer = new JSONObject();
        String userName = obj.get("name").toString();
        if(!userName.equals(getCurrPlayer())) {
            answer.put("answer","-1");  // not current player
            return answer;
        }
        int myColumn = -1;
        List<Long> columns = null;
        List<Long> rows = null;
        if (obj.get("myColumn") instanceof Long)
            myColumn = (int) (long) obj.get("myColumn");
        else myColumn = (int) obj.get("myColumn");
        System.out.println("la colonna è " + myColumn);
        if (obj.get("columns") instanceof List){
            try {
                columns = (List<Long>) obj.get("columns");
            }catch (Exception e){
                answer.put("answer","-8");  // invalid input
                return answer;
            }
        }

        if (obj.get("rows") instanceof List){
            try {
                rows = (List<Long>) obj.get("rows");
            }catch (Exception e){
                answer.put("answer","-8");  // invalid input
                return answer;
            }
        }

        System.out.print("prova coordinate: ");
        System.out.println(rows.get(0).intValue() +"/" + columns.get(0).intValue());
        System.out.println("finita");
        List<MainBoardCoordinates> coordinates = new ArrayList<>();
        for(int i = 0; i < columns.size(); i++){
            MainBoardCoordinates coord;
            try {
                coord = new MainBoardCoordinates(rows.get(i).intValue(),columns.get(i).intValue());
            } catch (Exception e) {
                answer.put("answer","-7");
                return answer;
            }
            coordinates.add(coord);
        }
        int x = -7456;
        x = pickAndInsert2(userName,coordinates, myColumn);
        answer.put("answer",Integer.toString(x));
        return answer;
    }



    /**
     * <p>Close connection when player disconnects </p>
     *
     * @param disconnectedPlayer disconnected player
     */
    @Override
    public void abortGame(String disconnectedPlayer) {
        // manda messaggi finali e chiude socket / connessioni RMI

        for (Player player : myGame.getPlayers()) {
            if (player.getOut() != null) {
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

    /**
     * <p>Refresh model method. Debug only</p>
     */
    @Override
    public void refresh() {   // debug purpose only
        myGame.refresh();
    }

    /**
     * <p>Returns the current player</p>
     *
     * @return username String of current player
     */

    @Override
    public String getCurrPlayer() {
        return myGame.getCurrentPlayer();
    }

    /**
     * <p>Sends a chat message</p>
     *
     * @param text      message to be sent
     * @param recipient String username of recipient
     * @param addresser String username of addresser
     * @return myGame.chatMessage return value
     */

    public int chatMessage2(String text, String recipient, String addresser) {
        return myGame.chatMessage(text, recipient, addresser);
    }
    public JSONObject sendChatMessage(JSONObject obj){
        String text = obj.get("text").toString();
        String recipient = obj.get("recipient").toString();
        String name= obj.get("name").toString();
        int ans = chatMessage2(text,recipient,name);
        JSONObject answer = new JSONObject();
        answer.put("answer", ans);
        return answer;
    }

    private void startRMI(int ID){
        String binder = "GameServer" + ID;
        try {
            Registry registry = LocateRegistry.getRegistry(1099);
            registry.bind(binder, this);
        } catch (RemoteException | AlreadyBoundException e) {
            System.out.println(e.getMessage());
        }
    }


    public void setMyGame(Game myGame) {
        this.myGame = myGame;
    }
}
