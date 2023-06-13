package com.example.gui.client;
import com.example.gui.common.Tile;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.Socket;
import java.util.List;


public class TCPserverParser implements Runnable {
    private final Socket socket;
    private final ClientTCP clientTCP;  // sarebbe forse meglio definire un interfaccia apposita
    private Integer myLobbyID;
    public TCPserverParser(Socket socket, ClientTCP clientTCP) {
        this.socket = socket;
        this.clientTCP = clientTCP;
        myLobbyID = null;
    }



    // ascolta tutti i messaggi del server (anche le risposte ai metodi)
    @Override
    public void run() {


        JSONObject obj = new JSONObject();
        boolean bool = true;  // sara messo a false con messaggio speciale di fine partita o simili
        ObjectInputStream in;
        try {
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        while (bool) {

            String str = null;   //  mi da codice duplicato ma staranno poi su 2 eseguibili diversi
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
            switch ((int)x) {
                default:
                    System.out.println("default branch, invalid type message");
                    break;
                case -1:
                    ansClientClosingApp(obj);
                    break;
                case 0:
                    ansCreateUser(obj);
                    break;
                case 1:
                    ansLobbyListRequest(obj);
                    break;
                case 2:
                    ansNewLobbyCreation(obj);
                    break;
                case 3:
                    ansJoinLobbyRequest(obj);
                    break;
                case 4:
                    ansLeaveLobbyRequest(obj);
                    break;
                case 777:
                    startGame(obj);
                    break;
                case 6:

                    break;
                case 99:
                    System.out.println("LOBBIES UPDATE RECIVED:");
                    List<Integer> lobbiesIDs = (List<Integer>) obj.get("IDs");
                    List<Integer> lobbiesCurrentSize = (List<Integer>) obj.get("CurrentSizes");
                    List<Integer> lobbiesMaxSizes = (List<Integer>) obj.get("MaxSizes");
                    for(int i = 0; i < lobbiesIDs.size(); i++)
                        System.out.println("ID: "+lobbiesIDs.get(i)+" current size: "+lobbiesCurrentSize.get(i)+" max size: "+lobbiesMaxSizes.get(i));


                    break;
                case 8:
                    //  messaggio con punteggi e vincitori
                    //  bool = false;
                    //  mando messaggi finali
                    bool = false;
                    break;
                }
            }

        System.out.println("input socket closing");
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    private void ansClientClosingApp(JSONObject obj){  //-1
        if(obj.get("answer").toString().equals("1")){
            System.out.println("connection closed");
            System.exit(0);
        }
    }

    private void ansCreateUser(JSONObject obj){   // 0
        if(obj.get("answer").toString().equals("1")){
            String ss = (String) obj.get("userName");
            clientTCP.setUserName(ss);
            System.out.println("userName "+ss+" successfully set");
        }
        if(obj.get("answer").toString().equals("0")){
            System.out.println("userName already taken, press 0 and enter a new one: ");
        }
        if(obj.get("answer").toString().equals("-1")){
            System.out.println("can't create a new user, this client already has an associated User");
        }
    }

    private void ansLobbyListRequest(JSONObject obj){  // 1
        if(obj.get("answer").toString().equals("1")){
            List<Integer> lobbiesIDs = (List<Integer>) obj.get("IDs");
            List<Integer> lobbiesCurrentSize = (List<Integer>) obj.get("CurrentSizes");
            List<Integer> lobbiesMaxSizes = (List<Integer>) obj.get("MaxSizes");
            for(int i = 0; i < lobbiesIDs.size(); i++)
                System.out.println("ID: "+lobbiesIDs.get(i)+" current size: "+lobbiesCurrentSize.get(i)+" max size: "+lobbiesMaxSizes.get(i));
        }
        else if (obj.get("answer").toString().equals("0")){
            System.out.println("no lobbies yet");
        }
    }

    private void ansNewLobbyCreation(JSONObject obj){   // 2
        switch (obj.get("answer").toString()){
            case "0" :
                System.out.println("cant create lobby when in a game or in a lobby, press 2 again");
                break;
            case  "-1":
                System.out.println("cant create lobby without creating an user first");
                break;
            case "-2":
                System.out.println("invalid game size, press 2 again");
                break;
            case "1":
                myLobbyID = (int)(long) obj.get("ID");
                System.out.println("new lobby created (and joined), ID: "+myLobbyID);
                break;
        }
    }


    private void ansJoinLobbyRequest(JSONObject obj){  // 3
        switch (obj.get("answer").toString()){
            case "0" :
                System.out.println("user is already in a lobby");
                break;
            case  "-1":
                System.out.println("lobby doesn't exist, press 3 again: ");
                break;
            case  "-2":
                System.out.println("cant join lobby without creating an user first");
                break;
            case "1":
                myLobbyID = (int)(long) obj.get("ID");
                System.out.println("lobby successfully joined");
                break;
        }
    }

    private void ansLeaveLobbyRequest(JSONObject obj){  // 4
        switch (obj.get("answer").toString()){
            case "0" :
                System.out.println("error, user is not in a lobby");
                break;
            case  "-1":
                System.out.println("error, user is in an active game, cant leave lobby (shut down app if you want)");
                break;
            case "1":
                System.out.println("lobby successfully left");
                break;
        }
    }

    private void startGame(JSONObject obj){  // 777
        System.out.println("partita cominciata" );

        JSONArray array = (JSONArray) obj.get("playersUsernames");  //  already shuffled (first player at [0])
        for(int i=0; i < array.size(); i++)
            clientTCP.getData().addPlayer(array.get(i).toString());

        System.out.print("Players: ");
        for (int i = 0; i < array.size() ; i++)
            System.out.print(clientTCP.getData().getPlayers().get(i).getUserName() + " ");




        List<List<Long>> intMainBoard = (List<List<Long>>) obj.get("mainBoard");
        Tile[][] mainBoard = new Tile[9][9];

        for(int i = 0; i < 9; i++){
            List<Long> lis = intMainBoard.get(i);
            for (int j = 0; j<9; j++){
                int x = lis.get(j).intValue();
                switch (x){
                    case 0:
                        mainBoard[i][j] = Tile.EMPTY;
                        break;
                    case 1:
                        mainBoard[i][j] = Tile.UNAVAILABLE;
                        break;
                    case 2:
                        mainBoard[i][j] = Tile.BOOK;
                        break;
                    case 3:
                        mainBoard[i][j] = Tile.GAME;
                        break;
                    case 4:
                        mainBoard[i][j] = Tile.FRAME;
                        break;
                    case 5:
                        mainBoard[i][j] = Tile.PLANT;
                        break;
                    case 6:
                        mainBoard[i][j] = Tile.TROPHY;
                        break;
                    case 7:
                        mainBoard[i][j] = Tile.CAT;
                        break;
                }
            }

        }
        clientTCP.getData().setMainboard(mainBoard);

        System.out.println("\nMain board: ");
        for(int i = 0; i < 9; i++){
            for (int j = 0; j<9; j++)
                System.out.printf("%11s ", clientTCP.getData().getMainboard()[i][j]);
            System.out.println();
        }




        System.out.println("\nnew commands:\n-1: close app / abort game\n5: ask game refresh\n6: pick and insert" );
    }



}
