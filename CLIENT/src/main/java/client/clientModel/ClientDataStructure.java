package client.clientModel;

import common.Tile;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ClientDataStructure {
    private final Tile[][] mainBoard;
    private final List<ClientPlayer> players;
    private String commonGoal1;
    private String commonGoal2;
    private ClientPersonalGoal myGoal;
    private final StringProperty myUsername = new SimpleStringProperty();
    private Integer myLobbyID;
    private final ObservableList<Lobby> lobbies = FXCollections.observableArrayList();
    private final BooleanProperty gameStatus = new SimpleBooleanProperty(false);
    private boolean gui = false;

    public ClientDataStructure() {
        players = new ArrayList<>();
        mainBoard = new Tile[9][9];
        myGoal = null;
    }

    public void addPlayer(String username) {
        players.add(new ClientPlayer(username));
    }

    public List<ClientPlayer> getPlayers() {
        return players;
    }

    public void setMyGoal(int[] coordinates, Tile[] values) {
        this.myGoal = new ClientPersonalGoal(coordinates, values);
    }

    public ClientPersonalGoal getMyGoal() {
        return myGoal;
    }

    public Tile[][] getMainBoard() {
        return mainBoard;
    }

    public void setMainBoard(List<List<Long>> intMainBoard) {
        for (int i = 0; i < 9; i++) {
            List<Long> lis = intMainBoard.get(i);
            for (int j = 0; j < 9; j++) {
                int x = lis.get(j).intValue();
                switch (x) {
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
    }

    public Tile getAndSetEmpty(int row, int col) {
        Tile myTile = mainBoard[row][col];
        mainBoard[row][col] = Tile.EMPTY;
        return myTile;
    }

    private void printMainBoard() {
        System.out.println("\nMain board: ");
        for (int i = 0; i < 9; i++)
            System.out.printf("%11s ", (i + 1));
        System.out.println();
        for (int i = 0; i < 9; i++) {
            System.out.print((i + 1) + "    ");
            for (int j = 0; j < 9; j++)
                System.out.printf("%20s ", mainBoard[i][j].toString());
            System.out.println();
        }
    }

    public void refresh() {
        printMainBoard();
        System.out.println("\ncommon goal 1: "+commonGoal1);
        System.out.println("common goal 2: " + commonGoal2);
        myGoal.print();
        for (ClientPlayer player : players)
            player.refresh();
    }

    public String getCommonGoal1() {
        return commonGoal1;
    }

    public String getCommonGoal2() {
        return commonGoal2;
    }

    public void setCommonGoal1(String commonGoal1) {
        this.commonGoal1 = commonGoal1;
    }

    public void setCommonGoal2(String commonGoal2) {
        this.commonGoal2 = commonGoal2;
    }

    public ObservableList<Lobby> getLobbies() {
        return lobbies;
    }

    public void setLobbies(List<Lobby> lobbies) {
        if (gui) {
            Platform.runLater(this.lobbies::clear);
            Platform.runLater(() -> this.lobbies.addAll(lobbies));
        } else {
            this.lobbies.clear();
            this.lobbies.addAll(lobbies);
        }
    }

    public void setGui(boolean gui) {
        this.gui = gui;
    }

    ////////////  ANSWERS ////////////


    public void ansClientClosingApp(String answer, String disconnectedPlayer) {  //-1
        switch (answer){
            default:
                System.out.println("something went wrong");
                System.exit(0);
            case "1":
                System.out.println("connection closed");
                System.exit(0);
            case "2":
                System.out.println("player " + disconnectedPlayer + " disconnected --->  game ended :(");
                System.exit(0);
        }
    }

    public void ansCreateUser(List<String> ans) {   // 0
        switch (ans.get(0)) {
            case "0":
                System.out.println("username already taken, press 0 and enter a new one: ");
                break;
            case "1":
                setMyUsername(ans.get(1));
                System.out.println("userName " + myUsername.get() + " successfully set");
                break;
            case "-1":
                System.out.println("can't create a new user, this client already has an associated User");
                break;
            case "-2":
                System.out.println("invalid special username, press 0 and enter a new one: ");
                break;
        }
    }

    public void ansLobbyListRequest(String answer, List<Integer> data) {  // 1
        switch (answer){
            case "1":
                List<Integer> lobbiesIDs = new ArrayList<>();
                List<Integer> lobbiesCurrentSize = new ArrayList<>();
                List<Integer> lobbiesMaxSizes = new ArrayList<>();

                List<Lobby> refreshedLobbies = new ArrayList<>();

                for (int i = 1; i < data.size(); i=i+3)
                    refreshedLobbies.add(new Lobby((int) (long) lobbiesMaxSizes.get(i), (int) (long) lobbiesIDs.get(i), (int) (long) lobbiesCurrentSize.get(i)));
                setLobbies(refreshedLobbies);
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

    public void ansNewLobbyCreation(JSONObject obj) {   // 2
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


    public void ansJoinLobbyRequest(JSONObject obj) {  // 3
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

    public void ansLeaveLobbyRequest(JSONObject obj) {  // 4
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

    public void ansPickAndInsert(JSONObject obj) {  // 5
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

    public void ansChatMessage(JSONObject obj){  // 6
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

    public void startGame(JSONObject obj) {  // 777
        System.out.println("GAME STARTED");
        JSONArray array = (JSONArray) obj.get("playersUsernames");  //  already shuffled (first player at [0])
        for (int i = 0; i < array.size(); i++)
            addPlayer(array.get(i).toString());

        List<List<Long>> intMainBoard = (List<List<Long>>) obj.get("mainBoard");
        setMainBoard(intMainBoard);
        setCommonGoal1((String) obj.get("commonGoal1"));
        setCommonGoal2((String) obj.get("commonGoal2"));
        if (gui) Platform.runLater(() -> gameStatus.set(true));
    }

    public void personalStartGame(JSONObject obj) {  // 778
        List<Long> coordinatesList = (List<Long>) obj.get("coordinates");
        List<Long> intValues = (List<Long>) obj.get("values");
        Tile[] values = {Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY};
        for (int i = 0; i < 6; i++) {
            values[i] = values[i].toTile(intValues.get(i).intValue());
        }
        int[] coordinates = coordinatesList.stream().mapToInt(i -> Math.toIntExact(i)).toArray();
        setMyGoal(coordinates, values);
        refresh();
        System.out.println("\ncurrent player turn: " + players.get(0).getUserName());  // ogni volta ce lo dirà il server, non ce lo salviamo
        System.out.println("\nnew commands:\n-1: close app / abort game\n 5: pick and insert\n 6: send chat message");
    }

    public void onLobbyUpdate(JSONObject obj) {  //  99
        System.out.println("LOBBIES UPDATE RECIVED:");
        List<Long> lobbiesIDs = (List<Long>) obj.get("IDss");
        List<Long> lobbiesCurrentSize = (List<Long>) obj.get("CurrentSizess");
        List<Long> lobbiesMaxSizes = (List<Long>) obj.get("MaxSizes");
        for (int i = 0; i < lobbiesIDs.size(); i++)
            System.out.println("ID: " + lobbiesIDs.get(i) + " current size: " + lobbiesCurrentSize.get(i) + " max size: " + lobbiesMaxSizes.get(i));
        List<Lobby> refreshedLobbies = new ArrayList<>();
        for(int i = 0; i <lobbiesIDs.size(); i++)
            refreshedLobbies.add(new Lobby((int)(long)lobbiesMaxSizes.get(i),(int)(long)lobbiesIDs.get(i),(int)(long)lobbiesCurrentSize.get(i) ));
        setLobbies(refreshedLobbies);
    }

    public void onGameUpdate(JSONObject obj) {  //  100
        System.out.println("GAME UPDATE RECIVED:");
        List<Long> longCols = (List<Long>) obj.get("cols");
        List<Long> longRows = (List<Long>) obj.get("rows");
        int[] cols = longCols.stream().mapToInt(i -> Math.toIntExact(i)).toArray();
        int[] rows = longRows.stream().mapToInt(i -> Math.toIntExact(i)).toArray();
        String updater = (String) obj.get("updater");
        String newCurrPlayer = (String) obj.get("newCurrPlayer");
        int column = (int) (long) obj.get("column");

        for (int i = 0; i < cols.length; i++) {
            Tile myTile = getAndSetEmpty(rows[i], cols[i]);
            for (ClientPlayer player : players)
                if (player.getUserName().equals(updater))
                    player.addTile(myTile, column);
        }
        refresh();
        System.out.println("new current player is " + newCurrPlayer);
    }

    public void recivedChatMessage(JSONObject obj) {  // 101
        String addresser = (String) obj.get("addresser");
        String text = (String) obj.get("text");
        String recipient = (String) obj.get("recipient");
        System.out.print("chat message [" + addresser + "--> " + recipient + "]: ");
        System.out.println(text);
    }

    public boolean isGameStatus() {
        return gameStatus.get();
    }

    public BooleanProperty gameStatusProperty() {
        return gameStatus;
    }

    public String getMyUsername() {
        return this.myUsername.get();
    }

    public void setMyUsername(String username) {
        if (gui) Platform.runLater(() -> this.myUsername.set(username));
        else myUsername.set(username);
    }

    public StringProperty usernameProperty() {
        return this.myUsername;
    }
}
