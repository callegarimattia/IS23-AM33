package client;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
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
                str =  in.readObject().toString();

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
                case -1: // answer to client closing his app
                    if(obj.get("answer").toString().equals("1")){
                        System.out.println("connection closed");
                        // dovrei killare l intera app poi
                        return;
                    }

                    break;
                case 0:   //  risposta a createUser username
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
                    break;
                case 1: // lobby list request answer
                    if(obj.get("answer").toString().equals("1")){
                        List<Integer> lobbiesIDs = (List<Integer>) obj.get("IDs");
                        List<Integer> lobbiesCurrentSize = (List<Integer>) obj.get("CurrentSizes");
                        List<Integer> lobbiesMaxSizes = (List<Integer>) obj.get("lobbiesMaxSizes");
                        for(int i = 0; i < lobbiesIDs.size(); i++)
                            System.out.println("ID: "+lobbiesIDs.get(i)+" current size: "+lobbiesCurrentSize.get(i)+" max size: "+lobbiesMaxSizes.get(i));

                    }
                    else if (obj.get("answer").toString().equals("0")){
                        System.out.println("no lobbies yet");
                    }
                    break;
                case 2:  // answer to new lobby creation request
                    switch (obj.get("answer").toString()){
                        case "0" :
                            System.out.println("cant create lobby when in a game or in a lobby, press 2 again");
                            break;
                        case  "-1":
                            System.out.println("cant create lobby without creating an user first, press 2 again");
                            break;
                        case "-2":
                            System.out.println("invalid game size, press 2 again");
                            break;
                        case "1":
                            myLobbyID = (int)(long) obj.get("ID");
                            System.out.println("new lobby created (and joined), ID: "+myLobbyID);
                            break;
                    }
                    break;
                case 99:
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



}
