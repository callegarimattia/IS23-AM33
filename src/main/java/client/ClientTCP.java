package client;
import org.json.simple.JSONObject;
import server.listenerStuff.GameUpdateEvent;
import server.listenerStuff.LobbiesUpdateEvent;
import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;



public class ClientTCP implements VirtualView, Client {
    private ClientDataStructure data;
    private Socket socket;
    private final ObjectOutputStream out;
    private final String ip = "127.0.0.1";   // saranno poi da prendere da arg / json
    private final int port = 2345;  // saranno poi da prendere da arg / json
    private String userName = null;

    public ClientTCP() throws IOException {
        newConnection(ip,port);
        out = new ObjectOutputStream(socket.getOutputStream());
        Runnable parser = new TCPserverParser(socket, this);
        Thread th = new Thread(parser);
        th.start();

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

    @Override
    public void createUser(String newUsername) {  // 0
        JSONObject obj = new JSONObject();
        obj.put("type", 0);
        obj.put("userName", newUsername);
        String message = obj.toString();
        try {
            out.writeObject(message);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Data Sent ...");

    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public void joinLobby(int lobbyID) {  //  1

    }

    @Override
    public void leaveLobby() {

    }

    @Override
    public void createLobby(int gameSize) {

    }

    @Override
    public void sendChatMessage(String userName, String message, int visibility) {

    }

    @Override
    public String getUserName() {
        return userName;
    }
}
