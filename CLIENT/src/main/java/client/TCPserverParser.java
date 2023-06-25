package client;

import client.clientModel.ClientPlayer;
import common.Tile;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;


public class TCPserverParser implements Runnable {
    private Displayer displayer;
    private final Socket socket;
    private final ClientTCP clientTCP;  // devo poi usare l interfaccia ma per ora ho i metodi solo su TCP
    private Integer myLobbyID;

    public TCPserverParser(Socket socket, ClientTCP clientTCP, Displayer displayer) {
        this.socket = socket;
        this.clientTCP = clientTCP;
        this.displayer = displayer;
        myLobbyID = null;
    }


    @Override
    public void run() {
        JSONObject obj;
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
            } catch (Exception e) {
                System.out.println("server went offline, press any key to close app");
                displayer.shutDown();
                break;
            }

            long x = (long) obj.get("type");
            switch ((int) x) {
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
                case 5:
                    ansPickAndInsert(obj);
                    break;
                case 6:
                    ansChatMessage(obj);
                    break;

                case 777:
                    startGame(obj);
                    break;
                case 778:
                    personalStartGame(obj);
                    break;
                case 99:
                    onLobbyUpdate(obj);
                    break;
                case 100:
                    onGameUpdate(obj);
                    break;
                case 101:
                    recivedChatMessage(obj);
                    break;
                case 999:
                    //  messaggio con punteggi e vincitori
                    //  bool = false;
                    //  mando messaggi finali
                    bool = false;
                    break;
            }
        }
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    private void ansClientClosingApp(JSONObject obj) {  //-1
        if (obj.get("answer").toString().equals("1")) {
            System.out.println("connection closed");
            System.exit(0);
        }
        if (obj.get("answer").toString().equals("2")) {
            System.out.println("player " + obj.get("disconnectedPlayer") + " disconnected --->  game ended :(");
            System.exit(0);
        }
    }
    private void ansCreateUser(JSONObject obj) {   // 0
        if (obj.get("answer").toString().equals("1")) {
            String ss = (String) obj.get("userName");
            clientTCP.setUserName(ss);
            System.out.println("userName " + ss + " successfully set");
        }
        switch (obj.get("answer").toString()){
            case "0":
                System.out.println("userName already taken, press 0 and enter a new one: ");
                break;
            case "-1":
                System.out.println("can't create a new user, this client already has an associated User");
                break;
            case "-2":
                System.out.println("invalid special username, press 0 and enter a new one: ");
                break;
        }
    }

    private void ansLobbyListRequest(JSONObject obj) {  // 1
        switch (obj.get("answer").toString()){
            case "1":
                List<Integer> lobbiesIDs = (List<Integer>) obj.get("IDs");
                List<Integer> lobbiesCurrentSize = (List<Integer>) obj.get("CurrentSizes");
                List<Integer> lobbiesMaxSizes = (List<Integer>) obj.get("MaxSizes");
                for (int i = 0; i < lobbiesIDs.size(); i++)
                    System.out.println("ID: " + lobbiesIDs.get(i) + " current size: " + lobbiesCurrentSize.get(i) + " max size: " + lobbiesMaxSizes.get(i));
                break;
            case "0":
                System.out.println("no lobbies yet");
                break;
            case "-1":
                System.out.println("cant ask lobbies list if game already started");
                break;
        }
    }

    private void ansNewLobbyCreation(JSONObject obj) {   // 2
        switch (obj.get("answer").toString()) {
            case "0":
                System.out.println("cant create lobby when in a game or in a lobby, press 2 again");
                break;
            case "-1":
                System.out.println("cant create lobby without creating an user first");
                break;
            case "-2":
                System.out.println("invalid game size, press 2 again");
                break;
            case "-3":
                System.out.println("invalid input, press 2 again");
                break;
            case "1":
                myLobbyID = (int) (long) obj.get("ID");
                System.out.println("new lobby created (and joined), ID: " + myLobbyID);
                break;
        }
    }


    private void ansJoinLobbyRequest(JSONObject obj) {  // 3
        switch (obj.get("answer").toString()) {
            case "0":
                System.out.println("invalid command, user is already in a lobby");
                break;
            case "-1":
                System.out.println("lobby doesn't exist, press 3 again: ");
                break;
            case "-2":
                System.out.println("cant join lobby without creating an user first");
                break;
            case "-3":
                System.out.println("lobby is full / already started, try again");
                break;
            case "-4":
                System.out.println("invalid input, press 3 again");
                break;
            case "1":
                myLobbyID = (int) (long) obj.get("ID");
                System.out.println("lobby successfully joined");
                break;
        }
    }

    private void ansLeaveLobbyRequest(JSONObject obj) {  // 4
        switch (obj.get("answer").toString()) {
            case "0":
                System.out.println("error, user is not in a lobby");
                break;
            case "-1":
                System.out.println("error, user is in an active game, cant leave lobby (shut down app if you want)");
                break;
            case "1":
                System.out.println("lobby successfully left");
                break;
        }
    }

    private void ansPickAndInsert(JSONObject obj) {  // 5
        switch (obj.get("answer").toString()) {
            case "0":
                System.out.println("not in game");
                break;
            case "-1":
                System.out.println("you are not the current player :(");
                break;
            case "1":
                System.out.println("pick success");
                break;
            case "-2":
                System.out.println("coordinates size error, try again");
                break;
            case "-3":
                System.out.println("you are not the current player :(((");
                break;
            case "-4":
                System.out.println("can't place in that column, try again");
                break;
            case "-5":
                System.out.println("invalid pick, try again");
                break;
            case "-6":
                System.out.println("cant insert there, try again");
                break;
            case "-7":
                System.out.println("coordinates out of bound index, try again");
                break;
        }
    }

    private void ansChatMessage(JSONObject obj){  // 6
        switch (obj.get("answer").toString()) {
            case "-1":
                System.out.println("no such recipient found, try again");
                break;
            case "0":
                System.out.println("cant chat while not in game");
                break;
            case "1":
                System.out.println("chat message correctly sent");
                break;
        }
    }

    private void startGame(JSONObject obj) {  // 777
        System.out.println("GAME STARTED");

        JSONArray array = (JSONArray) obj.get("playersUsernames");  //  already shuffled (first player at [0])
        for (int i = 0; i < array.size(); i++)
            clientTCP.getData().addPlayer(array.get(i).toString());

        List<List<Long>> intMainBoard = (List<List<Long>>) obj.get("mainBoard");
        clientTCP.getData().setMainBoard(intMainBoard);
        clientTCP.getData().setCommonGoal1((String) obj.get("commonGoal1"));
        clientTCP.getData().setCommonGoal2((String) obj.get("commonGoal2"));

    }

    private void personalStartGame(JSONObject obj) {  // 778
        List<Long> coordinatesList = (List<Long>) obj.get("coordinates");
        List<Long> intValues = (List<Long>) obj.get("values");
        Tile[] values = {Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY};
        for (int i = 0; i < 6; i++) {
            values[i] = values[i].toTile(intValues.get(i).intValue());
        }
        int[] coordinates = coordinatesList.stream().mapToInt(i -> Math.toIntExact(i)).toArray();
        clientTCP.getData().setMyGoal(coordinates, values);
        clientTCP.getData().refresh();
        System.out.println("\ncurrent player turn: " + clientTCP.getData().getPlayers().get(0).getUserName());  // ogni volta ce lo dirà il server, non ce lo salviamo
        System.out.println("\nnew commands:\n-1: close app / abort game\n 5: pick and insert\n 6: send chat message");
    }





    private void onLobbyUpdate(JSONObject obj) {  //  99
        System.out.println("LOBBIES UPDATE RECIVED:");
        List<Integer> lobbiesIDs = (List<Integer>) obj.get("IDss");
        List<Integer> lobbiesCurrentSize = (List<Integer>) obj.get("CurrentSizess");
        List<Integer> lobbiesMaxSizes = (List<Integer>) obj.get("MaxSizes");
        for (int i = 0; i < lobbiesIDs.size(); i++)
            System.out.println("ID: " + lobbiesIDs.get(i) + " current size: " + lobbiesCurrentSize.get(i) + " max size: " + lobbiesMaxSizes.get(i));
    }

    private void onGameUpdate(JSONObject obj) {  //  100
        System.out.println("GAME UPDATE RECIVED:");
        List<Long> longCols = (List<Long>) obj.get("cols");
        List<Long> longRows = (List<Long>) obj.get("rows");
        int[] cols = longCols.stream().mapToInt(i -> Math.toIntExact(i)).toArray();
        int[] rows = longRows.stream().mapToInt(i -> Math.toIntExact(i)).toArray();
        String updater = (String) obj.get("updater");
        String newCurrPlayer = (String) obj.get("newCurrPlayer");
        int column = (int) (long) obj.get("column");

        for (int i = 0; i < cols.length; i++) {
            Tile myTile = clientTCP.getData().getAndSetEmpty(rows[i], cols[i]);
            for (ClientPlayer player : clientTCP.getData().getPlayers())
                if (player.getUserName().equals(updater))
                    player.addTile(myTile, column);
        }

        clientTCP.getData().refresh();
        System.out.println("new current player is " + newCurrPlayer);

    }

    private void recivedChatMessage(JSONObject obj){  // 101
        String addresser = (String) obj.get("addresser");
        String text = (String) obj.get("text");
        String recipient = (String) obj.get("recipient");
        System.out.print("chat message ["+addresser + "--> "+ recipient + "]: ");
        System.out.println(text);
    }

}
