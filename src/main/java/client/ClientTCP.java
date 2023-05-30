package client;

import client.clientModel.ClientDataStructure;
import org.json.simple.JSONObject;
import server.listenerStuff.GameUpdateEvent;
import server.listenerStuff.LobbiesUpdateEvent;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ClientTCP implements VirtualView, Client {
    private ClientDataStructure data;
    private Socket socket;
    private final ObjectOutputStream out;
    private final String ip = "127.0.0.1";   // saranno poi da prendere da arg / json
    private final int port = 2345;  // saranno poi da prendere da arg / json
    private final int gameStatus;
    private Scanner in;

    public ClientTCP() throws IOException {
        data = new ClientDataStructure();
        newConnection(ip,port);
        out = new ObjectOutputStream(socket.getOutputStream());
        Runnable parser = new TCPserverParser(socket, this);
        Thread th = new Thread(parser);
        th.start();
        gameStatus = 0;
        // ci sarà poi ciclo while nel main che chiama i metodi utente

    }


    //  Metodi chiamati dal Server:

    @Override
    public void GameUpdate(GameUpdateEvent evt) throws RemoteException {

    }

    @Override
    public void LobbiesUpdate(LobbiesUpdateEvent evt) throws RemoteException {

    }

    //  Metodi chiamati dall' Utente:

    @Override
    public void newConnection(String serverIP, int port) {
        try {
            socket = new Socket(serverIP, port);
            System.out.println("Connection established");
        } catch (final IOException e) {
            System.out.println(e.getMessage());
        }
    }



    private void sendMessage(String message){
        try {
            out.writeObject(message);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void shutDown() {  // -1
        JSONObject obj = new JSONObject();
        obj.put("type", -1);
        if(data.getMyUsername()!= null)
            obj.put("toBeDeletedUser", data.getMyUsername());
        sendMessage(obj.toString());
    }

    @Override
    public void createUser() {  // 0
        if(data.getMyUsername() == null){
            System.out.print("insert userName: ");
            Scanner in = new Scanner(System.in);
            String newUsername = in.next();
            JSONObject obj = new JSONObject();
            obj.put("type", 0);
            obj.put("userName", newUsername);
            sendMessage(obj.toString());
        }
        else {
            System.out.println("invalid command, username already setted");
        }
    }

    @Override
    public void lobbyListRequest() {  // 1
        if(data.getPlayers() == null || data.getPlayers().size() < 2){
            JSONObject obj = new JSONObject();
            obj.put("type", 1);
            sendMessage(obj.toString());
        }
        else {
            System.out.println("invalid command, game already started");
        }
    }

    @Override
    public void createLobby() {  // 2
        if(data.getPlayers() == null || data.getPlayers().size() < 2){
            Scanner in = new Scanner(System.in);
            System.out.print("insert game size (max 4) : ");
            String str = in.next();
            while (!str.matches("-?\\d+(\\.\\d+)?")){
                System.out.print("insert a valid integer please: ");
                str = in.next();
            }
            int gameSize = Integer.parseInt(str);
            JSONObject obj = new JSONObject();
            obj.put("type", 2);
            obj.put("size", gameSize);
            sendMessage(obj.toString());
        }
        else {
            System.out.println("invalid command, game already started");
        }
    }

    @Override
    public void joinLobby() {  // 3
        if(data.getPlayers() == null || data.getPlayers().size() < 2){
            Scanner in = new Scanner(System.in);
            System.out.print("insert to be joined lobby ID: ");
            String str = in.next();
            while (!str.matches("-?\\d+(\\.\\d+)?")){
                System.out.print("insert a valid integer please: ");
                str = in.next();
            }
            int ID = Integer.parseInt(str);
            JSONObject obj = new JSONObject();
            obj.put("type", 3);
            obj.put("tobeJoinedLobbyID", ID);
            sendMessage(obj.toString());
        }
        else {
            System.out.println("invalid command, game already started");
        }
    }

    @Override
    public void leaveLobby() {  // 4
        if(data.getPlayers() == null || data.getPlayers().size() < 2){
            JSONObject obj = new JSONObject();
            obj.put("type", 4);
            sendMessage(obj.toString());
        }
        else {
            System.out.println("invalid command, game already started");
        }
    }

    @Override
    public void pickAndInsert() {  // 5
        if(data.getPlayers() == null || data.getPlayers().size() < 2)
            System.out.println("invalid command, game didn't start yet");
        else {
            Scanner in = new Scanner(System.in);
            System.out.print("(-1 to cancel method call) \ninsert num of tiles you want to pick from the mainBoard : ");
            String str = in.next();
            while (!str.matches("-?\\d+(\\.\\d+)?")){
                System.out.print("insert a valid integer please: ");
                str = in.next();
            }
            int numOfTiles = Integer.parseInt(str);
            if(numOfTiles == -1){
                System.out.println("method cancelled");
                return;
            }

            List<Integer> columns = new ArrayList<>();
            List<Integer> rows = new ArrayList<>();
            for(int i = 0; i < numOfTiles; i++){
                System.out.print("insert column of the " + (i+1) + "° tile  (starts from 1) : ");
                str = in.next();
                while (!str.matches("-?\\d+(\\.\\d+)?")){
                    System.out.print("insert a valid integer please: ");
                    str = in.next();
                }
                columns.add(Integer.parseInt(str) - 1);
                if(columns.get(i) + 1 == -1){
                    System.out.println("method cancelled");
                    return;
                }

                System.out.print("insert row of the " + (i+1) + "° tile  (starts from 1) : ");
                str = in.next();
                while (!str.matches("-?\\d+(\\.\\d+)?")){
                    System.out.print("insert a valid integer please: ");
                    str = in.next();
                }
                rows.add(Integer.parseInt(str) - 1);
                if(rows.get(i) + 1 == -1){
                    System.out.println("method cancelled");
                    return;
                }
            }

            System.out.print("insert the column of your Shelf where you want to place the tile/s (starts from 1): ");
            str = in.next();
            while (!str.matches("-?\\d+(\\.\\d+)?")){
                System.out.print("insert a valid integer please: ");
                str = in.next();
            }
            int myColumn = Integer.parseInt(str) -1 ;

            JSONObject obj = new JSONObject();
            obj.put("type", 5);
            obj.put("columns", columns);
            obj.put("rows", rows);
            obj.put("myColumn", myColumn);
            sendMessage(obj.toString());
        }
    }



    @Override
    public void sendChatMessage(String userName, String message, int visibility) {

    }

    @Override
    public String getUserName() {
        return data.getMyUsername();
    }

    public void setUserName(String userName) {
        data.ClientDataStructure(userName);
    }

    public ClientDataStructure getData() {
        return data;
    }
}
