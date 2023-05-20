package server.controller;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class TCPclientParser implements Runnable {
    private final Socket mySocket;
    private final LobbiesHandler lobbiesHandler;
    ObjectInputStream in = null;
    ObjectOutputStream out = null;

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
                default:  // 0   da gestire il fatto che creo user e gli metto dentro il socket solo quando il nickname è valido (devo anche iterare sugli altri user per vedere se ce gia uno user con quel socket)
                    String stt = (String) obj.get("userName");
                    if(lobbiesHandler.createUser(stt)){
                        answer.put("type", 0);
                        answer.put("answer", 1);
                    }
                    else {
                        answer.put("type", 0);
                        answer.put("answer", 0);
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
