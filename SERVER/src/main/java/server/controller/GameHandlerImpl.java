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

    @Override
    public int pickAndInsert(String userName, List<MainBoardCoordinates> coordinates, int column) {
        for (Player ply : myGame.getPlayers()) {
            if (userName.equals(ply.getUserName())) {
                return myGame.pickAndInsert(ply.getUserName(), coordinates, column);
            }
        }
        return 99;  // never reached
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

    public int chatMessage(String text, String recipient, String addresser) {
        return myGame.chatMessage(text, recipient, addresser);
    }

    private void startRMI(int ID){
        String binder = "GameServer" + ID;
        try {
            Registry registry = LocateRegistry.getRegistry(1099);
            registry.bind(binder, this);
        } catch (RemoteException | AlreadyBoundException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("------------------- " + binder + " RMI online-------------------");
    }

    @Override
    public JSONObject pickAndInsert(List<Integer> rows, List<Integer> columns, int myColumn) throws RemoteException {
        return null;
    }

    @Override
    public JSONObject sendChatMessage(String text, String recipient) {
        return null;
    }

    public void setMyGame(Game myGame) {
        this.myGame = myGame;
    }
}
