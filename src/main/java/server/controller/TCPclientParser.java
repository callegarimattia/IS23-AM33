package server.controller;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TCPclientParser implements Runnable {
    private final Socket mySocket;
    private final LobbiesHandler lobbiesHandler;
    private final GameHandler gameHandler = null;
    ObjectInputStream in = null;
    ObjectOutputStream out = null;
    private boolean inUser;
    private boolean inLobby;  // ricorda di rimettere a falso quando saremo in partita

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
        inLobby = false;
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
                case -1:  // client closing his app
                    if(inUser){
                        if(gameHandler != null){  // ovvero sono in game
                            gameHandler.abortGame();  // manda messaggio finale e chiude tutti i 4 (potenzialmente) thread parser
                            lobbiesHandler.removeLobby((String) obj.get("toBeDeletedUser"));
                            return; // termino questo thread
                        }
                        if(inLobby){
                            lobbiesHandler.removeUser((String) obj.get("toBeDeletedUser"));

                        }
                    }
                    answer.put("type", -1);
                    answer.put("answer", "1");
                    try {
                        out.writeObject(answer);
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                    try {  // chiudo socket
                        in.close();
                        out.close();
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println("connection with user "+(String) obj.get("toBeDeletedUser") +" closed");
                    return;
                case 0:  // 0   (NEW USER)
                    // da gestire il fatto che creo user e gli metto dentro il socket solo quando il nickname è valido (devo anche iterare sugli altri user per vedere se ce gia uno user con quel socket)
                    // gestiamo lato client il fatto che puo inviare certi comandi solo in certe situazioni
                    String newUserUsername = (String) obj.get("userName");
                    if(lobbiesHandler.createUser(newUserUsername)){
                        answer.put("type", 0);
                        answer.put("answer", "1");
                        answer.put("userName", newUserUsername);
                        lobbiesHandler.addTCPparserToUser(newUserUsername,this);
                        inUser = true;
                    }
                    else {
                        answer.put("type", 0);
                        answer.put("answer", "0");
                    }
                    try {
                        out.writeObject(answer);
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 1:  // 1
                    //  lobbiesHandler.joinLobby(params.get(1),(int)params.get(1) );
                    break;
                case 2:  // 2

                    break;
                case 3:   // disconnessine client  ---> chiusura partita
                  //  bool = false;
                    //  mando messaggi finali
                    isClientActive = false;
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
}
