package server.controller;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import server.exceptions.InputException;
import server.exceptions.LastRoundException;
import server.model.Lobby;
import server.model.MainBoardCoordinates;
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

            if(str == null){ /////////////  not sure
                disconnectedUser();
                return;
            }


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

                // fare metodi al posto del corpo per ogni case e farci poi sopra il test
                // fare prova disconnessione forzata client / porta occupata
                
                case -1:  // client closing his app using the button / cli command
                    this.clientClosingApp(obj, answer);
                    return;
                case 0:
                    newUser(obj, answer);
                    break;
                case 1:
                    listOfLobbiesRequest(obj, answer);
                    break;
                case 2:
                    newLobbyCreationRequest(obj,answer);
                    break;
                case 3:
                    joinLobbyRequest(obj, answer);
                    break;
                case 4:
                    leaveLobbyRequest(obj, answer);
                    break;
                case 5:
                    pickAndInsert(obj, answer);
                    break;
                case 6:
                    chatMessage(obj, answer);
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

    private void disconnectedUser(){
        if(inUser){
            User me = lobbiesHandler.searchUser(userName);
            if(me == null) return;
            if(me.isInGame()){
                gameHandler.abortGame(me.getUserName());  // manda messaggio finale e chiude tutti i 4 (potenzialmente) thread parser
                lobbiesHandler.abortLobby(userName);  // ed elimina anche tuttu i (4) user
                return;
            }
            else{  // sia che sia in una waitingLobby sia che sia un user e basta
                lobbiesHandler.removeUser(me.getUserName());
            }
        }
        try {
            in.close();
            out.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("connection with user "+ userName +" closed and user deleted");
    }

    private void clientClosingApp(JSONObject obj, JSONObject answer){  // -1
        String toBeDel = (String) obj.get("toBeDeletedUser");
        if(inUser){  // se sono gia stato associato ad uno user, altrimenti non devo modificare niente nel model
            User me = lobbiesHandler.searchUser((String) obj.get("toBeDeletedUser"));
            if(me.isInGame()){  // ovvero se sono in game
                gameHandler.abortGame(me.getUserName());  // manda messaggio finale e chiude tutti i 4 (potenzialmente) client
                lobbiesHandler.abortLobby((String) obj.get("toBeDeletedUser"));  // ed elimina anche tutti i (4) user
                return; // termino questo thread
            }
            else{  // sia che sia in una waitingLobby sia che sia un user e basta
                lobbiesHandler.removeUser(me.getUserName());
            }
        }
        answer.put("type", -1);
        answer.put("answer", "1");
        sendAnswer(answer);
        try {
            in.close();
            out.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("connection with user "+ toBeDel +" closed and user deleted");


    }

    private void newUser(JSONObject obj, JSONObject answer){  // 0
        answer.put("type", 0);
        if(inUser){
            answer.put("answer", "-1");  // socket gia associata ad uno user
            sendAnswer(answer);
            return;
        }
        String newUserUsername = (String) obj.get("userName");
        if(newUserUsername.equals("all")){
            answer.put("answer", "-2");   //  stringa speciale
            sendAnswer(answer);
            return;
        }
        if(lobbiesHandler.createUser(newUserUsername)){
            userName = (String) obj.get("userName");
            answer.put("answer", "1");
            answer.put("userName", newUserUsername);
            lobbiesHandler.addTCPparserToUser(newUserUsername,this);
            inUser = true;
        }
        else answer.put("answer", "0");
        sendAnswer(answer);
    }

    public void listOfLobbiesRequest(JSONObject obj, JSONObject answer){  // 1
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
    }

    private void newLobbyCreationRequest(JSONObject obj, JSONObject answer){ // 2
        answer.put("type", 2);
        if ( !inUser) {
            answer.put("answer", "-1");  // bisogna prima creare lo user
            sendAnswer(answer);
            return;
        }
        if((long) obj.get("size") > 4 || (long) obj.get("size") < 2){
            answer.put("answer", "-2");  // invalid game size
            sendAnswer(answer);
            return;
        }
        User myUser = lobbiesHandler.searchUser(userName);
        if(myUser.isInGame()|| myUser.isInLobby()){
            answer.put("answer", "0");  // cant create lobby when in a game or in a lobby
            sendAnswer(answer);
            return;
        }
        long size = (long) obj.get("size");
        int newID = lobbiesHandler.createLobby(userName,(int)size);
        answer.put("answer","1");
        answer.put("ID", newID);
        sendAnswer(answer);
    }

    private void joinLobbyRequest(JSONObject obj, JSONObject answer){  // 3
        answer.put("type", 3);
        if ( !inUser) {
            answer.put("answer", "-2");  // bisogna prima creare lo user
            sendAnswer(answer);
            return;
        }
        User toBeAddedUser = lobbiesHandler.searchUser(userName);
        Lobby myLobby = lobbiesHandler.searchLobby((int)(long) obj.get("tobeJoinedLobbyID"));
        if(myLobby == null){
            answer.put("answer","-1");  // lobby doesn't exist
            sendAnswer(answer);
            return;
        }

        if(toBeAddedUser.isInLobby()){
            answer.put("answer","0");  // user è gia in una lobby
            sendAnswer(answer);
            return;
        }
        lobbiesHandler.joinLobby(userName,myLobby.getID());
        if(!myLobby.isFull()){
            answer.put("answer",1);
            answer.put("ID",myLobby.getID());
            sendAnswer(answer);
        }
    }


    private  void leaveLobbyRequest(JSONObject obj, JSONObject answer){   // 4
        User me = lobbiesHandler.searchUser(userName);
        answer.put("type", 4);
        if(!me.isInLobby())
            answer.put("answer","0");  // user is not in a lobby
        if(me.isInGame())
            answer.put("answer","-1");  // user is in an active game, cant leave lobby (shut down app if you want)
        if(me.isInLobby()){
            lobbiesHandler.leaveLobby(userName);
            answer.put("answer","1");
        }
        sendAnswer(answer);
    }

    private void pickAndInsert(JSONObject obj, JSONObject answer){  // 5
        answer.put("type", 5);
        if(gameHandler == null)
            answer.put("answer","0");  // not in game
        else {
            if(userName != gameHandler.getCurrPlayer()){
                answer.put("answer","-1");  // not current player
                sendAnswer(answer);
                return;
            }
            int myColumn = (int) (long) obj.get("myColumn");
            List<Long> columns = (List<Long>) obj.get("columns");  // columns
            List<Long> rows = (List<Long>) obj.get("rows");  // raws
            List<MainBoardCoordinates> coordinates = new ArrayList<>();
            for(int i = 0; i < columns.size(); i++){
                MainBoardCoordinates coord;
                try {
                    coord = new MainBoardCoordinates(rows.get(i).intValue(),columns.get(i).intValue());
                } catch (Exception e) {
                    answer.put("answer","-7");
                    sendAnswer(answer);
                    return;
                }
                coordinates.add(coord);
            }
            int x = -7456;
            try {
                x = gameHandler.pickAndInsert(userName,coordinates, myColumn);
            } catch (InputException | LastRoundException e) {
                System.out.println(e.getMessage());
            }
            switch (x){
                case 1:
                    answer.put("answer","1");
                    break;
                case -2:
                    answer.put("answer","-2");
                    break;
                case -3:
                    answer.put("answer","-3");
                    break;
                case -4:
                    answer.put("answer","-4");
                    break;
                case -5:
                    answer.put("answer","-5");
                    break;
                case -6:
                    answer.put("answer","-6");
                    break;
            }
        }
        sendAnswer(answer);
    }

    private void chatMessage(JSONObject obj, JSONObject answer){  // 6
        answer.put("type", 6);
        String recipient = (String) obj.get("recipient");
        String text = (String) obj.get("text");
        if(gameHandler.chatMessage(text,recipient,userName) == 1)
            answer.put("answer", "1");
        else answer.put("answer", "-1");
        sendAnswer(answer);
    }


}
