package client;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CLI implements Runnable {

    Client client;
    private boolean inGame;
    public CLI() {
        createClient();
        inGame = true;
    }

    private void createClient(){
        Scanner in = new Scanner(System.in);

        System.out.println("Usage: T (or R) IPserver (T = TCP, R = RMI)");
        String str = in.next();

        while (!(str.equals("T") || str.equals("t") || str.equals("R") || str.equals("r"))) {
            System.out.print("invalid input, try again: ");
            str = in.next();
        }

        if (str.equals("T") || str.equals("t")) {
            try {
                client = new ClientTCP(this);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            try {
                client = new ClientRMI();
            } catch (RemoteException e) {
                System.out.println(e.getMessage());
            }
            client.newConnection("localhost", 1099);  // dovranno essere presi da arg/json/CL
        }
    }

    public void run() {
        Scanner in = new Scanner(System.in);
        System.out.println("List of commands: \n-1: exit game\n0: create user\n1: ask list of lobbies\n2: new lobby\n3: join lobby\n4: leave lobby");
        while (true) { // verra fermato dal parser che ha il riferimento
            String x = in.next();
            if(!inGame){
                System.out.println("shutting down...");
                return;
            }

            switch (x) {
                default:
                    System.out.println("invalid command, try again");
                    break;
                case "-1":
                    shutDownClient();
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

    public void shutDownClient() {  // -1
        client.shutDown();
    }

    private void createUser() {  // 0
            System.out.print("insert userName: ");
            Scanner in = new Scanner(System.in);
            String newUsername = in.next();
            client.createUser(newUsername);
    }

    private void lobbyListRequest() {  // 1
        client.lobbyListRequest();
    }

    private void createLobby() {  // 2
        Scanner in = new Scanner(System.in);
        System.out.print("insert game size (max 4) : ");
        String str = in.next();
        while (!str.matches("-?\\d+(\\.\\d+)?")) {
            System.out.print("insert a valid integer please: ");
            str = in.next();
        }
        int gameSize = Integer.parseInt(str);
        client.createLobby(gameSize);
    }

    private void joinLobby() {  // 3
            Scanner in = new Scanner(System.in);
            System.out.print("insert to be joined lobby ID: ");
            String str = in.next();
            while (!str.matches("-?\\d+(\\.\\d+)?")) {
                System.out.print("insert a valid integer please: ");
                str = in.next();
            }
            int ID = Integer.parseInt(str);
            client.joinLobby(ID);
    }

    private void leaveLobby() {  // 4
        client.leaveLobby();
    }

    private void pickAndInsert() {  // 5
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
        client.pickAndInsert(rows, columns, myColumn);
    }

    private void chatMessage() {  // 6
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");
        if (client.getData().getPlayers() == null || client.getData().getPlayers().size() < 2)
            System.out.println("invalid command, game didn't start yet");
        else {
            System.out.print("enter recipient username (\"all\" for broadcast) :");
            Scanner in = new Scanner(System.in);
            String recipient = in.next();
            System.out.print("insert text :");
            String text = scanner.next();
            client.sendChatMessage(text, recipient);
        }
    }


    public void shutDown() {
        inGame = false;
    }
}
