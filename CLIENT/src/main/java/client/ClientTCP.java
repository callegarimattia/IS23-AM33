package client;

import client.clientModel.ClientDataStructure;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import static java.lang.System.exit;


public class ClientTCP implements Client {
    private final ObjectOutputStream out;
    private final String ip = "127.0.0.1";   // saranno poi da prendere da arg / json
    private final int port = 2345;  // saranno poi da prendere da arg / json
    private ClientDataStructure data;
    private Socket socket;

    public ClientTCP(CLI cli) throws IOException {
        data = new ClientDataStructure();
        newConnection(ip, port);
        out = new ObjectOutputStream(socket.getOutputStream());
        Runnable parser = new TCPserverParser(socket, this, cli);
        Thread th = new Thread(parser);
        th.start();
    }


    //  Metodi chiamati dall' Utente:

    @Override
    public void newConnection(String serverIP, int port) {
        try {
            socket = new Socket(serverIP, port);
            System.out.println("Connection established");
        } catch (final IOException e) {
            System.out.print("server isn't online yet, closing application...");
            exit(-1);
        }
    }


    private void sendMessage(String message) {
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
        if (data.getMyUsername() != null)
            obj.put("toBeDeletedUser", data.getMyUsername());
        sendMessage(obj.toString());
    }

    @Override
    public void createUser(String userName) {  // 0
        JSONObject obj = new JSONObject();
        obj.put("type", 0);
        obj.put("userName", userName);
        sendMessage(obj.toString());
    }

    @Override
    public void lobbyListRequest() {  // 1
        JSONObject obj = new JSONObject();
        obj.put("type", 1);
        sendMessage(obj.toString());
    }

    @Override
    public void createLobby(int gameSize) {  // 2
        JSONObject obj = new JSONObject();
        obj.put("type", 2);
        obj.put("size", gameSize);
        sendMessage(obj.toString());
    }

    @Override
    public void joinLobby(int ID) {  // 3JSONObject obj = new JSONObject();
        JSONObject obj = new JSONObject();
        obj.put("type", 3);
        obj.put("tobeJoinedLobbyID", ID);
        sendMessage(obj.toString());
    }

    @Override
    public void leaveLobby() {  // 4
        JSONObject obj = new JSONObject();
        obj.put("type", 4);
        sendMessage(obj.toString());
    }

    @Override
    public void pickAndInsert(List<Integer> rows, List<Integer> columns, int myColumn) {  // 5
        JSONObject obj = new JSONObject();
        obj.put("type", 5);
        obj.put("columns", columns);
        obj.put("rows", rows);
        obj.put("myColumn", myColumn);
        sendMessage(obj.toString());
    }

    public void sendChatMessage(String text, String recipient){ // 6
        JSONObject obj = new JSONObject();
        obj.put("type", 6);
        obj.put("text", text);
        obj.put("recipient", recipient);
        sendMessage(obj.toString());
    }

    @Override
    public void setDisplayer(Thread th) {

    }


    @Override
    public void sendChatMessage(String userName, String message, int visibility) {

    }

    @Override
    public String getUserName() {
        return data.getMyUsername();
    }

    public void setUserName(String userName) {
        data.setMyUsername(userName);
    }

    public ClientDataStructure getData() {
        return data;
    }
}
