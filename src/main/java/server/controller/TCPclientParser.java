package server.controller;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import server.model.Lobby;
import server.model.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TCPclientParser implements Runnable {
    private final Socket mySocket;
    private LobbiesHandler lobbiesHandler;
    private GameHandler gameHandler = null;
    ObjectInputStream in = null;
    ObjectOutputStream out = null;
    private String userName;
    private boolean inUser;

    // gestisce tutto il traffico tra il server e uno specifico client
    public TCPclientParser(Socket mySocket, LobbiesHandler lobbiesHandler) {
        this.mySocket = mySocket;
        this.lobbiesHandler = lobbiesHandler;
        try {
            in = new ObjectInputStream(mySocket.getInputStream());
            out = new ObjectOutputStream(mySocket.getOutputStream());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        inUser = false;
    }

    private void sendAnswer(JSONObject answer){
        try {
            out.writeObject(answer);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    // credo che invece di usare il getter devo passare all user in e out subito, prima di entrare nel ciclo while
    public ObjectOutputStream getOut() {
        return out;
    }

    public void setGameHandler(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }

    public void setLobbiesHandler(LobbiesHandler lobbiesHandler) {
        this.lobbiesHandler = lobbiesHandler;
    }

    @Override
    public void run() {

        JSONObject obj = new JSONObject();
        boolean isClientActive = true;

        while (isClientActive) {

            String str = null;   //  codice duplicato ma starà in due eseguibili diversi
            try {
                str = in.readObject().toString();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println(e.getMessage());
            }

            JSONParser parser = new JSONParser();
            try {
                obj = (JSONObject) parser.parse(str);
            } catch (ParseException e) {
                System.out.println(e.getMessage());
            }
            long x = (long) obj.get("type");
            JSONObject answer = new JSONObject();
            switch ((int)x) {
                default:
                    System.out.println("default, do nothing");
                    break;
                case -1:  // client closing his app
                    if(inUser){  // se sono gia stato associato ad uno user, altrimenti non devo modificare niente nel model
                        User me = lobbiesHandler.searchUser((String) obj.get("toBeDeletedUser"));
                        if(me.isInGame()){  // ovvero se sono in game
                            gameHandler.abortGame();  // manda messaggio finale e chiude tutti i 4 (potenzialmente) thread parser
                            lobbiesHandler.abortLobby((String) obj.get("toBeDeletedUser"));  // ed elimina anche tuttu i (4) user
                            return; // termino questo thread
                        }
                        else{  // sia che sia in una waitingLobby sia che sia un user e basta
                            lobbiesHandler.removeUser(me.getUserName());
                        }
                    }
                    answer.put("type", -1);
                    answer.put("answer", "1");
                    sendAnswer(answer);
                    try {  // chiudo socket
                        in.close();
                        out.close();
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println("connection with user "+ obj.get("toBeDeletedUser") +" closed and user deleted");
                    return;
                case 0:  // 0   (NEW USER)
                    answer.put("type", 0);
                    if(inUser){
                        answer.put("answer", "-1");  // socket gia associata ad uno user
                        sendAnswer(answer);
                        break;
                    }
                    String newUserUsername = (String) obj.get("userName");
                    if(lobbiesHandler.createUser(newUserUsername)){
                        userName = (String) obj.get("userName");
                        answer.put("answer", "1");
                        answer.put("userName", newUserUsername);
                        lobbiesHandler.addTCPparserToUser(newUserUsername,this);
                        inUser = true;
                    }
                    else {
                        answer.put("type", 0);
                        answer.put("answer", "0");
                    }
                    sendAnswer(answer);
                    break;
                case 1:  // 1  ListOfLobbiesRequest
                    answer.put("type", 1);
                    if(lobbiesHandler.getWaitingLobbies().size() > 0){
                        answer.put("answer", "1");
                        List<Integer> lobbiesIDs = new ArrayList<>();
                        List<Integer> lobbiesCurrentSize = new ArrayList<>();
                        List<Integer> lobbiesMaxSizes = new ArrayList<>();
                        Set<Lobby> waitingLobbyList = lobbiesHandler.getWaitingLobbies();
                        for(Lobby lobby : waitingLobbyList){
                            lobbiesIDs.add(lobby.getID());
                            lobbiesCurrentSize.add(lobby.getUsers().size());
                            lobbiesMaxSizes.add(lobby.getGameSize());
                        }
                        answer.put("IDs", lobbiesIDs);
                        answer.put("CurrentSizes", lobbiesCurrentSize);
                        answer.put("MaxSizes", lobbiesMaxSizes);
                    }
                    else {
                        answer.put("answer","0");
                    }
                    sendAnswer(answer);
                    break;
                case 2:  // 2 new lobby creation request
                    answer.put("type", 2);
                    if ( !inUser) {
                        answer.put("answer", "-1");  // bisogna prima creare lo user
                        sendAnswer(answer);
                        break;
                    }
                    if((long) obj.get("size") > 4 || (long) obj.get("size") < 2){
                        answer.put("answer", "-2");  // invalid game size
                        sendAnswer(answer);
                        break;
                    }
                    User myUser = lobbiesHandler.searchUser(userName);
                    if(myUser.isInGame()|| myUser.isInLobby()){
                        answer.put("answer", "0");  // cant create lobby when in a game or in a lobby
                        sendAnswer(answer);
                        break;
                    }
                    else {
                        long size = (long) obj.get("size");
                        int newID = lobbiesHandler.createLobby(userName,(int)size);
                        answer.put("answer","1");
                        answer.put("ID", newID);
                        sendAnswer(answer);
                        break;
                    }
                case 3:   //  join lobby request
                    answer.put("type", 3);
                    if ( !inUser) {
                        answer.put("answer", "-2");  // bisogna prima creare lo user
                        sendAnswer(answer);
                        break;
                    }
                    User toBeAddedUser = lobbiesHandler.searchUser(userName);
                    Lobby myLobby = lobbiesHandler.searchLobby((int)(long) obj.get("tobeJoinedLobbyID"));
                    if(myLobby == null){
                        answer.put("answer","-1");  // lobby doesn't exist
                        sendAnswer(answer);
                        break;
                    }

                    if(toBeAddedUser.isInLobby()){
                        answer.put("answer","0");  // user è gia in una lobby
                        sendAnswer(answer);
                        break;
                    }
                    lobbiesHandler.joinLobby(userName,myLobby.getID());
                    answer.put("answer",1);
                    answer.put("ID",myLobby.getID());
                    sendAnswer(answer);
                    break;
                case 4:   //  leave lobby request
                    User me = lobbiesHandler.searchUser(userName);
                    if(!me.isInLobby()){
                        answer.put("type", 4);
                        answer.put("answer","0");  // user is not in a lobby
                    }
                    if(me.isInGame()){
                        answer.put("type", 4);
                        answer.put("answer","-1");  // user is in an active game, cant leave lobby (shut down app if you want)
                    }
                    if(me.isInLobby()){
                        lobbiesHandler.leaveLobby(userName);
                        answer.put("type", 4);
                        answer.put("answer","1");
                    }
                    sendAnswer(answer);
                    break;
                case 5:   //

                    break;
                case 6:   //

                    break;
            }

        }

        System.out.println("Closing this client socket");
        try {
            in.close();
            out.close();
            mySocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
