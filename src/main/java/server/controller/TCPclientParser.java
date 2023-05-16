package server.controller;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class TCPclientParser implements Runnable {
    private Socket mySocket = null;
    private LobbiesHandler lobbiesHandler;
    ObjectInputStream in = null;
    ObjectOutputStream out = null;

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
        boolean bool = true;

        while (bool) {

            String str = null;
            try {
                str = (String) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println(e.getMessage());
            }

            JSONParser parser = new JSONParser();
            try {
                obj = (JSONObject) parser.parse(str);
            } catch (ParseException e) {
                System.out.println(e.getMessage());
            }

            int x = obj.getInt("type");
            JSONObject answer = new JSONObject();
            switch (x) {
                default:  // 0   da gestire il fatto che creo user e gli metto dentro il socket solo quando il nickname è valido (devo anche iterare sugli altri user per vedere se ce gia uno user con quel socket)
                    if(lobbiesHandler.createUser(obj.getString("userName")))
                        answer.put("answer", 1);
                    else answer.put("answer", 0);
                    try {
                        out.writeObject(answer);
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                case 1:  // 1
                    //  lobbiesHandler.joinLobby(params.get(1),(int)params.get(1) );
                case 2:  // 2
                    ;

                case 3:   // disconnessine client  ---> chiusura partita
                  //  bool = false;
                    //  mando messaggi finali
                    break;
            }

        }

        System.out.println("Closing sockets");
        try {
            in.close();
            out.close();
            mySocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
