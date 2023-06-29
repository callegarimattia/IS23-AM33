package server.controller;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import server.exceptions.InputException;
import server.exceptions.LastRoundException;
import server.model.Lobby;
import common.MainBoardCoordinates;
import server.model.User;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
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

            if(str == null){
                if(inUser)
                    lobbiesHandler.disconnectedUser(userName);
                try {
                    in.close();
                    out.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
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
                    listOfLobbiesRequest(answer);
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
        List<String> ans = new ArrayList<>();
        if(inUser){
            ans.add(0,"-1"); // socket already associated with a user
            answer.put("data", ans);
            sendAnswer(answer);
            return;
        }
        String newUserUsername = (String) obj.get("userName");
        ans = lobbiesHandler.createUser(newUserUsername, null, this);
        if(ans.get(0).equals("1")){
            inUser = true;
            userName = newUserUsername;
        }
        answer.put("data", ans);
        sendAnswer(answer);
    }


    public void listOfLobbiesRequest(JSONObject answer){  // 1
        answer.put("type", 1);
        List<Integer> data = lobbiesHandler.lobbyListRequest(null, this);
        answer.put("data", data);
        sendAnswer(answer);
    }

    private void newLobbyCreationRequest(JSONObject obj, JSONObject answer){ // 2
        answer.put("type", 2);
        List<Integer> ans = new ArrayList<>();
        if (obj.get("size") instanceof Long){

        } else {
            ans.add(0,-3);
            answer.put("datar", ans);  // invalid input
            sendAnswer(answer);
            return;
        }

        ans = lobbiesHandler.createLobby((int) (long) obj.get("size"),null, this );
        answer.put("data", ans);
        sendAnswer(answer);
    }

    private void joinLobbyRequest(JSONObject obj, JSONObject answer){  // 3
        answer.put("type", 3);
        int ID;
        if (obj.get("tobeJoinedLobbyID") instanceof Long) {
            ID = (int) obj.get("tobeJoinedLobbyID");
            try {
                answer.put("answer",lobbiesHandler.joinLobby(ID,null,this));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            answer.put("answer", 4);  // invalid input
            sendAnswer(answer);
            return;
        }
        answer.put("ID",ID);

        sendAnswer(answer);

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

            int myColumn = -1;
            List<Long> columns = null;
            List<Long> rows = null;
            if (obj.get("myColumn") instanceof Long)
                myColumn = (int) (long) obj.get("myColumn");
            if (obj.get("columns") instanceof List){
                try {
                    columns = (List<Long>) obj.get("columns");
                }catch (Exception e){
                    answer.put("answer","-8");  // invalid input
                    sendAnswer(answer);
                    return;
                }
            }

            if (obj.get("rows") instanceof List){
                try {
                    rows = (List<Long>) obj.get("rows");
                }catch (Exception e){
                    answer.put("answer","-8");  // invalid input
                    sendAnswer(answer);
                    return;
                }
            }

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
            answer.put("answer",Integer.toString(x));
        }
        sendAnswer(answer);
    }

    private void chatMessage(JSONObject obj, JSONObject answer){  // 6
        answer.put("type", 6);
        if(gameHandler == null){
            answer.put("answer", "0");  // non in ancora in game
            sendAnswer(answer);
            return;
        }
        String recipient = (String) obj.get("recipient");
        String text = (String) obj.get("text");
        if(gameHandler.chatMessage(text,recipient,userName) == 1)
            answer.put("answer", "1");
        else answer.put("answer", "-1");
        sendAnswer(answer);
    }


}
