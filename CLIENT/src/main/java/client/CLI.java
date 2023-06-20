package client;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CLI implements Runnable {

    Client client;

    public CLI(Client client) {
        this.client = client;
    }

    @Override
    public void run() {
        Scanner in = new Scanner(System.in);
        boolean inGame = false;
        System.out.println("List of commands: \n-1: exit game\n0: create user\n1: ask list of lobbies\n2: new lobby\n3: join lobby\n4: leave lobby");
        while (!inGame) {
            String x = in.next();
            switch (x) {
                default:
                    System.out.println("invalid command, try again");
                    break;
                case "-1":
                    shutDown();
                    break;
                case "0":
                    createUser();
                    break;
                case "1":
                    lobbyListRequest();
                    break;
                case "2":
                    createLobby();
                    break;
                case "3":
                    joinLobby();
                    break;
                case "4":
                    leaveLobby();
                    break;
                case "5":
                    pickAndInsert();
                    break;
                case "6":
                    chatMessage();
                    break;
            }
        }
    }

    private void shutDown() {  // -1
        client.shutDown();
    }

    private void createUser() {  // 0
        if (client.getData().getMyUsername() == null) {
            System.out.print("insert userName: ");
            Scanner in = new Scanner(System.in);
            String newUsername = in.next();
            client.createUser(newUsername);
        } else {
            System.out.println("invalid command, username already setted");
        }
    }

    private void lobbyListRequest() {  // 1
        if (client.getData().getPlayers() == null || client.getData().getPlayers().size() < 2) {
            client.lobbyListRequest();
        } else {
            System.out.println("invalid command, game already started");
        }
    }

    private void createLobby() {  // 2
        if (client.getData().getPlayers() == null || client.getData().getPlayers().size() < 2) {
            Scanner in = new Scanner(System.in);
            System.out.print("insert game size (max 4) : ");
            String str = in.next();
            while (!str.matches("-?\\d+(\\.\\d+)?")) {
                System.out.print("insert a valid integer please: ");
                str = in.next();
            }
            int gameSize = Integer.parseInt(str);
            client.createLobby(gameSize);
        } else {
            System.out.println("invalid command, game already started");
        }
    }

    private void joinLobby() {  // 3
        if (client.getData().getPlayers() == null || client.getData().getPlayers().size() < 2) {
            Scanner in = new Scanner(System.in);
            System.out.print("insert to be joined lobby ID: ");
            String str = in.next();
            while (!str.matches("-?\\d+(\\.\\d+)?")) {
                System.out.print("insert a valid integer please: ");
                str = in.next();
            }
            int ID = Integer.parseInt(str);
            client.joinLobby(ID);
        } else {
            System.out.println("invalid command, game already started");
        }
    }

    private void leaveLobby() {  // 4
        if (client.getData().getPlayers() == null || client.getData().getPlayers().size() < 2)
            client.leaveLobby();
        else {
            System.out.println("invalid command, game already started");
        }
    }

    private void pickAndInsert() {  // 5
        if (client.getData().getPlayers() == null || client.getData().getPlayers().size() < 2)
            System.out.println("invalid command, game didn't start yet");
        else {
            Scanner in = new Scanner(System.in);
            System.out.print("(-1 to cancel method call) \ninsert num of tiles you want to pick from the mainBoard : ");
            String str = in.next();
            while (!str.matches("-?\\d+(\\.\\d+)?")) {
                System.out.print("insert a valid integer please: ");
                str = in.next();
            }
            int numOfTiles = Integer.parseInt(str);
            if (numOfTiles == -1) {
                System.out.println("method cancelled");
                return;
            }

            List<Integer> columns = new ArrayList<>();
            List<Integer> rows = new ArrayList<>();
            for (int i = 0; i < numOfTiles; i++) {
                System.out.print("insert column of the " + (i + 1) + "° tile  (starts from 1) : ");
                str = in.next();
                while (!str.matches("-?\\d+(\\.\\d+)?")) {
                    System.out.print("insert a valid integer please: ");
                    str = in.next();
                }
                columns.add(Integer.parseInt(str) - 1);
                if (columns.get(i) + 1 == -1) {
                    System.out.println("method cancelled");
                    return;
                }

                System.out.print("insert row of the " + (i + 1) + "° tile  (starts from 1) : ");
                str = in.next();
                while (!str.matches("-?\\d+(\\.\\d+)?")) {
                    System.out.print("insert a valid integer please: ");
                    str = in.next();
                }
                rows.add(Integer.parseInt(str) - 1);
                if (rows.get(i) + 1 == -1) {
                    System.out.println("method cancelled");
                    return;
                }
            }

            System.out.print("insert the column of your Shelf where you want to place the tile/s (starts from 1): ");
            str = in.next();
            while (!str.matches("-?\\d+(\\.\\d+)?")) {
                System.out.print("insert a valid integer please: ");
                str = in.next();
            }
            int myColumn = Integer.parseInt(str) - 1;
            client.pickAndInsert(columns, rows, myColumn);

        }
    }

    private void chatMessage() {  // 6
        if (client.getData().getPlayers() == null || client.getData().getPlayers().size() < 2)
            System.out.println("invalid command, game didn't start yet");
        else {
            System.out.print("enter recipient username (\"all\" for broadcast) :");
            Scanner in = new Scanner(System.in);
            String recipient = in.next();
            System.out.print("insert text :");
            String text = in.next();
            client.sendChatMessage(text, recipient);
        }
    }


}
