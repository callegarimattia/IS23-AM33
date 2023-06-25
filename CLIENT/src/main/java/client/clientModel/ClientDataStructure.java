package client.clientModel;

import client.Lobby;
import common.Tile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class ClientDataStructure {
    private final Tile[][] mainBoard;
    private final List<ClientPlayer> players;
    private String commonGoal1;
    private String commonGoal2;
    private ClientPersonalGoal myGoal;
    private String myUsername;
    private ObservableList<Lobby> lobbies;

    public String getMyUsername() {
        return myUsername;
    }

    public ClientDataStructure() {
        players = new ArrayList<>();
        mainBoard = new Tile[9][9];
        myGoal = null;
    }

    public void setMyUsername(String myUsername) {
        this.myUsername = myUsername;
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
        this.lobbies = FXCollections.observableArrayList(lobbies);
    }
}
