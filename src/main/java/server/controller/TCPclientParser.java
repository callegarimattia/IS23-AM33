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
    private final LobbiesHandler lobbiesHandler;
    private final GameHandler gameHandler = null;
    ObjectInputStream in = null;
    ObjectOutputStream out = null;
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
                    answer = new JSONObject();
                    if(inUser){
                        if(gameHandler != null){  // ovvero sono in game
                            gameHandler.abortGame();  // manda messaggio finale e chiude tutti i 4 (potenzialmente) thread parser
                            lobbiesHandler.removeLobby((String) obj.get("toBeDeletedUser"));  // ed elimina anche tuttu i (4) user
                            return; // termino questo thread
                        }
                        else{  // sia che sia in una waitingLobby sia che sia un user e basta
                            lobbiesHandler.removeUser((String) obj.get("toBeDeletedUser"));
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
                    answer = new JSONObject();
                    answer.put("type", 0);
                    // gestiamo lato client il fatto che puo inviare certi comandi solo in certe situazioni oppure vogliamo fare server super safe ?
                    if(inUser){
                        answer.put("answer", "-1");
                    }
                    String newUserUsername = (String) obj.get("userName");
                    if(lobbiesHandler.createUser(newUserUsername)){
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
                    answer = new JSONObject();
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
                    answer = new JSONObject();
                    answer.put("type", 2);
                    if ( !inUser) {
                        answer.put("answer", "-1");  // bisgona prima creare lo user
                        sendAnswer(answer);
                        break;
                    }
                    if((long) obj.get("size") > 4 || (long) obj.get("size") < 2){
                        answer.put("answer", "-2");  // invalid game size
                        sendAnswer(answer);
                        break;
                    }
                    User myUser = null;
                    for(User user : lobbiesHandler.getUsers())
                        if(user.getUserName().equals(obj.get("firstUserUserName")))
                            myUser = user;
                    if(gameHandler != null || myUser.isInLobby()){
                        answer.put("answer", "0");  // cant create lobby when in a game or in a lobby
                        sendAnswer(answer);
                        break;
                    }
                    else {
                        long size = (long) obj.get("size");
                        int newID = lobbiesHandler.createLobby((String) obj.get("firstUserUserName"),(int)size);
                        lobbiesHandler.joinLobby((String) obj.get("firstUserUserName"), newID);
                        answer.put("answer","1");
                        answer.put("ID", newID);
                        sendAnswer(answer);
                        break;
                    }
                case 3:   //

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
